package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Conexao;

public class EtapaProcessamentoDAO {

	public void inserirEtapaProcessamento(EtapaProcessamento etapaProcessamento) throws SQLException {
		
		String insert = "insert into etapa_processamento (lote_processado, categoria_processamento, dtProcessamento_etapaprocessamento, status_processamento_etapaprocessamento) "
				+ "values (?, ?, ?, ?)";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert);) {
			
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(2, etapaProcessamento.getCategoriaProcessamento().getId());
			pst.setDate(3, etapaProcessamento.getDtProcessamento());
			pst.setString(4, etapaProcessamento.getStatus());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamento() throws SQLException {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorStatus(String status) throws SQLException {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where status_processamento_etapaprocessamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, status);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorLoteProcessado(LoteProcessado loteProcessadoBuscado) throws SQLException {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where id_loteprocessado = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, loteProcessadoBuscado.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorCategoriaProcessamento(CategoriaProcessamento categoriaProcessamentoBuscada) throws SQLException {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where categoria_processamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, categoriaProcessamentoBuscada.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorDataProcessamento(Date dataInicial, Date dataFinal) throws SQLException {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where dtProcessamento_etapaprocessamento between ? and ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setDate(1, dataInicial);
			pst.setDate(2, dataFinal);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorTipoMaterialENaoConcluidas(TipoMaterial tipoMaterialBuscado) throws SQLException {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where id_tipomaterial = ? and not status_processamento_etapaprocessamento = 'Concluído';";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, tipoMaterialBuscado.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while (rset.next()) {
				
				Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
				String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
				
				int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
				String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
				String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
				CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
				Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
				Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
				
				listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
				
			}
			
		}

		return listaEtapaProcessamento;
		
	}
	
	public void buscarEtapaProcessamentoPorLoteProcessadoECategoriaProcessamento(EtapaProcessamento etapaProcessamento) throws SQLException {
		
		String select = "select * from info_etapa_processamento where id_loteprocessado = ? and id_categoriaprocessamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(2, etapaProcessamento.getCategoriaProcessamento().getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					etapaProcessamento.setDtProcessamento( rset.getDate("dtProcessamento_etapaprocessamento") );
					etapaProcessamento.setStatus( rset.getString("status_processamento_etapaprocessamento") );
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					etapaProcessamento.setCategoriaProcessamento( new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento) );
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					
					etapaProcessamento.setLoteProcessado( new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto) );
					
				}
				
			}
			
		}

	}
	
	public void buscarEtapaProcessamentoAtualPorLoteProcessado(EtapaProcessamento etapaProcessamento) throws SQLException {
		
		String select = "select * from info_etapa_processamento where id_loteprocessado = ? order by dtProcessamento_etapaprocessamento desc limit 1";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					etapaProcessamento.setDtProcessamento( rset.getDate("dtProcessamento_etapaprocessamento") );
					etapaProcessamento.setStatus( rset.getString("status_processamento_etapaprocessamento") );
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					etapaProcessamento.setCategoriaProcessamento( new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento) );
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					
					etapaProcessamento.setLoteProcessado( new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto) );
					
				}
				
			}
			
		}

	}
	
	public void atualizarEtapaProcessamento(EtapaProcessamento etapaProcessamento) throws SQLException {
		
		String update = "update etapa_processamento set dtProcessamento_etapaprocessamento = ?, status_processamento_etapaprocessamento = ? where lote_processado = ? and categoria_processamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {
			
			pst.setDate(1, etapaProcessamento.getDtProcessamento());
			pst.setString(2, etapaProcessamento.getStatus());
			pst.setInt(3, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(4, etapaProcessamento.getCategoriaProcessamento().getId());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public void deletarEtapaProcessamento(EtapaProcessamento etapaProcessamento) throws SQLException {
		
		String delete = "delete from etapa_processamento where lote_processado = ? and categoria_processamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(2, etapaProcessamento.getCategoriaProcessamento().getId());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoComParametro(Integer paramIdLoteProcessado, Integer paramIdCategoriaProcessamento, Integer paramIdTipoMaterial, String paramStatus, Date paramDtProcessamento) throws SQLException {
		
		List<EtapaProcessamento> listaEtapasProcessamento = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdLoteProcessado, paramIdCategoriaProcessamento, paramIdTipoMaterial, paramStatus, paramDtProcessamento);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapasProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
			
		}
		
		return listaEtapasProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoParaLoteProcessadoComParametro(Integer paramIdLoteProcessado, Integer paramIdLoteBruto, Integer paramIdCategoriaProcessamento, 
			Integer paramIdTipoMaterial, BigDecimal paramPesoMin, BigDecimal paramPesoMax, Date paramDtInicial, Date paramDtFinal) throws SQLException {
		
		List<EtapaProcessamento> listaEtapasProcessamento = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdLoteProcessado, paramIdLoteBruto, paramIdCategoriaProcessamento, paramIdTipoMaterial, paramPesoMin, paramPesoMax, paramDtInicial, paramDtFinal);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					Date dtProcessamento = rset.getDate("dtProcessamento_etapaprocessamento");
					String statusProcessamento = rset.getString("status_processamento_etapaprocessamento");
					
					int idCategoriaProcessamento = rset.getInt("id_categoriaprocessamento");
					String nomeCategoriaProcessamento = rset.getString("nome_categoriaprocessamento");
					String descricaoCategoriaProcessamento = rset.getString("descricao_categoriaprocessamento");				
					CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(idCategoriaProcessamento, nomeCategoriaProcessamento, descricaoCategoriaProcessamento);
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf( rset.getString("tipo_fornecedor") );
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
					
					listaEtapasProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
					
				}
				
			}
			
			
		}
		
		return listaEtapasProcessamento;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idLoteProcessado, Integer idCategoriaProcessamento, Integer idTipoMaterial, String status, Date dtProcessamento) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from info_etapa_processamento where 1=1");
		
		if (idLoteProcessado != null && idLoteProcessado != 0) {
			builder.append(" and id_loteprocessado = ?");
			parametros.add(idLoteProcessado);
		}
		
		if (idCategoriaProcessamento != null && idCategoriaProcessamento != 0) {
			builder.append(" and id_categoriaprocessamento = ?");;
			parametros.add(idCategoriaProcessamento);
		}
		
		if (idTipoMaterial != null && idTipoMaterial != 0) {
			builder.append(" and id_tipomaterial = ?");
			parametros.add(idTipoMaterial);
		}
		
		if (status != null && !status.isBlank()) {
			builder.append(" and status_processamento_etapaprocessamento like ?");
			parametros.add("%" + status.trim() + "%");
		}
		
		if (dtProcessamento != null) {
			builder.append(" and dtProcessamento_etapaprocessamento = ?");
			parametros.add(dtProcessamento);
		}
		
		builder.append(" order by dtProcessamento_etapaprocessamento desc");
		
		return builder.toString();
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idLoteProcessado, Integer idLoteBruto, Integer idCategoriaProcessamento, 
			Integer idTipoMaterial, BigDecimal pesoMin, BigDecimal pesoMax, Date dtInicial, Date dtFinal) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from info_etapa_processamento where 1=1");
		
		if (idLoteProcessado != null && idLoteProcessado != 0) {
			builder.append(" and id_loteprocessado = ?");
			parametros.add(idLoteProcessado);
		}
		
		if (idCategoriaProcessamento != null && idCategoriaProcessamento != 0) {
			builder.append(" and id_categoriaprocessamento = ?");;
			parametros.add(idCategoriaProcessamento);
		}
		
		if (idTipoMaterial != null && idTipoMaterial != 0) {
			builder.append(" and id_tipomaterial = ?");
			parametros.add(idTipoMaterial);
		}
		
		if (pesoMin != null) {
			builder.append(" and peso_atual_kg_loteprocessado >= ?");
			parametros.add(pesoMin);
		}
		
		if (pesoMax != null) {
			builder.append(" and peso_atual_kg_loteprocessado <= ?");
			parametros.add(pesoMax);
		}
		
		if (dtInicial != null) {
			builder.append(" and dtCriacao_loteprocessado >= ?");
			parametros.add(dtInicial);
		}
		
		if (dtFinal != null) {
			builder.append(" and dtCriacao_loteprocessado <= ?");
			parametros.add(dtFinal);
		}
		
		builder.append(" order by dtCriacao_loteprocessado desc");
		
		return builder.toString();
	}
	
}
