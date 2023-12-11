  package com.capg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.exception.CouponException;
import com.capg.model.Coupon;
import com.capg.repository.CouponRepository;

@Service
public class CouponServiceImpl implements CouponService {

	@Autowired
    private CouponRepository couponRepository;
	
	

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
    

    @Override
    public Coupon getCouponById(String couponId) throws CouponException {
    	Coupon coupon =  couponRepository.findById(couponId).get();
		if(coupon == null) {
			throw new CouponException("Coupon not found");
		}
		return coupon;
    }
    

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }
    

    @Override
    public Coupon deleteCouponById(String couponId) throws CouponException {
    	Optional<Coupon> coupon = couponRepository.findById(couponId);
        if (coupon.isPresent()) {
            couponRepository.deleteById(couponId);
        }else {
        	throw new CouponException("Invalid Coupon Id.");
        }
        return coupon.get();
	}


    @Override
    public Coupon updateCoupon(Coupon coupon, String couponId) throws CouponException {
    	Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
    	System.out.println(optionalCoupon);
    	if (optionalCoupon.isPresent()) {
            Coupon existingCoupon = optionalCoupon.get();
//            existingCoupon.setCouponCode(coupon.getCouponCode());
//            existingCoupon.setCompany(coupon.getCompany());
//            existingCoupon.setCouponId(coupon.getCouponId());
//            existingCoupon.setDescription(coupon.getDescription());
//            existingCoupon.setPrice(coupon.getPrice());
//            existingCoupon.setUsedBy(coupon.getUsedBy());
            
            if(coupon.getCouponCode() != null) {
            	existingCoupon.setCouponCode(coupon.getCouponCode());
            }
            if(coupon.getPrice() != 0L) {
            	existingCoupon.setPrice(coupon.getPrice());
            }
            if(coupon.getDescription() != null) {
            	existingCoupon.setDescription(coupon.getDescription());
            }
            if(coupon.getUsedBy() != null) {
            	existingCoupon.setUsedBy(coupon.getUsedBy());
            }
            
            Coupon savedCoupon = couponRepository.save(existingCoupon);
            
            return new Coupon(savedCoupon.getCouponId(), savedCoupon.getCouponCode(), savedCoupon.getCompany(), savedCoupon.getPrice(),savedCoupon.getDescription(),savedCoupon.getUsedBy());
        } else {
            throw new CouponException("Coupon not found");
        }
}


	@Override
	public Coupon getCouponByCouponCode(String couponCode) throws CouponException {
		// TODO Auto-generated method stub
		Optional<Coupon> optCoupon = Optional.ofNullable(couponRepository.findByCouponCode(couponCode));
		if(optCoupon.isPresent()) {
			return couponRepository.findByCouponCode(couponCode);
		}
		else {
			throw new CouponException("Coupon Code does not Present!!");
		}
	}


	@Override
	public String deleteCouponByCode(String couponCode) throws CouponException {
		// TODO Auto-generated method stub
		Optional<Coupon> optCoupon = Optional.ofNullable(couponRepository.findByCouponCode(couponCode));
		if(optCoupon.isPresent()) {
			
			couponRepository.deleteByCouponCode(couponCode);
			return "Coupon deleted by code successfully";
		}else {
			throw new CouponException("Coupon Code does not Present!!");
		}
		
	}
}
