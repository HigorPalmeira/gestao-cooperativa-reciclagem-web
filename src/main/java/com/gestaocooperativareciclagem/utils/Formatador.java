package com.gestaocooperativareciclagem.utils;

public class Formatador {

	public static String clearDoc(String doc) {
		
		if (doc == null) return doc;
		
		return doc.replaceAll("[.\\-/]", "");
		
	}
	
	public static String clearFone(String fone) {
		
		if (fone == null) return fone;
		
		return fone.replaceAll("\\D", "");
		
	}
	
}
