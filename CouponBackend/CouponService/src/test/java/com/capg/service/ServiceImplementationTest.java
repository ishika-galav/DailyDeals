package com.capg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capg.exception.CouponException;
import com.capg.model.Coupon;
import com.capg.repository.CouponRepository;

class ServiceImplementationTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCoupons() {
      
        List<Coupon> expectedCoupons = Arrays.asList(new Coupon());
        when(couponRepository.findAll()).thenReturn(expectedCoupons);

        List<Coupon> actualCoupons = couponService.getAllCoupons();

        assertEquals(expectedCoupons, actualCoupons);
    }

    @Test
    void testGetCouponById() throws CouponException {
        
        String couponId = "6540b8c9d810c87505706d8d";
        Coupon expectedCoupon = new Coupon();
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(expectedCoupon));

        Coupon actualCoupon = couponService.getCouponById(couponId);

        assertEquals(expectedCoupon, actualCoupon);
    }

    @Test
    void testGetCouponByIdThrowsException() {
   
        String couponId = "6540b8c9d810c87505706d8d";
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        assertThrows(CouponException.class, () -> couponService.getCouponById(couponId));
    }

    @Test
    void testCreateCoupon() {
   
        Coupon couponToCreate = new Coupon();
        when(couponRepository.save(couponToCreate)).thenReturn(couponToCreate);

        Coupon createdCoupon = couponService.createCoupon(couponToCreate);

        assertEquals(couponToCreate, createdCoupon);
    }

    @Test
    void testDeleteCouponById() throws CouponException {
        
        String couponId = "6540b8c9d810c87505706d8d";
        Coupon expectedCoupon = new Coupon();
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(expectedCoupon));

        Coupon deletedCoupon = couponService.deleteCouponById(couponId);

        assertEquals(expectedCoupon, deletedCoupon);
        verify(couponRepository, times(1)).deleteById(couponId);
    }

    @Test
    void testDeleteCouponByIdThrowsException() {
        
        String couponId = "6540b8c9d810c87505706d8d";
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        assertThrows(CouponException.class, () -> couponService.deleteCouponById(couponId));
        verify(couponRepository, never()).deleteById(couponId);
    }

    @Test
    void testUpdateCoupon() throws CouponException {
       
        String couponId = "6540b8c9d810c87505706d8d";
        Coupon existingCoupon = new Coupon();
        Coupon updatedCoupon = new Coupon();
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(existingCoupon));
        when(couponRepository.save(existingCoupon)).thenReturn(updatedCoupon);

        Coupon result = couponService.updateCoupon(existingCoupon, couponId);

        assertEquals(updatedCoupon, result);
    }

    @Test
    void testUpdateCouponThrowsException() {
        
        String couponId = "6540b8c9d810c87505706d8d";
        Coupon existingCoupon = new Coupon();
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        assertThrows(CouponException.class, () -> couponService.updateCoupon(existingCoupon, couponId));
    }

    @Test
    void testGetCouponByCouponCode() throws CouponException {
        
        String couponCode = "SWIGGY30";
        Coupon expectedCoupon = new Coupon();
        when(couponRepository.findByCouponCode(couponCode)).thenReturn(expectedCoupon);

        Coupon actualCoupon = couponService.getCouponByCouponCode(couponCode);

        assertEquals(expectedCoupon, actualCoupon);
    }

    @Test
    void testGetCouponByCouponCodeThrowsException() {
        
        String couponCode = "INVALID";
        when(couponRepository.findByCouponCode(couponCode)).thenReturn(null);

        assertThrows(CouponException.class, () -> couponService.getCouponByCouponCode(couponCode));
    }

    @Test
    void testDeleteCouponByCode() throws CouponException {
        
        String couponCode = "SWIGGY30";
        Coupon expectedCoupon = new Coupon();
        when(couponRepository.findByCouponCode(couponCode)).thenReturn(expectedCoupon);

        String result = couponService.deleteCouponByCode(couponCode);

        // Assert
        //assertEquals("Coupon deleted by code successfully");
    }}
