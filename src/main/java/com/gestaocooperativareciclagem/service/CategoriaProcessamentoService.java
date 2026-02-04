package com.gestaocooperativareciclagem.service;

import java.util.List;

import com.gestaocooperativareciclagem.dao.CategoriaProcessamentoDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;

public class CategoriaProcessamentoService {
	
	private CategoriaProcessamentoDAO categoriaProcessamentoDao;

	public CategoriaProcessamentoService(CategoriaProcessamentoDAO categoriaProcessamentoDao) {
		this.categoriaProcessamentoDao = categoriaProcessamentoDao;
	}
	
	public void inserirCategoriaProcessamento(String nome, String descricao) {
		
		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("É necessário informar um nome válido para uma nova categoria de processamento!");
		}
		
		if (descricao == null || descricao.isBlank()) {
			throw new RuntimeException("É necessário informar uma descrição válida para uma nova categoria de processamento!");
		}
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(nome, descricao);
		
		categoriaProcessamentoDao.inserirCategoriaProcessamento(categoriaProcessamento);
		
	}
	
	public void atualizarCategoriaProcessamento(int id, String nome, String descricao) {
		
		CategoriaProcessamento categoriaProcessamentoOriginal = buscarCategoriaProcessamentoPorId(id);
		
		CategoriaProcessamento categoriaProcessamentoAtualizada = new CategoriaProcessamento(id, nome, descricao);
		
		
		if (nome == null || nome.isBlank()) {
			categoriaProcessamentoAtualizada.setNome(categoriaProcessamentoOriginal.getNome());
		}
		
		if (descricao == null || descricao.isBlank()) {
			categoriaProcessamentoAtualizada.setDescricao(categoriaProcessamentoOriginal.getDescricao());
		}
		
		categoriaProcessamentoDao.atualizarCategoriaProcessamento(categoriaProcessamentoAtualizada);
		
	}
	
	public void deletarCategoriaProcessamento(int id) {
		
		categoriaProcessamentoDao.deletarCategoriaProcessamento(id);
		
	}
	
	public List<CategoriaProcessamento> listarCategoriasProcessamento() {
		
		return categoriaProcessamentoDao.listarCategoriasProcessamento();
		
	}
	
	public List<CategoriaProcessamento> listarCategoriasProcessamentoPorDescricao(String descricao) {
		
		if (descricao == null || descricao.isBlank()) {
			throw new RuntimeException("É necessário informar uma descrição válida para realizar uma busca!");
		}
		
		return categoriaProcessamentoDao.listarCategoriasProcessamentoPorDescricao(descricao);
		
	}
	
	public CategoriaProcessamento buscarCategoriaProcessamentoPorNome(String nome) {
		
		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("É necessário informar um nome válido para realizar uma busca!");
		}
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setNome(nome);
		
		categoriaProcessamentoDao.buscarCategoriaProcessamentoPorNome(categoriaProcessamento);
		
		return categoriaProcessamento;
		
	}
	
	public CategoriaProcessamento buscarCategoriaProcessamentoPorId(int id) {
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(id);
		
		categoriaProcessamentoDao.buscarCategoriaProcessamentoPorId(categoriaProcessamento);
		
		return categoriaProcessamento;
		
	}

}
