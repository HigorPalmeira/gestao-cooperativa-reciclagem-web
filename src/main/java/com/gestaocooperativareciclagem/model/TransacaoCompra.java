package com.gestaocooperativareciclagem.model;

import java.sql.Date;

import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;

public class TransacaoCompra {
	
	private int id;
	private double valorTotalCalculado;
	private StatusPagamentoTransacaoCompra status;
	private Date dtCalculo;
	private Date dtPagamento;
	private LoteBruto loteBruto;
	
	public TransacaoCompra() {}

	public TransacaoCompra(int id, double valorTotalCalculado, StatusPagamentoTransacaoCompra status, Date dtCalculo,
			Date dtPagamento, LoteBruto loteBruto) {
		this.id = id;
		this.valorTotalCalculado = valorTotalCalculado;
		this.status = status;
		this.dtCalculo = dtCalculo;
		this.dtPagamento = dtPagamento;
		this.loteBruto = loteBruto;
	}

	public TransacaoCompra(double valorTotalCalculado, StatusPagamentoTransacaoCompra status, Date dtCalculo,
			Date dtPagamento, LoteBruto loteBruto) {
		this.valorTotalCalculado = valorTotalCalculado;
		this.status = status;
		this.dtCalculo = dtCalculo;
		this.dtPagamento = dtPagamento;
		this.loteBruto = loteBruto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValorTotalCalculado() {
		return valorTotalCalculado;
	}

	public void setValorTotalCalculado(double valorTotalCalculado) {
		this.valorTotalCalculado = valorTotalCalculado;
	}

	public StatusPagamentoTransacaoCompra getStatus() {
		return status;
	}

	public void setStatus(StatusPagamentoTransacaoCompra status) {
		this.status = status;
	}

	public Date getDtCalculo() {
		return dtCalculo;
	}

	public void setDtCalculo(Date dtCalculo) {
		this.dtCalculo = dtCalculo;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public LoteBruto getLoteBruto() {
		return loteBruto;
	}

	public void setLoteBruto(LoteBruto loteBruto) {
		this.loteBruto = loteBruto;
	}

	@Override
	public String toString() {
		return "TransacaoCompra [id=" + id + ", valorTotalCalculado=" + valorTotalCalculado + ", status=" + status
				+ ", dtCalculo=" + dtCalculo + ", dtPagamento=" + dtPagamento + ", loteBruto=" + loteBruto
				+ "]";
	}

}
