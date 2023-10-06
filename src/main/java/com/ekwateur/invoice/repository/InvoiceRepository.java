package com.ekwateur.invoice.repository;

import com.ekwateur.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByClientReference(String clientReference);

    Optional<Invoice> findFirstByClientReferenceOrderByInvoiceDateDesc(String clientReference);
}
