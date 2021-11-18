package com.example.reactivetest.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO {
    private String externalId;

    private BigDecimal totalShippingAmount;

    private List<InvoiceItemDTO> items;
}
