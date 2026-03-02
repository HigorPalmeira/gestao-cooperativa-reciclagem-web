package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.utils.Conexao;

public class VendaDAO {
	
	public BigDecimal somarValorTotalVendasPorDatas(Date dtInicio, Date dtFim) throws SQLException {
		
		String sum = "select coalesce(sum(valor_total_venda), 0) from venda where dtVenda_venda between ? and ?";
		
		BigDecimal valorTotal = BigDecimal.ZERO;
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(sum);) {
			
			pst.setDate(1, dtInicio);
			pst.setDate(2, dtFim);
			
			ResultSet rset = pst.executeQuery();
			
			if (rset.next()) {
				valorTotal = rset.getBigDecimal(1);
			}
			
			rset.close();
			
		}
		
		return valorTotal;
		
	}
	
	public void inserirVenda(Venda venda) {
		
		String insert = "insert into venda (dtVenda_venda, valor_total_venda, cliente) values (?, ?, ?)";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			
			pst.setDate(1, venda.getDtVenda());
			pst.setBigDecimal(2, venda.getValorTotal());
			pst.setString(3, venda.getCliente().getCnpj());
			
			int linhasAfetadas = pst.executeUpdate();
			
			if (linhasAfetadas > 0) {
				ResultSet rset = pst.getGeneratedKeys();
				if (rset.next()) {
					venda.setId(rset.getInt(1));
				}
				
				rset.close();
			}
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Venda> listarVendas() {
		
		List<Venda> listaVenda = new ArrayList<>();
		
		String select = "select * from info_venda";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idVenda = rset.getInt("id_venda");
				Date dtVenda = rset.getDate("dtVenda_venda");
				BigDecimal valorTotalVenda = rset.getBigDecimal("valor_total_venda");
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresa = rset.getString("nome_empresa_cliente");
				String contatoPrincipal = rset.getString("contato_principal_cliente");
				String emailCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresa, contatoPrincipal, emailCliente, dtCadastroCliente);
				
				listaVenda.add(new Venda(idVenda, dtVenda, valorTotalVenda, cliente));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaVenda;
		
	}
	
	public List<Venda> listarVendasPorCliente(Cliente clienteBuscado) {
		
		List<Venda> listaVenda = new ArrayList<>();
		
		String select = "select * from info_venda where cnpj_cliente = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, clienteBuscado.getCnpj());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				int idVenda = rset.getInt("id_venda");
				Date dtVenda = rset.getDate("dtVenda_venda");
				BigDecimal valorTotalVenda = rset.getBigDecimal("valor_total_venda");
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresa = rset.getString("nome_empresa_cliente");
				String contatoPrincipal = rset.getString("contato_principal_cliente");
				String emailCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresa, contatoPrincipal, emailCliente, dtCadastroCliente);
				
				listaVenda.add(new Venda(idVenda, dtVenda, valorTotalVenda, cliente));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return listaVenda;
		
	}
	
	public void buscarVendaPorId(Venda venda) {
	
		String select = "select * from info_venda where id_venda = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setInt(1, venda.getId());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				venda.setId( rset.getInt("id_venda") );
				venda.setDtVenda( rset.getDate("dtVenda_venda") );
				venda.setValorTotal( rset.getBigDecimal("valor_total_venda") );
				
				String cnpjCliente = rset.getString("cnpj_cliente");
				String nomeEmpresa = rset.getString("nome_empresa_cliente");
				String contatoPrincipal = rset.getString("contato_principal_cliente");
				String emailCliente = rset.getString("email_contato_cliente");
				Date dtCadastroCliente = rset.getDate("dtCadastro_cliente");
				
				Cliente cliente = new Cliente(cnpjCliente, nomeEmpresa, contatoPrincipal, emailCliente, dtCadastroCliente);
				
				venda.setCliente(cliente);
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public void atualizarVenda(Venda venda) {
		
		String update = "update venda set valor_total_venda = ?, cliente = ? where id_venda = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setBigDecimal(1, venda.getValorTotal());
			pst.setString(2, venda.getCliente().getCnpj());
			pst.setInt(3, venda.getId());
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void deletarVenda(int id) throws SQLException {
		
		String delete = "delete from venda where id_venda = ?";
		
		try (Connection conexao = Conexao.getConnection();
			PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setInt(1, id);
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		}
		
	}

}
