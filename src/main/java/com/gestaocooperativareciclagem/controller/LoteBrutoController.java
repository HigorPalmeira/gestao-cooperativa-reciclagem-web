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

import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.service.FornecedorService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;

/**
 * Servlet implementation class LoteBrutoController
 */
@WebServlet({ "/LoteBrutoController", "/ListarLotesBruto",
			"/InserirLoteBruto", "/DetalharLoteBruto",
			"/AtualizarLoteBruto", "/DeletarLoteBruto"})
public class LoteBrutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoteBrutoService loteBrutoService;
    private FornecedorService fornecedorService;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoteBrutoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		try {
			loteBrutoService = new LoteBrutoService(new LoteBrutoDAO());
			fornecedorService = new FornecedorService(new FornecedorDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteBrutoService e/ou FornecedorService", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
			
				case "/DetalharLoteBruto":
					buscarLoteBruto(request, response);
					break;
					
				default:
					listarLotesBruto(request, response);
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
				case "/InserirLoteBruto":
					inserirLoteBruto(request, response);
					break;
					
				case "/AtualizarLoteBruto":
					System.out.println("Sem implementação...");
					break;
					
				case "/DeletarLoteBruto":
					System.out.println("Sem implementação...");
					break;
					
				default:
					listarLotesBruto(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String documento = request.getParameter("supplierDoc");
		double pesoEntrada = Double.parseDouble(request.getParameter("entryWeight"));
		
		Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);
		
		loteBrutoService.inserirLoteBruto(pesoEntrada, Date.valueOf(LocalDate.now()), StatusLoteBruto.RECEBIDO, fornecedor);
		
		response.sendRedirect(request.getContextPath() + "/ListarLotesBruto");
		
	}
	
	protected void buscarLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		LoteBruto loteBruto = loteBrutoService.buscarLoteBrutoPorId(id);
		
		request.setAttribute("loteBruto", loteBruto);
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_bruto/detalheLoteBruto.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarLotesBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<LoteBruto> lotesBrutos = loteBrutoService.listarLotesBrutos();
		
		request.setAttribute("listaLotesBrutos", lotesBrutos);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_bruto/lotesBrutos.jsp");
		
		reqDis.forward(request, response);
		
	}

}
