package com.gestaocooperativareciclagem.utils;

public class Formatador {

	public static String clearDoc(String doc) {
		
		if (doc == null) return doc;
		
		return doc.replaceAll("[.\\-/]", "");
		
	}
	
}
