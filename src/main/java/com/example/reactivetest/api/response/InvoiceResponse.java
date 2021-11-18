package com.example.reactivetest.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {

    private String id;

    private String externalId;

    private BigDecimal totalItemsAmount;

    private BigDecimal totalShippingAmount;

    private BigDecimal totalAmount;

    private List<InvoiceItemResponse> items;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXX")
    private LocalDateTime createAt;
}
