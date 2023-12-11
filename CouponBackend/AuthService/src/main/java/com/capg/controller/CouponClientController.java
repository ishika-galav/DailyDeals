package com.capg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.client.CouponClient;
import com.capg.dto.CouponDto;
import com.capg.exception.ResourceAlreadyExists;
import com.capg.exception.UserNotFoundException;
import com.capg.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/coupons")
@EnableFeignClients(basePackages = "com.capg.client")
@Slf4j
public class CouponClientController {
	
	@Autowired
	CouponClient couponClient;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/createCoupon")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CouponDto> createCoupon(@RequestBody CouponDto coupon)throws ResourceAlreadyExists, UserNotFoundException{
		log.info("saving..."+ coupon);
		CouponDto c = couponClient.createCoupon(coupon);//.getBody()
		CouponDto cdto = new CouponDto(c.getCouponId(), c.getCouponCode(), c.getCompany(), c.getPrice(), c.getDescription(), c.getUsedBy());
		
		return new  ResponseEntity<>(cdto, HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/deleteCouponByCode/{couponCode}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> deleteCouponByCode(@PathVariable String couponCode) {
		log.info("Deleting coupon with code "+ couponCode);
		return new ResponseEntity<>(couponClient.deleteCouponByCode(couponCode), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCouponById/{couponId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> deleteCouponById(@PathVariable String couponId) {
		log.info("Deleting coupon with Id "+ couponId);
		return new ResponseEntity<>(couponClient.deleteCouponById(couponId), HttpStatus.OK);
	}
	
	@PutMapping("/updateCoupon/{couponId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CouponDto> updateCoupon(@RequestBody CouponDto coupon, @PathVariable String couponId) throws  UserNotFoundException{
		log.info("Updating coupon with Id "+ coupon.getCouponId());
		return new ResponseEntity<>(couponClient.updateCoupon(coupon, couponId), HttpStatus.OK);
	}
	
	@GetMapping("/getCouponById/{couponId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<CouponDto> getCouponById(@PathVariable String couponId) throws  UserNotFoundException{
		log.info(couponClient.getCouponById(couponId).toString());
		CouponDto cdto = couponClient.getCouponById(couponId);
		return new ResponseEntity<>(cdto, HttpStatus.OK);
	}
	
	@GetMapping("/getCouponByCode/{couponCode}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<CouponDto> getCouponByCouponCode(@PathVariable String couponCode) throws  UserNotFoundException{
		log.info(couponClient.getCouponByCouponCode(couponCode).toString());
		CouponDto cdto = couponClient.getCouponByCouponCode(couponCode);
		return new ResponseEntity<>(cdto, HttpStatus.OK);
	}
	
	@GetMapping("/getAllCoupons")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<List<CouponDto>> getAllCoupons() throws UserNotFoundException{
		log.info(couponClient.getAllCoupons().toString());
		List<CouponDto> couponList = couponClient.getAllCoupons();
		return new ResponseEntity<>(couponList, HttpStatus.OK);
	}

}
