package com.example.reactivetest.entity.converter;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.entity.InvoiceEntity;

import java.util.Objects;

public class InvoiceEntityMapper {

    public static InvoiceEntity from(InvoiceDTO invoiceDTO) {

        return Objects.isNull(invoiceDTO) ? null : InvoiceEntity.builder()
                .name(invoiceDTO.getName())
                .totalAmount(invoiceDTO.getTotalAmount())
                .totalItemsAmount(invoiceDTO.getTotalItemsAmount())
                .totalShippingAmount(invoiceDTO.getTotalShippingAmount())
                .items(InvoiceItemMapper.from(invoiceDTO.getItems()))
                .build();

    }
}
