package com.gestaocooperativareciclagem.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class LoteProcessado {
	
	private int id;
	private BigDecimal pesoAtualKg;
	private Date dtCriacao;
	private TipoMaterial tipoMaterial;
	private LoteBruto loteBruto;
	
	public LoteProcessado() {}

	public LoteProcessado(int id, BigDecimal pesoAtualKg, Date dtCriacao, TipoMaterial tipoMaterial,
			LoteBruto loteBruto) {
		this.id = id;
		this.pesoAtualKg = pesoAtualKg;
		this.dtCriacao = dtCriacao;
		this.tipoMaterial = tipoMaterial;
		this.loteBruto = loteBruto;
	}

	public LoteProcessado(BigDecimal pesoAtualKg, TipoMaterial tipoMaterial, LoteBruto loteBruto) {
		this.pesoAtualKg = pesoAtualKg;
		this.dtCriacao = Date.valueOf(LocalDate.now());
		this.tipoMaterial = tipoMaterial;
		this.loteBruto = loteBruto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getPesoAtualKg() {
		return pesoAtualKg;
	}

	public void setPesoAtualKg(BigDecimal pesoAtualKg) {
		this.pesoAtualKg = pesoAtualKg;
	}

	public Date getDtCriacao() {
		return dtCriacao;
	}

	public void setDtCriacao(Date dtCriacao) {
		this.dtCriacao = dtCriacao;
	}

	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public LoteBruto getLoteBruto() {
		return loteBruto;
	}

	public void setLoteBruto(LoteBruto loteBruto) {
		this.loteBruto = loteBruto;
	}

	@Override
	public String toString() {
		return "LoteProcessado [id=" + id + ", pesoAtualKg=" + pesoAtualKg + ", dtCriacao=" + dtCriacao
				+ ", tipoMaterial=" + tipoMaterial + ", loteBruto=" + loteBruto + "]";
	}

}
