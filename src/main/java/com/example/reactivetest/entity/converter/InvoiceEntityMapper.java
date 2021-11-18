package com.example.reactivetest.entity.converter;

import com.example.reactivetest.api.request.InvoiceDTO;
import com.example.reactivetest.entity.InvoiceEntity;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class InvoiceEntityMapper {

    public static InvoiceEntity from(InvoiceDTO invoiceDTO) {

        return Objects.isNull(invoiceDTO) ? null : InvoiceEntity.builder()
                .externalId(invoiceDTO.getExternalId())
                .totalShippingAmount(invoiceDTO.getTotalShippingAmount())
                .items(InvoiceItemMapper.from(invoiceDTO.getItems()))
                .createAt(LocalDateTime.now(ZoneOffset.UTC))
                .build();

    }
}
