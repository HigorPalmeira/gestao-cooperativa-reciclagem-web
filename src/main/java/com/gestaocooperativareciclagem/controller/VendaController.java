package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
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
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class VendaController
 */
@WebServlet(
		name="VendaController",
		urlPatterns={ "/VendaController", "/ListarVendas", 
	"/DetalharVenda", "/NovaVenda", "/InserirVenda",
	"/AtualizarVenda", "/DeletarVenda"})
public class VendaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private VendaService vendaService;
	private TipoMaterialService tipoMaterialService;
       
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
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar VendaService e/ou TipoMaterialService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharVenda":
					buscarVenda(request, response);
					break;
					
				case "/NovaVenda":
					pageNovaVenda(request, response);
					break;
					
				default:
					listarVendas(request, response);
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
	
	protected void listarVendas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Venda> listaVendas = vendaService.listarVendas();
		
		request.setAttribute("listaVendas", listaVendas);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/venda/vendas.jsp");
		
		reqDis.forward(request, response);
		
	}
	
}
