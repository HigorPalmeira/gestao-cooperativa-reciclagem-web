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
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Conexao;

public class LoteBrutoDAO {
	
	public Long contarLoteBrutoPorData(Date dtLoteBruto) throws SQLException {
		
		String count = "select coalesce(count(*), 0) from lote_bruto where dtEntrada_lotebruto = ?";
		
		Long contagem = 0L;

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(count);) {
			
			pst.setDate(1, dtLoteBruto);
			
			try (ResultSet rset = pst.executeQuery();) {

				if (rset.next()) {
					contagem = rset.getLong(1);
				}
				
			}
			
		}
		
		return contagem;
		
	}
	
	public BigDecimal somarPesoEntradaLoteBrutoPorDatas(Date dtInicio, Date dtFim) throws SQLException {
		
		String sum = "select coalesce(sum(peso_entrada_kg_lotebruto), 0) from lote_bruto where dtEntrada_lotebruto between ? and ?";
		
		BigDecimal pesoTotal = BigDecimal.ZERO;
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(sum);) {
			
			pst.setDate(1, dtInicio);
			pst.setDate(2, dtFim);
			
			try (ResultSet rset = pst.executeQuery();) {
			
				if (rset.next()) {
					pesoTotal = rset.getBigDecimal(1);
				}
				
			}
			
		}
		
		return pesoTotal;
		
	}
	
	public void inserirLoteBruto(LoteBruto loteBruto) throws SQLException {
		
		String insert = "insert into lote_bruto (peso_entrada_kg_lotebruto, dtEntrada_lotebruto, status_lotebruto, fornecedor) values (?, ?, ?, ?)";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert);) {
			
			pst.setBigDecimal(1, loteBruto.getPesoEntradaKg());
			pst.setDate(2, loteBruto.getDtEntrada());
			pst.setString(3, loteBruto.getStatus().name());
			pst.setString(4, loteBruto.getFornecedor().getDocumento());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<LoteBruto> listarLotesBruto() throws SQLException {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					listaLotesBruto.add(new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor));
					
				}
				
			}
			
		}
		
		return listaLotesBruto;
		
	}
	
	public List<LoteBruto> listarLotesBrutoPorStatus(StatusLoteBruto status) throws SQLException {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto where status_lotebruto = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, status.name());
			
			try	(ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					listaLotesBruto.add(new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor));
					
				}
				
			}
			
		}
		
		return listaLotesBruto;
		
	}
	
	public List<LoteBruto> listarLotesBrutoPorFornecedor(Fornecedor fornecedorBuscado) throws SQLException {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto where documento_fornecedor = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, fornecedorBuscado.getDocumento());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					listaLotesBruto.add(new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor));
					
				}
				
			}
			
		}
		
		return listaLotesBruto;
		
	}
	
	public List<LoteBruto> listarLotesBrutoPorIntervaloDePesoEntrada(BigDecimal pesoEntradaInicial, BigDecimal pesoEntradaFinal) throws SQLException {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto where peso_entrada_kg_lotebruto between ? and ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setBigDecimal(1, pesoEntradaInicial);
			pst.setBigDecimal(2, pesoEntradaFinal);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					listaLotesBruto.add(new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor));
					
				}
				
			}
			
		}
		
		return listaLotesBruto;
		
	}
	
	public void buscarLoteBrutoPorId(LoteBruto loteBruto) throws SQLException {
		
		String select = "select * from info_lote_bruto where id_lotebruto = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, loteBruto.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					loteBruto.setId( rset.getInt("id_lotebruto") );
					loteBruto.setPesoEntradaKg( rset.getBigDecimal("peso_entrada_kg_lotebruto") );
					loteBruto.setDtEntrada( rset.getDate("dtEntrada_lotebruto") );
					
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					loteBruto.setStatus(statusLoteBruto);
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					loteBruto.setFornecedor(fornecedor);
					
				}
				
			}
			
		}
		
	}
	
	public void atualizarLoteBruto(LoteBruto loteBruto) throws SQLException {
		
		String update = "update lote_bruto set peso_entrada_kg_lotebruto=?, status_lotebruto=?, fornecedor=? where id_lotebruto=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update)) {
		
			pst.setBigDecimal(1, loteBruto.getPesoEntradaKg());
			pst.setString(2, loteBruto.getStatus().name());
			pst.setString(3, loteBruto.getFornecedor().getDocumento());
			pst.setInt(4, loteBruto.getId());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public void deletarLoteBruto(int id) throws SQLException {
		
		String delete = "delete from lote_bruto where id_lotebruto=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
		}
		
	}

	public List<LoteBruto> listarLotesBrutoComParametro(Integer paramIdLoteBruto, String paramDocumentoFornecedor, StatusLoteBruto paramStatusLoteBruto, BigDecimal paramPesoMin, BigDecimal paramPesoMax, Date paramDtInicial, Date paramDtFinal) throws SQLException {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdLoteBruto, paramDocumentoFornecedor, paramStatusLoteBruto, paramPesoMin, paramPesoMax, paramDtInicial, paramDtFinal);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery()) {
				
				while (rset.next()) {
					
					int idLoteBruto = rset.getInt("id_lotebruto");
					BigDecimal pesoEntradaKgLoteBruto = rset.getBigDecimal("peso_entrada_kg_lotebruto");
					Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
					StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
					
					String documentoFornecedor = rset.getString("documento_fornecedor");
					String nomeFornecedor = rset.getString("nome_fornecedor");
					TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
					
					Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
					
					listaLotesBruto.add(new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor));
					
				}
				
			}
			
		}
		
		return listaLotesBruto;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idLoteBruto, String documentoFornecedor, 
			StatusLoteBruto statusLoteBruto, BigDecimal pesoMin, BigDecimal pesoMax, Date dtInicial, Date dtFinal) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from info_lote_bruto where 1=1");
		
		if (idLoteBruto != null && idLoteBruto != 0) {
			builder.append(" and id_lotebruto = ?");
			parametros.add(idLoteBruto);
		}
		
		if (documentoFornecedor != null && !documentoFornecedor.isBlank()) {
			builder.append(" and documento_fornecedor like ?");
			parametros.add("%" + documentoFornecedor.trim() + "%");
		}
		
		if (statusLoteBruto != null) {
			builder.append(" and status_lotebruto = ?");
			parametros.add(statusLoteBruto.name());
		}
		
			
		if (pesoMin != null) {
			builder.append(" and peso_entrada_kg_lotebruto >= ?");
			parametros.add(pesoMin);
		}
			
		if (pesoMax != null) {
			builder.append(" and peso_entrada_kg_lotebruto <= ?");
			parametros.add(pesoMax);
		}
		
		
		if (dtInicial != null) {
			builder.append(" and dtEntrada_lotebruto >= ?");
			parametros.add(dtInicial);
		}
		
		if (dtFinal != null) {
			builder.append(" and dtEntrada_lotebruto <= ?");
			parametros.add(dtFinal);
		}
		
		builder.append(" order by dtEntrada_lotebruto desc");
		
		return builder.toString();
		
	}
	
}
