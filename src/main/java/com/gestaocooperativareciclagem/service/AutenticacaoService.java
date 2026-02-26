package com.gestaocooperativareciclagem.service;

import com.gestaocooperativareciclagem.dao.UsuarioDAO;
import com.gestaocooperativareciclagem.model.Usuario;

public class AutenticacaoService {
	
	private UsuarioDAO usuarioDao;
	
	public AutenticacaoService(UsuarioDAO usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
	public boolean login(Usuario usuario) {
		
		if (usuario == null) {
			throw new RuntimeException("Não é possível realizar o login do usuário.");
		}
		
		try {
			
			Usuario usuarioSalvo = new Usuario();
			usuarioSalvo.setEmail(usuario.getEmail());
			
			usuarioDao.buscarUsuarioPorEmail(usuarioSalvo);
			
			if (usuario.getSenha().equals(usuarioSalvo.getSenha())) {
				
				usuario.setId(usuarioSalvo.getId());
				usuario.setNome(usuarioSalvo.getNome());
				usuario.setEmail(usuarioSalvo.getEmail());
				usuario.setPapel(usuarioSalvo.getPapel());
				usuario.setSenha(null);
				
				return true;
				
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return false;
		
	}
	
	public boolean temEmailCadastrado(Usuario usuario) {
		
		if (usuario == null) {
			throw new RuntimeException("Não foi possível verificar o e-mail no sistema.");
		}
		
		if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
			throw new RuntimeException("E-mail inválido. Não é possível verificar o e-mail no sistema.");
		}
		
		usuarioDao.buscarUsuarioPorEmail(usuario);
		
		return usuario.getId() != 0;
		
	}

}
