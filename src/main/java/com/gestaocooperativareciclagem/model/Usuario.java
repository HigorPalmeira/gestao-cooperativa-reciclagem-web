package com.gestaocooperativareciclagem.model;

import com.gestaocooperativareciclagem.utils.Validador;

public class Usuario {

	private int id;
	private String nome;
	private String email;
	private String senha;
	private String papel;

	public Usuario() {}

	public Usuario(int id, String nome, String email, String senha, String papel) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.papel = papel;

		this.validar();
	}

	public Usuario(String nome, String email, String senha, String papel) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.papel = papel;

		this.validar();
	}

	private void validar() {

		if (nome == null) {
			throw new RuntimeException("Nome inválido! O nome não pode estar vazio.");
		}

		if (nome.isBlank()) {
			throw new RuntimeException("Nome inválido! O nome não pode estar branco.");
		}

		if (!Validador.isEmail(this.email)) {
			throw new RuntimeException("E-mail inválido! É necessário informar um e-mail válido.");
		}

		if (senha == null) {
			throw new RuntimeException("Senha inválida! A senha não pode estar vazia.");
		}

		if (senha.isBlank()) {
			throw new RuntimeException("Senha inválida! O senha não pode estar branco.");
		}

		if (papel == null) {
			throw new RuntimeException("Papel inválido! A papel não pode estar vazia.");
		}

		if (papel.isBlank()) {
			throw new RuntimeException("Papel inválido! O papel não pode estar branco.");
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {

		if (!Validador.isEmail(email)) {
			throw new RuntimeException("E-mail inválido!");
		}

		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", papel=" + papel
				+ "]";
	}

}
