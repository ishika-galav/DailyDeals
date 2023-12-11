package com.capg.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capg.email.model.Email;
import com.capg.email.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {
	@Autowired
	private EmailService service;
	@PostMapping("/sendMail")
	public ResponseEntity<String> sendMail(@RequestBody Email email){
		try {
			service.sendMail(email);
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Failed, Your Firewall blocked the connection",HttpStatus.OK);
		}
	}
}
