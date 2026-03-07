package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.CategoriaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.EtapaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.service.CategoriaProcessamentoService;
import com.gestaocooperativareciclagem.service.EtapaProcessamentoService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class LoteProcessadoController
 */
@WebServlet(
		name="LoteProcessadoController",
		urlPatterns= {"/LoteProcessadoController", "/ListarLotesProcessados", 
		"/DetalharLoteProcessado", "/NovoLoteProcessado",
		"/InserirLoteProcessado", "/AtualizarLoteProcessado",
		"/DeletarLoteProcessado", "/ListagemLotesProcessado"})
public class LoteProcessadoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private LoteProcessadoService loteProcessadoService;
	private LoteBrutoService loteBrutoService;
	private TipoMaterialService tipoMaterialService;
	private CategoriaProcessamentoService categoriaProcessamentoService;
	private EtapaProcessamentoService etapaProcessamentoService;
	private Gson gson;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoteProcessadoController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init() throws ServletException {
		try {
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
			etapaProcessamentoService = new EtapaProcessamentoService(new EtapaProcessamentoDAO());
			loteBrutoService = new LoteBrutoService(new LoteBrutoDAO());
			loteProcessadoService = new LoteProcessadoService(
					new LoteProcessadoDAO(), 
					new TransacaoCompraService(new TransacaoCompraDAO()), 
					new PrecoMaterialService(new PrecoMaterialDAO(), tipoMaterialService),
					etapaProcessamentoService,
					loteBrutoService);
			categoriaProcessamentoService = new CategoriaProcessamentoService(new CategoriaProcessamentoDAO());
			gson = new GsonBuilder()
					.setDateFormat("YYYY-MM-dd")
					.create();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteProcessadoService e/ou LoteBrutoService e/ou TipoMaterialService e/ou CategoriaProcessamentoService e/ou EtapaProcessamentoService e/ou Gson", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharLoteProcessado":
					buscarLoteProcessado(request, response);
					break;
					
				case "/NovoLoteProcessado":
					pageNovoLoteProcessado(request, response);
					break;
					
				case "/ListagemLotesProcessado":
					listarLotesProcessadosJson(request, response);
					break;
					
				default:
					pageListarLotesProcessados(request, response);
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
		response.setCharacterEncoding("UTF-8");
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/InserirLoteProcessado":
					inserirLoteProcessado(request, response);
					break;
					
				case "/AtualizarLoteProcessado":
					atualizarLoteProcessado(request, response);
					break;
					
				case "/DeletarLoteProcessado":
					deletarLoteProcessado(request, response);
					break;
					
				default:
					pageListarLotesProcessados(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String idLoteBrutoTexto = request.getParameter("rawBatchId");
			int idTipoMaterial = Integer.parseInt(request.getParameter("materialType"));
			int idCategoriaProcessamento = Integer.parseInt(request.getParameter("processStage"));
			BigDecimal pesoAtualKg = new BigDecimal(request.getParameter("currentWeight"));
			
			int idLoteBruto = Integer.parseInt(idLoteBrutoTexto.replaceAll("\\D", ""));
			
			TipoMaterial tipoMaterial = tipoMaterialService.buscarTipoMaterialPorId(idTipoMaterial);
			CategoriaProcessamento categoriaProcessamento = categoriaProcessamentoService.buscarCategoriaProcessamentoPorId(idCategoriaProcessamento);
			LoteBruto loteBruto = loteBrutoService.buscarLoteBrutoPorId(idLoteBruto);
			
			loteProcessadoService.inserirLoteProcessado(pesoAtualKg, tipoMaterial, categoriaProcessamento, loteBruto);
			
			response.sendRedirect(request.getContextPath() + "/ListarLotesProcessados");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void atualizarLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			int idLoteProcessado = Integer.parseInt(request.getParameter("id"));
			// int idTipoMaterial = Integer.parseInt(request.getParameter("materialType"));
			// int idLoteBruto = Integer.parseInt(request.getParameter("rawBatchId"));
			
			BigDecimal pesoAtualKg = new BigDecimal(request.getParameter("currentWeight"));
			
			// TipoMaterial tipoMaterial = tipoMaterialService.buscarTipoMaterialPorId(idTipoMaterial);
			// LoteBruto loteBruto = loteBrutoService.buscarLoteBrutoPorId(idLoteBruto);
			
			loteProcessadoService.atualizarLoteProcessado(idLoteProcessado, pesoAtualKg);
			
			request.getSession().setAttribute("msgSucesso", "Lote Processado atualizado com sucesso!");
			
		} catch (Exception e) {
			
			request.setAttribute("msgErro", "Ocorreu um erro na atualização do Lote Bruto! Se o erro persistir contate o administrador do sistema.<br>Erro: " + e.getMessage());
		
		}
		
		buscarLoteProcessado(request, response);
		
	}
	
	protected void deletarLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idLoteProcessado = Integer.parseInt(request.getParameter("id"));
			
			loteProcessadoService.deletarLoteProcessado(idLoteProcessado);
			response.sendRedirect(request.getContextPath() + "/ListarLotesProcessados");
			
		} catch (Exception e) {

			request.setAttribute("msgErro", "Ocorreu um erro na remoção do Lote Processado! Se o erro persistir contate o administrador do sistema.<br>Erro: " + e.getMessage());
			buscarLoteProcessado(request, response);

		}
		
		
	}
	
	protected void buscarLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			LoteProcessado loteProcessado = loteProcessadoService.buscarLoteProcessadoPorId(id);
			List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();
			List<EtapaProcessamento> listaEtapasProcessamento = etapaProcessamentoService.listarEtapasProcessamentoPorLoteProcessado(loteProcessado.getId());
			
			request.setAttribute("loteProcessado", loteProcessado);
			request.setAttribute("listaTiposMateriais", listaTiposMateriais);
			request.setAttribute("listaEtapasProcessamento", listaEtapasProcessamento);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_processados/detalheLoteProcessado.jsp");
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void pageNovoLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<TipoMaterial> listaTiposMaterial = tipoMaterialService.listarTiposMaterial();
		
		List<CategoriaProcessamento> listaCategoriasProcessamento = categoriaProcessamentoService.listarCategoriasProcessamento();
		
		List<LoteBruto> listaLotesBrutos = loteBrutoService.listarLotesBrutosPorStatus(StatusLoteBruto.EM_TRIAGEM);
	
		request.setAttribute("listaTiposMateriais", listaTiposMaterial);
		request.setAttribute("listaCategoriasProcessamento", listaCategoriasProcessamento);
		request.setAttribute("listaLotesBrutos", listaLotesBrutos);
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_processados/novoLoteProcessado.jsp");
		reqDis.forward(request, response);
		
	}
	
	protected void pageListarLotesProcessados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<LoteProcessado> listaLotesProcessados = loteProcessadoService.listarLotesProcessado();
		List<EtapaProcessamento> listaEtapasProcessamento = new ArrayList<>(listaLotesProcessados.size());
		
		for (LoteProcessado loteProcessado : listaLotesProcessados) {
			EtapaProcessamento etapa = etapaProcessamentoService.buscarEtapaProcessamentoAtualPorLoteProcessado(loteProcessado.getId());
			listaEtapasProcessamento.add(etapa);
		}
		
		request.setAttribute("listaLotesProcessados", listaLotesProcessados);
		request.setAttribute("listaEtapasProcessamento", listaEtapasProcessamento);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_processados/lotesProcessados.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarLotesProcessadosJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		try {
			
			List<LoteProcessado> listaLotesProcessado = listarLotesProcessado(request);
			List<EtapaProcessamento> listaEtapasProcessamento = new ArrayList<>(listaLotesProcessado.size());
			
			for (LoteProcessado loteProcessado : listaLotesProcessado) {
				EtapaProcessamento etapa = etapaProcessamentoService.buscarEtapaProcessamentoAtualPorLoteProcessado(loteProcessado.getId());
				listaEtapasProcessamento.add(etapa);
			}
			
			Map<String, Object> container = new HashMap<>();
			container.put("lotesProcessados", listaLotesProcessado);
			container.put("etapasProcessamento", listaEtapasProcessamento);
			
			String dadosJson = gson.toJson(container);
			
			PrintWriter out = response.getWriter();
			out.print(dadosJson);
			out.flush();
			
		} catch (Exception e) {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar os lotes processados. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");
			
			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();
			
		}
		
	}
	
	private List<LoteProcessado> listarLotesProcessado(HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		String idLoteProcessadoTxt = request.getParameter("id");
		String idLoteBrutoTxt = request.getParameter("idLoteBruto");
		String idTipoMaterialTxt = request.getParameter("idTipoMaterial");
		String pesoAtualTxt = request.getParameter("pesoAtual");
		String dtCriacaoTxt = request.getParameter("dataCriacao");
		
		Integer idLoteProcessado = null;
		if (idLoteProcessadoTxt != null && !idLoteProcessadoTxt.isBlank()) {
			idLoteProcessado = Integer.parseInt(idLoteProcessadoTxt.trim());
		}
		
		Integer idLoteBruto = null;
		if (idLoteBrutoTxt != null && !idLoteBrutoTxt.isBlank()) {
			idLoteBruto = Integer.parseInt(idLoteBrutoTxt.trim());
		}
		
		Integer idTipoMaterial = null;
		if (idTipoMaterialTxt != null && !idTipoMaterialTxt.isBlank()) {
			idTipoMaterial = Integer.parseInt(idTipoMaterialTxt.trim());
		}
		
		BigDecimal pesoAtual = null;
		if (pesoAtualTxt != null && !pesoAtualTxt.isBlank()) {
			pesoAtual = new BigDecimal(pesoAtualTxt.trim());
		}
		
		Date dtCriacao = null;
		if (dtCriacaoTxt != null && !dtCriacaoTxt.isBlank()) {
			dtCriacao = Date.valueOf(LocalDate.parse(dtCriacaoTxt.trim()));
		}
		
		return loteProcessadoService.listarLotesProcessadoComParametro(idLoteProcessado, idLoteBruto, idTipoMaterial, pesoAtual, dtCriacao);
		
	}

}
