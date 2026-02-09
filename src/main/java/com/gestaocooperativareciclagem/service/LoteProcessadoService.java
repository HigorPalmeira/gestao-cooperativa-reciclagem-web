package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
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
	
	public LoteProcessadoService(LoteProcessadoDAO loteProcessadoDao, TransacaoCompraService transacaoCompraService, PrecoMaterialService precoMaterialService) {
		this.loteProcessadoDao = loteProcessadoDao;
		this.transacaoCompraService = transacaoCompraService;
		this.precoMaterialService = precoMaterialService;
	}

	private void gerarTransacaoCompra(LoteBruto loteBruto, TipoMaterial tipoMaterial, double pesoKg) {
		
		PrecoMaterial precoMaterial = precoMaterialService.buscarPrecoMaterialVigentePorTipoMaterial(tipoMaterial);
		
		double valorTotalCalculado = precoMaterial.getPrecoCompra() * pesoKg;
		
		transacaoCompraService.inserirTransacaoCompra(valorTotalCalculado, StatusPagamentoTransacaoCompra.AGUARDANDO_PAGAMENTO, Date.valueOf(LocalDate.now()), loteBruto);
		
	}
	
	public void inserirLoteProcessado(double pesoAtualKg, TipoMaterial tipoMaterial, LoteBruto loteBruto) {
		
		if (pesoAtualKg <= 0) {
			throw new RuntimeException("O peso do lote processado é inválido! Para registrar o lote processado informe um peso (em Kg) válido.");
		}
		
		if (tipoMaterial == null) {
			throw new RuntimeException("O tipo de material é inválido! É necessário informar um tipo de material válido para registrar o lote processado.");
		}
		
		if (loteBruto == null || loteBruto.getStatus().equals(StatusLoteBruto.PROCESSADO)) {
			throw new RuntimeException("O lote bruto é inválido! É necessário informar corretamente o lote bruto para registrar o lote processado.");
		}
		
		LoteProcessado loteProcessado = new LoteProcessado(pesoAtualKg, tipoMaterial, loteBruto);
		
		loteProcessadoDao.inserirLoteProcessado(loteProcessado);

		gerarTransacaoCompra(loteBruto, tipoMaterial, loteProcessado.getPesoAtualKg());
		
	}
	
	public void atualizarLoteProcessado(int idLoteProcessado, double pesoAtualKg, TipoMaterial tipoMaterial, LoteBruto loteBruto) {
		
		LoteProcessado loteProcessado = buscarLoteProcessadoPorId(idLoteProcessado);
		
		LoteProcessado loteProcessadoAtualizado = new LoteProcessado();
		loteProcessadoAtualizado.setId(idLoteProcessado);
		
		loteProcessadoAtualizado.setPesoAtualKg(
				pesoAtualKg > 0
				? pesoAtualKg
				: loteProcessado.getPesoAtualKg()
				);
		
		loteProcessadoAtualizado.setTipoMaterial(
				tipoMaterial != null
				? tipoMaterial
				: loteProcessado.getTipoMaterial()
				);
		
		loteProcessadoAtualizado.setLoteBruto(
				loteBruto != null
				? loteBruto
				: loteProcessado.getLoteBruto()
				);
		
		loteProcessadoDao.atualizarLoteProcessado(loteProcessadoAtualizado);
		
	}
	
	public void deletarLoteProcessado(int id) {
		
		loteProcessadoDao.deletarLoteProcessado(id);
		
	}
	
	public List<LoteProcessado> listarLotesProcessado() {
	
		return loteProcessadoDao.listarLotesProcessado();
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorTipoMaterial(TipoMaterial tipoMaterial) {
		
		if (tipoMaterial == null) {
			throw new RuntimeException("Tipo de Material inválido! Não é possível realizar a busca com o tipo de material inválido!");
		}
		
		return loteProcessadoDao.listarLotesProcessadoPorTipoMaterial(tipoMaterial);
		
	}
	
	public List<LoteProcessado> listarLotesProcessadoPorLoteBruto(LoteBruto loteBruto) {
		
		if (loteBruto == null) {
			throw new RuntimeException("Lote Bruto inválido! Não é possível realizar a busca com o lote bruto inválido!");
		}
		
		return loteProcessadoDao.listarLotesProcessadoPorLoteBruto(loteBruto);
		
	}
	
	public LoteProcessado buscarLoteProcessadoPorId(int id) {
		
		LoteProcessado loteProcessado = new LoteProcessado();
		loteProcessado.setId(id);
		
		loteProcessadoDao.buscarLoteProcessadoPorId(loteProcessado);
		
		return loteProcessado;
		
	}
	
}
