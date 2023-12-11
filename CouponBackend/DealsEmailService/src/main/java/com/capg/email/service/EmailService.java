package com.capg.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.capg.email.model.Email;

@Service
public class EmailService implements EmailServicee {
	@Autowired
	private JavaMailSender sender;
 
	@Override
	public void sendMail(Email email) {
		// TODO Auto-generated method stub
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email.getTo());
		message.setSubject(email.getSubject());
		message.setText(email.getText());
		sender.send(message);
	}
 
}
