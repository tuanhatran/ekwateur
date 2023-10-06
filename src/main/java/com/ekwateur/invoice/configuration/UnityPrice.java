package com.ekwateur.invoice.configuration;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnityPrice {
    Float gas;
    Float electricity;
}
