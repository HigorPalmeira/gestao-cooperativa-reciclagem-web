package com.gestaocooperativareciclagem.service;

import java.sql.Date;
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
		
		TransacaoCompra transacaoCompra = buscarTransacaoCompraPorId(idTransacao);
		
		TransacaoCompra transacaoCompraAtualizada = new TransacaoCompra(idTransacao, valorTotalCalculado, status, 
				dtCalculo, dtPagamento, loteBruto);
		
		
		
	}
	
	public void deletarTransacaoCompra(int id) {
		
		transacaoCompraDao.deletarTransacaoCompra(id);
		
	}
	
	public List<TransacaoCompra> listarTransacoesCompra() {
		
		return transacaoCompraDao.listarTransacoesCompra();
		
	}
	
	public TransacaoCompra buscarTransacaoCompraPorId(int id) {
		
		TransacaoCompra transacaoCompra = new TransacaoCompra();
		transacaoCompra.setId(id);
		
		transacaoCompraDao.buscarTransacaoCompraPorId(transacaoCompra);
		
		return transacaoCompra;
		
	}

}
