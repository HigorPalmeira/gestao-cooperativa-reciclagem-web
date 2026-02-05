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
		
		if (cnpjCliente == null || cnpjCliente.isBlank()) {
			throw new RuntimeException("CNPJ do Cliente inválido! Não é pssível cadastrar uma venda sem um cliente válido.");
		}
		
		if (listaItensVenda == null || listaItensVenda.size() < 1) {
			throw new RuntimeException("Itens da Venda inválidos! É necessário informar corretamente os itens da venda, para o cadastro.");
		}
		
		Cliente cliente = clienteService.buscarClientePorCnpj(cnpjCliente);
		double valorTotal = listaItensVenda.stream()
				.mapToDouble(itemVenda -> itemVenda.getPrecoUnitarioKg() * itemVenda.getPesoVendidoKg())
				.sum();
		
		Venda venda = new Venda(dtVenda, valorTotal, cliente);
		
		vendaDao.inserirVenda(venda);
		
		for (ItemVenda itemVenda : listaItensVenda) {
			itemVenda.setVenda(venda);
		}
		
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
	
	public void atualizarVenda(int idVenda, Date dtVenda, String cnpjCliente, List<ItemVenda> listaItensVenda) {
		
		if (dtVenda.after(Date.valueOf(LocalDate.now()))) {
			throw new RuntimeException("Data da venda inválida! Não é possível atualizar uma venda em uma data posterior à atual.");
		}
		
		if (cnpjCliente == null || cnpjCliente.isBlank()) {
			throw new RuntimeException("CNPJ do Cliente inválido! Não é pssível cadastrar uma venda sem um cliente válido.");
		}
		
		if (listaItensVenda == null || listaItensVenda.size() < 1) {
			throw new RuntimeException("Itens da Venda inválidos! É necessário informar corretamente os itens da venda, para a atualização. Caso não possua itens, delete a venda.");
		}
		
		Cliente cliente = clienteService.buscarClientePorCnpj(cnpjCliente);
		double valorTotal = listaItensVenda.stream()
				.mapToDouble(itemVenda -> itemVenda.getPrecoUnitarioKg() * itemVenda.getPesoVendidoKg())
				.sum();
		
		Venda venda = new Venda(idVenda, dtVenda, valorTotal, cliente);
		
		vendaDao.atualizarVenda(venda);
		
	}
	
	public void deletarVenda(int id) {
		
		vendaDao.deletarVenda(id);
		
	}
	
	public List<Venda> listarVendas() {
		
		return vendaDao.listarVendas();
		
	}
	
	public Venda buscarVendaPorId(int id) {
		
		Venda venda = new Venda();
		venda.setId(id);
		
		vendaDao.buscarVendaPorId(venda);
		
		return venda;
		
	}

}
