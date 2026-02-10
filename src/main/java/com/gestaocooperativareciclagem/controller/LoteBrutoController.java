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

import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.service.LoteBrutoService;

/**
 * Servlet implementation class LoteBrutoController
 */
@WebServlet({ "/LoteBrutoController", "/ListarLotesBruto" })
public class LoteBrutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoteBrutoService loteBrutoService;
       
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
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteBrutoService", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void listarLotesBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<LoteBruto> lotesBrutos = loteBrutoService.listarLotesBrutos();
		
		request.setAttribute("listaLotesBrutos", lotesBrutos);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_bruto/lotesBrutos.jsp");
		
		reqDis.forward(request, response);
		
	}

}
