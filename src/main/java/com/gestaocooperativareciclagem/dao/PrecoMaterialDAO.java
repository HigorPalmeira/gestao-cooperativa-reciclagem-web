package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.PrecoMaterial;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.utils.Conexao;

public class PrecoMaterialDAO {

	public void inserirPrecoMaterial(PrecoMaterial precoMaterial) {
		
		String insert = "insert into preco_material (preco_compra_kg_precomaterial, dtVigencia_precomaterial, tipo_material) values (?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setDouble(1, precoMaterial.getPrecoCompra());
			pst.setDate(2, precoMaterial.getDtVigencia());
			pst.setInt(3, precoMaterial.getTipoMaterial().getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterial() {
		
		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();
		
		String select = "select * from info_preco_material";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idPrecoMaterial = rset.getInt("id_precomaterial");
				double precoCompraMaterial = rset.getDouble("preco_compra_kg_precomaterial");
				Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPrecoMaterial;
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorPrecoCompra(double precoCompra) {
		
		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();
		
		String select = "select * from info_preco_material where preco_compra_kg_precomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDouble(1, precoCompra);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idPrecoMaterial = rset.getInt("id_precomaterial");
				double precoCompraMaterial = rset.getDouble("preco_compra_kg_precomaterial");
				Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPrecoMaterial;
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorIntervaloPrecoCompra(double precoCompraInicial, double precoCompraFinal) {
		
		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();
		
		String select = "select * from info_preco_material where preco_compra_kg_precomaterial between ? and ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDouble(1, precoCompraInicial);
			pst.setDouble(2, precoCompraFinal);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idPrecoMaterial = rset.getInt("id_precomaterial");
				double precoCompraMaterial = rset.getDouble("preco_compra_kg_precomaterial");
				Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPrecoMaterial;
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorDataVigencia(Date dtVigencia) {
		
		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();
		
		String select = "select * from info_preco_material where dtVigencia_precomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDate(1, dtVigencia);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idPrecoMaterial = rset.getInt("id_precomaterial");
				double precoCompraMaterial = rset.getDouble("preco_compra_kg_precomaterial");
				Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPrecoMaterial;
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorIntervaloDataVigencia(Date dtVigenciaInicial, Date dtVigenciaFinal) {
		
		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();
		
		String select = "select * from info_preco_material where dtVigencia_precomaterial between ? and ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDate(1, dtVigenciaInicial);
			pst.setDate(2, dtVigenciaFinal);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idPrecoMaterial = rset.getInt("id_precomaterial");
				double precoCompraMaterial = rset.getDouble("preco_compra_kg_precomaterial");
				Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPrecoMaterial;
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorTipoMaterial(int idTipoMaterialBuscado) {
		
		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();
		
		String select = "select * from info_preco_material where id_tipomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDouble(1, idTipoMaterialBuscado);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idPrecoMaterial = rset.getInt("id_precomaterial");
				double precoCompraMaterial = rset.getDouble("preco_compra_kg_precomaterial");
				Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaPrecoMaterial;
		
	}
	
	public void buscarPrecoMaterialPorId(PrecoMaterial precoMaterial) {
		
		String select = "select * from info_preco_material where id_precomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, precoMaterial.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				precoMaterial.setId( rset.getInt("id_precomaterial") );
				precoMaterial.setPrecoCompra( rset.getDouble("preco_compra_kg_precomaterial") );
				precoMaterial.setDtVigencia( rset.getDate("dtVigencia_precomaterial") );
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				precoMaterial.setTipoMaterial( new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial) );
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void buscarPrecoMaterialVigentePorTipoMaterial(PrecoMaterial precoMaterial) {
		
		String select = "select * from info_preco_material where id_tipomaterial = ? "
				+ "and dtVigencia_precomaterial <= ? "
				+ "order by dtVigencia_precomaterial desc limit 1";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, precoMaterial.getTipoMaterial().getId());
			pst.setDate(2, Date.valueOf(LocalDate.now()));
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				precoMaterial.setId( rset.getInt("id_precomaterial") );
				precoMaterial.setPrecoCompra( rset.getDouble("preco_compra_kg_precomaterial") );
				precoMaterial.setDtVigencia( rset.getDate("dtVigencia_precomaterial") );
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				
				precoMaterial.setTipoMaterial( new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial) );
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarPrecoMaterial(PrecoMaterial precoMaterial) {
		
		String update = "update preco_material set preco_compra_kg_precomaterial = ?, dtVigencia_precomaterial = ?, tipo_material = ? where id_precomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setDouble(1, precoMaterial.getPrecoCompra());
			pst.setDate(2, precoMaterial.getDtVigencia());
			pst.setInt(3, precoMaterial.getTipoMaterial().getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarPrecoMaterial(int id) {
		
		String delete = "delete from preco_material where id_precomaterial = ?";
		
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
