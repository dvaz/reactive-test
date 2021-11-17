package com.example.reactivetest.api.response.converter;

import com.example.reactivetest.api.response.InvoiceResponse;
import com.example.reactivetest.entity.InvoiceEntity;

import java.util.Objects;

public class InvoiceResponseMapper {

    public static InvoiceResponse from(InvoiceEntity invoice){
        return Objects.isNull(invoice)? null : InvoiceResponse.builder()
                .id(invoice.getId())
                .externalId(invoice.getExternalId())
                .totalAmount(invoice.getTotalAmount())
                .totalItemsAmount(invoice.getTotalItemsAmount())
                .totalShippingAmount(invoice.getTotalShippingAmount())
                .items(InvoiceItemResponseMapper.from(invoice.getItems()))
                .createAt(invoice.getCreateAt())
                .build();
    }
}
