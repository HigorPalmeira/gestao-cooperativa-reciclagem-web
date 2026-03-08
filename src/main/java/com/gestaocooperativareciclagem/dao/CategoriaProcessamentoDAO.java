package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.utils.Conexao;

public class CategoriaProcessamentoDAO {
	
	public void inserirCategoriaProcessamento(CategoriaProcessamento categoriaProcessamento) throws SQLException {
		
		String insert = "insert into categoria_processamento (nome_categoriaprocessamento, descricao_categoriaprocessamento) values (?, ?)";
		
		try (Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(insert);) {
			
			pst.setString(1, categoriaProcessamento.getNome());
			pst.setString(2, categoriaProcessamento.getDescricao());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<CategoriaProcessamento> listarCategoriasProcessamento() throws SQLException {
		
		List<CategoriaProcessamento> listaCategoriasProcessamento = new ArrayList<>();
		
		String select = "select * from categoria_processamento";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int id = rset.getInt("id_categoriaprocessamento");
					String nome = rset.getString("nome_categoriaprocessamento");
					String descricao = rset.getString("descricao_categoriaprocessamento");
					
					listaCategoriasProcessamento.add(new CategoriaProcessamento(id, nome, descricao));
					
				}
				
			}
			
		}
		
		return listaCategoriasProcessamento;
		
	}
	
	public void buscarCategoriaProcessamentoPorId(CategoriaProcessamento categoriaProcessamento) throws SQLException {
		
		String select = "select * from categoria_processamento where id_categoriaprocessamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, categoriaProcessamento.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					categoriaProcessamento.setId(rset.getInt("id_categoriaprocessamento"));
					categoriaProcessamento.setNome(rset.getString("nome_categoriaprocessamento"));
					categoriaProcessamento.setDescricao(rset.getString("descricao_categoriaprocessamento"));
					
				}
				
			}
			
		}
		
	}
	
	public void buscarCategoriaProcessamentoPorNome(CategoriaProcessamento categoriaProcessamento) throws SQLException {
		
		String select = "select * from categoria_processamento where nome_categoriaprocessamento = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, categoriaProcessamento.getNome());
			
			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {
					
					categoriaProcessamento.setId(rset.getInt("id_categoriaprocessamento"));
					categoriaProcessamento.setNome(rset.getString("nome_categoriaprocessamento"));
					categoriaProcessamento.setDescricao(rset.getString("descricao_categoriaprocessamento"));
					
				}
				
			}
			
		}
		
	}
	
	public void atualizarCategoriaProcessamento(CategoriaProcessamento categoriaProcessamento) throws SQLException {
		
		String update = "update categoria_processamento set nome_categoriaprocessamento=?, descricao_categoriaprocessamento=? where id_categoriaprocessamento=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {
			
			pst.setString(1, categoriaProcessamento.getNome());
			pst.setString(2, categoriaProcessamento.getDescricao());
			pst.setInt(3, categoriaProcessamento.getId());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public void deletarCategoriaProcessamento(int id) throws SQLException {
		
		String delete = "delete from categoria_processamento where id_categoriaprocessamento=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<CategoriaProcessamento> listarCategoriasProcessamentoPorParametros(Integer idCategoria, String nomeCategoria, String descricaoCategoria) throws SQLException {
		
		List<CategoriaProcessamento> listaCategoriasProcessamento = new ArrayList<>();
		
		List<Object> listaParametros = new ArrayList<>();
		String select = buildQuerySelect(listaParametros, idCategoria, nomeCategoria, descricaoCategoria); 
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<listaParametros.size(); i++) {
				pst.setObject(i+1, listaParametros.get(i));
			}
			
			
			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {
					
					int id = rset.getInt("id_categoriaprocessamento");
					String nome = rset.getString("nome_categoriaprocessamento");
					String descricao = rset.getString("descricao_categoriaprocessamento");
					
					listaCategoriasProcessamento.add(new CategoriaProcessamento(id, nome, descricao));
					
				}
				
			}
			
		}
		
		return listaCategoriasProcessamento;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idCategoria, String nomeCategoria, String descricaoCategoria) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from categoria_processamento where 1=1");
		
		if (idCategoria != null && idCategoria != 0) {
			builder.append(" and id_categoriaprocessamento = ?");
			parametros.add(idCategoria);
		}
		
		if (nomeCategoria != null && !nomeCategoria.isBlank()) {
			builder.append(" and nome_categoriaprocessamento like ?");
			parametros.add("%" + nomeCategoria + "%");
		}
		
		if (descricaoCategoria != null && !descricaoCategoria.isBlank()) {
			builder.append(" and descricao_categoriaprocessamento like ?");
			parametros.add("%" + descricaoCategoria + "%");
		}
		
		return builder.toString();
		
	}

}
