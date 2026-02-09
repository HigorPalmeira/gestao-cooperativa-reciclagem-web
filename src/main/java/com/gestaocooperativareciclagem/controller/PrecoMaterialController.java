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

import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.model.PrecoMaterial;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;

/**
 * Servlet implementation class PrecoMaterialController
 */
@WebServlet({ "/PrecoMaterialController", "/ListarPrecosMateriais", "/DetalharPrecoMaterial" })
public class PrecoMaterialController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PrecoMaterialService precoMaterialService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrecoMaterialController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			precoMaterialService = new PrecoMaterialService(
					new PrecoMaterialDAO(), 
					new TipoMaterialService(new TipoMaterialDAO()));
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar PrecoMaterialService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "DetalharPrecoMaterial":
					System.out.println("Sem implementação...");
					break;
				
				default:
					listarPrecosMateriais(request, response);
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
	
	protected void listarPrecosMateriais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<PrecoMaterial> listaPrecosMateriais = precoMaterialService.listarPrecosMaterial();
		
		request.setAttribute("listaPrecosMateriais", listaPrecosMateriais);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/precos_materiais/precosMateriais.jsp");
		
		reqDis.forward(request, response);
		
	}

}
