package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;

/**
 * Servlet implementation class FornecedorController
 */
@WebServlet(urlPatterns={"/FornecedorController", "/ListarFornecedores", "/DetalharFornecedor"})
public class FornecedorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FornecedorDAO fornecedorDao;
	
	public void init() throws ServletException {
		try {
			fornecedorDao = new FornecedorDAO();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar FornecedorDAO", e);
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * */
    public FornecedorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());

		String path = request.getServletPath();
		
		try {
		
			switch(path) {
				case "/DetalharFornecedor":
					buscarFornecedorPorDocumento(request, response);
					break;
					
				default:
					listarFornecedores(request, response);
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
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String action = request.getParameter("action");
		
		try {
			switch(action) {
				case "criar":
					criarFornecedor(request, response);
					break;
					
				case "editar":
					editarFornecedor(request, response);
					break;
					
				case "deletar":
					deletarFornecedor(request, response);
					break;
					
				default:
					listarFornecedores(request, response);
					break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarFornecedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Fornecedor> fornecedores = fornecedorDao.listarFornecedores();
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarFornecedorPorDocumento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setDocumento(documento);
		
		fornecedorDao.buscarFornecedorPorDocumento(fornecedor);
		
		request.setAttribute("fornecedor", fornecedor);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/detalheFornecedor.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarFornecedoresPorNome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		
		List<Fornecedor> fornecedores = fornecedorDao.buscarFornecedoresPorNome(nome);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarFornecedoresPorTipo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		
		List<Fornecedor> fornecedores = fornecedorDao.buscarFornecedoresPorTipo(tipo);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarFornecedoresPorData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		
		List<Fornecedor> fornecedores = fornecedorDao.buscarFornecedoresPorTipo(tipo);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void criarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		String nome = request.getParameter("nome");
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		Date dtCadastro = Date.valueOf(request.getParameter("dtCadastro"));
		
		Fornecedor fornecedor = new Fornecedor(documento, nome, tipo, dtCadastro);
		fornecedorDao.inserirFornecedor(fornecedor);
		
		response.sendRedirect("ListarFornecedores");
		
	}

	protected void editarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		String nome = request.getParameter("nome");
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		
		Fornecedor fornecedor = new Fornecedor(documento, nome, tipo);
		fornecedorDao.atualizarFornecedor(fornecedor);
		
		response.sendRedirect("pages/fornecedor/fornecedores.jsp");
		
	}
	
	protected void deletarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		fornecedorDao.deletarFornecedor(documento);
		
		response.sendRedirect("pages/fornecedor/fornecedores.jsp");
		
	}
}
