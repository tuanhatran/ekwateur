package com.ekwateur.invoice.controller;

import com.ekwateur.invoice.model.Invoice;
import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import com.ekwateur.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class InvoiceCalculationController {

    @Autowired
    private final InvoiceService invoiceService;

    @GetMapping("/invoices/client/{reference}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> getAllInvoicesForClient(@PathVariable("reference") @Pattern(regexp = "EKW\\d{8}") String reference) {
        return invoiceService.getAllInvoicesForClient(reference);
    }

    @PostMapping("/invoices/client/{reference}/calculate")
    @ResponseStatus(HttpStatus.OK)
    public Invoice calculateInvoiceFor(@PathVariable("reference") @Pattern(regexp = "EKW\\d{8}")String reference, @Valid @RequestBody ConsumptionDetailInvoiceCalculationRequest request) {
        return invoiceService.calculateInvoiceFor(reference, request);
    }
}
