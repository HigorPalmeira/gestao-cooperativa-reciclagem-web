package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
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

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet(urlPatterns = {"/UsuarioController", "/ListarUsuarios", "/DetalharUsuario"})
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
		
		String action = request.getParameter("action");
		
		try {
			
			switch (action) {
				case "criar":
					inserirUsuario(request, response);
					break;
					
				case "editar":
					atualizarUsuario(request, response);
					break;
					
				case "deletar":
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
		
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String papel = request.getParameter("papel");
		
		usuarioService.inserirUsuario(nome, email, senha, papel);
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt( request.getParameter("id") );
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String papel = request.getParameter("papel");
		
		usuarioService.atualizarUsuario(id, nome, email, senha, papel);
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void deletarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
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
		
		int id = Integer.parseInt(request.getParameter("id"));
		
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
