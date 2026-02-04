package com.gestaocooperativareciclagem.service;

import java.util.List;

import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;

public class FornecedorService {
	
	private FornecedorDAO fornecedorDao;
	
	public FornecedorService(FornecedorDAO fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
	}
	
	/*
	 * protected void listarFornecedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Fornecedor> fornecedores = fornecedorDao.listarFornecedores();
		
		request.setAttribute("listaFornecedores", fornecedores);
		RequestDispatcher reqDis = request.getRequestDispatcher("pages/fornecedor/fornecedores.jsp");
		
		reqDis.forward(request, response);
		
	}
	 * */
	
	public List<Fornecedor> listarFornecedores() {
		
		return fornecedorDao.listarFornecedores();
		
	}

}
