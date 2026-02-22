package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.util.List;

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

/**
 * Servlet implementation class LoteProcessadoController
 */
@WebServlet(urlPatterns= {"/LoteProcessadoController", "/ListarLotesProcessados", 
		"/DetalharLoteProcessado", "/NovoLoteProcessado",
		"/InserirLoteProcessado", "/AtualizarLoteProcessado",
		"/DeletarLoteProcessado"})
public class LoteProcessadoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private LoteProcessadoService loteProcessadoService;
	private LoteBrutoService loteBrutoService;
	private TipoMaterialService tipoMaterialService;
	private CategoriaProcessamentoService categoriaProcessamentoService;
	
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
			loteProcessadoService = new LoteProcessadoService(
					new LoteProcessadoDAO(), 
					new TransacaoCompraService(new TransacaoCompraDAO()), 
					new PrecoMaterialService(new PrecoMaterialDAO(), tipoMaterialService),
					new EtapaProcessamentoService(new EtapaProcessamentoDAO()));
			loteBrutoService = new LoteBrutoService(new LoteBrutoDAO());
			categoriaProcessamentoService = new CategoriaProcessamentoService(new CategoriaProcessamentoDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteProcessadoService e/ou LoteBrutoService e/ou TipoMaterialService e/ou CategoriaProcessamentoService", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharLoteProcessado":
					System.out.println("Sem implementação...");
					break;
					
				case "/NovoLoteProcessado":
					pageNovoLoteProcessado(request, response);
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
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/InserirLoteProcessado":
					inserirLoteProcessado(request, response);
					break;
					
				case "/AtualizarLoteProcessado":
					System.out.println("Sem implementação...");
					break;
					
				case "/DeletarLoteProcessado":
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
	
	protected void inserirLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String idLoteBrutoTexto = request.getParameter("rawBatchId");
			int idTipoMaterial = Integer.parseInt(request.getParameter("materialType"));
			int idCategoriaProcessamento = Integer.parseInt(request.getParameter("processStage"));
			double pesoAtualKg = Double.parseDouble(request.getParameter("currentWeight"));
			
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
	
	protected void listarLotesProcessados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<LoteProcessado> listaLotesProcessados = loteProcessadoService.listarLotesProcessado();
		
		request.setAttribute("listaLotesProcessados", listaLotesProcessados);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/lotes_processados/lotesProcessados.jsp");
		
		reqDis.forward(request, response);
		
	}

}
