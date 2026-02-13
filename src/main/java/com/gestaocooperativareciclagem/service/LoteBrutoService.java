package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.util.List;

import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;

public class LoteBrutoService {
	
	private LoteBrutoDAO loteBrutoDao;
	
	public LoteBrutoService(LoteBrutoDAO loteBrutoDao) {
		this.loteBrutoDao = loteBrutoDao;
	}
	
	public void inserirLoteBruto(double pesoEntradaKg, Date dtEntrada, StatusLoteBruto status, Fornecedor fornecedor) {
		
		LoteBruto loteBruto = new LoteBruto(pesoEntradaKg, dtEntrada, status, fornecedor);
		
		loteBrutoDao.inserirLoteBruto(loteBruto);
		
	}
	
	public void atualizarLoteBruto(int idLoteBruto, double pesoEntradaKg, StatusLoteBruto status, Fornecedor fornecedor) {
		
		LoteBruto loteBruto = buscarLoteBrutoPorId(idLoteBruto);
		
		if (loteBruto.getStatus().equals(StatusLoteBruto.PROCESSADO)) {
			throw new RuntimeException("Não é possível editar um 'Lote Bruto' que já foi processado!");
		}
		
		// adicionar verificação no model
		loteBruto.setPesoEntradaKg(pesoEntradaKg);
		loteBruto.setStatus(status);
		loteBruto.setFornecedor(fornecedor);
		
		loteBrutoDao.atualizarLoteBruto(loteBruto);
		
	}
	
	public void deletarLoteBruto(int idLoteBruto) {
		
		loteBrutoDao.deletarLoteBruto(idLoteBruto);
		
	}
	
	public List<LoteBruto> listarLotesBrutos() {
		
		return loteBrutoDao.listarLotesBruto();
		
	}
	
	public List<LoteBruto> listarLotesBrutosPorStatus(StatusLoteBruto status) {
		
		return loteBrutoDao.listarLotesBrutoPorStatus(status);
		
	}
	
	public List<LoteBruto> listarLotesBrutosPorFornecedor(Fornecedor fornecedor) {
		
		return loteBrutoDao.listarLotesBrutoPorFornecedor(fornecedor);
		
	}
	
	public List<LoteBruto> listarLotesBrutosPorIntervaloDePesoEntrada(double pesoEntradaInicial, double pesoEntradaFinal) {
		
		if (pesoEntradaInicial < 0 || pesoEntradaFinal < 0) {
			throw new RuntimeException("Peso de entrada informado é inválido! É necessário informar um valor válido: maior que 0.");
		}
		
		return loteBrutoDao.listarLotesBrutoPorIntervaloDePesoEntrada(pesoEntradaInicial, pesoEntradaFinal);
		
	}
	
	public LoteBruto buscarLoteBrutoPorId(int id) {
		
		LoteBruto loteBruto = new LoteBruto();
		loteBruto.setId(id);
		
		loteBrutoDao.buscarLoteBrutoPorId(loteBruto);
		
		return loteBruto;
		
	}

}
