package com.capg.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponDto {
	
	@NotNull
	private String couponId;
	@NotBlank
	private String couponCode;
	@NotNull
	private String company;
	@NotNull
	private double price;
	@NotBlank
	private String description;
	@NotNull
	private String usedBy;

}
