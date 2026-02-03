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

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet(urlPatterns = {"/UsuarioController", "/ListarUsuarios", "/DetalharUsuario"})
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO usuarioDao;
	
	public void init() throws ServletException {
		try {
			usuarioDao = new UsuarioDAO();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar FornecedorDAO", e);
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
					criarUsuario(request, response);
					break;
					
				case "editar":
					editarUsuario(request, response);
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
	
	protected void criarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//String nome, String email, String senha, String papel
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String papel = request.getParameter("papel");
		
		Usuario usuario = new Usuario(nome, email, senha, papel);
		usuarioDao.inserirUsuario(usuario);
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void editarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt( request.getParameter("id") );
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String senha = request.getParameter("senha");
		String papel = request.getParameter("papel");
		
		Usuario usuario = new Usuario(id, nome, email, senha, papel);
		usuarioDao.atualizarUsuario(usuario);
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void deletarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		usuarioDao.deletarUsuario(id);
		
		response.sendRedirect("ListarUsuarios");
		
	}
	
	protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Usuario> usuarios = usuarioDao.listarUsuarios();
		
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarUsuariosPorPapel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String papel = request.getParameter("papel");
		
		List<Usuario> usuarios = usuarioDao.buscarUsuariosPorPapel(papel);
		
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarUsuariosPorNome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		
		List<Usuario> usuarios = usuarioDao.buscarUsuariosPorNome(nome);
		
		request.setAttribute("listaUsuarios", usuarios);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarUsuarioPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		Usuario usuario = new Usuario();
		usuario.setId(id);
		
		usuarioDao.buscarUsuarioPorId(usuario);
		
		request.setAttribute("usuario", usuario);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/detalheUsuario.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarUsuarioPorEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		
		usuarioDao.buscarUsuarioPorEmail(usuario);
		
		request.setAttribute("usuario", usuario);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/detalheUsuario.jsp");
		
		reqDis.forward(request, response);
		
	}

}
