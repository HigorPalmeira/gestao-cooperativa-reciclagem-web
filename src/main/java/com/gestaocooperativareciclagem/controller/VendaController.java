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

import com.gestaocooperativareciclagem.dao.ClienteDAO;
import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.service.ClienteService;
import com.gestaocooperativareciclagem.service.VendaService;

/**
 * Servlet implementation class VendaController
 */
@WebServlet({ "/VendaController", "/ListarVendas", "/DetalharVenda" })
public class VendaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private VendaService vendaService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VendaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			vendaService = new VendaService(
					new VendaDAO(), 
					new ItemVendaDAO(), 
					new ClienteService(new ClienteDAO()));
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar VendaService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharVenda":
					System.out.println("Sem implementação...");
					break;
					
				default:
					listarVendas(request, response);
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

	protected void listarVendas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Venda> listaVendas = vendaService.listarVendas();
		
		request.setAttribute("listaVendas", listaVendas);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/venda/vendas.jsp");
		
		reqDis.forward(request, response);
		
	}
	
}
