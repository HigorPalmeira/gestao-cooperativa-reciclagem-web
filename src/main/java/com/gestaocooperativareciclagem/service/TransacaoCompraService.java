package com.gestaocooperativareciclagem.service;

import java.sql.Date;
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
	
	public void inserirTransacaoCompra(double valorTotalCalculado, StatusPagamentoTransacaoCompra status, Date dtCalculo, LoteBruto loteBruto) {
		
		// adicionar validacao no model
		TransacaoCompra transacaoCompra = new TransacaoCompra(valorTotalCalculado, status, dtCalculo, null, loteBruto);
		
		transacaoCompraDao.inserirTransacaoCompra(transacaoCompra);
		
	}
	
	public void atualizarTransacaoCompra(int idTransacao, double valorTotalCalculado, StatusPagamentoTransacaoCompra status, 
			Date dtCalculo, Date dtPagamento, LoteBruto loteBruto) {
		
		TransacaoCompra transacaoCompraOriginal = buscarTransacaoCompraPorId(idTransacao);
		
		if (transacaoCompraOriginal.getStatus().equals(StatusPagamentoTransacaoCompra.PAGO)) {
			throw new RuntimeException("Não é possível editar uma transação de compra paga!");
		}
		
		TransacaoCompra transacaoCompraAtualizada = new TransacaoCompra();
		transacaoCompraAtualizada.setId(idTransacao);	
		
		transacaoCompraAtualizada.setValorTotalCalculado( 
				valorTotalCalculado > 0 
				? valorTotalCalculado 
				: transacaoCompraOriginal.getValorTotalCalculado()
				);
		
		transacaoCompraAtualizada.setStatus(
				status != null
				? status
				: transacaoCompraOriginal.getStatus()
				);
		
		transacaoCompraAtualizada.setDtCalculo(transacaoCompraAtualizada.getDtCalculo());
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
	
	public void deletarTransacaoCompra(int id) {
		
		transacaoCompraDao.deletarTransacaoCompra(id);
		
	}
	
	public List<TransacaoCompra> listarTransacoesCompra() {
		
		return transacaoCompraDao.listarTransacoesCompra();
		
	}
	
	public List<TransacaoCompra> listarTransacoesCompraPorLoteBruto(LoteBruto loteBruto) {
		
		if (loteBruto == null || loteBruto.getId() <= 0) {
			throw new RuntimeException("Lote Bruto inválido para busca! Informe um lote bruto válido para realizar a busca das transações de compra.");
		}
		
		return transacaoCompraDao.listarTransacoesCompraPorLoteBruto(loteBruto);
		
	}
	
	public List<TransacaoCompra> listarTransacoesCompraPorStatusPagamento(StatusPagamentoTransacaoCompra status) {
		
		return transacaoCompraDao.listarTransacoesCompraPorStatusPagamento(status);
		
	}
	
	public TransacaoCompra buscarTransacaoCompraPorId(int id) {
		
		TransacaoCompra transacaoCompra = new TransacaoCompra();
		transacaoCompra.setId(id);
		
		transacaoCompraDao.buscarTransacaoCompraPorId(transacaoCompra);
		
		return transacaoCompra;
		
	}

}
