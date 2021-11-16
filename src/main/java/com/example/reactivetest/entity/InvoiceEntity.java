package com.example.reactivetest.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@Document(collection = "invoices")
public class InvoiceEntity {

	@Id
	private String id;

	private String name;

	private BigDecimal totalItemsAmount;

	private BigDecimal totalShippingAmount;

	private BigDecimal totalAmount;

	private List<InvoiceItem> items;

}
