package com.example.reactivetest.api.response.converter;

import com.example.reactivetest.api.response.InvoiceResponse;
import com.example.reactivetest.entity.InvoiceEntity;

import java.util.Objects;

public class InvoiceResponseMapper {

    public static InvoiceResponse from(InvoiceEntity invoice){
        return Objects.isNull(invoice)? null : InvoiceResponse.builder()
                .name(invoice.getName())
                .totalAmount(invoice.getTotalAmount())
                .totalItemsAmount(invoice.getTotalItemsAmount())
                .totalShippingAmount(invoice.getTotalShippingAmount())
                .items(InvoiceItemResponseMapper.from(invoice.getItems()))
                .build();
    }
}
