package com.gestaocooperativareciclagem.service;

import java.sql.SQLException;
import java.util.List;

import com.gestaocooperativareciclagem.dao.UsuarioDAO;
import com.gestaocooperativareciclagem.model.Usuario;

public class UsuarioService {

	private UsuarioDAO usuarioDao;

	public UsuarioService(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public List<Usuario> listarUsuariosComParametro(Integer idUsuario, String nomeUsuario, String emailUsuario, String papelUsuario) throws SQLException {

		return usuarioDao.listarUsuariosComParametro(idUsuario, nomeUsuario, emailUsuario, papelUsuario);

	}

	public void inserirUsuario(String nome, String email, String senha, String papel) throws SQLException {

		Usuario usuario = new Usuario(nome, email, senha, papel);

		usuarioDao.inserirUsuario(usuario);

	}

	public void atualizarUsuario(int id, String nome, String email, String senha, String papel) throws SQLException {

		Usuario usuarioOriginal = buscarUsuarioPorId(id);

		String senhaFinal = (senha == null || senha.isBlank())
				? usuarioOriginal.getSenha()
				: senha;

		Usuario usuarioAtualizado = new Usuario(id,
				nome,
				email,
				senhaFinal,
				papel);

		if (nome == null || nome.isBlank()) {
			usuarioAtualizado.setNome(usuarioOriginal.getNome());
		}

		if (email == null || email.isBlank()) {
			usuarioAtualizado.setEmail(usuarioOriginal.getEmail());
		}

		if (papel == null || papel.isBlank()) {
			usuarioAtualizado.setPapel(usuarioOriginal.getPapel());
		}

		usuarioDao.atualizarUsuario(usuarioAtualizado);

	}

	public void deletarUsuario(int id) throws SQLException {

		usuarioDao.deletarUsuario(id);

	}

	public List<Usuario> listarUsuarios() throws SQLException {

		return usuarioDao.listarUsuarios();

	}

	public List<Usuario> listarUsuariosPorPapel(String papel) throws SQLException {

		if (papel == null || papel.isBlank()) {
			throw new RuntimeException("Papel inválido! É necessário informar um 'Papel' para realizar a busca.");
		}

		return usuarioDao.listarUsuariosPorPapel(papel);

	}

	public List<Usuario> listarUsuariosPorNome(String nome) throws SQLException {

		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("Nome inválido! É necessário informar um 'Nome' para realizar a busca.");
		}

		return usuarioDao.listarUsuariosPorNome(nome);

	}

	public Usuario buscarUsuarioPorId(int id) throws SQLException {

		Usuario usuario = new Usuario();
		usuario.setId(id);

		usuarioDao.buscarUsuarioPorId(usuario);

		return usuario;

	}

	public Usuario buscarUsuarioPorEmail(String email) throws SQLException {

		if (email == null || email.isBlank()) {
			throw new RuntimeException("E-mail inválido! É necessário informar um 'E-mail' para realizar a busca.");
		}

		Usuario usuario = new Usuario();
		usuario.setEmail(email);

		usuarioDao.buscarUsuarioPorEmail(usuario);

		return usuario;

	}

}
