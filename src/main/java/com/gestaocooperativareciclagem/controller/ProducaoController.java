package com.gestaocooperativareciclagem.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
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

import com.gestaocooperativareciclagem.dao.EtapaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.service.EtapaProcessamentoService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.google.gson.Gson;

/**
 * Servlet implementation class Producao
 */
@WebServlet(
		name="ProducaoController",
		urlPatterns= {"/Producao", "/AtualizarEtapaProcessamento"}
		)
public class ProducaoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TipoMaterialService tipoMaterialService;
	private EtapaProcessamentoService etapaProcessamentoService;
	private LoteProcessadoService loteProcessadoService;
       
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
			
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar TipoMaterialService e/ou LoteProcessadoService", e);
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
			
			System.out.println(body);
			/*
			Gson gson = new Gson();
			
			EtapaProcessamento etapaProcessamento = gson.fromJson(null, null)
			
			int idLoteProcessado = Integer.parseInt(request.getParameter("loteId"));
			int idCategoriaProcessamento = Integer.parseInt(request.getParameter("etapaId"));
			BigDecimal pesoAtual = new BigDecimal(request.getParameter("peso"));
			
			loteProcessadoService.atualizarLoteProcessado(idLoteProcessado, pesoAtual);
			loteProcessadoService.atualizarEtapaProcessamentoLoteProcessado(idLoteProcessado, idCategoriaProcessamento, Date.valueOf(LocalDate.now()), "Em Andamento");
			// etapaProcessamentoService.atualizarEtapaProcessamento(idLoteProcessado, idCategoriaProcessamento, Date.valueOf(LocalDate.now()), "Em Andamento");
			*/
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

}
