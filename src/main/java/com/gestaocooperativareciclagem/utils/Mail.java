package com.gestaocooperativareciclagem.utils;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	
	private final String EMAIL = "exemplo@gmail.com";
	private final String PASSWORD = "senha";
	
	public void sendMail(String assunto, String corpoEmail, String email) {
		
		Properties props = new Properties();
		
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(EMAIL, PASSWORD);
				
			}
			
		});
		
		session.setDebug(true);
		
		try {
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(EMAIL));
			
			
			Address[] toUser = InternetAddress.parse(email);
			
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setText(corpoEmail);
			
			Transport.send(message);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

}
