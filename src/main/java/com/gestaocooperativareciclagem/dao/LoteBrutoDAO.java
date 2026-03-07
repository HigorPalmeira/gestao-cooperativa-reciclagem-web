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
			
			ResultSet rset = pst.executeQuery();
			
			if (rset.next()) {
				contagem = rset.getLong(1);
			}
			
			rset.close();
			
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
			
			ResultSet rset = pst.executeQuery();
			
			if (rset.next()) {
				pesoTotal = rset.getBigDecimal(1);
			}
			
			rset.close();
			
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
	
	public List<LoteBruto> listarLotesBruto() {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
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
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLotesBruto;
		
	}
	
	public List<LoteBruto> listarLotesBrutoPorStatus(StatusLoteBruto status) {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto where status_lotebruto = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, status.name());
			
			ResultSet rset = pst.executeQuery();
			
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
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLotesBruto;
		
	}
	
	public List<LoteBruto> listarLotesBrutoPorFornecedor(Fornecedor fornecedorBuscado) {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto where documento_fornecedor = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, fornecedorBuscado.getDocumento());
			
			ResultSet rset = pst.executeQuery();
			
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
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLotesBruto;
		
	}
	
	public List<LoteBruto> listarLotesBrutoPorIntervaloDePesoEntrada(BigDecimal pesoEntradaInicial, BigDecimal pesoEntradaFinal) {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto where peso_entrada_kg_lotebruto between ? and ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setBigDecimal(1, pesoEntradaInicial);
			pst.setBigDecimal(2, pesoEntradaFinal);
			
			ResultSet rset = pst.executeQuery();
			
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
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLotesBruto;
		
	}
	
	public void buscarLoteBrutoPorId(LoteBruto loteBruto) {
		
		String select = "select * from info_lote_bruto where id_lotebruto = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, loteBruto.getId());
			
			ResultSet rset = pst.executeQuery();
			
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
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public void deletarLoteBruto(int id) {
		
		String delete = "delete from lote_bruto where id_lotebruto=?";
		
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

	public List<LoteBruto> listarLotesBrutoComParametro(Integer paramIdLoteBruto, String paramDocumentoFornecedor, StatusLoteBruto paramStatusLoteBruto, BigDecimal paramPesoEntrada, Date paramDtEntradaLoteBruto) throws SQLException {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdLoteBruto, paramDocumentoFornecedor, paramStatusLoteBruto, paramPesoEntrada, paramDtEntradaLoteBruto);
		
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
	
	private String buildQuerySelect(List<Object> parametros, Integer idLoteBruto, String documentoFornecedor, StatusLoteBruto statusLoteBruto, BigDecimal pesoEntrada, Date dtEntradaLoteBruto) {
		
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
			parametros.add(statusLoteBruto);
		}
		
		if (pesoEntrada != null) {
			builder.append(" and peso_entrada_kg_lotebruto = ?");
			parametros.add(pesoEntrada);
		}
		
		if (dtEntradaLoteBruto != null) {
			builder.append(" and dtEntrada_lotebruto = ?");
			parametros.add(dtEntradaLoteBruto);
		}
		
		builder.append(" order by dtEntrada_lotebruto desc");
		
		return builder.toString();
		
	}
	
}
