package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.sql.Date;
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
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;

/**
 * Servlet implementation class PrecoMaterialController
 */
@WebServlet(
		name="PrecoMaterialController", 
		urlPatterns={ "/PrecoMaterialController", "/ListarPrecosMateriais", 
	"/DetalharPrecoMaterial", "/InserirPrecoMaterial",
	"/AtualizarPrecoMaterial", "/DeletarPrecoMaterial"})
public class PrecoMaterialController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PrecoMaterialService precoMaterialService;
	private TipoMaterialService tipoMaterialService;
       
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
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
			precoMaterialService = new PrecoMaterialService(
					new PrecoMaterialDAO(), 
					tipoMaterialService);
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar PrecoMaterialService e/ou TipoMaterialService", e);
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
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/InserirPrecoMaterial":
					inserirPrecoMaterial(request, response);
					break;
					
				case "/AtualizarPrecoMaterial":
					atualizarPrecoMaterial(request, response);
					break;
					
				case "/DeletarPrecoMaterial":
					deletarPrecoMaterial(request, response);
					break;
					
				default:
					listarPrecosMateriais(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		double preco = Double.parseDouble(request.getParameter("modalPrice"));
		Date dtVigencia = Date.valueOf(request.getParameter("modalDate"));
		int idMaterial = Integer.parseInt(request.getParameter("modalMaterial"));
		
		precoMaterialService.inserirPrecoMaterial(preco, dtVigencia, idMaterial);
		
		response.sendRedirect(request.getContextPath() + "/ListarPrecosMateriais");
		
	}
	
	protected void atualizarPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int idPreco = Integer.parseInt(request.getParameter("modalId"));
		double preco = Double.parseDouble(request.getParameter("modalPrice"));
		Date dtVigencia = Date.valueOf(request.getParameter("modalDate"));
		int idMaterial = Integer.parseInt(request.getParameter("modalMaterial"));
		
		precoMaterialService.atualizarPrecoMaterial(idPreco, preco, dtVigencia, idMaterial);
		
		response.sendRedirect(request.getContextPath() + "/ListarPrecosMateriais");
		
	}
	
	protected void deletarPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int idPreco = Integer.parseInt(request.getParameter("modalId"));
		
		precoMaterialService.deletarPrecoMaterial(idPreco);
		
		response.sendRedirect(request.getContextPath() + "/ListarPrecosMateriais");
		
	}
	
	protected void listarPrecosMateriais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<PrecoMaterial> listaPrecosMateriais = precoMaterialService.listarPrecosMaterial();
		
		List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();
		
		request.setAttribute("listaPrecosMateriais", listaPrecosMateriais);
		request.setAttribute("listaTiposMateriais", listaTiposMateriais);
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/precos_materiais/precosMateriais.jsp");
		
		reqDis.forward(request, response);
		
	}

}
