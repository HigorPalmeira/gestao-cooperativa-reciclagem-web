package com.gestaocooperativareciclagem.model;

import java.math.BigDecimal;

public class ItemVenda {
	
	private int id;
	private TipoMaterial tipoMaterial;
	private Venda venda;
	private BigDecimal pesoVendidoKg;
	private BigDecimal precoUnitarioKg;
	
	public ItemVenda() {}

	public ItemVenda(int id, TipoMaterial tipoMaterial, Venda venda, BigDecimal pesoVendidoKg, BigDecimal precoUnitarioKg) {
		this.id = id;
		this.tipoMaterial = tipoMaterial;
		this.venda = venda;
		this.pesoVendidoKg = pesoVendidoKg;
		this.precoUnitarioKg = precoUnitarioKg;
	}

	public ItemVenda(TipoMaterial tipoMaterial, Venda venda, BigDecimal pesoVendidoKg, BigDecimal precoUnitarioKg) {
		this.tipoMaterial = tipoMaterial;
		this.venda = venda;
		this.pesoVendidoKg = pesoVendidoKg;
		this.precoUnitarioKg = precoUnitarioKg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TipoMaterial getTipoMaterial() {
		return tipoMaterial;
	}

	public void setTipoMaterial(TipoMaterial tipoMaterial) {
		this.tipoMaterial = tipoMaterial;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public BigDecimal getPesoVendidoKg() {
		return pesoVendidoKg;
	}

	public void setPesoVendidoKg(BigDecimal pesoVendidoKg) {
		this.pesoVendidoKg = pesoVendidoKg;
	}

	public BigDecimal getPrecoUnitarioKg() {
		return precoUnitarioKg;
	}

	public void setPrecoUnitarioKg(BigDecimal precoUnitarioKg) {
		this.precoUnitarioKg = precoUnitarioKg;
	}

	@Override
	public String toString() {
		return "ItemVenda [id=" + id + ", tipoMaterial=" + tipoMaterial + ", venda=" + venda + ", pesoVendidoKg="
				+ pesoVendidoKg + ", precoUnitarioKg=" + precoUnitarioKg + "]";
	}

}
