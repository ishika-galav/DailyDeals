package com.capg.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capg.model.Coupon;

@Repository
public interface CouponRepository extends MongoRepository<Coupon, String> {

	Coupon findByCouponCode(String couponCode);
	
	void deleteBy(String username);

	void deleteByCouponCode(String couponCode);

}
