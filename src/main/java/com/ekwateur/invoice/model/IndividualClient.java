package com.ekwateur.invoice.model;

import com.ekwateur.invoice.configuration.PriceProperties;
import com.ekwateur.invoice.request.ConsumptionDetailInvoiceCalculationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@DiscriminatorValue("individual")
public class IndividualClient extends Client{
    @Column
    String civility;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Override
    public Invoice calculateInvoiceFrom(Client client, ConsumptionDetailInvoiceCalculationRequest request, PriceProperties price) {
        Float gasTotalAmount = (request.getGasEndIndex() - request.getGasBeginIndex()) *  price.getIndividual().getGas();
        Float electricityTotalAmount = (request.getElectricityEndIndex() - request.getElectricityBeginIndex()) *  price.getIndividual().getElectricity();
        return Invoice.builder().client(client)
                .gasBeginIndex(request.getGasBeginIndex())
                .gasEndIndex(request.getGasEndIndex())
                .electricityBeginIndex(request.getElectricityBeginIndex())
                .electricityEndIndex(request.getElectricityEndIndex())
                .invoiceDate(new java.sql.Date(System.currentTimeMillis()))
                .gasTotalAmount(gasTotalAmount)
                .electricityTotalAmount(electricityTotalAmount)
                .electricityTotalAmount(electricityTotalAmount)
                .build();
    }
}
