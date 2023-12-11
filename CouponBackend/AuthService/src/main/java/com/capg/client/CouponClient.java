package com.capg.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capg.dto.CouponDto;
import com.capg.errorDecoder.CustomErrorDecoder;

@FeignClient(url = "http://localhost:9005/coupons",name = "coupon-service", configuration = CustomErrorDecoder.class)
public interface CouponClient {
	
	@PostMapping("/createCoupon")
	public CouponDto createCoupon(@RequestBody CouponDto coupon);
	
	@GetMapping("/getCouponById/{couponId}")
	public CouponDto getCouponById(@PathVariable String couponId);
	
	@DeleteMapping("/deleteCouponById/{couponId}")
	public String deleteCouponById(@PathVariable String couponId);
	
	@GetMapping("/getAllCoupon")
	public List<CouponDto> getAllCoupons();
	
	@PutMapping("/updateCoupon/{couponId}")
	public CouponDto updateCoupon(@RequestBody CouponDto coupon, @PathVariable String couponId);

	@GetMapping("/getCouponByCode/{couponCode}")
	public CouponDto getCouponByCouponCode(@PathVariable String couponCode);

	@DeleteMapping("/deleteCouponByCode/{couponCode}")
	public String deleteCouponByCode(@PathVariable String couponCode);

}
