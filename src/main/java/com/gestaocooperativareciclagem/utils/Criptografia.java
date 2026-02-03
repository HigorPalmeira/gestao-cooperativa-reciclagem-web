package com.gestaocooperativareciclagem.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografia {

	private static String getHexString(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
		byte messageDigest[] = algoritmo.digest(senha.getBytes("UTF-8"));

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest) {
			hexString.append(String.format("%02X", 0xFF & b));
		}

		return hexString.toString();

	}
	
	public static String criptografar(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		return getHexString(senha);
		
	}

}
