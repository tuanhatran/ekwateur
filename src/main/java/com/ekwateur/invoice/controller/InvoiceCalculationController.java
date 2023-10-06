package com.ekwateur.invoice.controller;

import com.ekwateur.invoice.model.Invoice;
import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import com.ekwateur.invoice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class InvoiceCalculationController {

    @Autowired
    private final InvoiceService invoiceService;

    @GetMapping("/invoices/client/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoicesForClient(@PathVariable("reference") String reference) {
        return invoiceService.getAllInvoicesForClient(reference);
    }

    @GetMapping("/invoices/client/{reference}/calculate")
    @ResponseStatus(HttpStatus.OK)
    public Invoice calculateInvoiceFor(@PathVariable("reference") String reference, @RequestBody ConsumptionDetailInvoiceCalculationRequest request) {
        return invoiceService.calculateInvoiceFor(reference, request);
    }
}
