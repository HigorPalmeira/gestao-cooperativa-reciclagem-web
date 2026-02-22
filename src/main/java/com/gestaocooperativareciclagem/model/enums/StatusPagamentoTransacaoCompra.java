package com.gestaocooperativareciclagem.model.enums;

public enum StatusPagamentoTransacaoCompra {
	
	PENDENTE("Pendente"),
	PAGO("Pago");
	
	private String descricao;
	
	StatusPagamentoTransacaoCompra(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public static StatusPagamentoTransacaoCompra fromDescricao(String descricao) {
		for (StatusPagamentoTransacaoCompra tipo : StatusPagamentoTransacaoCompra.values()) {
			if (tipo.descricao.equalsIgnoreCase(descricao)) {
				return tipo;
			}
		}
		
		throw new RuntimeException("Status de pagamento da transação compra não encontrado com base na descrição informada!");
	}

}
