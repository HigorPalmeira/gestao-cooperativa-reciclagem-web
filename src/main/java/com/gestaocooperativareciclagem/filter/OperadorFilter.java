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
 * Servlet Filter implementation class OperadorFilter
 */
@WebFilter(filterName="OperadorFilter")
public class OperadorFilter extends HttpFilter implements Filter {

    /**
	 *
	 */
	private static final long serialVersionUID = -218060726110289429L;

	/**
     * @see HttpFilter#HttpFilter()
     */
    public OperadorFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		List<String> papeisPermitidos = Arrays.asList("operador", "gerente", "administrador");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		Usuario usuario = (Usuario) req.getSession().getAttribute("usuarioLogado");

		if (papeisPermitidos.contains(usuario.getPapel().toLowerCase())) {
			chain.doFilter(request, response);
		} else {
			req.getSession().setAttribute("msgErro", "Acesso restrito a Operadores, Gerentes e Administradores");
			res.sendRedirect(req.getContextPath() + "/index.jsp");
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
