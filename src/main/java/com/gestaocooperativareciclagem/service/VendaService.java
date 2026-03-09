package com.gestaocooperativareciclagem.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.model.ItemVenda;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.utils.Formatador;
import com.gestaocooperativareciclagem.utils.Validador;

public class VendaService {

	private VendaDAO vendaDao;
	private ItemVendaDAO itemVendaDao;
	private ClienteService clienteService;

	public VendaService(VendaDAO vendaDao, ItemVendaDAO itemVendaDao, ClienteService clienteService) {
		this.vendaDao = vendaDao;
		this.itemVendaDao = itemVendaDao;
		this.clienteService = clienteService;
	}

	public List<Venda> listarVendasComParametro(Integer idVenda, Date dtInicial, Date dtFinal, BigDecimal valorMin,
			BigDecimal valorMax, String cnpjCliente) throws SQLException {

		return vendaDao.listarVendasComParametro(idVenda, dtInicial, dtFinal, valorMin, valorMax, cnpjCliente);

	}

	private void conectarItensDaVenda(Venda venda, List<ItemVenda> listaItensVenda) {

		if (venda == null) {
			throw new RuntimeException("A Venda é inválida e não pode ser conectada ao(s) Item(ns) vendido(s).");
		}

		if (listaItensVenda == null || listaItensVenda.size() < 1) {
			throw new RuntimeException("A lista de Itens da venda é inválida e não pode ser conectada na Venda.");
		}

		for (ItemVenda itemVenda : listaItensVenda) {
			itemVenda.setVenda(venda);
		}

	}

	public void inserirVenda(Date dtVenda, String cnpjCliente, List<ItemVenda> listaItensVenda) throws SQLException {

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

		BigDecimal valorTotal = listaItensVenda.stream()
				.map(itemVenda -> itemVenda.getPrecoUnitarioKg().multiply(itemVenda.getPesoVendidoKg()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Venda venda = new Venda(dtVenda, valorTotal, cliente);

		vendaDao.inserirVenda(venda);

		conectarItensDaVenda(venda, listaItensVenda);

		inserirListaItensVenda(listaItensVenda);

	}

	private void inserirItemVenda(ItemVenda itemVenda) throws SQLException {

		if (itemVenda == null) {
			throw new RuntimeException("Item da Venda inválido! O item da venda não pode estar vazio.");
		}

		if (itemVenda.getPesoVendidoKg().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("O peso vendido é inválido! O peso vendido deve ser maior que 0.0 Kg.");
		}

		if (itemVenda.getPrecoUnitarioKg().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("O preço unitário por quilo é inválido! O preço unitário deve ser maio que R$ 0,00/Kg.");
		}

		itemVendaDao.inserirItemVenda(itemVenda);

	}

	private void inserirListaItensVenda(List<ItemVenda> listaItensVenda) throws SQLException {

		if (listaItensVenda == null || listaItensVenda.size() == 0) {
			throw new RuntimeException("A lista de itens da venda é inválida! É necessário pelo menos um item na venda para ser cadastrado.");
		}

		for (ItemVenda itemVenda : listaItensVenda) {
			inserirItemVenda(itemVenda);
		}

	}

	public void atualizarVenda(int idVenda, String cnpjCliente, List<ItemVenda> listaItensVenda, List<ItemVenda> listaItensVendaRemovidos) throws SQLException {

		if (cnpjCliente == null || cnpjCliente.isBlank()) {
			throw new RuntimeException("CNPJ do Cliente inválido! Não é pssível cadastrar uma venda sem um cliente válido.");
		}

		if (listaItensVenda == null || listaItensVenda.size() < 1) {
			throw new RuntimeException("Itens da Venda inválidos! É necessário informar corretamente os itens da venda, para a atualização. Caso não possua itens, delete a venda.");
		}

		Cliente cliente = clienteService.buscarClientePorCnpj(cnpjCliente);
		BigDecimal valorTotal = listaItensVenda.stream()
				.map(itemVenda -> itemVenda.getPrecoUnitarioKg().multiply(itemVenda.getPesoVendidoKg()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		Venda venda = new Venda();
		venda.setId(idVenda);
		venda.setCliente(cliente);
		venda.setValorTotal(valorTotal);

		conectarItensDaVenda(venda, listaItensVenda);

		vendaDao.atualizarVenda(venda);

		for (ItemVenda itemVenda : listaItensVenda) {
			salvarItemVenda(itemVenda);
		}

		if (listaItensVendaRemovidos != null && listaItensVendaRemovidos.size() > 0) {
			for (ItemVenda itemVenda : listaItensVendaRemovidos) {
				deletarItemVenda(itemVenda);
			}
		}

	}

	public void deletarItemVenda(ItemVenda itemVenda) throws SQLException {

		if (itemVenda != null) {

			itemVendaDao.deletarItemVenda(itemVenda.getId());

		}

	}

	public void atualizarItemVenda(ItemVenda itemVenda) throws SQLException {

		if (itemVenda == null) {
			throw new RuntimeException("Não é possível atualizar um item da venda inválido!");
		}

		if (itemVenda.getPesoVendidoKg().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Não é possível atualizar um item da venda com o peso vendido inválido!");
		}

		if (itemVenda.getPrecoUnitarioKg().compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Não é possível atualizar um item da venda com o preço unitário inválido!");
		}

		if (itemVenda.getTipoMaterial() == null) {
			throw new RuntimeException("Não é possível atualizar um item da venda com o Tipo de Material inválido!");
		}

		if (itemVenda.getVenda() == null) {
			throw new RuntimeException("Não é possível atualizar um item da venda com a Venda inválida!");
		}

		itemVendaDao.atualizarItemVenda(itemVenda);

	}

	private void salvarItemVenda(ItemVenda itemVenda) throws SQLException {

		if (itemVenda == null) {
			throw new RuntimeException("Não é possível salvar um item da venda inválido!");
		}

		// verificação para operação
		if (itemVenda.getId() == 0) { // novo

			inserirItemVenda(itemVenda);

		} else {

			atualizarItemVenda(itemVenda);

		}

	}

	public void deletarVenda(int id) throws SQLException {

		vendaDao.deletarVenda(id);
		itemVendaDao.deletarItensVendaPorVenda(id);

	}

	public List<Venda> listarVendas() throws SQLException {

		return vendaDao.listarVendas();

	}

	public Venda buscarVendaPorId(int id) throws SQLException {

		Venda venda = new Venda();
		venda.setId(id);

		vendaDao.buscarVendaPorId(venda);

		return venda;

	}

	public List<Venda> buscarVendaPorCliente(String cnpjCliente) throws SQLException {

		String cnpj = Formatador.clearDoc(cnpjCliente);

		if (!Validador.isCnpj(cnpj)) {

		}

		Cliente cliente = new Cliente();
		cliente.setCnpj(cnpj);

		return vendaDao.listarVendasPorCliente(cliente);

	}

	public List<ItemVenda> listarItensVendaPorVenda(Venda venda) throws SQLException {

		if (venda == null) {
			throw new RuntimeException("Venda inválida! Informe uma venda válida para buscar seus itens.");
		}

		return itemVendaDao.listarItensVendaPorVenda(venda);

	}

	public List<ItemVenda> listarItensVendaPorTipoMaterial(TipoMaterial tipoMaterial) throws SQLException {

		if (tipoMaterial == null) {
			throw new RuntimeException("Tipo de Material inválido! Informe um tipo de material válido para buscar itens de venda do material");
		}

		return itemVendaDao.listarItensVendaPorTipoMaterial(tipoMaterial);

	}

	public BigDecimal somarValorTotalVendasPorDatas(Date dtInicio, Date dtFim) throws SQLException {

		if (dtInicio == null || dtFim == null) {
			throw new RuntimeException("Data(s) de busca inválida(s)!");
		}

		return vendaDao.somarValorTotalVendasPorDatas(dtInicio, dtFim);

	}

	public BigDecimal somarPesoVendidoItemVendaPorDatas(Date dtInicio, Date dtFim) throws SQLException {

		if (dtInicio == null || dtFim == null) {
			throw new RuntimeException("Data(s) de busca inválida(s)!");
		}

		return itemVendaDao.somarPesoVendidoItemVendaPorDatas(dtInicio, dtFim);

	}

}
