package com.gestaocooperativareciclagem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static final String URL = "jdbc:mysql://localhost:3306/dbgestaocooperativareciclagem?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
	private static final String USUARIO = "gestor";
	private static final String SENHA = "123456";

	public static Connection getConnection() {

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {

			throw new RuntimeException("Registrar Drive manualmente.", e);

		}

		try {

			return DriverManager.getConnection(URL, USUARIO, SENHA);

		} catch (SQLException e) {

			throw new RuntimeException("Erro ao conectar ao banco de dados.", e);

		}

	}

	public static void main(String[] args) {
		try {
			Connection conexao = getConnection();
			if (conexao != null) {
				System.out.println("Conexão bem-sucedida!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
