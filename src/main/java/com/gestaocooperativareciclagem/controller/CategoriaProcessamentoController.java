package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.CategoriaProcessamentoDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.service.CategoriaProcessamentoService;

/**
 * Servlet implementation class CategoriaProcessamentoController
 */
@WebServlet(
		name="CategoriaProcessamentoController",
		urlPatterns={ "/CategoriaProcessamentoController", "/ListarCategoriasProcessamento", 
	"/DetalharCategoriaProcessamento", "/InserirCategoriaProcessamento",
	"/DeletarCategoriaProcessamento", "/AtualizarCategoriaProcessamento"})
public class CategoriaProcessamentoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private CategoriaProcessamentoService categoriaProcessamentoService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoriaProcessamentoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		try {
			categoriaProcessamentoService = new CategoriaProcessamentoService(new CategoriaProcessamentoDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar CategoriaProcessamentoService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharCategoriaProcessamento":
					System.out.println("Sem implementação...");
					break;
					
				default:
					listarCategoriasProcessamento(request, response);
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
				case "/InserirCategoriaProcessamento":
					inserirCategoriaProcessamento(request, response);
					break;
					
				case "/AtualizarCategoriaProcessamento":
					atualizarCategoriaProcessamento(request, response);
					break;
					
				case "/DeletarCategoriaProcessamento":
					deletarCategoriaProcessamento(request, response);
					break;
					
				default:
					listarCategoriasProcessamento(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirCategoriaProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String nome = request.getParameter("modalName");
		String descricao = request.getParameter("modalDesc");
		
		categoriaProcessamentoService.inserirCategoriaProcessamento(nome, descricao);
		
		response.sendRedirect(request.getContextPath() + "/ListarCategoriasProcessamento");
		
	}
	
	protected void atualizarCategoriaProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("modalId"));
		String nome = request.getParameter("modalName");
		String descricao = request.getParameter("modalDesc");
		
		categoriaProcessamentoService.atualizarCategoriaProcessamento(id, nome, descricao);
		
		response.sendRedirect(request.getContextPath() + "/ListarCategoriasProcessamento");
		
	}
	
	protected void deletarCategoriaProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("modalId"));
		
		categoriaProcessamentoService.deletarCategoriaProcessamento(id);
		
		response.sendRedirect(request.getContextPath() + "/ListarCategoriasProcessamento");
		
	}

	protected void listarCategoriasProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String idCategoriaTxt = request.getParameter("idCategoria");
			String nomeCategoria = request.getParameter("nome");
			String descricaoCategoria = request.getParameter("descricao");
			
			Integer idCategoria = null;
			if (idCategoriaTxt != null) {
				idCategoria = Integer.parseInt(idCategoriaTxt);
			}
			
			List<CategoriaProcessamento> listaCategoriasProcessamento = categoriaProcessamentoService.listarCategoriasProcessamentoPorParametros(idCategoria, nomeCategoria, descricaoCategoria);
			
			request.setAttribute("listaCategoriasProcessamento", listaCategoriasProcessamento);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/categorias_processamento/categoriasProcessamento.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar as categorias de processamento.<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
	}
	
}
