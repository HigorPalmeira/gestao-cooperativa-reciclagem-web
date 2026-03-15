package com.gestaocooperativareciclagem.model.enums;

public enum StatusLoteBruto {

	RECEBIDO("Recebido"),
	EM_TRIAGEM("Em Triagem"),
	PROCESSADO("Processado");

	private String descricao;

	StatusLoteBruto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static StatusLoteBruto fromDescricao(String descricao) {
		for (StatusLoteBruto tipo : StatusLoteBruto.values()) {
			if (tipo.descricao.equalsIgnoreCase(descricao)) {
				return tipo;
			}
		}

		throw new RuntimeException("Status de lote bruto não encontrado com base na descrição informada!");
	}
}
