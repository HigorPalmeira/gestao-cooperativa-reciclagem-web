package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.enums.StatusLoteBruto;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Conexao;

public class LoteBrutoDAO {
	
	public void inserirLoteBruto(LoteBruto loteBruto) {
		
		String insert = "insert into lote_bruto (peso_entrada_kg_lotebruto, dtEntrada_lotebruto, status_lotebruto, fornecedor) values (?, ?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setDouble(1, loteBruto.getPesoEntradaKg());
			pst.setDate(2, loteBruto.getDtEntrada());
			pst.setString(3, loteBruto.getStatus().name());
			pst.setString(4, loteBruto.getFornecedor().getDocumento());
			
			pst.executeUpdate();

			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<LoteBruto> listarLotesBruto() {
		
		List<LoteBruto> listaLotesBruto = new ArrayList<>();
		
		String select = "select * from info_lote_bruto";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idLoteBruto = rset.getInt("id_lotebruto");
				double pesoEntradaKgLoteBruto = rset.getDouble("peso_entrada_kg_lotebruto");
				Date dtEntradaLoteBruto = rset.getDate("dtEntrada_lotebruto");
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.fromDescricao(rset.getString("status_lotebruto"));
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
				
				Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
				
				listaLotesBruto.add(new LoteBruto(idLoteBruto, pesoEntradaKgLoteBruto, dtEntradaLoteBruto, statusLoteBruto, fornecedor));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaLotesBruto;
		
	}
	
	public void buscarLoteBruto(LoteBruto loteBruto) {
		
		String select = "select * from info_lote_bruto where id_lotebruto = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, loteBruto.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				loteBruto.setId( rset.getInt("id_lotebruto") );
				loteBruto.setPesoEntradaKg( rset.getDouble("peso_entrada_kg_lotebruto") );
				loteBruto.setDtEntrada( rset.getDate("dtEntrada_lotebruto") );
				
				StatusLoteBruto statusLoteBruto = StatusLoteBruto.fromDescricao(rset.getString("status_lotebruto"));
				loteBruto.setStatus(statusLoteBruto);
				
				String documentoFornecedor = rset.getString("documento_fornecedor");
				String nomeFornecedor = rset.getString("nome_fornecedor");
				TipoFornecedor tipoFornecedor = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastroFornecedor = rset.getDate("dtCadastro_fornecedor");
				
				Fornecedor fornecedor = new Fornecedor(documentoFornecedor, nomeFornecedor, tipoFornecedor, dtCadastroFornecedor);
				loteBruto.setFornecedor(fornecedor);
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarLoteBruto(LoteBruto loteBruto) {
		
		String update = "update lote_bruto set peso_entrada_kg_lotebruto=?, status_lotebruto=?, fornecedor=? where id_lotebruto=?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setDouble(1, loteBruto.getPesoEntradaKg());
			pst.setString(2, loteBruto.getStatus().name());
			pst.setString(3, loteBruto.getFornecedor().getDocumento());
			pst.setInt(4, loteBruto.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarLoteBruto(int id) {
		
		String delete = "delete from lote_bruto where id_lotebruto=?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(delete);
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
