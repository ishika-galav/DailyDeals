package com.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.entity.TransactionDetails;
import com.payment.service.PaymentService;
import com.razorpay.RazorpayException;

@RestController
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {
	
	@Autowired
	PaymentService service;
	
	@GetMapping("/create_order/{amount}")
	public TransactionDetails createTransaction(@PathVariable Double amount) throws RazorpayException
	{
		return service.createTrasaction(amount);
	}
	

}
