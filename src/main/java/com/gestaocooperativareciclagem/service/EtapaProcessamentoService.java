package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.EtapaProcessamentoDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.LoteProcessado;

public class EtapaProcessamentoService {
	
	private EtapaProcessamentoDAO etapaProcessamentoDao;

	public EtapaProcessamentoService(EtapaProcessamentoDAO etapaProcessamentoDao) {
		this.etapaProcessamentoDao = etapaProcessamentoDao;
	}
	
	public void inserirEtapaProcessamentoService(int idLoteProcessado, int idCategoriaProcessamento, Date dtProcessamento, String status) {
		
		if (status == null || status.isBlank()) {
			throw new RuntimeException("Status inválido! É necessário informar um status válido para criar uma nova etapa de processamento.");
		}
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(idCategoriaProcessamento);
		
		EtapaProcessamento etapaProcessamento = new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, status);
		
		etapaProcessamentoDao.inserirEtapaProcessamento(etapaProcessamento);
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamento() {
		
		return etapaProcessamentoDao.listarEtapasProcessamento();
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorStatus(String status) {
		
		return etapaProcessamentoDao.listarEtapasProcessamentoPorStatus(status);
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorLoteProcessado(int idLoteProcessado) {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		
		return etapaProcessamentoDao.listarEtapasProcessamentoPorLoteProcessado(loteProcessado);
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorCategoriaProcessamento(int idCategoriaProcessamento) {
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(idCategoriaProcessamento);
		
		return etapaProcessamentoDao.listarEtapasProcessamentoPorCategoriaProcessamento(categoriaProcessamento);
		
	}
	
	public List<EtapaProcessamento> listarEtapasProcessamentoPorDataProcessamento(Date dataInicial, Date dataFinal) {
		
		boolean afterInicial = dataInicial.after(Date.valueOf(LocalDate.now()));
		
		if (afterInicial) {
			throw new RuntimeException("O intervalo de busca não pode começar com uma data posterior à atual.");
		}
		
		if (dataFinal == null) {
			dataFinal = Date.valueOf(LocalDate.now());
		}
		
		return etapaProcessamentoDao.listarEtapasProcessamentoPorDataProcessamento(dataInicial, dataFinal);
		
	}
	
	public EtapaProcessamento buscarEtapaProcessamentoPorLoteProcessadoECategoriaProcessamento(int idLoteProcessado, int idCategoriaProcessamento) {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(idCategoriaProcessamento);
		
		EtapaProcessamento etapaProcessamento = new EtapaProcessamento();
		etapaProcessamento.setLoteProcessado(loteProcessado);
		etapaProcessamento.setCategoriaProcessamento(categoriaProcessamento);
		
		etapaProcessamentoDao.buscarEtapaProcessamentoPorLoteProcessadoECategoriaProcessamento(etapaProcessamento);
		
		return etapaProcessamento;
		
	}
	
	public EtapaProcessamento buscarEtapaProcessamentoAtualPorLoteProcessado(int idLoteProcessado) {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		
		EtapaProcessamento etapaProcessamento = new EtapaProcessamento();
		etapaProcessamento.setLoteProcessado(loteProcessado);
		
		etapaProcessamentoDao.buscarEtapaProcessamentoAtualPorLoteProcessado(etapaProcessamento);
		
		return etapaProcessamento;
		
	}
	
	public void atualizarEtapaProcessamento(int idLoteProcessado, int idCategoriaProcessamento, Date dtProcessamento, String status) {
		
		EtapaProcessamento etapaProcessamentoOriginal = buscarEtapaProcessamentoPorLoteProcessadoECategoriaProcessamento(idLoteProcessado, idCategoriaProcessamento);
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(idCategoriaProcessamento);
		
		EtapaProcessamento etapaProcessamentoAtualizada = new EtapaProcessamento(loteProcessado, categoriaProcessamento, dtProcessamento, status);
		
		if (dtProcessamento == null) {
			etapaProcessamentoAtualizada.setDtProcessamento(etapaProcessamentoOriginal.getDtProcessamento());
		}
		
		if (status == null || status.isBlank()) {
			etapaProcessamentoAtualizada.setStatus(etapaProcessamentoOriginal.getStatus());
		}
		
		etapaProcessamentoDao.atualizarEtapaProcessamento(etapaProcessamentoAtualizada);
		
	}
	
	public void deletarEtapaProcessamento(int idLoteProcessado, int idCategoriaProcessamento) {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		
		CategoriaProcessamento categoriaProcessamento = new CategoriaProcessamento();
		categoriaProcessamento.setId(idCategoriaProcessamento);
		
		EtapaProcessamento etapaProcessamento = new EtapaProcessamento();
		etapaProcessamento.setLoteProcessado(loteProcessado);
		etapaProcessamento.setCategoriaProcessamento(categoriaProcessamento);
		
		etapaProcessamentoDao.deletarEtapaProcessamento(etapaProcessamento);
		
	}

}
