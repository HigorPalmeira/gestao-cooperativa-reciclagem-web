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
import com.gestaocooperativareciclagem.service.FornecedorService;

/**
 * Servlet implementation class FornecedorController
 */
@WebServlet(urlPatterns={"/FornecedorController", "/ListarFornecedores", "/DetalharFornecedor"})
public class FornecedorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FornecedorService fornecedorService;
	
	public void init() throws ServletException {
		try {
			fornecedorService = new FornecedorService(new FornecedorDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar FornecedorService", e);
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
					inserirFornecedor(request, response);
					break;
					
				case "editar":
					atualizarFornecedor(request, response);
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
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedores();
				//fornecedorDao.listarFornecedores();
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarFornecedorPorDocumento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		
		Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);
		
		request.setAttribute("fornecedor", fornecedor);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/detalheFornecedor.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarFornecedoresPorNome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedoresPorNome(nome);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarFornecedoresPorTipo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedoresPorTipo(tipo);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarFornecedoresPorData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Date dataInicial = Date.valueOf(request.getParameter("dataInicial"));
		Date dataFinal = Date.valueOf(request.getParameter("dataFinal"));
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedoresPorDataCadastro(dataInicial, dataFinal);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void inserirFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		String nome = request.getParameter("nome");
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));

		fornecedorService.inserirFornecedor(documento, nome, tipo);
		
		response.sendRedirect("ListarFornecedores");
		
	}

	protected void atualizarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documentoOriginal = request.getParameter("documentoOriginal");
		String documentoNovo = request.getParameter("documentoNovo");
		String nome = request.getParameter("nome");
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		
		fornecedorService.atualizarFornecedor(documentoOriginal, documentoNovo, nome, tipo);
		
		response.sendRedirect("pages/fornecedor/fornecedores.jsp");
		
	}
	
	protected void deletarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("documento");
		
		fornecedorService.deletarFornecedor(documento);
		
		response.sendRedirect("pages/fornecedor/fornecedores.jsp");
		
	}
}
