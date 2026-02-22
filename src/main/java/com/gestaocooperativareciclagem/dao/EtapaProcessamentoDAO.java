package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public void inserirEtapaProcessamento(EtapaProcessamento etapaProcessamento) {
		
		String insert = "insert into etapa_processamento (lote_processado, categoria_processamento, dtProcessamento_etapaprocessamento, status_processamento_etapaprocessamento) "
				+ "values (?, ?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(2, etapaProcessamento.getCategoriaProcessamento().getId());
			pst.setDate(3, etapaProcessamento.getDtProcessamento());
			pst.setString(4, etapaProcessamento.getStatus());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamento() {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
				
				listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorStatus(String status) {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where status_processamento_etapaprocessamento = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, status);
			
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
				
				listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorLoteProcessado(LoteProcessado loteProcessadoBuscado) {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where id_loteprocessado = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, loteProcessadoBuscado.getId());
			
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
				
				listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorCategoriaProcessamento(CategoriaProcessamento categoriaProcessamentoBuscada) {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where categoria_processamento = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, categoriaProcessamentoBuscada.getId());
			
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
				
				listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaEtapaProcessamento;
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorDataProcessamento(Date dataInicial, Date dataFinal) {
		
		List<EtapaProcessamento> listaEtapaProcessamento = new ArrayList<>();
		
		String select = "select * from info_etapa_processamento where dtProcessamento_etapaprocessamento between ? and ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDate(1, dataInicial);
			pst.setDate(2, dataFinal);
			
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				LoteProcessado loteProcessado = new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto);
				
				listaEtapaProcessamento.add(new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, statusProcessamento));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaEtapaProcessamento;
		
	}
	
	public void buscarEtapaProcessamentoPorLoteProcessadoECategoriaProcessamento(EtapaProcessamento etapaProcessamento) {
		
		String select = "select * from info_etapa_processamento where id_loteprocessado = ? and id_categoriaprocessamento = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(2, etapaProcessamento.getCategoriaProcessamento().getId());
			
			ResultSet rset = pst.executeQuery();
			
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf( rset.getString("status_lotebruto") );
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
				Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
				
				etapaProcessamento.setLoteProcessado( new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto) );
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void atualizarEtapaProcessamento(EtapaProcessamento etapaProcessamento) {
		
		String update = "update etapa_processamento set dtProcessamento_etapaprocessamento = ?, status_processamento_etapaprocessamento = ? where lote_processado = ? and categoria_processamento = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setDate(1, etapaProcessamento.getDtProcessamento());
			pst.setString(2, etapaProcessamento.getStatus());
			pst.setInt(3, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(4, etapaProcessamento.getCategoriaProcessamento().getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarEtapaProcessamento(EtapaProcessamento etapaProcessamento) {
		
		String delete = "delete from etapa_processamento where lote_processado = ? and categoria_processamento = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(delete);
			pst.setInt(1, etapaProcessamento.getLoteProcessado().getId());
			pst.setInt(2, etapaProcessamento.getCategoriaProcessamento().getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
