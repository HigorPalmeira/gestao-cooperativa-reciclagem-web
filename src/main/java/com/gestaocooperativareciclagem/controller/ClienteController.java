package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.gestaocooperativareciclagem.dao.ClienteDAO;
import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.service.ClienteService;
import com.gestaocooperativareciclagem.service.VendaService;
import com.gestaocooperativareciclagem.utils.Formatador;
import com.gestaocooperativareciclagem.utils.Validador;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class ClienteController
 */
@WebServlet(
		name="ClienteController",
		urlPatterns={ "/ClienteController", "/ListarClientes", 
				"/DetalharCliente", "/InserirCliente",
				"/AtualizarCliente", "/DeletarCliente",
				"/VerificarCliente", "/ListagemClientes"})
public class ClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ClienteService clienteService;
	private VendaService vendaService;
	private Gson gson;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClienteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			clienteService = new ClienteService(new ClienteDAO());
			vendaService = new VendaService(new VendaDAO(), new ItemVendaDAO(), clienteService);
			gson = new GsonBuilder()
					.setDateFormat("YYYY-MM-dd")
					.create();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar ClienteService e/ou VendaService e/ou Gson", e);
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
				case "/DetalharCliente":
					buscarClientePorCnpj(request, response);
					break;
					
				case "/VerificarCliente":
					verificarCliente(request, response);
					break;
					
				case "/ListagemClientes":
					listarClientesJson(request, response);
					break;
					
				default:
					pageListarClientes(request, response);
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
				case "/InserirCliente":
					inserirCliente(request, response);
					break;
					
				case "/AtualizarCliente":
					atualizarCliente(request, response);
					break;
					
				case "/DeletarCliente":
					deletarCliente(request, response);
					break;
					
				default:
					pageListarClientes(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void inserirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {

			String cnpj = request.getParameter("clientCnpj");
			String nomeEmpresa = request.getParameter("clientName");
			String contatoPrincipal = request.getParameter("clientContact");
			String emailContato = request.getParameter("clientEmail");
			
			clienteService.inserirCliente(cnpj, nomeEmpresa, contatoPrincipal, emailContato);
			
			response.sendRedirect(request.getContextPath() + "/ListarClientes");
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar inserir um novo cliente no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
		
	}
	
	protected void atualizarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String cnpjOriginal = request.getParameter("cnpj");
		String cnpj = request.getParameter("cnpjEdit");
		String nomeEmpresa = request.getParameter("companyName");
		String contatoPrincipal = request.getParameter("contact");
		String emailContato = request.getParameter("email");
		
		try {
			
			boolean temErro = false;
			StringBuilder msgErro = new StringBuilder("Erro: ");
			
			if (!Validador.isCnpj(Formatador.clearDoc(cnpj))) {
				msgErro.append("O cliente deve possuir um CNPJ válido.<br>");
				temErro = true;
			}
			
			if (!Validador.isTelefone(Formatador.clearFone(contatoPrincipal))) {
				msgErro.append("O cliente deve possuir um Telefone válido.<br>");
				temErro = true;
			}
			
			if (!Validador.isEmail(emailContato)) {
				msgErro.append("O cliente deve possuir um E-mail válido.<br>");
				temErro = true;
			}
			
			if (temErro) {
				
				request.setAttribute("msgErro", msgErro);
				
				buscarClientePorCnpj(request, response);
				
			} else {
				
				clienteService.atualizarCliente(cnpjOriginal, cnpj, nomeEmpresa, contatoPrincipal, emailContato);
				
				request.getSession().setAttribute("msgSucesso", "Cliente atualizado com sucesso!");
				
				buscarClientePorCnpj(request, response);
				
			}
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar atualizar os dados do cliente!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
	}
	
	protected void deletarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {

			String cnpj = request.getParameter("cnpj");
			
			clienteService.deletarCliente(cnpj);
			
			response.sendRedirect(request.getContextPath() + "/ListarClientes");
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar deletar os dados do cliente no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
		}
		
	}
	
	protected void verificarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cnpj = request.getParameter("clientCnpj");
		String origem = request.getParameter("origem");
		
		try {
			
			Cliente cliente = clienteService.buscarClientePorCnpj(cnpj);
			
			request.getSession().setAttribute("clienteEncontrado", cliente);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Cliente não encontrado!");
			
		}
		
		if (origem == null || origem.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + origem);
		}
		
	}
	
	protected void buscarClientePorCnpj(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String cnpj = request.getParameter("cnpj");
			
			Cliente cliente = clienteService.buscarClientePorCnpj(cnpj);
			
			List<Venda> listaVendas = vendaService.buscarVendaPorCliente(cnpj);
			
			request.setAttribute("cliente", cliente);
			request.setAttribute("listaVendas", listaVendas);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/clientes/detalheCliente.jsp");
			
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar o cliente pelo CNPJ informado!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
	}
	
	protected void pageListarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			List<Cliente> listaClientes = listarClientes(request);
			
			request.setAttribute("listaClientes", listaClientes);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/clientes/clientes.jsp");
			
			reqDis.forward(request, response);
			
		} catch (ServletException | IOException | SQLException e) {
			
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar os clientes cadastrados!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));
			
		}
		
		
	}
	
	protected void listarClientesJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		try {
			
			List<Cliente> listaClientes = listarClientes(request);
			
			String clientesJson = gson.toJson(listaClientes);
			
			PrintWriter out = response.getWriter();
			out.print(clientesJson);
			out.flush();
			
		} catch (Exception e) {
			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			
			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar os clientes. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");
			
			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();
			
		}
		
	}
	
	private List<Cliente> listarClientes(HttpServletRequest request) throws ServletException, IOException, SQLException {
		
		String cnpjCliente = request.getParameter("cnpj");
		String nomeEmpresa = request.getParameter("nome");
		String contatoPrincipal = request.getParameter("contato");
		String emailContato = request.getParameter("email");
		String dtCadastroTxt = request.getParameter("dataCadastro");
		
		Date dtCadastro = null;
		if (dtCadastroTxt != null && !dtCadastroTxt.isBlank()) {
			dtCadastro = Date.valueOf(LocalDate.parse(dtCadastroTxt.trim()));
		}
		
		return clienteService.listarClientesComParametro(cnpjCliente, nomeEmpresa, contatoPrincipal, emailContato, dtCadastro);
		
	}

}
