package com.gestaocooperativareciclagem.service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
	
	public void inserirLoteBruto(double pesoEntradaKg, Date dtEntrada, StatusLoteBruto status, Fornecedor fornecedor) throws SQLException {
		
		LoteBruto loteBruto = new LoteBruto(pesoEntradaKg, dtEntrada, status, fornecedor);
		
		loteBrutoDao.inserirLoteBruto(loteBruto);
		
	}
	
	public void atualizarLoteBruto(int idLoteBruto, double pesoEntradaKg, StatusLoteBruto status, Fornecedor fornecedor) throws SQLException {
		
		LoteBruto loteBruto = buscarLoteBrutoPorId(idLoteBruto);
		
		if (loteBruto.getStatus().equals(StatusLoteBruto.PROCESSADO)) {
			throw new RuntimeException("Não é possível editar um 'Lote Bruto' que já foi processado!");
		}
		
		// adicionar verificação no model
		if (fornecedor != null) {
			loteBruto.setFornecedor(fornecedor);
		}
		
		if (status != null) {
			loteBruto.setStatus(status);
		}
		
		if (pesoEntradaKg > 0) {
			loteBruto.setPesoEntradaKg(pesoEntradaKg);			
		}
		
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
	
	public Long contarLoteBrutoPorData(Date dtLote) throws SQLException {
		
		if (dtLote == null) {
			throw new RuntimeException("Data de busca inválida!");
		}
		
		if (dtLote.after(Date.valueOf(LocalDate.now()))) {
			throw new RuntimeException("A data de busca não pode ser posterior a data atual.");
		}
		
		return loteBrutoDao.contarLoteBrutoPorData(dtLote);
		
	}
	
	public Double somarPesoEntradaLoteBrutoPorDatas(Date dtInicio, Date dtFim) throws SQLException {
		
		if (dtInicio == null || dtFim == null) {
			throw new RuntimeException("Data(s) de busca inválida(s)!");
		}
		
		final Date hoje = Date.valueOf(LocalDate.now());
		if (dtInicio.after(hoje) || dtFim.after(hoje)) {
			throw new RuntimeException("A(s) data(s) de busca não pode(m) ser posterior(es) a data atual.");
		}
		
		return loteBrutoDao.somarPesoEntradaLoteBrutoPorDatas(dtInicio, dtFim);
		
	}

}
