package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;

/**
 * Servlet implementation class LoteProcessadoController
 */
@WebServlet(urlPatterns= {"/LoteProcessadoController", "/ListarLotesProcessados"})
public class LoteProcessadoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private LoteProcessadoService loteProcessadoService;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoteProcessadoController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		try {
			loteProcessadoService = new LoteProcessadoService(
					new LoteProcessadoDAO(), 
					new TransacaoCompraService(new TransacaoCompraDAO()), 
					new PrecoMaterialService(new PrecoMaterialDAO(), new TipoMaterialService(new TipoMaterialDAO())));
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteProcessadoService", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "DetalharLoteProcessado":
					System.out.println("Sem implementação...");
					break;
					
				default:
					listarLotesProcessados(request, response);
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
	
	protected void listarLotesProcessados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<LoteProcessado> listaLotesProcessados = loteProcessadoService.listarLotesProcessado();
		
		request.setAttribute("listaLotesProcessados", listaLotesProcessados);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_processados/lotesProcessados.jsp");
		
		reqDis.forward(request, response);
		
	}

}
