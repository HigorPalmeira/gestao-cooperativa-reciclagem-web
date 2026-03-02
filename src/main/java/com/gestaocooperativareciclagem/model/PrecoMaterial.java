package com.gestaocooperativareciclagem.model;

import java.math.BigDecimal;
import java.sql.Date;

public class PrecoMaterial {
	
	private int id;
	private BigDecimal precoCompra;
	private Date dtVigencia;
	private TipoMaterial tipoMaterial;
	
	public PrecoMaterial() {}
	
	public PrecoMaterial(int id, BigDecimal precoCompra, Date dtVigencia, TipoMaterial tipoMaterial) {
		this.id = id;
		this.precoCompra = precoCompra;
		this.dtVigencia = dtVigencia;
		this.tipoMaterial = tipoMaterial;
	}

	public PrecoMaterial(BigDecimal precoCompra, Date dtVigencia, TipoMaterial tipoMaterial) {
		this.precoCompra = precoCompra;
		this.dtVigencia = dtVigencia;
		this.tipoMaterial = tipoMaterial;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(BigDecimal precoCompra) {
		this.precoCompra = precoCompra;
	}

	public Date getDtVigencia() {
		return dtVigencia;
	}

	public void setDtVigencia(Date dtVigencia) {
		this.dtVigencia = dtVigencia;
	}

	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	@Override
	public String toString() {
		return "PrecoMaterial [id=" + id + ", precoCompra=" + precoCompra + ", dtVigencia=" + dtVigencia
				+ ", tipoMaterial=" + tipoMaterial + "]";
	}

}
