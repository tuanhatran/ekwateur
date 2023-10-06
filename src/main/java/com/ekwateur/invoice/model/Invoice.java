package com.ekwateur.invoice.model;

import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_reference", referencedColumnName = "reference")
    private Client client;

    private Float gasTotalAmount;
    private Float electricityTotalAmount;

    private Integer gasBeginIndex;
    private Integer gasEndIndex;
    private Integer electricityBeginIndex;
    private Integer electricityEndIndex;
    private java.sql.Date invoiceDate;

    public boolean isConsumptionDetailValid(Invoice invoice, ConsumptionDetailInvoiceCalculationRequest request) {
        return request.getGasEndIndex() >= request.getGasBeginIndex()
                && request.getElectricityEndIndex() >= request.getElectricityBeginIndex()
                && request.getGasBeginIndex() >= invoice.getGasEndIndex()
                && request.getElectricityBeginIndex() >= invoice.getElectricityEndIndex();
    }
}
