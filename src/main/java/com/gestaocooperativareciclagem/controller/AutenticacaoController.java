package com.gestaocooperativareciclagem.controller;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.UsuarioDAO;
import com.gestaocooperativareciclagem.model.Usuario;
import com.gestaocooperativareciclagem.service.AutenticacaoService;
import com.gestaocooperativareciclagem.utils.Criptografia;

/**
 * Servlet implementation class AutenticacaoController
 */
@WebServlet(
		name="AutenticacaoController",
		urlPatterns={ "/AutenticacaoController", "/Login", 
				"/Logout", "/RecuperarSenha" })
public class AutenticacaoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private AutenticacaoService autenticacaoService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutenticacaoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		
		try {
			
			autenticacaoService = new AutenticacaoService(new UsuarioDAO());
			
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar UsuarioService", e);
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Não há rotas GET...");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/Login":
					login(request, response);
					break;
					
				case "/Logout":
					logout(request, response);
					break;
					
				case "/RecuperarSenha":
					recuperarSenha(request, response);
					break;
					
				default:
					System.out.println("Caminho '" + path +"' não encontrado.");
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String email = request.getParameter("email");
			String senhaBruta = request.getParameter("password");
			
			
			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			usuario.setSenha(Criptografia.criptografar(senhaBruta));
			
			if (autenticacaoService.login(usuario)) {
				request.getSession().setAttribute("usuarioLogado", usuario);
				request.getSession().setAttribute("msgSucesso", "Login realizado com sucesso!");
				
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			
			} else {

				request.getSession().setAttribute("msgErro", "Senha ou E-mail incorretos!");
				request.getSession().setAttribute("email", email);
			
				response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
				
			}
			
		} catch (Exception e) {
			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar realizar o login.<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
		}

		
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			request.getSession().removeAttribute("usuarioLogado");
			
			response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void recuperarSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			
			String email = request.getParameter("email");
			
			Usuario usuario = new Usuario();
			usuario.setEmail(email);
			
			if (autenticacaoService.temEmailCadastrado(usuario)) {
				
				System.out.println("Enviando e-mail...");
				
				request.getSession().setAttribute("msgSucesso", "");
			
			} else {
			
				request.getSession().setAttribute("msgErro", "O e-mail '" + email + "' não está cadastrado no sistema.<br>Informe o e-mail do usuário para recuperação de senha.");
				response.sendRedirect( request.getHeader("referer") );
				
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

}
