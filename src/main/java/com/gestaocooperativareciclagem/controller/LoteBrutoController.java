package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.EtapaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.service.EtapaProcessamentoService;
import com.gestaocooperativareciclagem.service.FornecedorService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class LoteBrutoController
 */
@WebServlet(
		name="LoteBrutoController",
		urlPatterns={ "/LoteBrutoController", "/ListarLotesBruto",
			"/InserirLoteBruto", "/DetalharLoteBruto",
			"/AtualizarLoteBruto", "/DeletarLoteBruto",
			"/BuscarFornecedorLote", "/ListagemLotesBruto"})
public class LoteBrutoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoteBrutoService loteBrutoService;
    private FornecedorService fornecedorService;
    private LoteProcessadoService loteProcessadoService;
    private TransacaoCompraService transacaoCompraService;
    private EtapaProcessamentoService etapaProcessamentoService;
    private Gson gson;
    
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
			transacaoCompraService = new TransacaoCompraService(new TransacaoCompraDAO());
			etapaProcessamentoService = new EtapaProcessamentoService(new EtapaProcessamentoDAO());
			loteProcessadoService = new LoteProcessadoService(
											new LoteProcessadoDAO(), 
											transacaoCompraService, 
											new PrecoMaterialService(new PrecoMaterialDAO(), 
													new TipoMaterialService(new TipoMaterialDAO())),
											etapaProcessamentoService,
											loteBrutoService);
			gson = new GsonBuilder()
					.setDateFormat("YYYY-MM-dd")
					.create();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteBrutoService e/ou FornecedorService e/ou LoteProcessadoService e/ou TransacaoCompraService e/ou EtapaProcessamentoService e/ou Gson", e);
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
			
				case "/DetalharLoteBruto":
					buscarLoteBruto(request, response);
					break;
					
				case "/BuscarFornecedorLote":
					buscarFornecedorDoLoteBruto(request, response);
					break;
					
				case "/ListagemLotesBruto":
					listarLotesBrutoJson(request, response);
					break;
					
				default:
					pageListarLotesBruto(request, response);
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
				case "/InserirLoteBruto":
					inserirLoteBruto(request, response);
					break;
					
				case "/AtualizarLoteBruto":
					atualizarLoteBruto(request, response);
					break;
					
				case "/DeletarLoteBruto":
					deletarLoteBruto(request, response);
					break;
					
				default:
					pageListarLotesBruto(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void inserirLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		String origem = request.getParameter("origem");
		
		try {
			
			String documento = request.getParameter("supplierDoc");
			BigDecimal pesoEntrada = new BigDecimal(request.getParameter("entryWeight")); 
			
			Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);
			
			loteBrutoService.inserirLoteBruto(pesoEntrada, Date.valueOf(LocalDate.now()), StatusLoteBruto.RECEBIDO, fornecedor);

			response.sendRedirect(request.getContextPath() + "/ListarLotesBruto");
			
		} catch (Exception e) {
			request.getSession().setAttribute("msgErro", "Ocorreu um erro no cadastro do lote bruto.<br>Erro: " + e.getMessage());
			
			if (origem == null || origem.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else {
				response.sendRedirect(request.getContextPath() + origem);
			}
			
		}
		
	}
	
	protected void atualizarLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idLoteBruto = Integer.parseInt(request.getParameter("id"));
			BigDecimal pesoEntrada = new BigDecimal(request.getParameter("entryWeight"));
			StatusLoteBruto status = StatusLoteBruto.fromDescricao(request.getParameter("batchStatus"));
			String documento = request.getParameter("supplierDoc");
		
			Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);
			
			loteBrutoService.atualizarLoteBruto(idLoteBruto, pesoEntrada, status, fornecedor);
			
			request.getSession().setAttribute("msgSucesso", "Lote Bruto atualizado com sucesso!");
			
		} catch (Exception e) {
			request.setAttribute("msgErro", "Ocorreu um erro na atualização do Lote Bruto! Se o erro persistir contate o administrador do sistema.<br>Erro: " + e.getMessage());
		}
		
		buscarLoteBruto(request, response);
	}
	
	protected void deletarLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idLoteBruto = Integer.parseInt(request.getParameter("id"));
			
			loteBrutoService.deletarLoteBruto(idLoteBruto);
			
			response.sendRedirect(request.getContextPath() + "/ListarLotesBruto");
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao deletar o lote bruto!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
	}
	
	protected void buscarLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int id = Integer.parseInt(request.getParameter("id"));
			
			LoteBruto loteBruto = loteBrutoService.buscarLoteBrutoPorId(id);
			
			request.setAttribute("loteBruto", loteBruto);
			
			carregarDependenciasDoLoteBruto(request, loteBruto);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_bruto/detalheLoteBruto.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar os dados do lote bruto!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
	}
	
	protected void buscarFornecedorDoLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idLoteBruto = Integer.parseInt(request.getParameter("id"));
			BigDecimal pesoEntrada = new BigDecimal(request.getParameter("entryWeight"));
			StatusLoteBruto status = StatusLoteBruto.fromDescricao(request.getParameter("batchStatus"));
			String documento = request.getParameter("supplierDoc");
			
			
			LoteBruto loteBruto = loteBrutoService.buscarLoteBrutoPorId(idLoteBruto);
			loteBruto.setPesoEntradaKg(pesoEntrada);
			loteBruto.setStatus(status);

			request.setAttribute("loteBruto", loteBruto);
			carregarDependenciasDoLoteBruto(request, loteBruto);
			
			Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);

			if (fornecedor != null) {
				loteBruto.setFornecedor(fornecedor);
			} else {
				
				request.setAttribute("msgErro", "Nenhum fornecedor encontrado com o documento: " + documento);
				
				Fornecedor fornecedorFalso = new Fornecedor();
				fornecedorFalso.setDocumento(documento);
				fornecedorFalso.setNome("");
				loteBruto.setFornecedor(fornecedorFalso);
				
			}
			
			request.setAttribute("loteBruto", loteBruto);
			
		} catch (Exception e) {
			// throw new ServletException(e);
			request.setAttribute("msgErro", "Ocorreu um erro na busca pelo fornecedor! Se o erro persistir contate o administrador do sistema.");
		}
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_bruto/detalheLoteBruto.jsp");
		reqDis.forward(request, response);
		
	}
	
	protected void pageListarLotesBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			List<LoteBruto> lotesBrutos = listarLotesBruto(request);
			
			request.setAttribute("listaLotesBrutos", lotesBrutos);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_bruto/lotesBrutos.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar os lotes brutos registrados!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
		
	}
	
	protected void listarLotesBrutoJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		try {
			
			List<LoteBruto> listarLotesBruto = listarLotesBruto(request);
			
			String lotesBrutoJson = gson.toJson(listarLotesBruto);
			
			PrintWriter out = response.getWriter();
			out.print(lotesBrutoJson);
			out.flush();
			
		} catch (Exception e) {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar os lotes brutos. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");
			
			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();
			
		}
		
	}
	
	private void carregarDependenciasDoLoteBruto(HttpServletRequest request, LoteBruto loteBruto) throws SQLException {
		
		List<TransacaoCompra> listaTransacoesCompra = transacaoCompraService.listarTransacoesCompraPorLoteBruto(loteBruto);

		List<LoteProcessado> listaLotesProcessados = loteProcessadoService.listarLotesProcessadoPorLoteBruto(loteBruto);
		List<EtapaProcessamento> listaEtapasProcessamento = new ArrayList<>(listaLotesProcessados.size());
		
		for (LoteProcessado loteProcessado : listaLotesProcessados) {
			EtapaProcessamento etapa = etapaProcessamentoService.buscarEtapaProcessamentoAtualPorLoteProcessado(loteProcessado.getId());
			listaEtapasProcessamento.add(etapa);
		}
		
		request.setAttribute("listaTransacoesCompra", listaTransacoesCompra);
		request.setAttribute("listaLotesProcessados", listaLotesProcessados);
		request.setAttribute("listaEtapasProcessamento", listaEtapasProcessamento);
		
	}
	
	private List<LoteBruto> listarLotesBruto(HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		String idLoteBrutoTxt = request.getParameter("id");
		String documentoFornecedor = request.getParameter("doc");
		String statusTxt = request.getParameter("status");
		String pesoEntradaTxt = request.getParameter("pesoEntrada");
		String dtEntradaTxt = request.getParameter("dataEntrada");
		
		Integer idLoteBruto = null;
		if (idLoteBrutoTxt != null && !idLoteBrutoTxt.isBlank()) {
			idLoteBruto = Integer.parseInt(idLoteBrutoTxt.trim());
		}
		
		StatusLoteBruto status = null;
		if (statusTxt != null && !statusTxt.isBlank()) {
			status = StatusLoteBruto.valueOf(statusTxt.trim());
		}
		
		BigDecimal pesoEntrada = null;
		if (pesoEntradaTxt != null && !pesoEntradaTxt.isBlank()) {
			pesoEntrada = new BigDecimal(pesoEntradaTxt.trim());
		}
		
		Date dtEntrada = null;
		if (dtEntradaTxt != null && !dtEntradaTxt.isBlank()) {
			dtEntrada = Date.valueOf(LocalDate.parse(dtEntradaTxt.trim()));
		}
		
		return loteBrutoService.listarLotesBrutoComParametro(idLoteBruto, documentoFornecedor, status, pesoEntrada, dtEntrada);
		
	}

}
