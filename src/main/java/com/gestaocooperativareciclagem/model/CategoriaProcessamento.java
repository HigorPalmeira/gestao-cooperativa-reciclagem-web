package com.gestaocooperativareciclagem.model;

public class CategoriaProcessamento {

	private int id;
	private String nome;
	private String descricao;

	public CategoriaProcessamento() {}

	public CategoriaProcessamento(int id, String nome, String descricao) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
	}

	public CategoriaProcessamento(String nome, String descricao) {
		this.nome = nome;
		this.descricao = descricao;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "CategoriaProcessamento [id=" + id + ", nome=" + nome + ", descricao=" + descricao + "]";
	}

}
