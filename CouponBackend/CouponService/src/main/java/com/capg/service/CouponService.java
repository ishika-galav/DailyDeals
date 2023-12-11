package com.capg.service;

import java.util.List;

import com.capg.exception.CouponException;
import com.capg.model.Coupon;

public interface CouponService {
	
	List<Coupon> getAllCoupons();
    Coupon getCouponById(String couponId) throws CouponException;
    Coupon getCouponByCouponCode(String couponCode) throws CouponException;
    Coupon createCoupon(Coupon coupon);
    Coupon deleteCouponById(String couponId) throws CouponException;
    String deleteCouponByCode(String couponCode) throws CouponException;
    Coupon updateCoupon( Coupon coupon, String couponId) throws CouponException;

}
