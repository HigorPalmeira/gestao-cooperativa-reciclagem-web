package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
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

import com.gestaocooperativareciclagem.dao.ClienteDAO;
import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.ItemVenda;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.service.ClienteService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.VendaService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class VendaController
 */
@WebServlet(
		name="VendaController",
		urlPatterns={ "/VendaController", "/ListarVendas", 
	"/DetalharVenda", "/NovaVenda", "/InserirVenda",
	"/AtualizarVenda", "/DeletarVenda",
	"/ListagemVendas"})
public class VendaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private VendaService vendaService;
	private TipoMaterialService tipoMaterialService;
	private Gson gson;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VendaController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			vendaService = new VendaService(
					new VendaDAO(), 
					new ItemVendaDAO(), 
					new ClienteService(new ClienteDAO()));
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
			gson = new GsonBuilder()
					.setDateFormat("YYYY-MM-dd")
					.create();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar VendaService e/ou TipoMaterialService e/ou Gson", e);
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
				case "/DetalharVenda":
					buscarVenda(request, response);
					break;
					
				case "/NovaVenda":
					pageNovaVenda(request, response);
					break;
					
				case "/ListagemVendas":
					listarVendasJson(request, response);
					break;
					
				default:
					pageListarVendas(request, response);
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
				case "/InserirVenda":
					inserirVenda(request, response);
					break;
					
				case "/AtualizarVenda":
					atualizarVenda(request, response);
					break;
					
				case "/DeletarVenda":
					deletarVenda(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
					
	}

	protected void pageNovaVenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();
		
		request.setAttribute("listaTiposMateriais", listaTiposMateriais);
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/venda/novaVenda.jsp");
		reqDis.forward(request, response);
		
	}
	
	protected void inserirVenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String cnpj = request.getParameter("clientCnpj");
			String itensJson = request.getParameter("itensVendaJson");
			
			Gson gson = new Gson();
			Type typeList = new TypeToken<ArrayList<ItemVenda>>() {}.getType();
			List<ItemVenda> listaItensVenda = gson.fromJson(itensJson, typeList);
			
			vendaService.inserirVenda(Date.valueOf(LocalDate.now()), cnpj, listaItensVenda);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
		response.sendRedirect(request.getContextPath() + "/ListarVendas");
		
	}
	
	protected void atualizarVenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			int idVenda = Integer.parseInt(request.getParameter("id"));
			String cnpj = request.getParameter("clientCnpj");
			String itensJson = request.getParameter("itensVendaJson");
			String itensRemovidosJson = request.getParameter("itensVendaRemovidosJson");
			
			Gson gson = new Gson();
			Type typeList = new TypeToken<ArrayList<ItemVenda>>() {}.getType();
			List<ItemVenda> listaItensVenda = gson.fromJson(itensJson, typeList);
			List<ItemVenda> listaItensVendaRemovidos = gson.fromJson(itensRemovidosJson, typeList);
			
			vendaService.atualizarVenda(idVenda, cnpj, listaItensVenda, listaItensVendaRemovidos);
			
			request.getSession().setAttribute("msgSucesso", "Venda atualizada com sucesso!");
			
		} catch (Exception e) {

			request.setAttribute("msgErro", "Ocorreu um erro ao tentar atualizar a venda. Erro: " + e.getMessage());
	
		}
		
		buscarVenda(request, response);

	}
	
	protected void deletarVenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idVenda = Integer.parseInt(request.getParameter("id"));
			
			vendaService.deletarVenda(idVenda);
			
			response.sendRedirect(request.getContextPath() + "/ListarVendas");
			
		} catch (Exception e) {
			
			request.setAttribute("msgErro", "Ocorreu um erro ao tentar deletar a venda. Erro: " + e.getMessage());
			buscarVenda(request, response);
			
		}
		
	}
	
	protected void buscarVenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int idVenda = Integer.parseInt(request.getParameter("id"));
			
			Venda venda = vendaService.buscarVendaPorId(idVenda);
			
			List<TipoMaterial> listaTiposMateriais = tipoMaterialService.listarTiposMaterial();
			List<ItemVenda> listaItensVenda = vendaService.listarItensVendaPorVenda(venda);
			
			request.setAttribute("venda", venda);
			request.setAttribute("listaTiposMateriais", listaTiposMateriais);
			request.setAttribute("listaItensVenda", listaItensVenda);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/venda/detalheVenda.jsp");
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void pageListarVendas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			List<Venda> listaVendas = listarVendas(request);
			
			request.setAttribute("listaVendas", listaVendas);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/venda/vendas.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar as vendas registradas!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
		
	}
	
	protected void listarVendasJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		try {
			
			List<Venda> listaVendas = listarVendas(request);
			
			String vendasJson = gson.toJson(listaVendas);
			
			PrintWriter out = response.getWriter();
			out.print(vendasJson);
			out.flush();
			
		} catch (Exception e) {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar as vendas. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");
			
			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();
			
		}
		
	}
	
	private List<Venda> listarVendas(HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		String idVendaTxt = request.getParameter("id");
		String dtVendaTxt = request.getParameter("dataVenda");
		String valorTotalTxt = request.getParameter("valor");
		String cnpjCliente = request.getParameter("cnpj");
		
		Integer idVenda = null;
		if (idVendaTxt != null && !idVendaTxt.isBlank()) {
			idVenda = Integer.parseInt(idVendaTxt.trim());
		}
		
		Date dtVenda = null;
		if (dtVendaTxt != null && !dtVendaTxt.isBlank()) {
			dtVenda = Date.valueOf(LocalDate.parse(dtVendaTxt.trim()));
		}
		
		BigDecimal valorTotal = null;
		if (valorTotalTxt != null && !valorTotalTxt.isBlank()) {
			valorTotal = new BigDecimal(valorTotalTxt.trim());
		}
		
		return vendaService.listarVendasComParametro(idVenda, dtVenda, valorTotal, cnpjCliente);
		
	}
	
}
