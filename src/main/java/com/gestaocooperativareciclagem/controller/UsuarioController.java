package com.gestaocooperativareciclagem.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.UsuarioDAO;
import com.gestaocooperativareciclagem.model.Usuario;
import com.gestaocooperativareciclagem.service.UsuarioService;
import com.gestaocooperativareciclagem.utils.Criptografia;
import com.gestaocooperativareciclagem.utils.Validador;
import com.google.gson.Gson;

/**
 * Servlet implementation class UsuarioController
 */
@WebServlet(
		name="UsuarioController",
		urlPatterns = {"/UsuarioController", "/ListarUsuarios",
		"/DetalharUsuario", "/InserirUsuario",
		"/AtualizarUsuario", "/DeletarUsuario",
		"/ListagemUsuarios"})
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UsuarioService usuarioService;
	private Gson gson;

	@Override
	public void init() throws ServletException {
		try {
			usuarioService = new UsuarioService(new UsuarioDAO());
			gson = new Gson();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar UsuarioService e/ou Gson", e);
		}
	}

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String path = request.getServletPath();

		try {

			switch(path) {
				case "/DetalharUsuario":
					buscarUsuarioPorId(request, response);
					break;

				case "/ListagemUsuarios":
					listarUsuariosJson(request, response);
					break;

				default:
					pageListarUsuarios(request, response);
					break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String path = request.getServletPath();

		try {

			switch(path) {
				case "/InserirUsuario":
					inserirUsuario(request, response);
					break;

				case "/AtualizarUsuario":
					atualizarUsuario(request, response);
					break;

				case "/DeletarUsuario":
					deletarUsuario(request, response);
					break;

				default:
					pageListarUsuarios(request, response);
					break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void inserirUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		try {

			String nome = request.getParameter("userName");
			String email = request.getParameter("userEmail");
			String senha = null;
			String confirmSenha = null;
			String papel = request.getParameter("userRole");

			boolean senhaCorresponde = false;

			senha = Criptografia.criptografar(request.getParameter("userPass"));
			confirmSenha = Criptografia.criptografar(request.getParameter("userPassConfirm"));

			senhaCorresponde = senha.equals(confirmSenha);

			if (senhaCorresponde) {
				usuarioService.inserirUsuario(nome, email, senha, papel);
			}

			response.sendRedirect("ListarUsuarios");

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar inserir os dados de um novo usuário no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		try {

			int id = Integer.parseInt( request.getParameter("userId") );
			String nome = request.getParameter("userName").trim();
			String email = request.getParameter("userEmail").trim();
			String papel = request.getParameter("userRole").trim();

			String senhaBruta = request.getParameter("newPass").trim();
			String confirmSenhaBruta = request.getParameter("confirmPass").trim();
			String senhaCriptografada = null;

			StringBuilder msgErro = new StringBuilder("Erro: ");
			boolean temErro = false;

			if (senhaBruta != null && !senhaBruta.isEmpty()) {

				if (senhaBruta.equals(confirmSenhaBruta)) {
					senhaCriptografada = Criptografia.criptografar(senhaBruta);
				} else {
					msgErro.append("As senhas não coincidem.<br>");
					temErro = true;
				}

			}

			if (!Validador.isEmail(email)) {
				msgErro.append("O usuário deve possuir um e-mail válido.<br>");
				temErro = true;
			}

			if (papel == null || papel.isBlank()) {
				msgErro.append("O usuário deve possuir um papel válido.<br>");
				temErro = true;
			}

			if (temErro) {

				request.setAttribute("msgErro", msgErro.toString());

				buscarUsuarioPorId(request, response);

			} else {

				usuarioService.atualizarUsuario(id, nome, email, senhaCriptografada, papel);

				request.getSession().setAttribute("msgSucesso", "Usuário atualizado com sucesso!");

				response.sendRedirect(request.getContextPath() + "/DetalharUsuario?userId=" + id);

			}


		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar atualizar os dados de um usuário no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void deletarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			int id = Integer.parseInt(request.getParameter("userId"));
			usuarioService.deletarUsuario(id);

			response.sendRedirect("ListarUsuarios");

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar deletar os dados de um usuário no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void pageListarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			List<Usuario> usuarios = listarUsuarios(request);

			request.setAttribute("listaUsuarios", usuarios);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");

			reqDis.forward(request, response);


		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar listar os usuários registrados!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}


	}

	protected void listarUsuariosJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");

		try {

			List<Usuario> listaUsuarios = listarUsuarios(request);

			String usuariosJson = gson.toJson(listaUsuarios);

			PrintWriter out = response.getWriter();
			out.print(usuariosJson);
			out.flush();

		} catch (Exception e) {

			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			StringBuilder builder = new StringBuilder();
			builder.append("{\"error\":");
			builder.append(" \"Ocorreu um erro ao tentar listar os usuários. Erro: ");
			builder.append(e.getMessage());
			builder.append("\", \"code\": 400}");

			PrintWriter out = response.getWriter();
			out.print(builder.toString());
			out.flush();

		}

	}

	private List<Usuario> listarUsuarios(HttpServletRequest request) throws ServletException, IOException, SQLException {

		String idUsuarioTxt = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String papel = request.getParameter("papel");

		Integer idUsuario = null;
		if (idUsuarioTxt != null && !idUsuarioTxt.isBlank()) {
			idUsuario = Integer.parseInt(idUsuarioTxt.trim());
		}

		return usuarioService.listarUsuariosComParametro(idUsuario, nome, email, papel);

	}

	protected void listarUsuariosPorPapel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			String papel = request.getParameter("papel");

			List<Usuario> usuarios = usuarioService.listarUsuariosPorPapel(papel);

			request.setAttribute("listaUsuarios", usuarios);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar os dados dos usuários no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void listarUsuariosPorNome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			String nome = request.getParameter("nome");

			List<Usuario> usuarios = usuarioService.listarUsuariosPorNome(nome);

			request.setAttribute("listaUsuarios", usuarios);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/usuarios.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar os dados dos usuários no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void buscarUsuarioPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			int id = Integer.parseInt(request.getParameter("userId"));

			Usuario usuario = usuarioService.buscarUsuarioPorId(id);

			request.setAttribute("usuario", usuario);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/detalheUsuario.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar os dados do usuário no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

	protected void buscarUsuarioPorEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			String email = request.getParameter("email");

			Usuario usuario = usuarioService.buscarUsuarioPorEmail(email);

			request.setAttribute("usuario", usuario);
			RequestDispatcher reqDis = request.getRequestDispatcher("pages/usuario/detalheUsuario.jsp");

			reqDis.forward(request, response);

		} catch (Exception e) {

			request.getSession().setAttribute("msgErro", "Ocorreu um erro ao tentar buscar os dados do usuário no sistema!<br>Erro: " + e.getMessage());
			response.sendRedirect(request.getHeader("referer"));

		}

	}

}
