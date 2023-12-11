package com.capg.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.capg.entity.Email;
import com.capg.errorDecoder.CustomErrorDecoder;

@FeignClient(url="http://localhost:5050/email", name="mail-service")
public interface EmailClient {

	@PostMapping("/sendMail")
	public String sendMail(@RequestBody Email email);
	
}	
