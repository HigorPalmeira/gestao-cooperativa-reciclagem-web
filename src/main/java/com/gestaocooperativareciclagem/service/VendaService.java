package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.model.ItemVenda;
import com.gestaocooperativareciclagem.model.Venda;

public class VendaService {
	
	private VendaDAO vendaDao;
	private ItemVendaDAO itemVendaDao;
	private ClienteService clienteService;

	public VendaService(VendaDAO vendaDao, ItemVendaDAO itemVendaDao, ClienteService clienteService) {
		this.vendaDao = vendaDao;
		this.itemVendaDao = itemVendaDao;
		this.clienteService = clienteService;
	}
	
	public void inserirVenda(Date dtVenda, String cnpjCliente, List<ItemVenda> listaItensVenda) {
		
		if (dtVenda.after(Date.valueOf(LocalDate.now()))) {
			throw new RuntimeException("Data da venda inválida! Não é possível cadastrar uma venda em uma data posterior à atual.");
		}
		
		Cliente cliente = clienteService.buscarClientePorCnpj(cnpjCliente);
		double valorTotal = listaItensVenda.stream()
				.mapToDouble(itemVenda -> itemVenda.getPrecoUnitarioKg() * itemVenda.getPesoVendidoKg())
				.sum();
		
		Venda venda = new Venda(dtVenda, valorTotal, cliente);
		
		vendaDao.inserirVenda(venda);
		
		// preciso do id da venda para inserir os itens
		
		inserirListaItensVenda(listaItensVenda);
		
	}
	
	public void inserirItemVenda(ItemVenda itemVenda) {
	
		if (itemVenda == null) {
			throw new RuntimeException("Item da Venda inválido! O item da venda não pode estar vazio.");
		}
		
		if (itemVenda.getPesoVendidoKg() <= 0) {
			throw new RuntimeException("O peso vendido é inválido! O peso vendido deve ser maior que 0.0 Kg.");
		}
		
		if (itemVenda.getPrecoUnitarioKg() <= 0) {
			throw new RuntimeException("O preço unitário por quilo é inválido! O preço unitário deve ser maio que R$ 0,00/Kg.");
		}
		
		itemVendaDao.inserirItemVenda(itemVenda);
		
	}
	
	public void inserirListaItensVenda(List<ItemVenda> listaItensVenda) {
		
		if (listaItensVenda == null || listaItensVenda.size() == 0) {
			throw new RuntimeException("A lista de itens da venda é inválida! É necessário pelo menos um item na venda para ser cadastrado.");
		}
		
		for (ItemVenda itemVenda : listaItensVenda) {
			inserirItemVenda(itemVenda);
		}
		
	}

}
