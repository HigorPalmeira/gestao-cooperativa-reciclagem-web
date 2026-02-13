package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Formatador;
import com.gestaocooperativareciclagem.utils.Validador;

public class FornecedorService {
	
	private FornecedorDAO fornecedorDao;
	
	public FornecedorService(FornecedorDAO fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
	}
	
	public void inserirFornecedor(String documento, String nome, TipoFornecedor tipo) {
		
		Fornecedor fornecedor = new Fornecedor(Formatador.clearDoc(documento), nome, tipo);
		
		fornecedorDao.inserirFornecedor(fornecedor);
		
	}
	
	public void inserirFornecedor(Fornecedor fornecedor) {
		
		if (fornecedor == null) {
			throw new RuntimeException("Fornecedor inválido! Não é possível cadastrar o fornecedor.");
		}
		
		fornecedorDao.inserirFornecedor(fornecedor);
		
	}
	
	public void atualizarFornecedor(String documentoOriginal, String documentoNovo, String nome, TipoFornecedor tipo) {
		
		Fornecedor fornecedor = new Fornecedor(documentoNovo, nome, tipo);
		
		fornecedorDao.atualizarFornecedor(documentoOriginal, fornecedor);
		
	}
	
	public void deletarFornecedor(String documento) {
		
		if (!Validador.isCnpj(documento) && !Validador.isCpf(documento)) {
			throw new RuntimeException("Documento inválido!");
		}
		
		fornecedorDao.deletarFornecedor(documento);
		
	}
	
	public List<Fornecedor> listarFornecedores() {
		
		return fornecedorDao.listarFornecedores();
		
	}
	
	public List<Fornecedor> listarFornecedoresPorNome(String nome) {
		
		if (nome == null) {
			throw new RuntimeException("Nome inválido! O nome não pode estar vazio.");
		}
		
		if (nome.isBlank()) {
			throw new RuntimeException("Nome inválido! O nome não pode estar em branco.");
		}
		
		return fornecedorDao.listarFornecedoresPorNome(nome);
		
	}
	
	public List<Fornecedor> listarFornecedoresPorTipo(TipoFornecedor tipo) {
		
		return fornecedorDao.listarFornecedoresPorTipo(tipo);
		
	}
	
	public List<Fornecedor> listarFornecedoresPorDataCadastro(Date dataInicial, Date dataFinal) {
		
		boolean afterInicial = dataInicial.after(Date.valueOf(LocalDate.now()));
		boolean afterFinal = dataFinal.after(Date.valueOf(LocalDate.now()));
		
		if (afterInicial) {
			throw new RuntimeException("O intervalo de busca não pode começar com uma data posterior à atual.");
		}
		
		if ( afterInicial && afterFinal ) {
			throw new RuntimeException("Não é possível realizar a busca com datas posteriores à atual");
		}
		
		return fornecedorDao.listarFornecedoresPorDataCadastro(dataInicial, dataFinal);
		
	}

	public Fornecedor buscarFornecedorPorDocumento(String documento) {
		
		String fDocumento = Formatador.clearDoc(documento);
		
		if (!Validador.isCpf(fDocumento) && !Validador.isCnpj(fDocumento)) {
			throw new RuntimeException("Documento inválido!");
		}
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setDocumento(fDocumento);
		
		fornecedorDao.buscarFornecedorPorDocumento(fornecedor);
		
		return fornecedor;
		
	}
	

}
