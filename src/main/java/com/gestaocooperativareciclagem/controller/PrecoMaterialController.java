package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class PrecoMaterialController
 */
@WebServlet(
		name="PrecoMaterialController",
		urlPatterns={ "/PrecoMaterialController", "/ListarPrecosMateriais",
	"/DetalharPrecoMaterial", "/InserirPrecoMaterial",
	"/AtualizarPrecoMaterial", "/DeletarPrecoMaterial",
	"/ListagemPrecosMaterial"})
public class PrecoMaterialController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private PrecoMaterialService precoMaterialService;
	private TipoMaterialService tipoMaterialService;
	private Gson gson;

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
	@Override
	public void init(ServletConfig config) throws ServletException {

		try {
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
			precoMaterialService = new PrecoMaterialService(
					new PrecoMaterialDAO(),
					tipoMaterialService);
			gson = new GsonBuilder()
					.setDateFormat("YYYY-MM-dd")
					.create();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar PrecoMaterialService e/ou TipoMaterialService e/ou Gson", e);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String path = request.getServletPath();

		try {

			switch(path) {
				case "/DetalharPrecoMaterial":
					System.out.println("Sem implementação...");
					break;

				case "/ListagemPrecosMaterial":
					listarPrecosMateriaisJson(request, response);
					break;

				default:
					pageListarPrecosMateriais(request, response);
					break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

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
					pageListarPrecosMateriais(request, response);
					break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void inserirPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {

			BigDecimal preco = new BigDecimal(request.getParameter("modalPrice"));
			Date dtVigencia = Date.valueOf(request.getParameter("modalDate"));
			int idMaterial = Integer.parseInt(request.getParameter("modalMaterial"));

			precoMaterialService.inserirPrecoMaterial(preco, dtVigencia, idMaterial);

			response.sendRedirect(request.getContextPath() + "/ListarPrecosMateriais");

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar inserir os dados de um novo preço de material!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void atualizarPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {

			int idPreco = Integer.parseInt(request.getParameter("modalId"));
			BigDecimal preco = new BigDecimal(request.getParameter("modalPrice"));
			Date dtVigencia = Date.valueOf(request.getParameter("modalDate"));
			int idMaterial = Integer.parseInt(request.getParameter("modalMaterial"));

			precoMaterialService.atualizarPrecoMaterial(idPreco, preco, dtVigencia, idMaterial);

			response.sendRedirect(request.getContextPath() + "/ListarPrecosMateriais");

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar atualizar os dados de um preço de material!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void deletarPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			int idPreco = Integer.parseInt(request.getParameter("modalId"));

			precoMaterialService.deletarPrecoMaterial(idPreco);

			response.sendRedirect(request.getContextPath() + "/ListarPrecosMateriais");

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar deletar os dados do preço de material!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void pageListarPrecosMateriais(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			List<PrecoMaterial> listaPrecosMateriais = listarPrecosMaterial(request);

			List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();

			request.setAttribute("listaPrecosMateriais", listaPrecosMateriais);
			request.setAttribute("listaTiposMateriais", listaTiposMateriais);

			RequestDispatcher reqDis = request.getRequestDispatcher("pages/precos_materiais/precosMateriais.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar os preços dos materiais!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void listarPrecosMateriaisJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");

		try {

			List<PrecoMaterial> listaPrecosMaterial = listarPrecosMaterial(request);
			List<TipoMaterial> listaTiposMaterial = tipoMaterialService.listarTiposMaterial();

			Map<String, Object> container = new HashMap<>();
			container.put("precosMateriais", listaPrecosMaterial);
			container.put("tiposMaterial", listaTiposMaterial);

			String dadosJson = gson.toJson(container);

			PrintWriter out = response.getWriter();
			out.print(dadosJson);
			out.flush();

		} catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar os preços dos materiais. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");

			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();

		}

	}

	private List<PrecoMaterial> listarPrecosMaterial(HttpServletRequest request) throws ServletException, IOException, SQLException {

		String idPrecoMaterialTxt = request.getParameter("id");
		String precoMinTxt = request.getParameter("precoMin");
		String precoMaxTxt = request.getParameter("precoMax");
		String dtInicialTxt = request.getParameter("dataInicial");
		String dtFinalTxt = request.getParameter("dataFinal");
		String idTipoMaterialTxt = request.getParameter("idTipoMaterial");
		String nomeTipoMaterial = request.getParameter("nomeTipoMaterial");

		Integer idPrecoMaterial = null;
		if (idPrecoMaterialTxt != null && !idPrecoMaterialTxt.isBlank()) {
			idPrecoMaterial = Integer.parseInt(idPrecoMaterialTxt.trim());
		}

		Integer idTipoMaterial = null;
		if (idTipoMaterialTxt != null && !idTipoMaterialTxt.isBlank()) {
			idTipoMaterial = Integer.parseInt(idTipoMaterialTxt.trim());
		}

		BigDecimal precoMin = null;
		if (precoMinTxt != null && !precoMinTxt.isBlank()) {
			precoMin = new BigDecimal(precoMinTxt.trim());
		}

		BigDecimal precoMax = null;
		if (precoMaxTxt != null && !precoMaxTxt.isBlank()) {
			precoMax = new BigDecimal(precoMaxTxt.trim());
		}

		Date dtInicial = null;
		if (dtInicialTxt != null && !dtInicialTxt.isBlank()) {
			dtInicial = Date.valueOf(LocalDate.parse(dtInicialTxt.trim()));
		}

		Date dtFinal = null;
		if (dtFinalTxt != null && !dtFinalTxt.isBlank()) {
			dtFinal = Date.valueOf(LocalDate.parse(dtFinalTxt.trim()));
		}

		return precoMaterialService.listarPrecosMaterialComParametro(idPrecoMaterial, precoMin, precoMax, dtInicial, dtFinal, idTipoMaterial, nomeTipoMaterial);

	}

}
