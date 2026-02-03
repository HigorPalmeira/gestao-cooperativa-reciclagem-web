package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Usuario;
import com.gestaocooperativareciclagem.utils.Conexao;

public class UsuarioDAO {
	
	public void inserirUsuario(Usuario usuario) {
		
		String insert = "insert into usuario (nome_usuario, email_usuario, senha_usuario, papel) values (?, ?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getSenha());
			pst.setString(4, usuario.getPapel());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Usuario> listarUsuarios() {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		String select = "select * from usuario";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int id = rset.getInt("id_usuario");
				String nome = rset.getString("nome_usuario");
				String email = rset.getString("email_usuario");
				String senha = rset.getString("senha_usuario");
				String papel = rset.getString("papel");
				
				listaUsuarios.add(new Usuario(id, nome, email, senha, papel));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaUsuarios;
		
	}
	
	public List<Usuario> buscarUsuariosPorPapel(String papelUsuario) {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		String select = "select * from usuario where papel = ?;";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, papelUsuario);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int id = rset.getInt("id_usuario");
				String nome = rset.getString("nome_usuario");
				String email = rset.getString("email_usuario");
				String senha = rset.getString("senha_usuario");
				String papel = rset.getString("papel");
				
				listaUsuarios.add(new Usuario(id, nome, email, senha, papel));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaUsuarios;
		
	}
	
	public List<Usuario> buscarUsuariosPorNome(String nomeUsuario) {
		
		List<Usuario> listaUsuarios = new ArrayList<>();
		
		String select = "select * from usuario where nome_usuario = ?;";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, nomeUsuario);
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int id = rset.getInt("id_usuario");
				String nome = rset.getString("nome_usuario");
				String email = rset.getString("email_usuario");
				String senha = rset.getString("senha_usuario");
				String papel = rset.getString("papel");
				
				listaUsuarios.add(new Usuario(id, nome, email, senha, papel));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaUsuarios;
		
	}
	
	public void buscarUsuarioPorId(Usuario usuario) {
		
		String select = "select * from usuario where id_usuario = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, usuario.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				usuario.setId( rset.getInt("id_usuario") );
				usuario.setNome( rset.getString("nome_usuario") );
				usuario.setEmail( rset.getString("email_usuario") );
				usuario.setSenha( rset.getString("senha_usuario") );
				usuario.setPapel( rset.getString("papel") );
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void buscarUsuarioPorEmail(Usuario usuario) {
		
		String select = "select * from usuario where email_usuario = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, usuario.getEmail());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				usuario.setId( rset.getInt("id_usuario") );
				usuario.setNome( rset.getString("nome_usuario") );
				usuario.setEmail( rset.getString("email_usuario") );
				usuario.setSenha( rset.getString("senha_usuario") );
				usuario.setPapel( rset.getString("papel") );
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarUsuario(Usuario usuario) {
		
		String update = "update usuario set nome_usuario=?, email_usuario=?, senha_usuario=?, papel=? where id_usuario=?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setString(1, usuario.getNome());
			pst.setString(2, usuario.getEmail());
			pst.setString(3, usuario.getSenha());
			pst.setString(4, usuario.getPapel());
			pst.setInt(5, usuario.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarUsuario(int id) {
		
		String delete = "delete from usuario where id_usuario=?";
		
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
