package com.example.reactivetest.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "invoices")
public class InvoiceEntity {

    @Id
    private String id;

    @Indexed
    private String externalId;

    private BigDecimal totalItemsAmount;

    private BigDecimal totalShippingAmount;

    private BigDecimal totalAmount;

    private List<InvoiceItem> items;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Indexed
    private LocalDateTime createAt;

}
