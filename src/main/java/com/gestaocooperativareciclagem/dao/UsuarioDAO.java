package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Usuario;
import com.gestaocooperativareciclagem.utils.Conexao;

public class UsuarioDAO {
	
	public void inserirUsuario(Usuario usuario) throws SQLException {
		
		String insert = "insert into usuario (nome_usuario, email_usuario, senha_usuario, papel) values (?, ?, ?, ?)";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert);) {
			
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getSenha());
			pst.setString(4, usuario.getPapel());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<Usuario> listarUsuarios() throws SQLException {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		String select = "select * from usuario";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int id = rset.getInt("id_usuario");
					String nome = rset.getString("nome_usuario");
					String email = rset.getString("email_usuario");
					String papel = rset.getString("papel");
					
					Usuario usuario = new Usuario();
					usuario.setId(id);
					usuario.setNome(nome);
					usuario.setEmail(email);
					usuario.setPapel(papel);
					
					listaUsuarios.add(usuario);
					
				}
				
			}
			
		}
		
		return listaUsuarios;
		
	}
	
	public List<Usuario> listarUsuariosPorPapel(String papelUsuario) throws SQLException {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		String select = "select * from usuario where papel = ?;";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, papelUsuario);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int id = rset.getInt("id_usuario");
					String nome = rset.getString("nome_usuario");
					String email = rset.getString("email_usuario");
					String papel = rset.getString("papel");
					
					Usuario usuario = new Usuario();
					usuario.setId(id);
					usuario.setNome(nome);
					usuario.setEmail(email);
					usuario.setPapel(papel);
					
					listaUsuarios.add(usuario);
					
				}
				
			}
			
		}
		
		return listaUsuarios;
		
	}
	
	public List<Usuario> listarUsuariosPorNome(String nomeUsuario) throws SQLException {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		String select = "select * from usuario where nome_usuario = ?;";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, nomeUsuario);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					int id = rset.getInt("id_usuario");
					String nome = rset.getString("nome_usuario");
					String email = rset.getString("email_usuario");
					String papel = rset.getString("papel");
					
					Usuario usuario = new Usuario();
					usuario.setId(id);
					usuario.setNome(nome);
					usuario.setEmail(email);
					usuario.setPapel(papel);
					
					listaUsuarios.add(usuario);
					
				}
				
			}
			
		}
		
		return listaUsuarios;
		
	}
	
	public void buscarUsuarioPorId(Usuario usuario) throws SQLException {
		
		String select = "select * from usuario where id_usuario = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setInt(1, usuario.getId());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					usuario.setId( rset.getInt("id_usuario") );
					usuario.setNome( rset.getString("nome_usuario") );
					usuario.setEmail( rset.getString("email_usuario") );
					usuario.setSenha( rset.getString("senha_usuario") );
					usuario.setPapel( rset.getString("papel") );
					
				}
				
			}
			
		}
		
	}
	
	public void buscarUsuarioPorEmail(Usuario usuario) throws SQLException {
		
		String select = "select * from usuario where email_usuario = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, usuario.getEmail());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					usuario.setId( rset.getInt("id_usuario") );
					usuario.setNome( rset.getString("nome_usuario") );
					usuario.setEmail( rset.getString("email_usuario") );
					usuario.setSenha( rset.getString("senha_usuario") );
					usuario.setPapel( rset.getString("papel") );
					
				}
				
			}
			
		}
		
	}
	
	public void atualizarUsuario(Usuario usuario) throws SQLException {
		
		String update = "update usuario set nome_usuario=?, email_usuario=?, senha_usuario=?, papel=? where id_usuario=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {
			
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getSenha());
			pst.setString(4, usuario.getPapel());
			pst.setInt(5, usuario.getId());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public void deletarUsuario(int id) throws SQLException {
		
		String delete = "delete from usuario where id_usuario=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
		}
		
	}

	public List<Usuario> listarUsuariosComParametro(Integer idUsuario, String nomeUsuario, String emailUsuario, String papelUsuario) throws SQLException {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, idUsuario, nomeUsuario, emailUsuario, papelUsuario);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery()) {
				
				while(rset.next()) {
					
					int id = rset.getInt("id_usuario");
					String nome = rset.getString("nome_usuario");
					String email = rset.getString("email_usuario");
					String papel = rset.getString("papel");
					
					Usuario usuario = new Usuario();
					usuario.setId(id);
					usuario.setNome(nome);
					usuario.setEmail(email);
					usuario.setPapel(papel);
					
					listaUsuarios.add(usuario);
					
				}
				
			}
			
		}
		
		return listaUsuarios;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, Integer idUsuario, String nomeUsuario, String emailUsuario, String papelUsuario) {

		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from usuario where 1=1");
		
		if (idUsuario != null && idUsuario != 0) {
			builder.append(" and id_usuario = ?");
			parametros.add(idUsuario);
		}
		
		if (nomeUsuario != null && !nomeUsuario.isBlank()) {
			builder.append(" and nome_usuario like ?");
			parametros.add("%" + nomeUsuario.trim() + "%");
		}
		
		if (emailUsuario != null && !emailUsuario.isBlank()) {
			builder.append(" and email_usuario like ?");
			parametros.add("%" + emailUsuario.trim() + "%");
		}
		
		if (papelUsuario != null && !papelUsuario.isBlank()) {
			builder.append(" and papel like ?");
			parametros.add("%" + papelUsuario.trim() + "%");
		}
		
		return builder.toString();
		
	}
	
}
