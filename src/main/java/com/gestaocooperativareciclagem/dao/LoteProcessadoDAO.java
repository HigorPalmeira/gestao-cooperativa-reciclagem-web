package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Conexao;

public class LoteProcessadoDAO {
	
	public BigDecimal somarPesoTotalLoteProcessadoPorEtapaProcessamento(String etapaProcessamento) throws SQLException {
		
		String sum = "select coalesce(sum(peso_atual_kg_loteprocessado), 0) from info_etapa_processamento where nome_categoriaprocessamento = ?";
		
		BigDecimal pesoTotal = BigDecimal.ZERO;
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(sum);) {
			
			pst.setString(1, etapaProcessamento);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				if (rset.next()) {
					pesoTotal = rset.getBigDecimal(1);
				}

			}
			
		}
		
		return pesoTotal;
		
	}

	public void inserirLoteProcessado(LoteProcessado loteProcessado) throws SQLException {
		
		String insert = "insert into lote_processado (peso_atual_kg_loteprocessado, dtCriacao_loteprocessado, tipo_material, lote_bruto) "
				+ "values (?, ?, ?, ?)";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);) {
			
			pst.setBigDecimal(1, loteProcessado.getPesoAtualKg());
			pst.setDate(2, loteProcessado.getDtCriacao());
			pst.setInt(3, loteProcessado.getTipoMaterial().getId());
			pst.setInt(4, loteProcessado.getLoteBruto().getId());
			
			int linhasAfetadas = pst.executeUpdate();
			
			if (linhasAfetadas > 0) {
				
				try (ResultSet rset = pst.getGeneratedKeys();) {
					
					if (rset.next()) {
						loteProcessado.setId(rset.getInt(1));
					}
					
				}
				
			}
			
		}
		
	}
	
	public List<LoteProcessado> listarLotesProcessado() throws SQLException {
	
		List<LoteProcessado> listaLoteProcessado = new ArrayList<>();
		
		String select = "select * from info_lote_processado";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);

					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					listaLoteProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
					
				}
				
			}
			
		}
		
		return listaLoteProcessado;
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorTipoMaterial(TipoMaterial tipoMaterialBuscado) throws SQLException {
		
		List<LoteProcessado> listaLoteProcessado = new ArrayList<>();
		
		String select = "select * from info_lote_processado where id_tipomaterial = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, tipoMaterialBuscado.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);

					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					listaLoteProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
					
				}
				
			}
			
		}
		
		return listaLoteProcessado;
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorLoteBruto(LoteBruto loteBrutoBuscado) throws SQLException {
		
		List<LoteProcessado> listaLoteProcessado = new ArrayList<>();
		
		String select = "select * from info_lote_processado where id_lotebruto = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, loteBrutoBuscado.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);

					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					listaLoteProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
					
				}
				
			}
			
		}
		
		return listaLoteProcessado;
		
	}
	
	public void buscarLoteProcessadoPorId(LoteProcessado loteProcessado) throws SQLException {
		
		String select = "select * from info_lote_processado where id_loteprocessado = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, loteProcessado.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					loteProcessado.setId( rset.getInt("id_loteprocessado") );
					loteProcessado.setPesoAtualKg( rset.getBigDecimal("peso_atual_kg_loteprocessado") );
					loteProcessado.setDtCriacao( rset.getDate("dtCriacao_loteprocessado") );
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					loteProcessado.setTipoMaterial(tipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);

					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					loteProcessado.setLoteBruto(loteBruto);
					
				}
				
			}
			
		}
		
	}
	
	public void atualizarLoteProcessado(LoteProcessado loteProcessado) throws SQLException {
		
		String update = "update lote_processado set peso_atual_kg_loteprocessado = ? "
				+ "where id_loteprocessado = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {
			
			pst.setBigDecimal(1, loteProcessado.getPesoAtualKg());
			pst.setInt(2, loteProcessado.getId());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public void deletarLoteProcessado(int id) throws SQLException {
		
		String delete = "delete from lote_processado where id_loteprocessado = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoComParametro(Integer paramIdLoteProcessado, Integer paramIdLoteBruto, Integer paramIdTipoMaterial, BigDecimal paramPesoAtualLoteProcessado, Date paramDtCriacaoLoteProcessado) throws SQLException {
		
		List<LoteProcessado> listaLotesProcessado = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdLoteProcessado, paramIdLoteBruto, paramIdTipoMaterial, paramPesoAtualLoteProcessado, paramDtCriacaoLoteProcessado);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while (rset.next()) {
					
					int idLoteProcessado = rset.getInt("id_loteprocessado");
					BigDecimal pesoAtualKgLoteProcessado = rset.getBigDecimal("peso_atual_kg_loteprocessado");
					Date dtCriacaoLoteProcessado = rset.getDate("dtCriacao_loteprocessado");
					
					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.valueOf(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);

					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
					
					listaLotesProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
					
				}
				
			}
			
		}
		
		return listaLotesProcessado;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idLoteProcessado, Integer idLoteBruto, Integer idTipoMaterial, BigDecimal pesoAtualLoteProcessado, Date dtCriacaoLoteProcessado) {
	
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from info_lote_processado where 1=1");
		
		if (idLoteProcessado != null && idLoteProcessado != 0) {
			builder.append(" and id_loteprocessado = ?");
			parametros.add(idLoteProcessado);
		}
		
		if (idLoteBruto != null && idLoteBruto != 0) {
			builder.append(" and id_lotebruto = ?");
			parametros.add(idLoteBruto);
		}
		
		if (idTipoMaterial != null && idTipoMaterial != 0) {
			builder.append(" and id_tipomaterial = ?");
			parametros.add(idTipoMaterial);
		}
		
		if (pesoAtualLoteProcessado != null) {
			builder.append(" and peso_atual_kg_loteprocessado = ?");
			parametros.add(pesoAtualLoteProcessado);
		}
		
		if (dtCriacaoLoteProcessado != null) {
			builder.append(" and dtCriacao_loteprocessado = ?");
			parametros.add(dtCriacaoLoteProcessado);
		}
		
		builder.append(" order by dtCriacao_loteprocessado desc");
		
		return builder.toString();
		
	}
	
}
