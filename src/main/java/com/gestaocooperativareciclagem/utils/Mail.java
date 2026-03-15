package com.gestaocooperativareciclagem.utils;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	public void sendMail(String assunto, String corpoEmail, String email) {

		Properties credenciais = new Properties();

		try (InputStream input = getClass().getClassLoader().getResourceAsStream("credentials.properties")) {

			if (input == null) {
				System.err.println("Erro: Arquivo credentials.properties não encontrado na pasta resources!");
				return;
			}

			credenciais.load(input);

		} catch (Exception e) {

			System.err.println("Erro ao ler o arquivo de propriedades.");
			e.printStackTrace();
			return;

		}

		final String usuario = credenciais.getProperty("email.usuario");
		final String senha = credenciais.getProperty("email.senha");


		Properties propsSMTP = new Properties();

		propsSMTP.put("mail.smtp.auth", "true");
		propsSMTP.put("mail.smtp.starttls.enable", "true");
		propsSMTP.put("mail.smtp.host", "smtp.gmail.com");
		// propsSMTP.put("mail.smtp.socketFactory.port", "465");
		// propsSMTP.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		propsSMTP.put("mail.smtp.port", "587");

		Session session = Session.getDefaultInstance(propsSMTP, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(usuario, senha);

			}

		});

		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(usuario));


			Address[] toUser = InternetAddress.parse(email);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			// message.setText(corpoEmail);
			message.setContent(corpoEmail, "text/html; charset=utf-8");

			Transport.send(message);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
