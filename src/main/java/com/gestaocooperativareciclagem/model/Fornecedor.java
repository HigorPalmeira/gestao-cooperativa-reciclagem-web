package com.gestaocooperativareciclagem.model;

import java.sql.Date;
import java.time.LocalDate;

import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Validador;

public class Fornecedor {
	
	private String documento;
	private String nome;
	private TipoFornecedor tipo;
	private Date dtCadastro;
	
	public Fornecedor() {}
	
	public Fornecedor(String documento, String nome, TipoFornecedor tipo) {
		this.documento = documento;
		this.nome = nome;
		this.tipo = tipo;
		this.dtCadastro = Date.valueOf(LocalDate.now());
		
		this.validar();
	}
	
	public Fornecedor(String documento, String nome, TipoFornecedor tipo, Date dataCadastro) {
		this.documento = documento;
		this.nome = nome;
		this.tipo = tipo;
		this.dtCadastro = dataCadastro;
		
		this.validar();
	}
	
	private void validar() {
		
		if (this.nome == null) {
			throw new RuntimeException("Nome inválido! O nome não pode estar vazio.");
		}
		
		if (this.nome.isBlank()) {
			throw new RuntimeException("Nome inválido! O nome não pode estar em branco.");
		}
		
		if (this.tipo.equals(TipoFornecedor.COLETOR)) {
			if (!Validador.isCpf(this.documento)) {
				throw new RuntimeException("CPF inválido!");
			}
		} else {
			if (!Validador.isCnpj(this.documento)) {
				throw new RuntimeException("CNPJ inválido!");
			}
		}
		
		if (this.dtCadastro.after(Date.valueOf(LocalDate.now()))) {
			throw new RuntimeException("Data de cadastro inválida! Não é possível realizar o cadastro com uma data posterior à data atual");
		}
		
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoFornecedor getTipo() {
		return tipo;
	}

	public void setTipo(TipoFornecedor tipo) {
		this.tipo = tipo;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	@Override
	public String toString() {
		return "Fornecedor [documento=" + documento + ", nome=" + nome + ", tipo=" + tipo + ", dataCadastro=" + dtCadastro + "]";
	}

}
