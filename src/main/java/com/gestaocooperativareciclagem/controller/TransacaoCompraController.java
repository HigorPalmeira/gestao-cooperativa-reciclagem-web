package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
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

import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class TransacaoCompraController
 */
@WebServlet(
		name="TransacaoCompraController",
		urlPatterns={ "/TransacaoCompraController", "/ListarTransacoesCompra",
	"/DetalharTransacaoCompra", "/AtualizarTransacaoCompra",
	"/DeletarTransacaoCompra", "/ListagemTransacoesCompra"})
public class TransacaoCompraController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private TransacaoCompraService transacaoCompraService;
	private Gson gson;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransacaoCompraController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		try {

			transacaoCompraService = new TransacaoCompraService(new TransacaoCompraDAO());
			gson = new GsonBuilder()
					.setDateFormat("YYYY-MM-dd")
					.create();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar TransacaoCompraService e/ou Gson", e);
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
				case "/DetalharTransacaoCompra":
					buscarTransacaoCompra(request, response);
					break;

				case "/ListagemTransacoesCompra":
					listarTransacoesCompraJson(request, response);
					break;

				default:
					pageListarTransacoesCompra(request, response);
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
				case "/AtualizarTransacaoCompra":
					atualizarTransacaoCompra(request, response);
					break;

				case "/DeletarTransacaoCompra":
					deletarTransacaoCompra(request, response);
					break;

				default:
					pageListarTransacoesCompra(request, response);
					break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void atualizarTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			int idTransacao = Integer.parseInt(request.getParameter("id"));
			String statusTexto = request.getParameter("paymentStatus");

			StatusPagamentoTransacaoCompra status = StatusPagamentoTransacaoCompra.valueOf(statusTexto);

			transacaoCompraService.atualizarTransacaoCompra(idTransacao, BigDecimal.ZERO, status, null, Date.valueOf(LocalDate.now()), null);

			request.getSession().setAttribute("msgSucesso", "Transação de Compra atualizada com sucesso!");

		} catch (Exception e) {
			request.setAttribute("msgErro", "Ocorreu um erro na atualização da Transação de Compra. Error: " + e.getMessage());
		}

		buscarTransacaoCompra(request, response);

	}

	protected void deletarTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			int idTransacao = Integer.parseInt(request.getParameter("id"));

			transacaoCompraService.deletarTransacaoCompra(idTransacao);

			response.sendRedirect(request.getContextPath() + "/ListarTransacoesCompra");

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar deletar os dados da transação de compra! Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void buscarTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			int idTransacao = Integer.parseInt(request.getParameter("id"));

			TransacaoCompra transacaoCompra = transacaoCompraService.buscarTransacaoCompraPorId(idTransacao);

			request.setAttribute("transacaoCompra", transacaoCompra);

			RequestDispatcher reqDis = request.getRequestDispatcher("pages/transacoes_compra/detalheTransacaoCompra.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar os dados da transação de compra! Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void pageListarTransacoesCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			List<TransacaoCompra> listaTransacoesCompra = listarTransacoesCompra(request);

			request.setAttribute("listaTransacoesCompra", listaTransacoesCompra);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/transacoes_compra/transacoesCompra.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar as transações de compra!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}


	}

	protected void listarTransacoesCompraJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");

		try {

			List<TransacaoCompra> listaTransacoesCompra = listarTransacoesCompra(request);

			String transacoesCompraJson = gson.toJson(listaTransacoesCompra);

			PrintWriter out = response.getWriter();
			out.print(transacoesCompraJson);
			out.flush();

		} catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar as transações de compra. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");

			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();

		}

	}

	private List<TransacaoCompra> listarTransacoesCompra(HttpServletRequest request) throws ServletException, IOException, SQLException {

		String idTransacaoCompraTxt = request.getParameter("id");
		String idLoteBrutoTxt = request.getParameter("idLoteBruto");
		String dtPagamentoInicialTxt = request.getParameter("dataInicial");
		String dtPagamentoFinalTxt = request.getParameter("dataFinal");
		String valorMinTxt = request.getParameter("valorMin");
		String valorMaxTxt = request.getParameter("valorMax");
		String statusPagamentoTxt = request.getParameter("status");

		Integer idTransacaoCompra = null;
		if (idTransacaoCompraTxt != null && !idTransacaoCompraTxt.isBlank()) {
			idTransacaoCompra = Integer.parseInt(idTransacaoCompraTxt.trim());
		}

		Integer idLoteBruto = null;
		if (idLoteBrutoTxt != null && !idLoteBrutoTxt.isBlank()) {
			idLoteBruto = Integer.parseInt(idLoteBrutoTxt.trim());
		}

		Date dtPagamentoInicial = null;
		if (dtPagamentoInicialTxt != null && !dtPagamentoInicialTxt.isBlank()) {
			dtPagamentoInicial = Date.valueOf(LocalDate.parse(dtPagamentoInicialTxt.trim()));
		}

		Date dtPagamentoFinal = null;
		if (dtPagamentoFinalTxt != null && !dtPagamentoFinalTxt.isBlank()) {
			dtPagamentoFinal = Date.valueOf(LocalDate.parse(dtPagamentoFinalTxt.trim()));
		}

		BigDecimal valorMin = null;
		if (valorMinTxt != null && !valorMinTxt.isBlank()) {
			valorMin = new BigDecimal(valorMinTxt.trim());
		}

		BigDecimal valorMax = null;
		if (valorMaxTxt != null && !valorMaxTxt.isBlank()) {
			valorMax = new BigDecimal(valorMaxTxt.trim());
		}

		StatusPagamentoTransacaoCompra statusPagamento = null;
		if (statusPagamentoTxt != null && !statusPagamentoTxt.isBlank()) {
			statusPagamento = StatusPagamentoTransacaoCompra.valueOf(statusPagamentoTxt.trim());
		}

		return transacaoCompraService.listarTransacoesCompraComParametro(idTransacaoCompra, idLoteBruto, dtPagamentoInicial, dtPagamentoFinal, valorMin, valorMax, statusPagamento);

	}

}
