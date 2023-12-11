package com.capg.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Coupons")
public class Coupon {
	
//	public Coupon(String string, String string2, String string3, String string4, double d, String string5) {
//		// TODO Auto-generated constructor stub
//	}
	@MongoId
	private String couponId;
	private String couponCode;
	private String company;
	private double price;
	private String description;
	private String usedBy;

}
