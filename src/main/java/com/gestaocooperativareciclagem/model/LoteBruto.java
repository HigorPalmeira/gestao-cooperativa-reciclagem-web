package com.gestaocooperativareciclagem.model;

import java.sql.Date;

import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;

public class LoteBruto {
	
	private int id;
	private double pesoEntradaKg;
	private Date dtEntrada;
	private StatusLoteBruto status;
	private Fornecedor fornecedor;
	
	public LoteBruto() {}
	
	public LoteBruto(int id, double pesoEntradaKg, Date dtEntrada, StatusLoteBruto status, Fornecedor fornecedor) {
		this.id = id;
		this.pesoEntradaKg = pesoEntradaKg;
		this.dtEntrada = dtEntrada;
		this.status = status;
		this.fornecedor = fornecedor;
	}

	public LoteBruto(double pesoEntradaKg, Date dtEntrada, StatusLoteBruto status, Fornecedor fornecedor) {
		this.pesoEntradaKg = pesoEntradaKg;
		this.dtEntrada = dtEntrada;
		this.status = status;
		this.fornecedor = fornecedor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPesoEntradaKg() {
		return pesoEntradaKg;
	}

	public void setPesoEntradaKg(double pesoEntradaKg) {
		this.pesoEntradaKg = pesoEntradaKg;
	}

	public Date getDtEntrada() {
		return dtEntrada;
	}

	public void setDtEntrada(Date dtEntrada) {
		this.dtEntrada = dtEntrada;
	}

	public StatusLoteBruto getStatus() {
		return status;
	}

	public void setStatus(StatusLoteBruto status) {
		this.status = status;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public String toString() {
		return "LoteBruto [id=" + id + ", pesoEntradaKg=" + pesoEntradaKg + ", dtEntrada=" + dtEntrada + ", status="
				+ status + ", fornecedor=" + fornecedor + "]";
	}

}
