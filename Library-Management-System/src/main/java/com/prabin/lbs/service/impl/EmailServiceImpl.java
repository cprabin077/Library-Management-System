package com.prabin.lbs.service.impl;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.prabin.lbs.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	private final JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(String to, String subject, String body) {
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		}catch (MailException | MessagingException e) {
			throw new MailSendException("Find to send email");
		} {
			
		}
		
	}

}
