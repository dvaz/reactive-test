package com.example.reactivetest.entity.converter;

import com.example.reactivetest.api.request.InvoiceItemDTO;
import com.example.reactivetest.entity.InvoiceItem;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InvoiceItemMapper {


    public static List<InvoiceItem> from(List<InvoiceItemDTO> invoiceItemDTOS) {
        return Objects.isNull(invoiceItemDTOS) || invoiceItemDTOS.isEmpty() ? Collections.emptyList() :
                invoiceItemDTOS.stream().map(InvoiceItemMapper::from).collect(Collectors.toList());
    }

    public static InvoiceItem from(InvoiceItemDTO invoiceItemDTO) {

        return Objects.isNull(invoiceItemDTO) ? null : InvoiceItem.builder()
                .itemAmount(invoiceItemDTO.getItemAmount())
                .productDescription(invoiceItemDTO.getProductDescription())
                .quantity(invoiceItemDTO.getQuantity())
                .build();

    }
}
