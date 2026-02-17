package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
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

/**
 * Servlet implementation class ClienteController
 */
@WebServlet({ "/ClienteController", "/ListarClientes", 
	"/DetalharCliente", "/InserirCliente",
	"/AtualizarCliente", "/DeletarCliente"})
public class ClienteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ClienteService clienteService;
	private VendaService vendaService;
	
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
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar ClienteService e/ou VendaService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/DetalharCliente":
					buscarClientePorCnpj(request, response);
					break;
					
				default:
					listarClientes(request, response);
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
					listarClientes(request, response);
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void inserirCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String cnpj = request.getParameter("clientCnpj");
		String nomeEmpresa = request.getParameter("clientName");
		String contatoPrincipal = request.getParameter("clientContact");
		String emailContato = request.getParameter("clientEmail");
		
		clienteService.inserirCliente(cnpj, nomeEmpresa, contatoPrincipal, emailContato);
		
		response.sendRedirect(request.getContextPath() + "/ListarClientes");
		
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
			e.printStackTrace();
		}
		
	}
	
	protected void deletarCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cnpj = request.getParameter("cnpj");
		
		clienteService.deletarCliente(cnpj);
		
		response.sendRedirect(request.getContextPath() + "/ListarClientes");
		
	}
	
	protected void buscarClientePorCnpj(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		String cnpj = request.getParameter("cnpj");
		
		Cliente cliente = clienteService.buscarClientePorCnpj(cnpj);
		
		List<Venda> listaVendas = vendaService.buscarVendaPorCliente(cnpj);
		
		request.setAttribute("cliente", cliente);
		request.setAttribute("listaVendas", listaVendas);
		
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/clientes/detalheCliente.jsp");
		
		reqDis.forward(request, response);
		
	}
	
	protected void listarClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Cliente> listaClientes = clienteService.listarClientes();
		
		request.setAttribute("listaClientes", listaClientes);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/clientes/clientes.jsp");
		
		reqDis.forward(request, response);
		
	}

}
