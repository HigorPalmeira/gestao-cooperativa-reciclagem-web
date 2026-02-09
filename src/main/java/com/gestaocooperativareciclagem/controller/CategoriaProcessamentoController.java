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
@WebServlet({ "/CategoriaProcessamentoController", "/ListarCategoriasProcessamento", "/DetalharCategoriaProcessamento" })
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
				case "DetalharCategoriaProcessamento":
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	protected void listarCategoriasProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<CategoriaProcessamento> listaCategoriasProcessamento = categoriaProcessamentoService.listarCategoriasProcessamento();
		
		request.setAttribute("listaCategoriasProcessamento", listaCategoriasProcessamento);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/categorias_processamento/categoriasProcessamento.jsp");
		
		reqDis.forward(request, response);
		
	}
	
}
