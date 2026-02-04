package com.gestaocooperativareciclagem.service;

import java.util.List;

import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;

public class FornecedorService {
	
	private FornecedorDAO fornecedorDao;
	
	public FornecedorService(FornecedorDAO fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
	}
	
	public List<Fornecedor> listarFornecedores() {
		
		return fornecedorDao.listarFornecedores();
		
	}

}
