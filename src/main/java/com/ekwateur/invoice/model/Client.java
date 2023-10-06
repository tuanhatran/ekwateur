package com.ekwateur.invoice.model;

import com.ekwateur.invoice.configuration.PriceProperties;
import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="client_type")
@Getter
public abstract class Client {
    @Id
    String reference;

    @Column(name = "gas_delivery_point", nullable = false, unique = true)
    String gasDeliveryPoint;

    @Column(name = "electricity_delivery_point", nullable = false, unique = true)
    String electricityDeliveryPoint;

    public abstract Invoice calculateInvoiceFrom(Client client, ConsumptionDetailInvoiceCalculationRequest request, PriceProperties price);
}
