package com.ekwateur.invoice.configuration;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@EnableConfigurationProperties
@ConfigurationProperties("price")
@Data
public class PriceProperties {
    private UnityPrice individual;

    private Professional professional;

    @Data
    public static class Professional {
        private UnityPrice bigRevenue;
        private UnityPrice smallRevenue;
    }
}
