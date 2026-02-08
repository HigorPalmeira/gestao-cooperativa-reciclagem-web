package com.gestaocooperativareciclagem.service;

import java.util.List;

import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.model.LoteBruto;

public class LoteBrutoService {
	
	private LoteBrutoDAO loteBrutoDao;
	
	public LoteBrutoService(LoteBrutoDAO loteBrutoDao) {
		this.loteBrutoDao = loteBrutoDao;
	}
	
	public List<LoteBruto> listarLotesBrutos() {
		
		return loteBrutoDao.listarLotesBruto();
		
	}

}
