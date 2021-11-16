package com.example.reactivetest.api.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InvoiceItemResponse {

	private Long quantity;

	private String productDescription;

	private BigDecimal itemAmount;


}
