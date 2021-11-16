package com.example.reactivetest.api.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class InvoiceDTO {
    private String name;

    private BigDecimal totalItemsAmount;

    private BigDecimal totalShippingAmount;

    private BigDecimal totalAmount;

    private List<InvoiceItemDTO> items;
}
