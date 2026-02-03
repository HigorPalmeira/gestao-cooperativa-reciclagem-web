package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.utils.Conexao;

public class CategoriaProcessamentoDAO {
	
	public void inserirCategoriaProcessamento(CategoriaProcessamento categoriaProcessamento) {
		
		String insert = "insert into categoria_processamento (nome_categoriaprocessamento, descricao_categoriaprocessamento) values (?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setString(1, categoriaProcessamento.getNome());
			pst.setString(2, categoriaProcessamento.getDescricao());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<CategoriaProcessamento> listarCategoriasProcessamento() {
		
		List<CategoriaProcessamento> listaCategoriasProcessamento = new ArrayList<>();
		
		String select = "select * from categoria_processamento";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int id = rset.getInt("id_categoriaprocessamento");
				String nome = rset.getString("nome_categoriaprocessamento");
				String descricao = rset.getString("descricao_categoriaprocessamento");
				
				listaCategoriasProcessamento.add(new CategoriaProcessamento(id, nome, descricao));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaCategoriasProcessamento;
		
	}
	
	public void buscarCategoriaProcessamento(CategoriaProcessamento categoriaProcessamento) {
		
		String select = "select * from categoria_processamento where id_categoriaprocessamento = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, categoriaProcessamento.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				categoriaProcessamento.setId(rset.getInt("id_categoriaprocessamento"));
				categoriaProcessamento.setNome(rset.getString("nome_categoriaprocessamento"));
				categoriaProcessamento.setDescricao(rset.getString("descricao_categoriaprocessamento"));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarCategoriaProcessamento(CategoriaProcessamento categoriaProcessamento) {
		
		String update = "update categoria_processamento set nome_categoriaprocessamento=?, descricao_categoriaprocessamento=? where id_categoriaprocessamento=?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setString(1, categoriaProcessamento.getNome());
			pst.setString(2, categoriaProcessamento.getDescricao());
			pst.setInt(3, categoriaProcessamento.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarCategoriaProcessamento(int id) {
		
		String delete = "delete from categoria_processamento where id_categoriaprocessamento=?";
		
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
