package com.gestaocooperativareciclagem.service;

import java.sql.SQLException;
import java.util.List;

import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.model.TipoMaterial;

public class TipoMaterialService {

	private TipoMaterialDAO tipoMaterialDao;

	public TipoMaterialService(TipoMaterialDAO tipoMaterialDao) {
		this.tipoMaterialDao = tipoMaterialDao;
	}

	public List<TipoMaterial> listarTiposMaterialComParametros(Integer idTipoMaterial, String nomeTipoMaterial, String descricaoTipoMaterial) throws SQLException {

		return tipoMaterialDao.listarTiposMaterialComParametros(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

	}

	public void inserirTipoMaterial(String nome, String descricao) throws SQLException {

		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("Nome inválido! É necessário informar um nome para criar um novo tipo de material.");
		}

		if (descricao == null || descricao.isBlank()) {
			throw new RuntimeException("Descrição inválida! É necessário informar uma descrição para criar um novo tipo de material.");
		}

		TipoMaterial tipoMaterial = new TipoMaterial(nome, descricao);

		tipoMaterialDao.inserirTipoMaterial(tipoMaterial);

	}

	public void atualizarTipoMaterial(int id, String nome, String descricao) throws SQLException {

		TipoMaterial tipoMaterialOriginal = buscarTipoMaterialPorId(id);

		TipoMaterial tipoMaterialAtualizado = new TipoMaterial(id, nome, descricao);

		if (nome == null || nome.isBlank()) {
			tipoMaterialAtualizado.setNome(tipoMaterialOriginal.getNome());
		}

		if (descricao == null || descricao.isBlank()) {
			tipoMaterialAtualizado.setDescricao(descricao);
		}

		tipoMaterialDao.atualizarTipoMaterial(tipoMaterialAtualizado);

	}

	public void deletarTipoMaterial(int id) throws SQLException {

		tipoMaterialDao.deletarTipoMaterial(id);

	}

	public List<TipoMaterial> listarTiposMaterial() throws SQLException {

		return tipoMaterialDao.listarTiposMaterial();

	}

	public List<TipoMaterial> listarTiposMaterialPorDescricao(String descricao) throws SQLException {

		if (descricao == null || descricao.isBlank()) {
			throw new RuntimeException("Descrição inválida! É necessário informar uma 'Descrição' para realizar a busca.");
		}

		return tipoMaterialDao.listarTiposMaterialPorDescricao(descricao);

	}

	public TipoMaterial buscarTipoMaterialPorId(int id) throws SQLException {

		TipoMaterial tipoMaterial = new TipoMaterial();
		tipoMaterial.setId(id);

		tipoMaterialDao.buscarTipoMaterialPorId(tipoMaterial);

		return tipoMaterial;

	}

	public TipoMaterial buscarTipoMaterialPorNome(String nome) throws SQLException {

		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("Nome inválido! É necessário informar um 'Nome' para realizar a busca.");
		}

		TipoMaterial tipoMaterial = new TipoMaterial();
		tipoMaterial.setNome(nome);

		tipoMaterialDao.buscarTipoMaterialPorNome(tipoMaterial);

		return tipoMaterial;

	}

}
