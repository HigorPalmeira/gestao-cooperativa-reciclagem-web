package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.UsuarioDAO;
import com.gestaocooperativareciclagem.model.Usuario;
import com.gestaocooperativareciclagem.service.UsuarioService;
import com.gestaocooperativareciclagem.utils.Criptografia;
import com.gestaocooperativareciclagem.utils.Validador;

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet(
		name="UsuarioController",
		urlPatterns = {"/UsuarioController", "/ListarUsuarios", 
		"/DetalharUsuario", "/InserirUsuario",
		"/AtualizarUsuario", "/DeletarUsuario"})
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioService usuarioService;
	
	public void init() throws ServletException {
		try {
			usuarioService = new UsuarioService(new UsuarioDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar UsuarioService", e);
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharUsuario":
					buscarUsuarioPorId(request, response);
					break;
				
				default:
					listarUsuarios(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/InserirUsuario":
					inserirUsuario(request, response);
					break;
					
				case "/AtualizarUsuario":
					atualizarUsuario(request, response);
					break;
					
				case "/DeletarUsuario":
					deletarUsuario(request, response);
					break;
					
				default:
					listarUsuarios(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String nome = request.getParameter("userName");
		String email = request.getParameter("userEmail");
		String senha = null;
		String confirmSenha = null;
		String papel = request.getParameter("userRole");
		
		boolean senhaCorresponde = false;
		
		try {
			senha = Criptografia.criptografar(request.getParameter("userPass"));
			confirmSenha = Criptografia.criptografar(request.getParameter("userPassConfirm"));
			
			senhaCorresponde = senha.equals(confirmSenha);
					
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		if (senhaCorresponde) {
			usuarioService.inserirUsuario(nome, email, senha, papel);
		}
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt( request.getParameter("userId") );
		String nome = request.getParameter("userName").trim();
		String email = request.getParameter("userEmail").trim();
		String papel = request.getParameter("userRole").trim();
		
		String senhaBruta = request.getParameter("newPass").trim();
		String confirmSenhaBruta = request.getParameter("confirmPass").trim();
		String senhaCriptografada = null;

		StringBuilder msgErro = new StringBuilder("Erro: ");
		boolean temErro = false;

		try {
			
			if (senhaBruta != null && !senhaBruta.isEmpty()) {
				
				if (senhaBruta.equals(confirmSenhaBruta)) {
					senhaCriptografada = Criptografia.criptografar(senhaBruta);
				} else {
					msgErro.append("As senhas não coincidem.<br>");
					temErro = true;
				}
				
			}
			
			if (!Validador.isEmail(email)) {
				msgErro.append("O usuário deve possuir um e-mail válido.<br>");
				temErro = true;
			}
			
			if (papel == null || papel.isBlank()) {
				msgErro.append("O usuário deve possuir um papel válido.<br>");
				temErro = true;
			}
			
			if (temErro) {
				
				request.setAttribute("msgErro", msgErro.toString());
				
				buscarUsuarioPorId(request, response);
				
			} else {
				
				usuarioService.atualizarUsuario(id, nome, email, senhaCriptografada, papel);

				request.getSession().setAttribute("msgSucesso", "Usuário atualizado com sucesso!");
				
				response.sendRedirect(request.getContextPath() + "/DetalharUsuario?userId=" + id);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}
	
	protected void deletarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("userId"));
		usuarioService.deletarUsuario(id);
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Usuario> usuarios = usuarioService.listarUsuarios();
		
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarUsuariosPorPapel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String papel = request.getParameter("papel");
		
		List<Usuario> usuarios = usuarioService.listarUsuariosPorPapel(papel);
		
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarUsuariosPorNome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		
		List<Usuario> usuarios = usuarioService.listarUsuariosPorNome(nome);
		
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarUsuarioPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("userId"));
		
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		
		request.setAttribute("usuario", usuario);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/detalheUsuario.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarUsuarioPorEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);
		
		request.setAttribute("usuario", usuario);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/detalheUsuario.jsp");
		
		reqDis.forward(request, response);
		
	}

}
