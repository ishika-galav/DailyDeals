package com.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransactionDetails {
	
	private String orderId;
	private String currency;
	private int amount;
	private String key;

}
