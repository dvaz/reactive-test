package com.example.reactivetest.api.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceDTO {
    private String externalId;

    private BigDecimal totalShippingAmount;

    private List<InvoiceItemDTO> items;
}
