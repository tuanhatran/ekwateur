package com.ekwateur.invoice.service;

import com.ekwateur.invoice.configuration.PriceProperties;
import com.ekwateur.invoice.exception.ClientNotFoundException;
import com.ekwateur.invoice.exception.ConsumptionDetailInvalidException;
import com.ekwateur.invoice.model.Client;
import com.ekwateur.invoice.model.Invoice;
import com.ekwateur.invoice.repository.ClientRepository;
import com.ekwateur.invoice.repository.InvoiceRepository;
import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    private final ClientRepository clientRepository;

    private final PriceProperties priceProperties;

    public List<Invoice> getAllInvoicesForClient(String reference) {
        return invoiceRepository.findByClientReference(reference);
    }

    public Invoice calculateInvoiceFor(String reference, ConsumptionDetailInvoiceCalculationRequest request) {
        Optional<Client> clientOpt = clientRepository.findByReference(reference);
        if (clientOpt.isEmpty()) {
            String message = "Client with reference: " + reference + " NOT FOUND";
            log.error(message);
            throw new ClientNotFoundException(message);
        }

        Optional<Invoice> lastInvoice = invoiceRepository.findFirstByClientReferenceOrderByInvoiceDateDesc(reference);

        if (lastInvoice.isPresent()){
            if (lastInvoice.get().isConsumptionDetailValid(lastInvoice.get(), request)) {
                Invoice invoice = clientOpt.get().calculateInvoiceFrom(clientOpt.get(), request, priceProperties);
                invoiceRepository.save(invoice);
                return invoice;
            } else {
                String message = "Consumption detail is not valid";
                log.error(message);
                throw new ConsumptionDetailInvalidException(message);
            }
        } else {
            Invoice invoice = clientOpt.get().calculateInvoiceFrom(clientOpt.get(), request, priceProperties);
            log.info("Invoice calculate successfully for client {} with request {}", reference, request);
            invoiceRepository.save(invoice);
            return invoice;
        }
    }
}
