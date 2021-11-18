package com.example.reactivetest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXX")
    @JsonFormat(pattern = "yyyy-MM-dd[ ]['T']HH:mm[:ss][.SSS]X[':00']")
    @Indexed
    private LocalDateTime createAt;

}
