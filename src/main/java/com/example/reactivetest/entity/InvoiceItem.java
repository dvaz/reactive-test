package com.example.reactivetest.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceItem {

	private Long quantity;

	private String productDescription;

	private BigDecimal itemAmount;


}
