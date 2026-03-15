package com.gestaocooperativareciclagem.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Venda {

	private int id;
	private Date dtVenda;
	private BigDecimal valorTotal;
	private Cliente cliente;

	public Venda() {}

	public Venda(int id, Date dtVenda, BigDecimal valorTotal, Cliente cliente) {
		this.id = id;
		this.dtVenda = dtVenda;
		this.valorTotal = valorTotal;
		this.cliente = cliente;
	}

	public Venda(Date dtVenda, BigDecimal valorTotal, Cliente cliente) {
		this.dtVenda = dtVenda;
		this.valorTotal = valorTotal;
		this.cliente = cliente;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDtVenda() {
		return dtVenda;
	}

	public void setDtVenda(Date dtVenda) {
		this.dtVenda = dtVenda;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Venda [id=" + id + ", dtVenda=" + dtVenda + ", valorTotal=" + valorTotal + ", cliente=" + cliente
				+ "]";
	}

}
