package com.gestaocooperativareciclagem.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.PrecoMaterial;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.model.enums.StatusPagamentoTransacaoCompra;

public class LoteProcessadoService {
	
	private LoteProcessadoDAO loteProcessadoDao;
	private TransacaoCompraService transacaoCompraService;
	private PrecoMaterialService precoMaterialService;
	private EtapaProcessamentoService etapaProcessamentoService;
	private LoteBrutoService loteBrutoService;
	
	public LoteProcessadoService(LoteProcessadoDAO loteProcessadoDao, TransacaoCompraService transacaoCompraService, 
			PrecoMaterialService precoMaterialService, EtapaProcessamentoService etapaProcessamentoService,
			LoteBrutoService loteBrutoService) {
		this.loteProcessadoDao = loteProcessadoDao;
		this.transacaoCompraService = transacaoCompraService;
		this.precoMaterialService = precoMaterialService;
		this.etapaProcessamentoService = etapaProcessamentoService;
		this.loteBrutoService = loteBrutoService;
	}
	
	public List<LoteProcessado> listarLotesProcessadoComParametro(Integer idLoteProcessado, Integer idLoteBruto, Integer idTipoMaterial, BigDecimal pesoAtualLoteProcessado, Date dtCriacaoLoteProcessado) throws SQLException {
		
		return loteProcessadoDao.listarLotesProcessadoComParametro(idLoteProcessado, idLoteBruto, idTipoMaterial, pesoAtualLoteProcessado, dtCriacaoLoteProcessado);
		
	}

	private void gerarTransacaoCompra(LoteBruto loteBruto, TipoMaterial tipoMaterial, BigDecimal pesoKg) {
		
		PrecoMaterial precoMaterial = precoMaterialService.buscarPrecoMaterialVigentePorTipoMaterial(tipoMaterial);
		
		BigDecimal valorTotalCalculado = precoMaterial.getPrecoCompra().multiply(pesoKg);
		
		transacaoCompraService.inserirTransacaoCompra(valorTotalCalculado, StatusPagamentoTransacaoCompra.PENDENTE, Date.valueOf(LocalDate.now()), loteBruto);
		
	}
	
	public void inserirLoteProcessado(BigDecimal pesoAtualKg, TipoMaterial tipoMaterial, CategoriaProcessamento categoriaProcessamento, LoteBruto loteBruto) throws SQLException {
		
		if (pesoAtualKg.compareTo(BigDecimal.ZERO) <= 0) {
			throw new RuntimeException("O peso do lote processado é inválido! Para registrar o lote processado informe um peso (em Kg) válido.");
		}
		
		if (tipoMaterial == null) {
			throw new RuntimeException("O tipo de material é inválido! É necessário informar um tipo de material válido para registrar o lote processado.");
		}
		
		if (categoriaProcessamento == null) {
			throw new RuntimeException("A categoria de processamento é inválida! É necessário informar uma categoria de processamento válida para registrar o lote processado.");
		}
		
		if (loteBruto == null || loteBruto.getStatus().equals(StatusLoteBruto.PROCESSADO)) {
			throw new RuntimeException("O lote bruto é inválido! É necessário informar corretamente o lote bruto para registrar o lote processado.");
		}
		
		
		List<LoteProcessado> listaLotesProcessados = listarLotesProcessadoPorLoteBruto(loteBruto);
		BigDecimal pesoTotal = listaLotesProcessados.stream()
				.map(LoteProcessado::getPesoAtualKg)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		if (loteBruto.getPesoEntradaKg().compareTo( pesoTotal.add(pesoAtualKg) ) < 0) {
			throw new RuntimeException("Não é possível cadastrar o lote processado com o peso atual. O peso máximo aceito para o lote é de: " + (loteBruto.getPesoEntradaKg().subtract(pesoTotal).doubleValue()));
		}
		
		
		LoteProcessado loteProcessado = new LoteProcessado(pesoAtualKg, tipoMaterial, loteBruto);
		
		loteProcessadoDao.inserirLoteProcessado(loteProcessado);
		etapaProcessamentoService.inserirEtapaProcessamento(loteProcessado.getId(), categoriaProcessamento.getId(), Date.valueOf(LocalDate.now()), "Em Andamento");

		gerarTransacaoCompra(loteBruto, tipoMaterial, loteProcessado.getPesoAtualKg());
			
		if (loteBruto.getPesoEntradaKg().compareTo( pesoTotal.add(pesoAtualKg) ) == 0) {
			loteBrutoService.atualizarLoteBruto(loteBruto.getId(), BigDecimal.ZERO, StatusLoteBruto.PROCESSADO, null);
		}
		
	}
	
	public void atualizarLoteProcessado(int idLoteProcessado, BigDecimal pesoAtualKg) throws SQLException {
		
		LoteProcessado loteProcessado = buscarLoteProcessadoPorId(idLoteProcessado);
		
		LoteProcessado loteProcessadoAtualizado = new LoteProcessado();
		loteProcessadoAtualizado.setId(idLoteProcessado);
		
		if (pesoAtualKg.compareTo(BigDecimal.ZERO) > 0) {
			loteProcessadoAtualizado.setPesoAtualKg(pesoAtualKg);
		} else {
			loteProcessadoAtualizado.setPesoAtualKg(loteProcessado.getPesoAtualKg());
		}
		/*
		loteProcessadoAtualizado.setPesoAtualKg(
				pesoAtualKg.compareTo(BigDecimal.ZERO) > 0
				? pesoAtualKg
				: loteProcessado.getPesoAtualKg()
				);
		*/
		loteProcessadoDao.atualizarLoteProcessado(loteProcessadoAtualizado);
		
	}
	
	public void atualizarEtapaProcessamentoLoteProcessado(int idLoteProcessado, int idCategoriaProcessamento, Date dtProcessamento, String statusProcessamento) throws SQLException {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(idLoteProcessado);
		
		loteProcessadoDao.buscarLoteProcessadoPorId(loteProcessado);
		
		EtapaProcessamento etapaProcessamentoAtual = etapaProcessamentoService.buscarEtapaProcessamentoAtualPorLoteProcessado(idLoteProcessado);
		
		if (etapaProcessamentoAtual.getCategoriaProcessamento().getId() == idCategoriaProcessamento) {
			
			etapaProcessamentoAtual.setDtProcessamento(dtProcessamento);
			etapaProcessamentoAtual.setStatus(statusProcessamento);
			etapaProcessamentoService.atualizarEtapaProcessamento(idLoteProcessado, idCategoriaProcessamento, dtProcessamento, statusProcessamento);
			
		} else {
			
			etapaProcessamentoService.atualizarEtapaProcessamento(etapaProcessamentoAtual.getLoteProcessado().getId(), etapaProcessamentoAtual.getCategoriaProcessamento().getId(), dtProcessamento, "Concluído");
			etapaProcessamentoService.inserirEtapaProcessamento(idLoteProcessado, idCategoriaProcessamento, dtProcessamento, statusProcessamento);
			
		}
		
	}
	
	public void deletarLoteProcessado(int id) throws SQLException {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(id);
		
		loteProcessadoDao.buscarLoteProcessadoPorId(loteProcessado);
		
		if (loteProcessado.getDtCriacao() != null) {

			EtapaProcessamento etapa = etapaProcessamentoService.buscarEtapaProcessamentoAtualPorLoteProcessado(id);
			
			if (etapa.getCategoriaProcessamento() != null) {

				if (etapa.getCategoriaProcessamento().getNome().equalsIgnoreCase("Finalizado")) {
					throw new RuntimeException("O Lote Processado não pode ser deletado. O lote já foi finalizado e está pronto para venda.");
				}
				
				if (etapa.getCategoriaProcessamento().getNome().equalsIgnoreCase("Vendido")) {
					throw new RuntimeException("O Lote Processado não pode ser deletado. O lote já foi vendido.");
				}
				
			}

		}
		
		loteProcessadoDao.deletarLoteProcessado(id);
		
	}
	
	public List<LoteProcessado> listarLotesProcessado() throws SQLException {
	
		return loteProcessadoDao.listarLotesProcessado();
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorTipoMaterial(TipoMaterial tipoMaterial) throws SQLException {
		
		if (tipoMaterial == null) {
			throw new RuntimeException("Tipo de Material inválido! Não é possível realizar a busca com o tipo de material inválido!");
		}
		
		return loteProcessadoDao.listarLotesProcessadoPorTipoMaterial(tipoMaterial);
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorLoteBruto(LoteBruto loteBruto) throws SQLException {
		
		if (loteBruto == null) {
			throw new RuntimeException("Lote Bruto inválido! Não é possível realizar a busca com o lote bruto inválido!");
		}
		
		return loteProcessadoDao.listarLotesProcessadoPorLoteBruto(loteBruto);
		
	}
	
	public LoteProcessado buscarLoteProcessadoPorId(int id) throws SQLException {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(id);
		
		loteProcessadoDao.buscarLoteProcessadoPorId(loteProcessado);
		
		return loteProcessado;
		
	}
	
	public BigDecimal somarPesoTotalLoteProcessadoPorEtapaProcessamento(String etapaProcessamento) throws SQLException {
		
		if (etapaProcessamento == null || etapaProcessamento.isBlank()) {
			throw new RuntimeException("Etapa de Processamento inválida para busca.");
		}
		
		return loteProcessadoDao.somarPesoTotalLoteProcessadoPorEtapaProcessamento(etapaProcessamento);
		
	}
	
}
