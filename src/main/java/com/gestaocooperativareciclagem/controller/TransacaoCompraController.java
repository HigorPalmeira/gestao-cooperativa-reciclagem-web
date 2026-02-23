package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;

/**
 * Servlet implementation class TransacaoCompraController
 */
@WebServlet({ "/TransacaoCompraController", "/ListarTransacoesCompra", 
	"/DetalharTransacaoCompra", "/AtualizarTransacaoCompra",
	"/DeletarTransacaoCompra"})
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
				case "/DetalharTransacaoCompra":
					buscarTransacaoCompra(request, response);
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
		
		String path = request.getContextPath();
		
		try {
			
			switch(path) {
				case "/AtualizarTransacaoCompra":
					atualizarTransacaoCompra(request, response);
					break;
					
				case "/DeletarTransacaoCompra":
					deletarTransacaoCompra(request, response);
					break;
					
				default:
					listarTransacoesCompra(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void atualizarTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idTransacao = Integer.parseInt(request.getParameter("id"));
			String statusTexto = request.getParameter("paymentStatus");
			
			StatusPagamentoTransacaoCompra status = StatusPagamentoTransacaoCompra.valueOf(statusTexto);
			
			transacaoCompraService.atualizarTransacaoCompra(idTransacao, 0, status, null, Date.valueOf(LocalDate.now()), null);
			
			request.getSession().setAttribute("msgSucesso", "Transação de Compra atualizada com sucesso!");
			
		} catch (Exception e) {
			request.setAttribute("msgErro", "Ocorreu um erro na atualização da Transação de Compra. Error: " + e.getMessage());
		}
		
		buscarTransacaoCompra(request, response);
		
	}
	
	protected void deletarTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idTransacao = Integer.parseInt(request.getParameter("id"));
			
			transacaoCompraService.deletarTransacaoCompra(idTransacao);
			
			response.sendRedirect(request.getContextPath() + "/ListarTransacoesCompra");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void buscarTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idTransacao = Integer.parseInt(request.getParameter("id"));
			
			TransacaoCompra transacaoCompra = transacaoCompraService.buscarTransacaoCompraPorId(idTransacao);
			
			request.setAttribute("transacaoCompra", transacaoCompra);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/transacoes_compra/detalheTransacaoCompra.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarTransacoesCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<TransacaoCompra> listaTransacoesCompra = transacaoCompraService.listarTransacoesCompra();
		
		request.setAttribute("listaTransacoesCompra", listaTransacoesCompra);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/transacoes_compra/transacoesCompra.jsp");
		
		reqDis.forward(request, response);
		
	}

}
