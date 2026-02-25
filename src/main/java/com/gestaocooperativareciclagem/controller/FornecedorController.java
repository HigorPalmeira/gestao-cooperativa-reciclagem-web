package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.service.FornecedorService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.gestaocooperativareciclagem.utils.Formatador;

/**
 * Servlet implementation class FornecedorController
 */
@WebServlet(
		name="FornecedorController",
		urlPatterns={"/FornecedorController", "/ListarFornecedores", 
		"/DetalharFornecedor", "/InserirFornecedor", 
		"/AtualizarFornecedor", "/DeletarFornecedor",
		"/VerificarFornecedor"})
public class FornecedorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FornecedorService fornecedorService;
	private LoteBrutoService loteBrutoService;
	private TransacaoCompraService transacaoCompraService;
	
	public void init() throws ServletException {
		try {
			fornecedorService = new FornecedorService(new FornecedorDAO());
			loteBrutoService = new LoteBrutoService(new LoteBrutoDAO());
			transacaoCompraService = new TransacaoCompraService(new TransacaoCompraDAO());
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar FornecedorService e/ou LoteBrutoService e/ou TransacaoCompraService", e);
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     * 
     * */
    public FornecedorController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
		
			switch(path) {
				case "/DetalharFornecedor":
					buscarFornecedorPorDocumento(request, response);
					break;
					
				case "/VerificarFornecedor":
					verificarFornecedor(request, response);
					break;
					
				default:
					listarFornecedores(request, response);
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
				case "/InserirFornecedor":
					inserirFornecedor(request, response);
					break;
					
				case "/AtualizarFornecedor":
					atualizarFornecedor(request, response);
					break;
					
				case "/DeletarFornecedor":
					deletarFornecedor(request, response);
					break;
					
				
					
				default:
					listarFornecedores(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void listarFornecedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedores();
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void buscarFornecedorPorDocumento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("doc");
		
		Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);
		
		List<LoteBruto> listaLotesBrutos = loteBrutoService.listarLotesBrutosPorFornecedor(fornecedor);
		
		List<TransacaoCompra> listaTransacoesCompra = new ArrayList<>();
		
		for (LoteBruto loteBruto : listaLotesBrutos) {
			listaTransacoesCompra.addAll( transacaoCompraService.listarTransacoesCompraPorLoteBruto(loteBruto) );
		}
		
		request.setAttribute("fornecedor", fornecedor);
		request.setAttribute("listaLotesBrutos", listaLotesBrutos);
		request.setAttribute("listaTransacoesCompra", listaTransacoesCompra);
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/detalheFornecedor.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarFornecedoresPorNome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nome = request.getParameter("nome");
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedoresPorNome(nome);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarFornecedoresPorTipo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TipoFornecedor tipo = TipoFornecedor.fromDescricao(request.getParameter("tipo"));
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedoresPorTipo(tipo);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarFornecedoresPorData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Date dataInicial = Date.valueOf(request.getParameter("dataInicial"));
		Date dataFinal = Date.valueOf(request.getParameter("dataFinal"));
		
		List<Fornecedor> fornecedores = fornecedorService.listarFornecedoresPorDataCadastro(dataInicial, dataFinal);
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void inserirFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String nome = request.getParameter("supplierName");
		String documento = request.getParameter("supplierDoc");
		String tipoTexto = request.getParameter("supplierType");

		try {
			
			TipoFornecedor tipo = TipoFornecedor.fromDescricao(tipoTexto);
			
			fornecedorService.inserirFornecedor(documento, nome, tipo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/ListarFornecedores");
		
	}

	protected void atualizarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String documentoOriginal = request.getParameter("doc");
		String documento = request.getParameter("docEdit");
		String nome = request.getParameter("name");
		String tipoTexto = request.getParameter("type");
		
		try {
			
			TipoFornecedor tipo = TipoFornecedor.fromDescricao(tipoTexto);

			boolean temErro = false;
			String msgErro = "";
			
			if (tipo.equals(TipoFornecedor.COLETOR) && Formatador.clearDoc(documento).length() != 11) {
				temErro = true;
				msgErro = "Erro: Um Fornecedor do tipo 'Coletor' deve possuir CPF, não CNPJ.";
			}
			
			if ( (tipo.equals(TipoFornecedor.EMPRESA) || tipo.equals(TipoFornecedor.MUNICIPIO)) && Formatador.clearDoc(documento).length() != 14 ) {
				temErro = true;
				msgErro = "Erro: Um Fornecedor do tipo 'Empresa' ou 'Município' deve possuir CNPJ, não CPF.";
			}
			
			if (temErro) {
				
				request.setAttribute("msgErro", msgErro);
				
				buscarFornecedorPorDocumento(request, response);
				
			} else {

				fornecedorService.atualizarFornecedor(documentoOriginal, documento, nome, tipo);
				
				request.getSession().setAttribute("msgSucesso", "Fornecedor atualizado com sucesso!");
				
				buscarFornecedorPorDocumento(request, response);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void deletarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("doc");
		
		fornecedorService.deletarFornecedor(documento);
		
		response.sendRedirect(request.getContextPath() + "/ListarFornecedores");
		
	}
	
	protected void verificarFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String documento = request.getParameter("supplierDoc");
		String origem = request.getParameter("origem");
		
		try {
			
			Fornecedor fornecedor = fornecedorService.buscarFornecedorPorDocumento(documento);
			
			request.getSession().setAttribute("fornecedorEncontrado", fornecedor);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Fornecedor não encontrado!");
			
		}
		
		if (origem == null || origem.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + origem);
		}
		
	}
}
