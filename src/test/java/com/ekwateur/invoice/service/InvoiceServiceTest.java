package com.ekwateur.invoice.service;

import com.ekwateur.invoice.configuration.PriceProperties;
import com.ekwateur.invoice.configuration.UnityPrice;
import com.ekwateur.invoice.exception.ClientNotFoundException;
import com.ekwateur.invoice.exception.ConsumptionDetailInvalidException;
import com.ekwateur.invoice.exception.InvoiceNotFoundException;
import com.ekwateur.invoice.model.Client;
import com.ekwateur.invoice.model.IndividualClient;
import com.ekwateur.invoice.model.Invoice;
import com.ekwateur.invoice.model.ProfessionalClient;
import com.ekwateur.invoice.repository.ClientRepository;
import com.ekwateur.invoice.repository.InvoiceRepository;
import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Tag("Unit")
@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @InjectMocks
    private InvoiceService service;

    private InvoiceRepository invoiceRepository;

    private ClientRepository clientRepository;

    private PriceProperties priceProperties;

    private ConsumptionDetailInvoiceCalculationRequest request;

    @BeforeEach
    void setUp() {
        request = ConsumptionDetailInvoiceCalculationRequest.builder()
                .electricityBeginIndex(168)
                .electricityEndIndex(268)
                .gasBeginIndex(200)
                .gasEndIndex(300)
                .build();

        priceProperties = new PriceProperties();
        priceProperties.setIndividual(UnityPrice.builder().gas(0.115F).electricity(0.121F).build());
        PriceProperties.Professional professional = new PriceProperties.Professional();
        professional.setBigRevenue(UnityPrice.builder().gas(0.111F).electricity(0.114F).build());
        professional.setSmallRevenue(UnityPrice.builder().gas(0.113F).electricity(0.118F).build());
        priceProperties.setProfessional(professional);

        invoiceRepository = Mockito.mock(InvoiceRepository.class);
        clientRepository = Mockito.mock(ClientRepository.class);
        service = new InvoiceService(invoiceRepository, clientRepository, priceProperties);
    }

    @Test
    void getAllInvoicesForClient_ClientWithRefNotFound() {
        Mockito.when(clientRepository.findByReference("Reference")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ClientNotFoundException.class, () -> service.getAllInvoicesForClient("Reference"));

        Assertions.assertEquals(exception.getMessage(), "Client with reference: Reference NOT FOUND");

        Mockito.verify(invoiceRepository, Mockito.times(0)).findByClientReference(anyString());
    }

    @Test
    void getAllInvoicesForClient_NoInvoiceForClient() {
        IndividualClient client = IndividualClient.builder()
                .build();
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.of(client));
        List<Invoice> emptyInvoice = new ArrayList<>();
        Mockito.when(invoiceRepository.findByClientReference("EKW00000001")).thenReturn(emptyInvoice);

        Exception exception = assertThrows(InvoiceNotFoundException.class, () -> service.getAllInvoicesForClient("EKW00000001"));

        Assertions.assertEquals(exception.getMessage(), "No invoices for client with reference: EKW00000001");
        Mockito.verify(invoiceRepository, Mockito.times(1)).findByClientReference(anyString());
    }

    @Test
    void getAllInvoicesForClient_ClientHasInvoice() {
        IndividualClient client = IndividualClient.builder()
                .build();
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.of(client));
        Invoice anInvoice = Invoice.builder()
                .client(client)
                .gasTotalAmount(10f)
                .electricityTotalAmount(15f)
                .build();
        List<Invoice> expectedInvoices = List.of(anInvoice);
        Mockito.when(invoiceRepository.findByClientReference("EKW00000001")).thenReturn(expectedInvoices);

        List<Invoice> result = service.getAllInvoicesForClient("EKW00000001");

        Assertions.assertEquals(expectedInvoices.size(), result.size());
        Assertions.assertEquals(anInvoice, result.get(0));
        Assertions.assertEquals(anInvoice.getGasTotalAmount(), result.get(0).getGasTotalAmount());
        Assertions.assertEquals(anInvoice.getElectricityTotalAmount(), result.get(0).getElectricityTotalAmount());
    }

    @Test
    void calculateInvoice_KO_Exception_ClientNotFound() {
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ClientNotFoundException.class, () -> service.calculateInvoiceFor("EKW00000001", ConsumptionDetailInvoiceCalculationRequest.builder().build()));

        Assertions.assertEquals(exception.getMessage(), "Client with reference: EKW00000001 NOT FOUND");
    }

    @Test
    void calculateInvoice_OK_CalculateFirstInvoice_IndividualClient() {
        IndividualClient client = IndividualClient.builder()
                .civility("M.")
                .firstName("Dupont")
                .lastName("DU PONT")
                .build();
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.of(client));
        Mockito.when(invoiceRepository.findFirstByClientReferenceOrderByInvoiceDateDesc("EKW00000001")).thenReturn(Optional.empty());

        Invoice result = service.calculateInvoiceFor("EKW00000001", request);

        Mockito.verify(invoiceRepository, Mockito.times(1)).save(any(Invoice.class));
        Assertions.assertEquals(client.getFirstName(), ((IndividualClient) result.getClient()).getFirstName());
        Assertions.assertEquals(client.getLastName(), ((IndividualClient) result.getClient()).getLastName());
        Assertions.assertEquals(11.5F, result.getGasTotalAmount());
        Assertions.assertEquals(12.1F, result.getElectricityTotalAmount());
        Assertions.assertEquals(168, result.getElectricityBeginIndex());
        Assertions.assertEquals(268, result.getElectricityEndIndex());
        Assertions.assertEquals(200, result.getGasBeginIndex());
        Assertions.assertEquals(300, result.getGasEndIndex());
    }

    @Test
    void calculateInvoice_OK_CalculateFirstInvoice_ProfessionalClient() {
        ProfessionalClient client = ProfessionalClient.builder()
                .siretNumber("SIRET00001")
                .socialReason("Rakuten")
                .revenue(1200000F)
                .build();
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.of(client));
        Mockito.when(invoiceRepository.findFirstByClientReferenceOrderByInvoiceDateDesc("EKW00000001")).thenReturn(Optional.empty());

        Invoice result = service.calculateInvoiceFor("EKW00000001", request);

        Mockito.verify(invoiceRepository, Mockito.times(1)).save(any(Invoice.class));
        Assertions.assertEquals(client.getSiretNumber(), ((ProfessionalClient) result.getClient()).getSiretNumber());
        Assertions.assertEquals(client.getSocialReason(), ((ProfessionalClient) result.getClient()).getSocialReason());
        Assertions.assertEquals(client.getRevenue(), ((ProfessionalClient) result.getClient()).getRevenue());
        Assertions.assertEquals(11.1F, result.getGasTotalAmount());
        Assertions.assertEquals(11.4F, result.getElectricityTotalAmount());
    }

    @Test
    void calculateInvoice_OK_CalculateInvoice_InvoiceExisted_RequestValid_ProfessionalClient() {
        ProfessionalClient client = ProfessionalClient.builder()
                .siretNumber("SIRET00001")
                .socialReason("Rakuten")
                .revenue(1200000F)
                .build();
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.of(client));
        Invoice lastInvoice = Invoice.builder()
                .electricityEndIndex(168)
                .electricityBeginIndex(8)
                .gasEndIndex(200)
                .gasBeginIndex(8)
                .build();

        Mockito.when(invoiceRepository.findFirstByClientReferenceOrderByInvoiceDateDesc("EKW00000001")).thenReturn(Optional.of(lastInvoice));

        Invoice result = service.calculateInvoiceFor("EKW00000001", request);

        Mockito.verify(invoiceRepository, Mockito.times(1)).save(any(Invoice.class));
        Assertions.assertEquals(client.getSiretNumber(), ((ProfessionalClient) result.getClient()).getSiretNumber());
        Assertions.assertEquals(client.getSocialReason(), ((ProfessionalClient) result.getClient()).getSocialReason());
        Assertions.assertEquals(client.getRevenue(), ((ProfessionalClient) result.getClient()).getRevenue());
        Assertions.assertEquals(11.1F, result.getGasTotalAmount());
        Assertions.assertEquals(11.4F, result.getElectricityTotalAmount());
    }

    @Test
    void calculateInvoice_OK_CalculateInvoice_InvoiceExisted_RequestInvalid_Exception() {
        ProfessionalClient client = ProfessionalClient.builder().build();
        Mockito.when(clientRepository.findByReference("EKW00000001")).thenReturn(Optional.of(client));
        Invoice lastInvoice = Invoice.builder()
                .electricityEndIndex(170)
                .electricityBeginIndex(8)
                .gasEndIndex(205)
                .gasBeginIndex(8)
                .build();

        Mockito.when(invoiceRepository.findFirstByClientReferenceOrderByInvoiceDateDesc("EKW00000001")).thenReturn(Optional.of(lastInvoice));

        Exception exception = assertThrows(ConsumptionDetailInvalidException.class, () -> service.calculateInvoiceFor("EKW00000001", request));

        Assertions.assertEquals(exception.getMessage(), "Consumption detail is not valid");
    }
}