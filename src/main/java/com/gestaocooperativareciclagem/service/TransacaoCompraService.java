package com.gestaocooperativareciclagem.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;

public class TransacaoCompraService {

	private TransacaoCompraDAO transacaoCompraDao;

	public TransacaoCompraService(TransacaoCompraDAO transacaoCompraDao) {
		this.transacaoCompraDao = transacaoCompraDao;
	}

	public List<TransacaoCompra> listarTransacoesCompraComParametro(Integer idTransacaoCompra, Integer idLoteBruto, Date dtPagamentoInicial,
			Date dtPagamentoFinal, BigDecimal valorMin, BigDecimal valorMax, StatusPagamentoTransacaoCompra statusPagamentoTransacaoCompra) throws SQLException {

		return transacaoCompraDao.listarTransacoesCompraComParametros(idTransacaoCompra, idLoteBruto, dtPagamentoInicial, dtPagamentoFinal, valorMin, valorMax, statusPagamentoTransacaoCompra);

	}

	public void inserirTransacaoCompra(BigDecimal valorTotalCalculado, StatusPagamentoTransacaoCompra status, Date dtCalculo, LoteBruto loteBruto) throws SQLException {

		// adicionar validacao no model
		TransacaoCompra transacaoCompra = new TransacaoCompra(valorTotalCalculado, status, dtCalculo, null, loteBruto);

		transacaoCompraDao.inserirTransacaoCompra(transacaoCompra);

	}

	public void atualizarTransacaoCompra(int idTransacao, BigDecimal valorTotalCalculado, StatusPagamentoTransacaoCompra status,
			Date dtCalculo, Date dtPagamento, LoteBruto loteBruto) throws SQLException {

		TransacaoCompra transacaoCompraOriginal = buscarTransacaoCompraPorId(idTransacao);

		if (transacaoCompraOriginal.getStatus().equals(StatusPagamentoTransacaoCompra.PAGO)) {
			throw new RuntimeException("Não é possível editar uma transação de compra paga!");
		}

		TransacaoCompra transacaoCompraAtualizada = new TransacaoCompra();
		transacaoCompraAtualizada.setId(idTransacao);

		transacaoCompraAtualizada.setValorTotalCalculado(
				valorTotalCalculado.compareTo(BigDecimal.ZERO) > 0
				? valorTotalCalculado
				: transacaoCompraOriginal.getValorTotalCalculado()
				);

		transacaoCompraAtualizada.setStatus(
				status != null
				? status
				: transacaoCompraOriginal.getStatus()
				);

		transacaoCompraAtualizada.setDtCalculo(transacaoCompraOriginal.getDtCalculo());
		if ( dtCalculo != null && !(dtCalculo.after(Date.valueOf(LocalDate.now()))) ) {
			transacaoCompraAtualizada.setDtCalculo(dtCalculo);
		}

		transacaoCompraAtualizada.setDtPagamento(transacaoCompraOriginal.getDtPagamento());
		if (dtPagamento != null) {
			transacaoCompraAtualizada.setDtPagamento(dtPagamento);
		}

		transacaoCompraAtualizada.setLoteBruto(
				loteBruto != null
				? loteBruto
				: transacaoCompraOriginal.getLoteBruto()
				);

		transacaoCompraDao.atualizarTransacaoCompra(transacaoCompraAtualizada);

	}

	public void deletarTransacaoCompra(int id) throws SQLException {

		transacaoCompraDao.deletarTransacaoCompra(id);

	}

	public List<TransacaoCompra> listarTransacoesCompra() throws SQLException {

		return transacaoCompraDao.listarTransacoesCompra();

	}

	public List<TransacaoCompra> listarTransacoesCompraPorLoteBruto(LoteBruto loteBruto) throws SQLException {

		if (loteBruto == null || loteBruto.getId() <= 0) {
			throw new RuntimeException("Lote Bruto inválido para busca! Informe um lote bruto válido para realizar a busca das transações de compra.");
		}

		return transacaoCompraDao.listarTransacoesCompraPorLoteBruto(loteBruto);

	}

	public List<TransacaoCompra> listarTransacoesCompraPorStatusPagamento(StatusPagamentoTransacaoCompra status) throws SQLException {

		return transacaoCompraDao.listarTransacoesCompraPorStatusPagamento(status);

	}

	public TransacaoCompra buscarTransacaoCompraPorId(int id) throws SQLException {

		TransacaoCompra transacaoCompra = new TransacaoCompra();
		transacaoCompra.setId(id);

		transacaoCompraDao.buscarTransacaoCompraPorId(transacaoCompra);

		return transacaoCompra;

	}

	public Long contarTransacaoCompraPorStatus(StatusPagamentoTransacaoCompra status) throws SQLException {

		if (status == null) {
			throw new RuntimeException("Status de Pagamento para a busca inválido.");
		}

		return transacaoCompraDao.contarTransacaoCompraPorStatus(status);

	}

	public BigDecimal somarValorTotalTransacaoCompraPorStatus(StatusPagamentoTransacaoCompra status) throws SQLException {

		if (status == null) {
			throw new RuntimeException("Status de Pagamento para a busca inválido.");
		}

		return transacaoCompraDao.somarValorTotalTransacaoCompraPorStatus(status);

	}

}
