package com.gestaocooperativareciclagem.model;

import java.sql.Date;

public class PrecoMaterial {
	
	private int id;
	private double precoCompra;
	private Date dtVigencia;
	private TipoMaterial tipoMaterial;
	
	public PrecoMaterial() {}
	
	public PrecoMaterial(int id, double precoCompra, Date dtVigencia, TipoMaterial tipoMaterial) {
		this.id = id;
		this.precoCompra = precoCompra;
		this.dtVigencia = dtVigencia;
		this.tipoMaterial = tipoMaterial;
	}

	public PrecoMaterial(double precoCompra, Date dtVigencia, TipoMaterial tipoMaterial) {
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

	public double getPrecoCompra() {
		return precoCompra;
	}

	public void setPrecoCompra(double precoCompra) {
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
