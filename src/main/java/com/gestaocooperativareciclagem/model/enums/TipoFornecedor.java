package com.gestaocooperativareciclagem.model.enums;

public enum TipoFornecedor {
	COLETOR("Coletor"),
	MUNICIPIO("Municipio"),
	EMPRESA("Empresa");

	private String descricao;

	TipoFornecedor(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoFornecedor fromDescricao(String descricao) {
		for (TipoFornecedor tipo : TipoFornecedor.values()) {
			if (tipo.descricao.equalsIgnoreCase(descricao)) {
				return tipo;
			}
		}

		throw new RuntimeException("Tipo de fornecedor não encontrado com base na descrição informada!");
	}
}
