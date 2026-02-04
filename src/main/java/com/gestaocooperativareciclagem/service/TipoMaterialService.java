package com.gestaocooperativareciclagem.service;

import java.util.List;

import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.model.TipoMaterial;

public class TipoMaterialService {
	
	private TipoMaterialDAO tipoMaterialDao;

	public TipoMaterialService(TipoMaterialDAO tipoMaterialDao) {
		this.tipoMaterialDao = tipoMaterialDao;
	}
	
	public void inserirTipoMaterial(String nome, String descricao) {
		
		if (nome == null || nome.isBlank()) {
			throw new RuntimeException("Nome inválido! É necessário informar um nome para criar um novo tipo de material.");
		}
		
		if (descricao == null || descricao.isBlank()) {
			throw new RuntimeException("Descrição inválida! É necessário informar uma descrição para criar um novo tipo de material.");
		}
		
		TipoMaterial tipoMaterial = new TipoMaterial(nome, descricao);
		
		tipoMaterialDao.inserirTipoMaterial(tipoMaterial);

	}
	
	public List<TipoMaterial> listarTiposMaterial() {
		
	}

}
