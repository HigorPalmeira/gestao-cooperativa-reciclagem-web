package com.gestaocooperativareciclagem.model;

import java.sql.Date;
import java.time.LocalDate;

import com.gestaocooperativareciclagem.utils.Validador;

public class Cliente {
	
	private String cnpj;
	private String nomeEmpresa;
	private String contatoPrincipal;
	private String emailContato;
	private Date dtCadastro;
	
	public Cliente() {}

	public Cliente(String cnpj, String nomeEmpresa, String contatoPrincipal, String emailContato) {
		this.cnpj = cnpj;
		this.nomeEmpresa = nomeEmpresa;
		this.contatoPrincipal = contatoPrincipal;
		this.emailContato = emailContato;
		this.dtCadastro = Date.valueOf(LocalDate.now());
		
		this.validar();
	}
	
	public Cliente(String cnpj, String nomeEmpresa, String contatoPrincipal, String emailContato, Date dtCadastro) {
		this.cnpj = cnpj;
		this.nomeEmpresa = nomeEmpresa;
		this.contatoPrincipal = contatoPrincipal;
		this.emailContato = emailContato;
		this.dtCadastro = dtCadastro;
		
		this.validar();
		
	}
	
	private void validar() {
		
		if (!Validador.isCnpj(this.cnpj)) {
			throw new RuntimeException("CNPJ inválido!");
		}
		
		if (!Validador.isEmail(this.emailContato)) {
			throw new RuntimeException("E-mail inválido!");
		}
		
		if (!Validador.isTelefone(this.contatoPrincipal)) {
			throw new RuntimeException("Telefone de contato inválido!");
		}
		
		if (nomeEmpresa == null) {
			throw new RuntimeException("Nome da Empresa inválido! O nome da empresa não pode estar vazio.");
		}
		
		if (nomeEmpresa.isBlank()) {
			throw new RuntimeException("Nome da Empresa inválido! O noma da empresa não pode estar em branco.");
		}
		
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		
		if (!Validador.isCnpj(cnpj)) {
			throw new RuntimeException("CNPJ inválido!");
		}
		
		this.cnpj = cnpj;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getContatoPrincipal() {
		return contatoPrincipal;
	}

	public void setContatoPrincipal(String contatoPrincipal) {
		
		if (!Validador.isTelefone(contatoPrincipal)) {
			throw new RuntimeException("Telefone de contato inválido!");
		}
		
		this.contatoPrincipal = contatoPrincipal;
	}

	public String getEmailContato() {
		return emailContato;
	}

	public void setEmailContato(String emailContato) {
		
		if (!Validador.isEmail(emailContato)) {
			throw new RuntimeException("E-mail inválido!");
		}
		
		this.emailContato = emailContato;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	@Override
	public String toString() {
		return "Cliente [cnpj=" + cnpj + ", nomeEmpresa=" + nomeEmpresa + ", contatoPrincipal=" + contatoPrincipal
				+ ", emailContato=" + emailContato + ", dtCadastro=" + dtCadastro + "]";
	}

}
