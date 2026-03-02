package com.gestaocooperativareciclagem.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
import com.gestaocooperativareciclagem.utils.Mail;

/**
 * Servlet implementation class AutenticacaoController
 */
@WebServlet(
		name="AutenticacaoController",
		urlPatterns={ "/AutenticacaoController", "/Login", 
				"/Logout", "/RecuperarSenha",
				"/AlterarSenha"})
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
		
		String path = request.getServletPath();
		
		try {
			
			switch(path) {
				case "/AlterarSenha":
					pageAlterarSenha(request, response);
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
				case "/Login":
					login(request, response);
					break;
					
				case "/Logout":
					logout(request, response);
					break;
					
				case "/RecuperarSenha":
					recuperarSenha(request, response);
					break;
					
				case "/AlterarSenha":
					alterarSenha(request, response);
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

				String link = "http://localhost:8080" + request.getContextPath() + "/AlterarSenha?id=" + usuario.getId();
				
				String templateHtml = """
						<!DOCTYPE html>
						<html lang="pt-BR">
						<head>
						    <meta charset="UTF-8">
						</head>
						<body style="background-color: #f4f6f9; font-family: Arial, sans-serif; color: #333333; margin: 0; padding: 20px;">
						    
						    <table width="100%%" border="0" cellspacing="0" cellpadding="0">
						        <tr>
						            <td align="center">
						                <div style="max-width: 600px; width: 100%%; background-color: #ffffff; border: 1px solid #dee2e6; border-radius: 8px; overflow: hidden; text-align: left;">
						                    
						                    <div style="background-color: #0056b3; padding: 20px; text-align: center;">
						                        <h1 style="color: #ffffff; margin: 0; font-size: 24px; letter-spacing: 1px;">EcoSystem</h1>
						                    </div>
						                    
						                    <div style="padding: 30px;">
						                        <h2 style="color: #0056b3; font-size: 20px; margin-top: 0;">Recuperação de Senha</h2>
						                        
						                        <p style="line-height: 1.6;">Olá,</p>
						                        
						                        <p style="line-height: 1.6;">
						                            Este e-mail contém as instruções para a recuperação de senha do usuário pertencente à conta <strong>%s</strong>.
						                        </p>
						                        
						                        <p style="line-height: 1.6;">
						                            Para redefinir sua senha com segurança, por favor, clique no botão abaixo:
						                        </p>
						
						                        <div style="text-align: center; margin: 35px 0;">
						                            <a href="%s" style="background-color: #0056b3; color: #ffffff; padding: 14px 28px; text-decoration: none; border-radius: 4px; font-weight: bold; display: inline-block; font-size: 16px;">
						                                Acessar Link de Recuperação
						                            </a>
						                        </div>
						
						                        <p style="line-height: 1.6;">
						                            Altere a senha na página de destino e aguarde a confirmação do sistema.
						                        </p>
						
						                        <hr style="border: none; border-top: 1px solid #dee2e6; margin: 25px 0;">
						                        
						                        <p style="line-height: 1.5; color: #666666; font-size: 13px;">
						                            Se você não solicitou a redefinição de senha, por favor, ignore este e-mail. O link expirará em breve por questões de segurança.
						                        </p>
						                    </div>
						                    
						                    <div style="background-color: #e9ecef; padding: 15px; text-align: center; border-top: 1px solid #dee2e6;">
						                        <p style="margin: 0; color: #666666; font-size: 12px;">
						                            <strong>EcoSystem</strong><br>
						                            Este é um e-mail automático, por favor não responda.
						                        </p>
						                    </div>
						
						                </div>
						            </td>
						        </tr>
						    </table>
						
						</body>
						</html>
						""";
				
				String corpoEmail = String.format(templateHtml, email, link);
				
				Mail mail = new Mail();
				mail.sendMail("Recuperação de Senha - Ecosystem", corpoEmail, email);
				
				request.getSession().setAttribute("msgSucesso", "Um e-mail foi enviado para " + email + ", leia as instruções e recupere a senha.");
				response.sendRedirect( request.getHeader("referer") );
				
			} else {
			
				request.getSession().setAttribute("msgErro", "O e-mail '" + email + "' não está cadastrado no sistema.<br>Informe o e-mail do usuário para recuperação de senha.");
				response.sendRedirect( request.getHeader("referer") );
				
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}

	protected void alterarSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int id = Integer.parseInt(request.getParameter("userId"));
			String novaSenhaBruta = request.getParameter("newPassword");
			String novaSenhaConfirm = request.getParameter("confirmPassword");
			
			if (novaSenhaBruta.equals(novaSenhaConfirm)) {
				
				autenticacaoService.alterarSenha(id, Criptografia.criptografar(novaSenhaBruta));
				
				request.getSession().setAttribute("msgSucesso", "Senha alterada com sucesso! Efetue o login.");
				response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
				
			} else {
				
				request.getSession().setAttribute("msgErro", "As senhas não correspondem!");
				response.sendRedirect( request.getHeader("referer") );
				
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
	protected void pageAlterarSenha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			int userId = Integer.parseInt(request.getParameter("id"));
			
			request.setAttribute("userId", userId);
			
			RequestDispatcher reqDis = request.getRequestDispatcher("/pages/login/alterarSenha.jsp");
			reqDis.forward(request, response);
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
	}
	
}
