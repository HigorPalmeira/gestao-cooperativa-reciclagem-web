package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	public Long contarTransacaoCompraPorStatus(StatusPagamentoTransacaoCompra status) throws SQLException {
		
		String count = "select coalesce(count(*), 0) from transacao_compra where status_pagamento_transacaocompra = ?";
		
		Long contagem = 0L;
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(count);) {
			
			pst.setString(1, status.name());
			
			ResultSet rset = pst.executeQuery();
			
			if (rset.next()) {
				contagem = rset.getLong(1);
			}
			
			rset.close();
			
		}
		
		return contagem;
		
	}
	
	public BigDecimal somarValorTotalTransacaoCompraPorStatus(StatusPagamentoTransacaoCompra status) throws SQLException {
		
		String sum = "select coalesce(sum(valor_total_calculado_transacaocompra), 0) from transacao_compra where status_pagamento_transacaocompra = ?";
		
		BigDecimal valorTotal = BigDecimal.ZERO;
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(sum);) {
			
			pst.setString(1, status.name());
			
			ResultSet rset = pst.executeQuery();
			
			if (rset.next()) {
				valorTotal = rset.getBigDecimal(1);
			}
			
			rset.close();
			
		}
		
		return valorTotal;
		
	}
	
	public void inserirTransacaoCompra(TransacaoCompra transacaoCompra) {
		
		String insert = "insert into transacao_compra (valor_total_calculado_transacaocompra, status_pagamento_transacaocompra, "
				+ "dtCalculo_transacaocompra, dtPagamento_transacaocompra, lote_bruto) "
				+ "values (?, ?, ?, ?, ?);";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setBigDecimal(1, transacaoCompra.getValorTotalCalculado());
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
				BigDecimal valorTotalCalculado = rset.getBigDecimal("valor_total_calculado_transacaocompra");
				StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
						.valueOf(rset.getString("status_pagamento_transacaocompra"));
				Date dtCalculo = rset.getDate("dtCalculo_transacaocompra");
				Date dtPagamento = rset.getDate("dtPagamento_transacaocompra");
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				BigDecimal pesoEntradaKg = rset.getBigDecimal("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
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
	
	public List<TransacaoCompra> listarTransacoesCompraPorLoteBruto(LoteBruto loteBrutoBuscado) {
		
		List<TransacaoCompra> listaTransacaoCompra = new ArrayList<>();
		
		String select = "select * from info_transacao_compra where id_lotebruto = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, loteBrutoBuscado.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idTransacaoCompra = rset.getInt("id_transacaocompra");
				BigDecimal valorTotalCalculado = rset.getBigDecimal("valor_total_calculado_transacaocompra");
				StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
						.valueOf(rset.getString("status_pagamento_transacaocompra"));
				Date dtCalculo = rset.getDate("dtCalculo_transacaocompra");
				Date dtPagamento = rset.getDate("dtPagamento_transacaocompra");
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				BigDecimal pesoEntradaKg = rset.getBigDecimal("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
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
	
	public List<TransacaoCompra> listarTransacoesCompraPorStatusPagamento(StatusPagamentoTransacaoCompra status) {
		
		List<TransacaoCompra> listaTransacaoCompra = new ArrayList<>();
		
		String select = "select * from info_transacao_compra where status_pagamento_transacaocompra = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, status.name());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idTransacaoCompra = rset.getInt("id_transacaocompra");
				BigDecimal valorTotalCalculado = rset.getBigDecimal("valor_total_calculado_transacaocompra");
				StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
						.valueOf(rset.getString("status_pagamento_transacaocompra"));
				Date dtCalculo = rset.getDate("dtCalculo_transacaocompra");
				Date dtPagamento = rset.getDate("dtPagamento_transacaocompra");
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				BigDecimal pesoEntradaKg = rset.getBigDecimal("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
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
	
	public void buscarTransacaoCompraPorId(TransacaoCompra transacaoCompra) {
		
		String select = "select * from info_transacao_compra where id_transacaocompra = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, transacaoCompra.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				transacaoCompra.setId( rset.getInt("id_transacaocompra") );
				transacaoCompra.setValorTotalCalculado( rset.getBigDecimal("valor_total_calculado_transacaocompra") );
				
				StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
						.valueOf(rset.getString("status_pagamento_transacaocompra"));
				transacaoCompra.setStatus(statusPagamento);
				
				transacaoCompra.setDtCalculo( rset.getDate("dtCalculo_transacaocompra") );
				transacaoCompra.setDtPagamento( rset.getDate("dtPagamento_transacaocompra") );
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				BigDecimal pesoEntradaKg = rset.getBigDecimal("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
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
			pst.setBigDecimal(1, transacaoCompra.getValorTotalCalculado());
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

	public List<TransacaoCompra> listarTransacoesCompraComParametros(Integer paramIdTransacaoCompra, Integer paramIdLoteBruto, BigDecimal paramValorTotalCalculadoTransacaoCompra, StatusPagamentoTransacaoCompra paramStatusPagamentoTransacaoCompra) throws SQLException {
		
		List<TransacaoCompra> listaTransacoesCompra = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdTransacaoCompra, paramIdLoteBruto, paramValorTotalCalculadoTransacaoCompra, paramStatusPagamentoTransacaoCompra);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery()) {
				
				while (rset.next()) {
					
					int idTransacaoCompra = rset.getInt("id_transacaocompra");
					BigDecimal valorTotalCalculado = rset.getBigDecimal("valor_total_calculado_transacaocompra");
					StatusPagamentoTransacaoCompra statusPagamento = StatusPagamentoTransacaoCompra
							.valueOf(rset.getString("status_pagamento_transacaocompra"));
					Date dtCalculo = rset.getDate("dtCalculo_transacaocompra");
					Date dtPagamento = rset.getDate("dtPagamento_transacaocompra");
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKg = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKg, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					listaTransacoesCompra.add(new TransacaoCompra(idTransacaoCompra, valorTotalCalculado, statusPagamento, dtCalculo, dtPagamento, loteBruto));
					
				}
				
			}
			
		}
		
		return listaTransacoesCompra;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idTransacaoCompra, Integer idLoteBruto, BigDecimal valorTotalCalculadoTransacaoCompra, StatusPagamentoTransacaoCompra statusPagamentoTransacaoCompra) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from info_transacao_compra where 1=1");
		
		if (idTransacaoCompra != null && idTransacaoCompra != 0) {
			builder.append(" and id_transacaocompra = ?");
			parametros.add(idTransacaoCompra);
		}
		
		if (idLoteBruto != null && idLoteBruto != 0) {
			builder.append(" and id_lotebruto = ?");
			parametros.add(idLoteBruto);
		}
		
		if (valorTotalCalculadoTransacaoCompra != null) {
			builder.append(" and valor_total_calculado_transacaocompra = ?");
			parametros.add(valorTotalCalculadoTransacaoCompra);
		}
		
		if (statusPagamentoTransacaoCompra != null) {
			builder.append(" and status_pagamento_transacaocompra = ?");
			parametros.add(statusPagamentoTransacaoCompra.name());
		}

		return builder.toString();
		
	}
	
}
