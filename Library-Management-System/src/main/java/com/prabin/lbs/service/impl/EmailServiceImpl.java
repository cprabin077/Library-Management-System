package com.prabin.lbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.prabin.lbs.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private final JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(String to, String subject, String body) {
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
			
			helper.setSubject(subject);
			helper.setText(body,true);
			helper.setTo(to);
			javaMailSender.send(mimeMessage);
		}
		catch (MailException e) {
			
			throw new MailSendException("Find to send email");
			
		}catch (MessagingException e) {
			
			throw new RuntimeException(e);
		}
		
	}

}
