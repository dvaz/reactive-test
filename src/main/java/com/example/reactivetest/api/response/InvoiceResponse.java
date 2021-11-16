package com.example.reactivetest.api.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class InvoiceResponse {

    private String id;

    private String name;

    private BigDecimal totalItemsAmount;

    private BigDecimal totalShippingAmount;

    private BigDecimal totalAmount;

    private List<InvoiceItemResponse> items;
}
