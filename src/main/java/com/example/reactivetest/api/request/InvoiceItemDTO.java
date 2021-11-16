package com.example.reactivetest.api.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemDTO {

	private Long quantity;

	private String productDescription;

	private BigDecimal itemAmount;


}
