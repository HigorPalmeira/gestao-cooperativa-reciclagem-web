package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.time.LocalDate;

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
	
	private void gerarTransacaoCompra(LoteBruto loteBruto, TipoMaterial tipoMaterial, double pesoKg) {
		
		PrecoMaterial precoMaterial = precoMaterialService.buscarPrecoMaterialVigentePorTipoMaterial(tipoMaterial);

		double valorTotalCalculado = precoMaterial.getPrecoCompra() * pesoKg;
		
		transacaoCompraService.inserirTransacaoCompra(valorTotalCalculado, StatusPagamentoTransacaoCompra.AGUARDANDO_PAGAMENTO, Date.valueOf(LocalDate.now()), loteBruto);
		
		
	}
	
}
