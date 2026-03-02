package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.model.ItemVenda;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.utils.Conexao;

public class ItemVendaDAO {
	
	public BigDecimal somarPesoVendidoItemVendaPorDatas(Date dtInicio, Date dtFim) throws SQLException {
		
		String sum = "select sum(peso_vendido_kg_itemvenda) from info_item_venda where dtVenda_venda between ? and ?";
		
		BigDecimal soma = BigDecimal.ZERO;
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(sum);) {
			
			pst.setDate(1, dtInicio);
			pst.setDate(2, dtFim);
			
			ResultSet rset = pst.executeQuery();
			
			if (rset.next()) {
				soma = rset.getBigDecimal(1);
			}
			
			rset.close();
			
		}
		
		return soma;
		
	}
	
	public void inserirItemVenda(ItemVenda itemVenda) {
		
		String insert = "insert into item_venda (tipo_material, venda, peso_vendido_kg_itemvenda, preco_unitario_kg_itemvenda) "
				+ "values (?, ?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setInt(1, itemVenda.getTipoMaterial().getId());
			pst.setInt(2, itemVenda.getVenda().getId());
			pst.setBigDecimal(3, itemVenda.getPesoVendidoKg());
			pst.setBigDecimal(4, itemVenda.getPrecoUnitarioKg());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<ItemVenda> listarItensVenda() {
		
		List<ItemVenda> listaItemVenda = new ArrayList<>();
		
		String select = "select * from info_item_venda";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idItemVenda = rset.getInt("id_itemvenda");
				BigDecimal pesoVendidoKgItemVenda = rset.getBigDecimal("peso_vendido_kg_itemvenda");
				BigDecimal precoUnitarioKgItemVenda = rset.getBigDecimal("preco_unitario_kg_itemvenda");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				int idVenda = rset.getInt("id_venda");
				Date dtVenda = rset.getDate("dtVenda_venda");
				BigDecimal valorTotalVenda = rset.getBigDecimal("valor_total_venda");
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresaCliente = rset.getString("nome_empresa_cliente");
				String contatoPrincipalCliente = rset.getString("contato_principal_cliente");
				String emailContatoCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresaCliente, contatoPrincipalCliente, emailContatoCliente, dtCadastroCliente);
				Venda venda = new Venda(idVenda, dtVenda, valorTotalVenda, cliente);
				
				listaItemVenda.add(new ItemVenda(idItemVenda, tipoMaterial, venda, pesoVendidoKgItemVenda, precoUnitarioKgItemVenda));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaItemVenda;
		
	}
	
	public List<ItemVenda> listarItensVendaPorVenda(Venda vendaBuscada) {
		
		List<ItemVenda> listaItemVenda = new ArrayList<>();
		
		String select = "select * from info_item_venda where id_venda = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, vendaBuscada.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idItemVenda = rset.getInt("id_itemvenda");
				BigDecimal pesoVendidoKgItemVenda = rset.getBigDecimal("peso_vendido_kg_itemvenda");
				BigDecimal precoUnitarioKgItemVenda = rset.getBigDecimal("preco_unitario_kg_itemvenda");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				int idVenda = rset.getInt("id_venda");
				Date dtVenda = rset.getDate("dtVenda_venda");
				BigDecimal valorTotalVenda = rset.getBigDecimal("valor_total_venda");
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresaCliente = rset.getString("nome_empresa_cliente");
				String contatoPrincipalCliente = rset.getString("contato_principal_cliente");
				String emailContatoCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresaCliente, contatoPrincipalCliente, emailContatoCliente, dtCadastroCliente);
				Venda venda = new Venda(idVenda, dtVenda, valorTotalVenda, cliente);
				
				listaItemVenda.add(new ItemVenda(idItemVenda, tipoMaterial, venda, pesoVendidoKgItemVenda, precoUnitarioKgItemVenda));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaItemVenda;
		
	}
	
	public List<ItemVenda> listarItensVendaPorTipoMaterial(TipoMaterial tipoMaterialBuscado) {
		
		List<ItemVenda> listaItemVenda = new ArrayList<>();
		
		String select = "select * from info_item_venda where id_tipomaterial = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, tipoMaterialBuscado.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idItemVenda = rset.getInt("id_itemvenda");
				BigDecimal pesoVendidoKgItemVenda = rset.getBigDecimal("peso_vendido_kg_itemvenda");
				BigDecimal precoUnitarioKgItemVenda = rset.getBigDecimal("preco_unitario_kg_itemvenda");
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);
				
				int idVenda = rset.getInt("id_venda");
				Date dtVenda = rset.getDate("dtVenda_venda");
				BigDecimal valorTotalVenda = rset.getBigDecimal("valor_total_venda");
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresaCliente = rset.getString("nome_empresa_cliente");
				String contatoPrincipalCliente = rset.getString("contato_principal_cliente");
				String emailContatoCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresaCliente, contatoPrincipalCliente, emailContatoCliente, dtCadastroCliente);
				Venda venda = new Venda(idVenda, dtVenda, valorTotalVenda, cliente);
				
				listaItemVenda.add(new ItemVenda(idItemVenda, tipoMaterial, venda, pesoVendidoKgItemVenda, precoUnitarioKgItemVenda));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaItemVenda;
		
	}
	
	public void buscarItemVendaPorId(ItemVenda itemVenda) {
		
		String select = "select * from info_item_venda where id_itemvenda = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, itemVenda.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				itemVenda.setId( rset.getInt("id_itemvenda") );
				itemVenda.setPesoVendidoKg( rset.getBigDecimal("peso_vendido_kg_itemvenda") );
				itemVenda.setPrecoUnitarioKg( rset.getBigDecimal("preco_unitario_kg_itemvenda") );
				
				int idTipoMaterial = rset.getInt("id_tipomaterial");
				String nomeTipoMaterial = rset.getString("nome_tipomaterial");
				String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");
				itemVenda.setTipoMaterial( new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial) );
				
				int idVenda = rset.getInt("id_venda");
				Date dtVenda = rset.getDate("dtVenda_venda");
				BigDecimal valorTotalVenda = rset.getBigDecimal("valor_total_venda");
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresaCliente = rset.getString("nome_empresa_cliente");
				String contatoPrincipalCliente = rset.getString("contato_principal_cliente");
				String emailContatoCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresaCliente, contatoPrincipalCliente, emailContatoCliente, dtCadastroCliente);

				itemVenda.setVenda(new Venda(idVenda, dtVenda, valorTotalVenda, cliente));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void atualizarItemVenda(ItemVenda itemVenda) {
		
		String update = "update item_venda set tipo_material = ?, venda = ?, peso_vendido_kg_itemvenda = ?, preco_unitario_kg_itemvenda = ? where id_itemvenda = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setInt(1, itemVenda.getTipoMaterial().getId());
			pst.setInt(2, itemVenda.getVenda().getId());
			pst.setBigDecimal(3, itemVenda.getPesoVendidoKg());
			pst.setBigDecimal(4, itemVenda.getPrecoUnitarioKg());
			pst.setInt(5, itemVenda.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarItemVenda(int id) throws SQLException {
		
		String delete = "delete from item_venda where id_itemvenda = ?";
		
		try (Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		}
		
	}
	
	public void deletarItensVendaPorVenda(int idVenda) throws SQLException {
		
		String delete = "delete from item_venda where venda = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, idVenda);
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		}
		
	}

}
