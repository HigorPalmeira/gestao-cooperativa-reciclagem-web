package com.gestaocooperativareciclagem.service;

import java.util.List;

import com.gestaocooperativareciclagem.dao.ClienteDAO;
import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.utils.Formatador;

public class ClienteService {
	
	private ClienteDAO clienteDao;
	
	public ClienteService(ClienteDAO clienteDao) {
		this.clienteDao = clienteDao;
	}
	
	public void inserirCliente(String cnpj, String nomeEmpresa, String contatoPrincipal, String emailContato) {
		
		Cliente cliente = new Cliente(Formatador.clearDoc(cnpj), nomeEmpresa, Formatador.clearFone(contatoPrincipal), emailContato);
		
		clienteDao.inserirCliente(cliente);
		
	}
	
	public void atualizarCliente(String cnpjOriginal, String cnpj, String nomeEmpresa, String contatoPrincipal, String emailContato) {
		
		Cliente clienteOriginal = buscarClientePorCnpj(cnpjOriginal);
		
		Cliente clienteAtualizado = new Cliente(Formatador.clearDoc(cnpj), nomeEmpresa, Formatador.clearFone(contatoPrincipal), emailContato);
		
		if (cnpj == null || cnpj.isBlank()) {
			clienteAtualizado.setCnpj(clienteOriginal.getCnpj());
		}
		
		if (nomeEmpresa == null || nomeEmpresa.isBlank()) {
			clienteAtualizado.setNomeEmpresa(clienteOriginal.getNomeEmpresa());
		}
		
		if (contatoPrincipal == null || contatoPrincipal.isBlank()) {
			clienteAtualizado.setContatoPrincipal(clienteOriginal.getContatoPrincipal());
		}
		
		if (emailContato == null || emailContato.isBlank()) {
			clienteAtualizado.setEmailContato(clienteOriginal.getEmailContato());
		}
		
		clienteDao.atualizarCliente(clienteAtualizado);
		
	}
	
	public void deletarCliente(String cnpj) {
		
		if (cnpj == null || cnpj.isBlank()) {
			throw new RuntimeException("CNPJ inválido! É necessário informar um 'CNPJ' para realizar a remoção.");
		}
		
		clienteDao.deletarCliente(Formatador.clearDoc(cnpj));
		
	}

	public List<Cliente> listarClientes() {
		
		return clienteDao.listarClientes();
		
	}
	
	public List<Cliente> listarClientesPorNomeEmpresa(String nomeEmpresa) {
		
		if (nomeEmpresa == null || nomeEmpresa.isBlank()) {
			throw new RuntimeException("Nome da empresa inválido! É necessário informar um 'Nome da empresa' para realizar a busca.");
		}
		
		return clienteDao.listarClientesPorNomeEmpresa(nomeEmpresa);
		
	}
	
	public Cliente buscarClientePorCnpj(String cnpj) {
		
		if (cnpj == null || cnpj.isBlank()) {
			throw new RuntimeException("CNPJ inválido! É necessário informar um 'CNPJ' para realizar a busca.");
		}
		
		Cliente cliente = new Cliente();
		cliente.setCnpj(Formatador.clearDoc(cnpj));
		
		clienteDao.buscarClientePorCnpj(cliente);
		
		return cliente;
		
	}
	
	public Cliente buscarClientePorEmailContato(String emailContato) {
		
		if (emailContato == null || emailContato.isBlank()) {
			throw new RuntimeException("E-mail de contato inválido! É necessário informar um 'E-mail de contato' para realizar a busca.");
		}
		
		Cliente cliente = new Cliente();
		cliente.setEmailContato(emailContato);
		
		clienteDao.buscarClientePorEmailContato(cliente);
		
		return cliente;
		
	}
	
	public Cliente buscarClientePorContatoPrincipal(String contatoPrincipal) {
		
		if (contatoPrincipal == null || contatoPrincipal.isBlank()) {
			throw new RuntimeException("Contato Principal inválido! É necessário informar um 'Contato Principal' para realizar a busca.");
		}
		
		Cliente cliente = new Cliente();
		cliente.setContatoPrincipal(Formatador.clearFone(contatoPrincipal));
		
		clienteDao.buscarClientePorContatoPrincipal(cliente);
		
		return cliente;
		
	}

}
