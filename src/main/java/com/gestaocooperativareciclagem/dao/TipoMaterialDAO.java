package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.utils.Conexao;

public class TipoMaterialDAO {
	
	public void inserirTipoMaterial(TipoMaterial tipoMaterial) {
		
		String insert = "insert into tipo_material (nome_tipomaterial, descricao_tipomaterial) values (?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setString(1, tipoMaterial.getNome());
			pst.setString(2, tipoMaterial.getDescricao());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<TipoMaterial> listarTiposMaterial() {
		
		List<TipoMaterial> listaTiposMaterial = new ArrayList<>();
		
		String select = "select * from tipo_material";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int id = rset.getInt("id_tipomaterial");
				String nome = rset.getString("nome_tipomaterial");
				String descricao = rset.getString("descricao_tipomaterial");
				
				listaTiposMaterial.add(new TipoMaterial(id, nome, descricao));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaTiposMaterial;
		
	}
	
	public void buscarTipoMaterial(TipoMaterial tipoMaterial) {
		
		String select = "select * from tipo_material where id_tipomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, tipoMaterial.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				tipoMaterial.setId( rset.getInt("id_tipomaterial") );
				tipoMaterial.setNome( rset.getString("nome_tipomaterial") );
				tipoMaterial.setDescricao( rset.getString("descricao_tipomaterial") );
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarTipoMaterial(TipoMaterial tipoMaterial) {
		
		String update = "update tipo_material set nome_tipomaterial=?, descricao_tipomaterial=? where id_tipomaterial=?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setString(1, tipoMaterial.getNome());
			pst.setString(2, tipoMaterial.getDescricao());
			pst.setInt(3, tipoMaterial.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarUsuario(int id) {
		
		String delete = "delete from tipo_material where id_tipomaterial=?";
		
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
