package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public void inserirLoteProcessado(LoteProcessado loteProcessado) {
		
		String insert = "insert into lote_processado (peso_atual_kg_loteprocessado, dtCriacao_loteprocessado, tipo_material, lote_bruto) "
				+ "values (?, ?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			pst.setDouble(1, loteProcessado.getPesoAtualKg());
			pst.setDate(2, loteProcessado.getDtCriacao());
			pst.setInt(3, loteProcessado.getTipoMaterial().getId());
			pst.setInt(4, loteProcessado.getLoteBruto().getId());
			
			int linhasAfetadas = pst.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rset = pst.getGeneratedKeys();
				if (rset.next()) {
					loteProcessado.setId(rset.getInt(1));
				}
				
				rset.close();
			}
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<LoteProcessado> listarLotesProcessado() {
	
		List<LoteProcessado> listaLoteProcessado = new ArrayList<>();
		
		String select = "select * from info_lote_processado";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				listaLoteProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLoteProcessado;
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorTipoMaterial(TipoMaterial tipoMaterialBuscado) {
		
		List<LoteProcessado> listaLoteProcessado = new ArrayList<>();
		
		String select = "select * from info_lote_processado where id_tipomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, tipoMaterialBuscado.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				listaLoteProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLoteProcessado;
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorLoteBruto(LoteBruto loteBrutoBuscado) {
		
		List<LoteProcessado> listaLoteProcessado = new ArrayList<>();
		
		String select = "select * from info_lote_processado where id_lotebruto = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, loteBrutoBuscado.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idLoteProcessado = rset.getInt("id_loteprocessado");
				double pesoAtualKgLoteProcessado = rset.getDouble("peso_atual_kg_loteprocessado");
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				listaLoteProcessado.add(new LoteProcessado(idLoteProcessado, pesoAtualKgLoteProcessado, dtCriacaoLoteProcessado, tipoMaterial, loteBruto));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLoteProcessado;
		
	}
	
	public void buscarLoteProcessadoPorId(LoteProcessado loteProcessado) {
		
		String select = "select * from info_lote_processado where id_loteprocessado = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, loteProcessado.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				loteProcessado.setId( rset.getInt("id_loteprocessado") );
				loteProcessado.setPesoAtualKg( rset.getDouble("peso_atual_kg_loteprocessado") );
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
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.valueOf(rset.getString("status_lotebruto"));
				LoteBruto loteBruto = new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor);
				
				loteProcessado.setLoteBruto(loteBruto);
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarLoteProcessado(LoteProcessado loteProcessado) {
		
		String update = "update lote_processado set peso_atual_kg_loteprocessado = ?, "
				+ "tipo_material = ?, lote_bruto = ? where id_loteprocessado = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setDouble(1, loteProcessado.getPesoAtualKg());
			pst.setInt(2, loteProcessado.getTipoMaterial().getId());
			pst.setInt(3, loteProcessado.getLoteBruto().getId());
			pst.setInt(4, loteProcessado.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarLoteProcessado(int id) {
		
		String delete = "delete from lote_processado where id_loteprocessado = ?";
		
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
