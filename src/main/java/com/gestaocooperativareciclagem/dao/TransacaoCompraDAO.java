package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Conexao;

public class TransacaoCompraDAO {
	
	public void inserirTransacaoCompra(TransacaoCompra transacaoCompra) {
		
		String insert = "insert into transacao_compra (valor_total_calculado_transacaocompra, status_pagamento_transacaocompra, "
				+ "dtCalculo_transacaocompra, dtPagamento_transacaocompra, lote_bruto) "
				+ "values (?, ?, ?, ?, ?);";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setDouble(1, transacaoCompra.getValorTotalCalculado());
			pst.setString(2, transacaoCompra.getStatus().name());
			pst.setDate(3, transacaoCompra.getDtCalculo());
			pst.setDate(4, transacaoCompra.getDtPagamento());
			pst.setInt(5, transacaoCompra.getLoteBruto().getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<TransacaoCompra> listarTransacoesCompra() {
		
		List<TransacaoCompra> listaTransacaoCompra = new ArrayList<>();
		
		String select = "select * from info_transacao_compra";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idTransacaoCompra = rset.getInt("id_transacaocompra");
				double valorTotalCalculado = rset.getDouble("valor_total_calculado_transacaocompra");
				StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
						.fromDescricao(rset.getString("status_pagamento_transacaocompra"));
				Date dtCalculo = rset.getDate("dtCalculo_transacaocompra");
				Date dtPagamento = rset.getDate("dtPagamento_transacaocompra");
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				double pesoEntradaKg = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.fromDescricao(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
				
				Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKg, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				listaTransacaoCompra.add(new TransacaoCompra(idTransacaoCompra, valorTotalCalculado, statusPagamento, dtCalculo, dtPagamento, loteBruto));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaTransacaoCompra;
	}
	
	public void buscarTransacaoCompra(TransacaoCompra transacaoCompra) {
		
		String select = "select * from info_transacao_compra where id_transacaocompra = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, transacaoCompra.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				transacaoCompra.setId( rset.getInt("id_transacaocompra") );
				transacaoCompra.setValorTotalCalculado( rset.getDouble("valor_total_calculado_transacaocompra") );
				
				StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
						.fromDescricao(rset.getString("status_pagamento_transacaocompra"));
				transacaoCompra.setStatus(statusPagamento);
				
				transacaoCompra.setDtCalculo( rset.getDate("dtCalculo_transacaocompra") );
				transacaoCompra.setDtPagamento( rset.getDate("dtPagamento_transacaocompra") );
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				double pesoEntradaKg = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.fromDescricao(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
				
				Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKg, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				transacaoCompra.setLoteBruto(loteBruto);
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarTransacaoCompra(TransacaoCompra transacaoCompra) {
		
		String update = "update transacao_compra set valor_total_calculado = ?, status_pagamento_transacaocompra = ?, "
				+ "dtCalculo_transacaocompra = ?, dtPagamento_transacaocompra = ?, "
				+ "lote_bruto = ? where id_transacaocompra = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setDouble(1, transacaoCompra.getValorTotalCalculado());
			pst.setString(2, transacaoCompra.getStatus().name());
			pst.setDate(3, transacaoCompra.getDtCalculo());
			pst.setDate(4, transacaoCompra.getDtPagamento());
			pst.setInt(5, transacaoCompra.getLoteBruto().getId());
			pst.setInt(6, transacaoCompra.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarTransacaoCompra(int id) {
		
		String delete = "delete from transacao_compra where id_transacaocompra = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(delete);
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
