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

import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;

/**
 * Servlet implementation class TransacaoCompraController
 */
@WebServlet({ "/TransacaoCompraController", "/ListarTransacoesCompra", "/DetalharTransacaoCompra" })
public class TransacaoCompraController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TransacaoCompraService transacaoCompraService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransacaoCompraController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			
			transacaoCompraService = new TransacaoCompraService(new TransacaoCompraDAO());
			
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar TransacaoCompraService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "DetalharTransacaoCompra":
					System.out.println("Sem implementação...");
					break;
					
				default:
					listarTransacoesCompra(request, response);
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
	
	protected void listarTransacoesCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<TransacaoCompra> listaTransacoesCompra = transacaoCompraService.listarTransacoesCompra();
		
		request.setAttribute("listaTransacoesCompra", listaTransacoesCompra);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/transacoes_compra/transacoesCompra.jsp");
		
		reqDis.forward(request, response);
		
	}

}
