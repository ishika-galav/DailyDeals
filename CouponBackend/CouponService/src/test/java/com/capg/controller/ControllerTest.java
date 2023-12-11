package com.capg.controller;

import com.capg.exception.CouponException;
import com.capg.model.Coupon;
import com.capg.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ControllerTest {

    @Mock
    private CouponService couponService;

    @InjectMocks
    private CouponController couponController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCoupon_Success() {
        // Arrange
        Coupon mockCoupon = new Coupon("couponId", "code", "company", 50.0, "Description", "01/01/2023");
        when(couponService.createCoupon(any(Coupon.class))).thenReturn(mockCoupon);

        // Act
        ResponseEntity<Coupon> response = couponController.createCoupon(mockCoupon);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockCoupon, response.getBody());
        verify(couponService, times(1)).createCoupon(any(Coupon.class));
    }

    @Test
    void getCouponById_Success() throws CouponException {
        // Arrange
        String couponId = "couponId";
        Coupon mockCoupon = new Coupon(couponId, "code", "company", 50.0, "Description", "01/01/2023");
        when(couponService.getCouponById(couponId)).thenReturn(mockCoupon);

        // Act
        ResponseEntity<Coupon> response = couponController.getCouponById(couponId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCoupon, response.getBody());
        verify(couponService, times(1)).getCouponById(couponId);
    }

    @Test
    void deleteCouponById_Success() throws CouponException {
        // Arrange
        String couponId = "couponId";
        Coupon mockCoupon = new Coupon(couponId, "code", "company", 50.0, "Description", "01/01/2023");
        when(couponService.deleteCouponById(couponId)).thenReturn(mockCoupon);

        // Act
        ResponseEntity<String> response = couponController.deleteCouponById(couponId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Coupon deleted successfully", response.getBody());
        verify(couponService, times(1)).deleteCouponById(couponId);
    }

    @Test
    void getAllCoupons_Success() {
        // Arrange
        List<Coupon> mockCoupons = Arrays.asList(
                new Coupon("1", "code1", "company1", 50.0, "Description1", "01/01/2023"),
                new Coupon("2", "code2", "company2", 60.0, "Description2", "02/01/2023")
        );
        when(couponService.getAllCoupons()).thenReturn(mockCoupons);

        // Act
        ResponseEntity<List<Coupon>> response = couponController.getAllCoupons();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCoupons, response.getBody());
        verify(couponService, times(1)).getAllCoupons();
    }

    @Test
    void updateCoupon_Success() throws CouponException {
        // Arrange
        String couponId = "couponId";
        Coupon mockCoupon = new Coupon(couponId, "code", "company", 50.0, "Description", "01/01/2023");
        when(couponService.updateCoupon(any(Coupon.class), eq(couponId))).thenReturn(mockCoupon);

        // Act
        ResponseEntity<Coupon> response = couponController.updateCoupon(mockCoupon, couponId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCoupon, response.getBody());
        verify(couponService, times(1)).updateCoupon(any(Coupon.class), eq(couponId));
    }

    @Test
    void deleteCouponByCode_Success() throws CouponException {
        // Arrange
        String couponCode = "code";
        when(couponService.deleteCouponByCode(couponCode)).thenReturn("Coupon deleted successfully");

        // Act
        ResponseEntity<String> response = couponController.deleteCouponByCode(couponCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Coupon deleted successfully", response.getBody());
        verify(couponService, times(1)).deleteCouponByCode(couponCode);
    }

    @Test
    void getCouponByCouponCode_Success() throws CouponException {
        // Arrange
        String couponCode = "code";
        Coupon mockCoupon = new Coupon("couponId", couponCode, "company", 50.0, "Description", "01/01/2023");
        when(couponService.getCouponByCouponCode(couponCode)).thenReturn(mockCoupon);

        // Act
        ResponseEntity<Coupon> response = couponController.getCouponByCouponCode(couponCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockCoupon, response.getBody());
        verify(couponService, times(1)).getCouponByCouponCode(couponCode);
    }
}