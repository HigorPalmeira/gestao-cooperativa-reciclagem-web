package com.gestaocooperativareciclagem.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.model.PrecoMaterial;
import com.gestaocooperativareciclagem.model.TipoMaterial;

public class PrecoMaterialService {
	
	private PrecoMaterialDAO precoMaterialDao;
	private TipoMaterialService tipoMaterialService;
	
	public PrecoMaterialService(PrecoMaterialDAO precoMaterialDao, TipoMaterialService tipoMaterialService) {
		this.precoMaterialDao = precoMaterialDao;
		this.tipoMaterialService = tipoMaterialService;
	}
	
	public List<PrecoMaterial> listarPrecosMaterialComParametro(Integer idPrecoMaterial, BigDecimal precoMin, 
			BigDecimal precoMax, Date dtInicial, Date dtFinal, Integer idTipoMaterial, String nomeTipoMaterial) throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterialComParametro(idPrecoMaterial, precoMin, precoMax, dtInicial, dtFinal, idTipoMaterial, nomeTipoMaterial);
		
	}
	
	public void inserirPrecoMaterial(BigDecimal precoCompra, Date dtVigencia, int idTipoMaterial) throws SQLException {
		
		if (precoCompra.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Preço de Compra inválido! É necessário informar um valor válido: maior que zero.");
		}
		
		if (dtVigencia.before(Date.valueOf(LocalDate.now()))) {
			throw new RuntimeException("Data de Vigência inválida! Não é possível cadastrar um preço de compra com vigência anterior ao dia atual.");
		}
		
		TipoMaterial tipoMaterial = tipoMaterialService.buscarTipoMaterialPorId(idTipoMaterial);
		
		PrecoMaterial precoMaterial = new PrecoMaterial(precoCompra, dtVigencia, tipoMaterial);
		
		precoMaterialDao.inserirPrecoMaterial(precoMaterial);
		
	}
	
	public void atualizarPrecoMaterial(int idPrecoMaterial, BigDecimal precoCompra, Date dtVigencia, int idTipoMaterial) throws SQLException {
		
		if (precoCompra.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("Preço de Compra inválido! É necessário informar um valor válido: maior que zero.");
		}
		
		if (dtVigencia.before(Date.valueOf(LocalDate.now()))) {
			throw new RuntimeException("Data de Vigência inválida! Não é possível atualizar um preço de compra com vigência anterior ao dia atual.");
		}
		
		try {
			
			buscarPrecoMaterialPorId(idPrecoMaterial);
			
		} catch (Exception e) {
			throw new RuntimeException("O ID do preço do material informado não pertence a nenhum registro na base de dados.", e);
		}
		
		TipoMaterial tipoMaterial = tipoMaterialService.buscarTipoMaterialPorId(idTipoMaterial);
		PrecoMaterial precoMaterial = new PrecoMaterial(idTipoMaterial, precoCompra, dtVigencia, tipoMaterial);
		
		precoMaterialDao.atualizarPrecoMaterial(precoMaterial);
		
	}
	
	public void deletarPrecoMaterial(int id) throws SQLException {
		
		precoMaterialDao.deletarPrecoMaterial(id);
		
	}

	public List<PrecoMaterial> listarPrecosMaterial() throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterial();
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorPrecoCompra(BigDecimal precoCompra) throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterialPorPrecoCompra(precoCompra);
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorIntervaloPrecoCompra(BigDecimal precoCompraInicial, BigDecimal precoCompraFinal) throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterialPorIntervaloPrecoCompra(precoCompraInicial, precoCompraFinal);
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorDataVigencia(Date dtVigencia) throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterialPorDataVigencia(dtVigencia);
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorIntervaloDataVigencia(Date dtVigenciaInicial, Date dtVigenciaFinal) throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterialPorIntervaloDataVigencia(dtVigenciaInicial, dtVigenciaFinal);
		
	}
	
	public List<PrecoMaterial> listarPrecosMaterialPorTipoMaterial(int idTipoMaterial) throws SQLException {
		
		return precoMaterialDao.listarPrecosMaterialPorTipoMaterial(idTipoMaterial);
		
	}
	
	public PrecoMaterial buscarPrecoMaterialPorId(int id) throws SQLException {
		
		PrecoMaterial precoMaterial = new PrecoMaterial();
		precoMaterial.setId(id);
		
		precoMaterialDao.buscarPrecoMaterialPorId(precoMaterial);
		
		return precoMaterial;
		
	}
	
	public PrecoMaterial buscarPrecoMaterialVigentePorTipoMaterial(TipoMaterial tipoMaterial) throws SQLException {
		
		PrecoMaterial precoMaterial = new PrecoMaterial();
		precoMaterial.setTipoMaterial(tipoMaterial);
		
		precoMaterialDao.buscarPrecoMaterialVigentePorTipoMaterial(precoMaterial);
		
		return precoMaterial;
		
	}
	
}
