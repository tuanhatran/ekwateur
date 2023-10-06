package com.ekwateur.invoice.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConsumptionDetailInvoiceCalculationRequest {
    private Integer gasBeginIndex;
    private Integer gasEndIndex;
    private Integer electricityBeginIndex;
    private Integer electricityEndIndex;
}
