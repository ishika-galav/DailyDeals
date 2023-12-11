
package com.payment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.razorpay.RazorpayException;

@RestControllerAdvice
public class ExceptionHandler {
	
	@org.springframework.web.bind.annotation.ExceptionHandler(RazorpayException.class)
	public ResponseEntity<String> handleException()
	{
		return new ResponseEntity<String>("Razorpay Exception is thrown",HttpStatus.NOT_FOUND);
	}

}
