package com.gestaocooperativareciclagem.service;

import java.sql.SQLException;
import java.util.List;

import com.gestaocooperativareciclagem.dao.CategoriaProcessamentoDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;

public class CategoriaProcessamentoService {

	private CategoriaProcessamentoDAO categoriaProcessamentoDao;

	public CategoriaProcessamentoService(CategoriaProcessamentoDAO categoriaProcessamentoDao) {
		this.categoriaProcessamentoDao = categoriaProcessamentoDao;
	}

	public List<CategoriaProcessamento> listarCategoriasProcessamentoPorParametros(Integer idCategoria, String nomeCategoria, String descricaoCategoria) throws SQLException {

		return categoriaProcessamentoDao.listarCategoriasProcessamentoPorParametros(idCategoria, nomeCategoria, descricaoCategoria);

	}

	public void inserirCategoriaProcessamento(String nome, String descricao) throws SQLException {

		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("É necessário informar um nome válido para uma nova categoria de processamento!");
		}

		if (descricao == null || descricao.isBlank()) {
			throw new RuntimeException("É necessário informar uma descrição válida para uma nova categoria de processamento!");
		}

		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento(nome, descricao);

		categoriaProcessamentoDao.inserirCategoriaProcessamento(categoriaProcessamento);

	}

	public void atualizarCategoriaProcessamento(int id, String nome, String descricao) throws SQLException {

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

	public void deletarCategoriaProcessamento(int id) throws SQLException {

		categoriaProcessamentoDao.deletarCategoriaProcessamento(id);

	}

	public List<CategoriaProcessamento> listarCategoriasProcessamento() throws SQLException {

		return categoriaProcessamentoDao.listarCategoriasProcessamento();

	}

	public CategoriaProcessamento buscarCategoriaProcessamentoPorNome(String nome) throws SQLException {

		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("É necessário informar um nome válido para realizar uma busca!");
		}

		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setNome(nome);

		categoriaProcessamentoDao.buscarCategoriaProcessamentoPorNome(categoriaProcessamento);

		return categoriaProcessamento;

	}

	public CategoriaProcessamento buscarCategoriaProcessamentoPorId(int id) throws SQLException {

		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(id);

		categoriaProcessamentoDao.buscarCategoriaProcessamentoPorId(categoriaProcessamento);

		return categoriaProcessamento;

	}

}
