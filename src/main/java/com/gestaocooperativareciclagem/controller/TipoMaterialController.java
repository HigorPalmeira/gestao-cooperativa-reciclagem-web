package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import com.google.gson.Gson;

/**
 * Servlet implementation class TipoMaterialController
 */
@WebServlet(
		name="TipoMaterialController",
		urlPatterns={ "/TipoMaterialController", "/ListarTiposMateriais", 
	"/DetalharTipoMaterial", "/InserirTipoMaterial", 
	"/AtualizarTipoMaterial", "/DeletarTipoMaterial",
	"/ListagemTiposMaterial"})
public class TipoMaterialController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TipoMaterialService tipoMaterialService;
	private Gson gson;
       
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
			gson = new Gson();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar TipoMaterialService e/ou Gson", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharTipoMaterial":
					System.out.println("Sem implementação...");
					break;
					
				case "/ListagemTiposMaterial":
					listarTiposMateriais(request, response);
					break;
					
				default:
					pageListarTiposMateriais(request, response);
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

		request.setCharacterEncoding("UTF-8");
		
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
					pageListarTiposMateriais(request, response);
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

	protected void pageListarTiposMateriais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			List<TipoMaterial> listaTiposMateriais = listarTipos(request); 
			//tipoMaterialService.listarTiposMaterial();
			
			request.setAttribute("listaTiposMateriais", listaTiposMateriais);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/tipos_materiais/tiposMaterial.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarTiposMateriais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		try {
			
			List<TipoMaterial> listaTiposMateriais = listarTipos(request);
			
			String tiposJson = gson.toJson(listaTiposMateriais);
			
			PrintWriter out = response.getWriter();
			out.print(tiposJson);
			out.flush();
			
		} catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar as categorias de processamento. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");
			
			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();

		}
		
	}
	
	private List<TipoMaterial> listarTipos(HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		String idTipoMaterialTxt = request.getParameter("idTipoMaterial");
		String nomeTipoMaterial = request.getParameter("nome");
		String descricaoTipoMaterial = request.getParameter("descricao");
		
		Integer idTipoMaterial = null;
		if (idTipoMaterialTxt != null) {
			idTipoMaterial = Integer.parseInt(idTipoMaterialTxt);
		}
		
		return tipoMaterialService.listarTiposMaterialComParametros(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
		
	}
	
}
