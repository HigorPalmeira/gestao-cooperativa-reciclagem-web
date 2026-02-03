package com.gestaocooperativareciclagem.model;

import java.sql.Date;

public class EtapaProcessamento {
	
	private LoteProcessado loteProcessado;
	private CategoriaProcessamento categoriaProcessamento;
	private Date dtProcessamento;
	private String status;
	
	public EtapaProcessamento() {}

	public EtapaProcessamento(LoteProcessado loteProcessado, CategoriaProcessamento categoriaProcessamento,
			Date dtProcessamento, String status) {
		this.loteProcessado = loteProcessado;
		this.categoriaProcessamento = categoriaProcessamento;
		this.dtProcessamento = dtProcessamento;
		this.status = status;
	}

	public LoteProcessado getLoteProcessado() {
		return loteProcessado;
	}

	public void setLoteProcessado(LoteProcessado loteProcessado) {
		this.loteProcessado = loteProcessado;
	}

	public CategoriaProcessamento getCategoriaProcessamento() {
		return categoriaProcessamento;
	}

	public void setCategoriaProcessamento(CategoriaProcessamento categoriaProcessamento) {
		this.categoriaProcessamento = categoriaProcessamento;
	}

	public Date getDtProcessamento() {
		return dtProcessamento;
	}

	public void setDtProcessamento(Date dtProcessamento) {
		this.dtProcessamento = dtProcessamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "EtapaProcessamento [loteProcessado=" + loteProcessado + ", categoriaProcessamento="
				+ categoriaProcessamento + ", dtProcessamento=" + dtProcessamento + ", status=" + status + "]";
	}
	
}
