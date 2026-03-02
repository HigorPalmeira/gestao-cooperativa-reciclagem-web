package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.ClienteDAO;
import com.gestaocooperativareciclagem.dao.EtapaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;
import com.gestaocooperativareciclagem.service.ClienteService;
import com.gestaocooperativareciclagem.service.EtapaProcessamentoService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.gestaocooperativareciclagem.service.VendaService;

/**
 * Servlet implementation class Home
 */
@WebServlet(
		name="HomeController",
		urlPatterns= {"/Home"}
		)
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private LoteBrutoService loteBrutoService;
	private VendaService vendaService;
	private LoteProcessadoService loteProcessadoService;
	private TransacaoCompraService transacaoCompraService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			
			loteBrutoService = new LoteBrutoService(new LoteBrutoDAO());
			vendaService = new VendaService(new VendaDAO(), new ItemVendaDAO(), new ClienteService(new ClienteDAO()));
			transacaoCompraService = new TransacaoCompraService(new TransacaoCompraDAO());
			loteProcessadoService = new LoteProcessadoService(new LoteProcessadoDAO(), 
					transacaoCompraService, 
					new PrecoMaterialService(new PrecoMaterialDAO(), new TipoMaterialService(new TipoMaterialDAO())),
					new EtapaProcessamentoService(new EtapaProcessamentoDAO()),
					loteBrutoService);
			
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar LoteBrutoService e/ou VendaService e/ou TransacaoCompraService e/ou LoteProcessadoService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/Home":
					pageIndexDashboard(request, response);
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
	
	protected void pageIndexDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			LocalDate hoje = LocalDate.now();
			LocalDate inicioMes = hoje.with(TemporalAdjusters.firstDayOfMonth());
			LocalDate fimMes = hoje.with(TemporalAdjusters.lastDayOfMonth());
			
			Date dtHoje = Date.valueOf(hoje);
			
			Long lotesBrutosRecebidosHoje = loteBrutoService.contarLoteBrutoPorData(dtHoje);
			BigDecimal lotesBrutosKgRecebidosHoje = loteBrutoService.somarPesoEntradaLoteBrutoPorDatas(dtHoje, dtHoje);
			BigDecimal totalVendasNoMes = vendaService.somarValorTotalVendasPorDatas(Date.valueOf(inicioMes), Date.valueOf(fimMes));
			
			Long totalPagamentosPendentes = transacaoCompraService.contarTransacaoCompraPorStatus(StatusPagamentoTransacaoCompra.PENDENTE);
			BigDecimal valorTotalPagamentosPendentes = transacaoCompraService.somarValorTotalTransacaoCompraPorStatus(StatusPagamentoTransacaoCompra.PENDENTE);
			BigDecimal lotesProcessadosKgProntos = loteProcessadoService.somarPesoTotalLoteProcessadoPorEtapaProcessamento("Pronto para Venda");
			
			request.setAttribute("lotesBrutosRecebidosHoje", lotesBrutosRecebidosHoje);
			request.setAttribute("lotesBrutosKgRecebidosHoje", lotesBrutosKgRecebidosHoje);
			
			request.setAttribute("totalVendasMes", totalVendasNoMes);
			
			request.setAttribute("totalPagamentosPendentes", totalPagamentosPendentes);
			request.setAttribute("valorTotalPagamentosPendentes", valorTotalPagamentosPendentes);
			
			request.setAttribute("lotesProcessadosKgProntos", lotesProcessadosKgProntos);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("index.jsp");
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar ir para a página inicial!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
		
		}
		
	}

}
