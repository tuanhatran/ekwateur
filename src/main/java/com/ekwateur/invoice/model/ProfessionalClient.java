package com.ekwateur.invoice.model;

import com.ekwateur.invoice.configuration.PriceProperties;
import com.ekwateur.invoice.configuration.UnityPrice;
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
@DiscriminatorValue("professional")
@EqualsAndHashCode(callSuper = true)
public class ProfessionalClient extends Client{
    public static final Float REVENUE_THRESHOLD = 1000000F;
    @Column(name = "siret_number", nullable = false)
    String siretNumber;

    @Column(name = "social_reason")
    String socialReason;

    @Column(nullable = false)
    Float revenue;

    @Override
    public Invoice calculateInvoiceFrom(ConsumptionDetailInvoiceCalculationRequest request, PriceProperties price) {
        UnityPrice unityPrice = getUnityPriceFrom(price, revenue);
        Float gasTotalAmount = (request.getGasEndIndex() - request.getGasBeginIndex()) *  unityPrice.getGas();
        Float electricityTotalAmount = (request.getElectricityEndIndex() - request.getElectricityBeginIndex()) *  unityPrice.getElectricity();
        return Invoice.builder().client(this)
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

    private UnityPrice getUnityPriceFrom(PriceProperties price, Float revenue) {
        if (revenue >= REVENUE_THRESHOLD){
            return price.getProfessional().getBigRevenue();
        }
        return price.getProfessional().getSmallRevenue();
    }
}
