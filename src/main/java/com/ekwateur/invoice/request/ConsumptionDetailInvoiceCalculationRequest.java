package com.ekwateur.invoice.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConsumptionDetailInvoiceCalculationRequest {
    @Min(0)
    private Integer gasBeginIndex;
    @Min(0)
    private Integer gasEndIndex;
    @Min(0)
    private Integer electricityBeginIndex;
    @Min(0)
    private Integer electricityEndIndex;
}
