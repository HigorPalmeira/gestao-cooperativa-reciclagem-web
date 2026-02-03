package com.gestaocooperativareciclagem.model;

public class ItemVenda {
	
	private int id;
	private TipoMaterial tipoMaterial;
	private Venda venda;
	private double pesoVendidoKg;
	private double precoUnitarioKg;
	
	public ItemVenda() {}

	public ItemVenda(int id, TipoMaterial tipoMaterial, Venda venda, double pesoVendidoKg, double precoUnitarioKg) {
		this.id = id;
		this.tipoMaterial = tipoMaterial;
		this.venda = venda;
		this.pesoVendidoKg = pesoVendidoKg;
		this.precoUnitarioKg = precoUnitarioKg;
	}

	public ItemVenda(TipoMaterial tipoMaterial, Venda venda, double pesoVendidoKg, double precoUnitarioKg) {
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

	public double getPesoVendidoKg() {
		return pesoVendidoKg;
	}

	public void setPesoVendidoKg(double pesoVendidoKg) {
		this.pesoVendidoKg = pesoVendidoKg;
	}

	public double getPrecoUnitarioKg() {
		return precoUnitarioKg;
	}

	public void setPrecoUnitarioKg(double precoUnitarioKg) {
		this.precoUnitarioKg = precoUnitarioKg;
	}

	@Override
	public String toString() {
		return "ItemVenda [id=" + id + ", tipoMaterial=" + tipoMaterial + ", venda=" + venda + ", pesoVendidoKg="
				+ pesoVendidoKg + ", precoUnitarioKg=" + precoUnitarioKg + "]";
	}

}
