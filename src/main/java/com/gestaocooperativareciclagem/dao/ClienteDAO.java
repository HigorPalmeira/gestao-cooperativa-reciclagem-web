package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.utils.Conexao;

public class ClienteDAO {
	
	public void inserirCliente(Cliente cliente) throws SQLException {
		
		String insert = "insert into cliente (cnpj_cliente, nome_empresa_cliente, contato_principal_cliente, email_contato_cliente, dtCadastro_cliente) values (?, ?, ?, ?, ?)";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert);) {
			
			pst.setString(1, cliente.getCnpj());
			pst.setString(2, cliente.getNomeEmpresa());
			pst.setString(3, cliente.getContatoPrincipal());
			pst.setString(4, cliente.getEmailContato());
			pst.setDate(5, cliente.getDtCadastro());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<Cliente> listarClientes() throws SQLException {
		
		List<Cliente> listaClientes = new ArrayList<>();
		
		String select = "select * from cliente";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					String cnpj = rset.getString("cnpj_cliente");
					String nomeEmpresa = rset.getString("nome_empresa_cliente");
					String contatoPrincipal = rset.getString("contato_principal_cliente");
					String emailContato = rset.getString("email_contato_cliente");
					Date dtCadastro = rset.getDate("dtCadastro_cliente");
					
					listaClientes.add(new Cliente(cnpj, nomeEmpresa, contatoPrincipal, emailContato, dtCadastro));
					
				}
				
			}
			
		}
		
		return listaClientes;
		
	}
	
	public List<Cliente> listarClientesPorNomeEmpresa(String nome) throws SQLException {
		
		List<Cliente> listaClientes = new ArrayList<>();
		
		String select = "select * from cliente where nome_empresa_cliente=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, nome);
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					String cnpj = rset.getString("cnpj_cliente");
					String nomeEmpresa = rset.getString("nome_empresa_cliente");
					String contatoPrincipal = rset.getString("contato_principal_cliente");
					String emailContato = rset.getString("email_contato_cliente");
					Date dtCadastro = rset.getDate("dtCadastro_cliente");
					
					listaClientes.add(new Cliente(cnpj, nomeEmpresa, contatoPrincipal, emailContato, dtCadastro));
					
				}
				
			}
			
		}
		
		return listaClientes;
		
	}
	
	public void buscarClientePorCnpj(Cliente cliente) throws SQLException {
		
		String select = "select * from cliente where cnpj_cliente = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, cliente.getCnpj());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					cliente.setCnpj(rset.getString("cnpj_cliente"));
					cliente.setNomeEmpresa(rset.getString("nome_empresa_cliente"));
					cliente.setContatoPrincipal(rset.getString("contato_principal_cliente"));
					cliente.setEmailContato(rset.getString("email_contato_cliente"));
					cliente.setDtCadastro(rset.getDate("dtCadastro_cliente"));
					
				}
				
			}
			
		}
		
	}
	
	public void buscarClientePorEmailContato(Cliente cliente) throws SQLException {
		
		String select = "select * from cliente where email_contato_cliente = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, cliente.getEmailContato());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					cliente.setCnpj(rset.getString("cnpj_cliente"));
					cliente.setNomeEmpresa(rset.getString("nome_empresa_cliente"));
					cliente.setContatoPrincipal(rset.getString("contato_principal_cliente"));
					cliente.setEmailContato(rset.getString("email_contato_cliente"));
					cliente.setDtCadastro(rset.getDate("dtCadastro_cliente"));
					
				}
				
			}
			
		}
		
	}
	
	public void buscarClientePorContatoPrincipal(Cliente cliente) throws SQLException {
		
		String select = "select * from cliente where contato_principal_cliente = ?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			pst.setString(1, cliente.getContatoPrincipal());
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					cliente.setCnpj(rset.getString("cnpj_cliente"));
					cliente.setNomeEmpresa(rset.getString("nome_empresa_cliente"));
					cliente.setContatoPrincipal(rset.getString("contato_principal_cliente"));
					cliente.setEmailContato(rset.getString("email_contato_cliente"));
					cliente.setDtCadastro(rset.getDate("dtCadastro_cliente"));
					
				}
				
			}
			
		}
		
	}
	
	public void atualizarCliente(Cliente cliente) throws SQLException {
		
		String update = "update cliente set nome_empresa_cliente=?, contato_principal_cliente=?, email_contato_cliente=? where cnpj_cliente=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {
			
			pst.setString(1, cliente.getNomeEmpresa());
			pst.setString(2, cliente.getContatoPrincipal());
			pst.setString(3, cliente.getEmailContato());
			pst.setString(4, cliente.getCnpj());
			
			pst.executeUpdate();
			
		}
		
	}
	
	public void deletarCliente(String cnpj) throws SQLException {
		
		String delete = "delete from cliente where cnpj_cliente=?";
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {
			
			pst.setString(1, cnpj);
			
			pst.executeUpdate();
			
		}
		
	}
	
	public List<Cliente> listarClientesComParametro(String cnpjCliente, String nomeEmpresaCliente, String contatoPrincipalCliente, String emailContatoCliente, Date dtCadastroCliente) throws SQLException {
		
		List<Cliente> listaClientes = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		
		String select = buildQuerySelect(parametros, cnpjCliente, nomeEmpresaCliente, contatoPrincipalCliente, emailContatoCliente, dtCadastroCliente);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					String cnpj = rset.getString("cnpj_cliente");
					String nomeEmpresa = rset.getString("nome_empresa_cliente");
					String contatoPrincipal = rset.getString("contato_principal_cliente");
					String emailContato = rset.getString("email_contato_cliente");
					Date dtCadastro = rset.getDate("dtCadastro_cliente");
					
					listaClientes.add(new Cliente(cnpj, nomeEmpresa, contatoPrincipal, emailContato, dtCadastro));
					
				}
				
			}
			
		}
		
		return listaClientes;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, String cnpjCliente, String nomeEmpresaCliente, String contatoPrincipalCliente, String emailContatoCliente, Date dtCadastroCliente) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from cliente where 1=1");
		
		if (cnpjCliente != null && !cnpjCliente.isBlank()) {
			builder.append(" and cnpj_cliente like ?");
			parametros.add("%" + cnpjCliente.trim() + "%");
		}
		
		if (nomeEmpresaCliente != null && !nomeEmpresaCliente.isBlank()) {
			builder.append(" and nome_empresa_cliente like ?");
			parametros.add("%" + nomeEmpresaCliente.trim() + "%");
		}
		
		if (contatoPrincipalCliente != null && !contatoPrincipalCliente.isBlank()) {
			builder.append(" and contato_principal_cliente like ?");
			parametros.add("%" + contatoPrincipalCliente.trim() + "%");
		}
		
		if (emailContatoCliente != null && !emailContatoCliente.isBlank()) {
			builder.append(" and email_contato_cliente like ?");
			parametros.add("%" + emailContatoCliente.trim() + "%");
		}
		
		if (dtCadastroCliente != null) {
			builder.append(" and dtCadastro_cliente = ?");
			parametros.add(dtCadastroCliente);
		}
		
		return builder.toString();
		
	}

}
