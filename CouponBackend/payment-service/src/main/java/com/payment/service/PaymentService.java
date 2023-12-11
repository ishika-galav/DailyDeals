package com.payment.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.payment.entity.TransactionDetails;
import com.payment.exception.PaymentIsNotSuccessfullException;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

@Service
public class PaymentService {
	
	private static final String KEY = "rzp_test_AKDTqC0kbyoY3c";
	private static final String KEY_SECRET ="Z77IahM19vgsE55nWIoLBqaG";
	private static final String CURRENCY="INR";
	
	public TransactionDetails createTrasaction(Double amount) throws RazorpayException  
	{
		//amount
		//currency
		//key
		//secret key
		
		
			
			JSONObject jsonObject = new JSONObject();
			
			jsonObject.put("amount", (amount*100));
			jsonObject.put("currency", CURRENCY);
//			jsonObject.put("key", KEY);
//			jsonObject.put("amount", amount);
			
			RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
			Order order = razorpayClient.orders.create(jsonObject);
			
			return prepareTransactionDetails(order);
			
			
			
		
		
		 
		
	}
	
	private TransactionDetails prepareTransactionDetails(Order order)
	{
		String orderId = order.get("id");
		String currency = order.get("currency");
		int amount = order.get("amount");
//		String key = order.get("key");
		
		TransactionDetails details = new TransactionDetails(orderId, currency, amount,KEY);
		
		return details;
	}

}
