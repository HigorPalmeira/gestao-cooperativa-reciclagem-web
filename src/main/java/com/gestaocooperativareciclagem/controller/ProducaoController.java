package com.gestaocooperativareciclagem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
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
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.service.CategoriaProcessamentoService;
import com.gestaocooperativareciclagem.service.EtapaProcessamentoService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class Producao
 */
@WebServlet(
		name="ProducaoController",
		urlPatterns= {"/Producao", "/AtualizarEtapaProcessamento",
				"/ListarLotesProducao", "/ListarEtapasProducao",
				"/ListarCategoriasProducao"}
		)
public class ProducaoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TipoMaterialService tipoMaterialService;
	private EtapaProcessamentoService etapaProcessamentoService;
	private LoteProcessadoService loteProcessadoService;
	private CategoriaProcessamentoService categoriaProcessamentoService;
	private Gson gson;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProducaoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
			etapaProcessamentoService = new EtapaProcessamentoService(new EtapaProcessamentoDAO());
			loteProcessadoService = new LoteProcessadoService(new LoteProcessadoDAO(), 
					new TransacaoCompraService(new TransacaoCompraDAO()), 
					new PrecoMaterialService(new PrecoMaterialDAO(), tipoMaterialService), 
					etapaProcessamentoService, 
					new LoteBrutoService(new LoteBrutoDAO()));
			categoriaProcessamentoService = new CategoriaProcessamentoService(new CategoriaProcessamentoDAO());
			
			gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd")
					.create();
			
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar TipoMaterialService e/ou EtapaProcessamento e/ou LoteProcessadoService e/ou CategoriaProcessamentoService e/ou Gson", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/Producao":
					pageProducao(request, response);
					break;
					
				case "/ListarLotesProducao":
					listarLotesProcessados(request, response);
					break;
					
				case "/ListarEtapasProducao":
					listarEtapasProcessamento(request, response);
					break;
					
				case "/ListarCategoriasProducao":
					listarCategoriasProcessamento(request, response);
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
				case "/AtualizarEtapaProcessamento":
					atualizarEtapaProcessamentoLoteProcessado(request, response);
					break;
					
				default:
					pageProducao(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}

	}
	
	protected void pageProducao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();
			
			request.setAttribute("listaTiposMateriais", listaTiposMateriais);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/producao/producao.jsp");
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar acessar a 'Produção'.<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
	}
	
	protected void atualizarEtapaProcessamentoLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			BufferedReader reader = request.getReader();
			StringBuilder strBuilder = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				strBuilder.append(line);
			}
			
			String body = strBuilder.toString();
			
			
			Type typeEtapa = new TypeToken<EtapaProcessamento>() {}.getType();
			EtapaProcessamento etapaProcessamento = gson.fromJson(body, typeEtapa);
			
			
			loteProcessadoService.atualizarLoteProcessado(etapaProcessamento.getLoteProcessado().getId(), 
					etapaProcessamento.getLoteProcessado().getPesoAtualKg());
			loteProcessadoService.atualizarEtapaProcessamentoLoteProcessado(
					etapaProcessamento.getLoteProcessado().getId(), 
					etapaProcessamento.getCategoriaProcessamento().getId(), 
					Date.valueOf(LocalDate.now()), 
					"Em Andamento");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarLotesProcessados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			int idTipoMaterial = Integer.parseInt(request.getParameter("idTipoMaterial"));
			
			TipoMaterial tipoMaterial = new TipoMaterial();
			tipoMaterial.setId(idTipoMaterial);
			
			List<LoteProcessado> listaLotesProcessados = loteProcessadoService.listarLotesProcessadoPorTipoMaterial(tipoMaterial);
			
			String lotesJson = gson.toJson(listaLotesProcessados);
			System.out.println("Response: \n" + lotesJson);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(lotesJson);
			out.flush();
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarEtapasProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			int idTipoMaterial = Integer.parseInt(request.getParameter("idTipoMaterial"));
			
			List<EtapaProcessamento> listaEtapasProcessamento = etapaProcessamentoService.listarEtapasProcessamentoPorTipoMaterialENaoConcluidas(idTipoMaterial);
			
			String etapasJson = gson.toJson(listaEtapasProcessamento);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(etapasJson);
			out.flush();
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarCategoriasProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			List<CategoriaProcessamento> listaCategoriasProcessamento = categoriaProcessamentoService.listarCategoriasProcessamento();
			
			String categoriaJson = gson.toJson(listaCategoriasProcessamento);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			PrintWriter out = response.getWriter();
			out.print(categoriaJson);
			out.flush();
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

}
