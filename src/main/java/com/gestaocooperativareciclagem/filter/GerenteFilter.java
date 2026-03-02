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
 * Servlet Filter implementation class GerenteFilter
 */
@WebFilter(filterName="GerenteFilter")
public class GerenteFilter extends HttpFilter implements Filter {
       
    /**
	 * 
	 */
	private static final long serialVersionUID = 864926140217797959L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public GerenteFilter() {
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
		
		List<String> papeisPermitidos = Arrays.asList("gerente", "administrador");
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");
		
		if (papeisPermitidos.contains(usuario.getPapel().toLowerCase())) {
			chain.doFilter(request, response);			
		} else {
			req.getSession().setAttribute("msgErro", "Acesso restrito a Gerentes e Administradores");
			res.sendRedirect(req.getHeader("referer")); // req.getContextPath() + "/index.jsp"
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
