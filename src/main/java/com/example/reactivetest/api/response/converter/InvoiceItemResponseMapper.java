package com.example.reactivetest.api.response.converter;

import com.example.reactivetest.api.response.InvoiceItemResponse;
import com.example.reactivetest.entity.InvoiceItem;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InvoiceItemResponseMapper {


    public static List<InvoiceItemResponse> from(List<InvoiceItem> invoiceItems) {
        return Objects.isNull(invoiceItems) || invoiceItems.isEmpty() ? Collections.emptyList() :
                invoiceItems.stream().map(InvoiceItemResponseMapper::from).collect(Collectors.toList());
    }

    public static InvoiceItemResponse from(InvoiceItem invoiceItem) {

        return Objects.isNull(invoiceItem) ? null : InvoiceItemResponse.builder()
                .itemAmount(invoiceItem.getItemAmount())
                .productDescription(invoiceItem.getProductDescription())
                .quantity(invoiceItem.getQuantity())
                .build();
    }
}
