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

import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.service.TipoMaterialService;

/**
 * Servlet implementation class TipoMaterialController
 */
@WebServlet({ "/TipoMaterialController", "/ListarTiposMateriais", 
	"/DetalharTipoMaterial", "/InserirTipoMaterial", 
	"/AtualizarTipoMaterial", "/DeletarTipoMaterial" })
public class TipoMaterialController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TipoMaterialService tipoMaterialService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TipoMaterialController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar TipoMaterialService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharTipoMaterial":
					System.out.println("Sem implementação...");
					break;
					
				default:
					listarTiposMateriais(request, response);
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
				case "/InserirTipoMaterial":
					inserirTipoMaterial(request, response);
					break;
					
				case "/AtualizarTipoMaterial":
					atualizarTipoMaterial(request, response);
					break;
					
				case "/DeletarTipoMaterial":
					deletarTipoMaterial(request, response);
					break;
					
				default:
					listarTiposMateriais(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirTipoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String nome = request.getParameter("modalName");
		String descricao = request.getParameter("modalDesc");
		
		tipoMaterialService.inserirTipoMaterial(nome, descricao);
		
		response.sendRedirect(request.getContextPath() + "/ListarTiposMateriais");
		
	}
	
	protected void atualizarTipoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		int id = Integer.parseInt(request.getParameter("modalId"));
		String nome = request.getParameter("modalName");
		String descricao = request.getParameter("modalDesc");
		
		tipoMaterialService.atualizarTipoMaterial(id, nome, descricao);
		
		response.sendRedirect(request.getContextPath() + "/ListarTiposMateriais");
		
	}
	
	protected void deletarTipoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int id = Integer.parseInt(request.getParameter("modalId"));
		
		tipoMaterialService.deletarTipoMaterial(id);
		
		response.sendRedirect(request.getContextPath() + "/ListarTiposMateriais");
		
	}

	protected void listarTiposMateriais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();
		
		request.setAttribute("listaTiposMateriais", listaTiposMateriais);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/tipos_materiais/tiposMaterial.jsp");
		
		reqDis.forward(request, response);
		
	}
	
}
