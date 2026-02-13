package com.gestaocooperativareciclagem.utils;

import java.util.regex.Pattern;

public class Validador {

	private static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
	private static final String TELEFONE_REGEX_PATTERN = "(\\(?\\d{2}\\)?\\s?)?(\\d{4,5}\\-?\\d{4})";
	
	public static boolean isCpf(String cpf) {

		if (cpf == null)
			return false;

		if (cpf.isBlank())
			return false;

		if (cpf.length() != 11)
			return false;

		if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222")
				|| cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555")
				|| cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888")
				|| cpf.equals("99999999999"))
			return false;

		char dv1, dv2;
		int soma, resto, num, peso;

		try {

			soma = 0;
			peso = 10;
			// primeiro digito verificador
			for (int i = 0; i < 9; i++) {

				num = (int) (cpf.charAt(i) - 48);
				soma += (num * peso--);

			}

			resto = 11 - (soma % 11);
			if (resto == 10 || resto == 11) {
				dv1 = '0';
			} else {
				dv1 = (char) (resto + 48);
			}

			// segundo digito verificador
			soma = 0;
			peso = 11;

			for (int i = 0; i < 10; i++) {

				num = (int) (cpf.charAt(i) - 48);
				soma += (num * peso--);

			}

			resto = 11 - (soma % 11);
			if (resto == 10 || resto == 11) {
				dv2 = '0';
			} else {
				dv2 = (char) (resto + 48);
			}

			// verificar os dv
			if (dv1 == cpf.charAt(9) && dv2 == cpf.charAt(10)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {

			return false;

		}

	}

	public static boolean isCnpj(String cnpj) {

		if (cnpj == null)
			return false;

		if (cnpj.isBlank())
			return false;

		if (cnpj.length() != 14)
			return false;

		if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
				|| cnpj.equals("33333333333333") || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
				|| cnpj.equals("66666666666666") || cnpj.equals("77777777777777") || cnpj.equals("88888888888888")
				|| cnpj.equals("99999999999999"))
			return false;

		char dv1, dv2;
		int soma, resto, num, peso;

		try {

			// primeiro digito
			soma = 0;
			peso = 2;

			for (int i = 11; i >= 0; i--) {

				num = (int) (cnpj.charAt(i) - 48);
				soma += (num * peso++);
				if (peso == 10) {
					peso = 2;
				}

			}

			resto = soma % 11;
			if (resto == 0 || resto == 1) {
				dv1 = '0';
			} else {
				dv1 = (char) ((11 - resto) + 48);
			}

			// segundo digito
			soma = 0;
			peso = 2;

			for (int i = 12; i >= 0; i--) {

				num = (int) (cnpj.charAt(i) - 48);
				soma += (num * peso++);
				if (peso == 10) {
					peso = 2;
				}

			}

			resto = soma % 11;
			if (resto == 0 || resto == 1) {
				dv2 = '0';
			} else {
				dv2 = (char) ((11 - resto) + 48);
			}

			// verificando os digitos
			if (dv1 == cnpj.charAt(12) && dv2 == cnpj.charAt(13)) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {

			return false;

		}

	}

	public static boolean isEmail(String email) {

		if (email == null)
			return false;

		return Pattern.compile(EMAIL_REGEX_PATTERN).matcher(email).matches();

	}

	public static boolean isTelefone(String telefone) {

		if (telefone == null)
			return false;

		return Pattern.compile(TELEFONE_REGEX_PATTERN).matcher(telefone).matches();

	}

}
