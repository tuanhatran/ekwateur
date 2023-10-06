package com.ekwateur.invoice.repository;

import com.ekwateur.invoice.model.Client;
import com.ekwateur.invoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByReference(String reference);
}
