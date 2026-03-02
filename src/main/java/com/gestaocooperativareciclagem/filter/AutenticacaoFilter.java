package com.gestaocooperativareciclagem.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.model.Usuario;

/**
 * Servlet Filter implementation class AutenticacaoFilter
 */
@WebFilter({ "/*" })
public class AutenticacaoFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = -7397960415620094970L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public AutenticacaoFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		List<String> rotasPublicas = Arrays.asList("/Login", "/AlterarSenha", "/Logout", "/RecuperarSenha", 
				"/pages/login/login.jsp", "/pages/login/recuperacaoSenha.jsp", 
				"/pages/login/alterarSenha.jsp");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String uriCompleta = req.getRequestURI();
		String contextPath = req.getContextPath();
		
		String uriAtual = uriCompleta.substring(contextPath.length());
		
		if (rotasPublicas.contains(uriAtual) || uriAtual.startsWith("/assets/_css")) {
			
			chain.doFilter(request, response);
			
		} else {
			
			Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
			
			if (usuario != null) {
				
				chain.doFilter(request, response);				
			
			} else {
				
				res.sendRedirect(contextPath + "/pages/login/login.jsp");
				
			}
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
