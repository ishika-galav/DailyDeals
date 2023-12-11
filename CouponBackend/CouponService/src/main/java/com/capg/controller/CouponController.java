package com.capg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.exception.CouponException;

import com.capg.model.Coupon;
import com.capg.service.CouponService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/coupons")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class CouponController {
	
	@Autowired
	CouponService couponService;
	
	
	@PostMapping("/createCoupon")
	public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon){
		log.info("Saving.. " + coupon);
		return new ResponseEntity<Coupon>(couponService.createCoupon(coupon), HttpStatus.CREATED);
	}
	
	@GetMapping("/getCouponById/{couponId}")
	public ResponseEntity<Coupon> getCouponById(@PathVariable String couponId) throws CouponException{
		log.info(couponService.getCouponById(couponId).toString());
		return new ResponseEntity<>(couponService.getCouponById(couponId), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCouponById/{couponId}")
	public ResponseEntity<String> deleteCouponById(@PathVariable String couponId) throws CouponException{
		Coupon coupon = couponService.deleteCouponById(couponId);
		log.info("Deleting coupon with Id " + coupon.getCouponId());
		return new ResponseEntity<>("Coupon deleted successfully", HttpStatus.OK);
	}
	
	@GetMapping("/getAllCoupon")
	public ResponseEntity<List<Coupon>> getAllCoupons(){
		log.info(couponService.getAllCoupons().toString());
		return new ResponseEntity<>(couponService.getAllCoupons(), HttpStatus.OK);
	}
	
	@PutMapping("/updateCoupon/{couponId}")
	public ResponseEntity<Coupon>updateCoupon(@RequestBody Coupon coupon, @PathVariable String couponId) throws CouponException{
		log.info("Updating Coupon with Id " + coupon.getCouponId());
		return new ResponseEntity<>(couponService.updateCoupon(coupon, couponId), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCouponByCode/{couponCode}")
	public ResponseEntity<String> deleteCouponByCode(@PathVariable String couponCode) throws CouponException{
		log.info("Deleting coupon with code " + couponCode);
		return new ResponseEntity<>(couponService.deleteCouponByCode(couponCode), HttpStatus.OK);
	}
	
	@GetMapping("/getCouponByCode/{couponCode}")
	public ResponseEntity<Coupon> getCouponByCouponCode(@PathVariable String couponCode) throws CouponException{
		log.info(couponService.getCouponByCouponCode(couponCode).toString());
		return new ResponseEntity<>(couponService.getCouponByCouponCode(couponCode), HttpStatus.OK);
	}

}
