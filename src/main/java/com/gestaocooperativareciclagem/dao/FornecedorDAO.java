package com.gestaocooperativareciclagem.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.enums.TipoFornecedor;
import com.gestaocooperativareciclagem.utils.Conexao;

public class FornecedorDAO {

	public void inserirFornecedor(Fornecedor fornecedor) {

		String insert = "insert into fornecedor (documento_fornecedor, nome_fornecedor, tipo_fornecedor, dtCadastro_fornecedor) values (?, ?, ?, ?)";

		try {

			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(insert);
			pst.setString(1, fornecedor.getDocumento());
			pst.setString(2, fornecedor.getNome());
			pst.setString(3, fornecedor.getTipo().name());
			pst.setDate(4, fornecedor.getDtCadastro());

			pst.executeUpdate();

			pst.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Fornecedor> listarFornecedores() {

		String select = "select * from fornecedor";
		List<Fornecedor> listaFornecedores = new ArrayList<>();

		try {

			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(select);

			ResultSet rset = pst.executeQuery();

			while (rset.next()) {

				String documento = rset.getString("documento_fornecedor");
				String nome = rset.getString("nome_fornecedor");
				TipoFornecedor tipo = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastro = rset.getDate("dtCadastro_fornecedor");

				listaFornecedores.add(new Fornecedor(documento, nome, tipo, dtCadastro));

			}

			rset.close();
			pst.close();
			conexao.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listaFornecedores;

	}
	
	public void buscarFornecedorPorDocumento(Fornecedor fornecedor) {
		
		String select = "select * from fornecedor where documento_fornecedor = ?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, fornecedor.getDocumento());
			
			ResultSet rset = pst.executeQuery();
			
			while(rset.next()) {
				
				fornecedor.setDocumento(rset.getString("documento_fornecedor"));
				fornecedor.setNome(rset.getString("nome_fornecedor"));
				fornecedor.setTipo(TipoFornecedor
						.fromDescricao(rset.getString("tipo_fornecedor")));
				fornecedor.setDtCadastro(rset.getDate("dtCadastro_fornecedor"));
				
			}
			
			rset.close();
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Fornecedor> listarFornecedoresPorNome(String nomeFornecedor) {

		String select = "select * from fornecedor where nome_fornecedor = ?";
		List<Fornecedor> listaFornecedores = new ArrayList<>();

		try {

			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, nomeFornecedor);
			
			ResultSet rset = pst.executeQuery();

			while (rset.next()) {

				String documento = rset.getString("documento_fornecedor");
				String nome = rset.getString("nome_fornecedor");
				TipoFornecedor tipo = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastro = rset.getDate("dtCadastro_fornecedor");

				listaFornecedores.add(new Fornecedor(documento, nome, tipo, dtCadastro));

			}

			rset.close();
			pst.close();
			conexao.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listaFornecedores;

	}
	
	public List<Fornecedor> listarFornecedoresPorTipo(TipoFornecedor tipoFornecedor) {

		String select = "select * from fornecedor where tipo_fornecedor = ?";
		List<Fornecedor> listaFornecedores = new ArrayList<>();

		try {

			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setString(1, tipoFornecedor.getDescricao());
			
			ResultSet rset = pst.executeQuery();

			while (rset.next()) {

				String documento = rset.getString("documento_fornecedor");
				String nome = rset.getString("nome_fornecedor");
				TipoFornecedor tipo = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastro = rset.getDate("dtCadastro_fornecedor");

				listaFornecedores.add(new Fornecedor(documento, nome, tipo, dtCadastro));

			}

			rset.close();
			pst.close();
			conexao.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listaFornecedores;

	}
	
	public List<Fornecedor> listarFornecedoresPorDataCadastro(Date dataInicial, Date dataFinal) {

		String select = "select * from fornecedor where dtCadastro_fornecedor between ? and ?";
		List<Fornecedor> listaFornecedores = new ArrayList<>();

		try {

			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(select);
			pst.setDate(1, dataInicial);
			pst.setDate(2, dataFinal);
			
			ResultSet rset = pst.executeQuery();

			while (rset.next()) {

				String documento = rset.getString("documento_fornecedor");
				String nome = rset.getString("nome_fornecedor");
				TipoFornecedor tipo = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
				Date dtCadastro = rset.getDate("dtCadastro_fornecedor");

				listaFornecedores.add(new Fornecedor(documento, nome, tipo, dtCadastro));

			}

			rset.close();
			pst.close();
			conexao.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

		return listaFornecedores;

	}

	public void atualizarFornecedor(String documentoOriginal, Fornecedor fornecedor) {

		String update = "update fornecedor set documento_fornecedor=?, nome_fornecedor=?, tipo_fornecedor=? where documento_fornecedor=?";

		try {

			Connection conexao = Conexao.getConnection();

			PreparedStatement pst = conexao.prepareStatement(update);
			pst.setString(1, fornecedor.getDocumento());
			pst.setString(2, fornecedor.getNome());
			pst.setString(3, fornecedor.getTipo().name());
			pst.setString(4, documentoOriginal);

			pst.executeUpdate();

			pst.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void deletarFornecedor(String documento) {
		
		String delete = "delete from fornecedor where documento_fornecedor=?";
		
		try {
			
			Connection conexao = Conexao.getConnection();
			
			PreparedStatement pst = conexao.prepareStatement(delete);
			pst.setString(1, documento);
			
			pst.executeUpdate();
			
			pst.close();
			conexao.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Fornecedor> listarFornecedoresComParametro(String paramDocumentoFornecedor, String paramNomeFornecedor, TipoFornecedor paramTipoFornecedor, Date paramDtCadastroFornecedor) throws SQLException {
		
		List<Fornecedor> listaFornecedores = new ArrayList<>();
		
		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramDocumentoFornecedor, paramNomeFornecedor, paramTipoFornecedor, paramDtCadastroFornecedor);
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {
			
			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}
			
			try (ResultSet rset = pst.executeQuery();) {
				
				while(rset.next()) {
					
					String documento = rset.getString("documento_fornecedor");
					String nome = rset.getString("nome_fornecedor");
					TipoFornecedor tipo = TipoFornecedor.fromDescricao(rset.getString("tipo_fornecedor"));
					Date dtCadastro = rset.getDate("dtCadastro_fornecedor");

					listaFornecedores.add(new Fornecedor(documento, nome, tipo, dtCadastro));
					
				}
				
			}
			
		}
		
		return listaFornecedores;
		
	}
	
	private String buildQuerySelect(List<Object> parametros, String documentoFornecedor, String nomeFornecedor, TipoFornecedor tipoFornecedor, Date dtCadastroFornecedor) {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("select * from fornecedor where 1=1");
		
		if (documentoFornecedor != null && !documentoFornecedor.isBlank()) {
			builder.append(" and documento_fornecedor like ?");
			parametros.add("%" + documentoFornecedor.trim() + "%");
		}
		
		if (nomeFornecedor != null && !nomeFornecedor.isBlank()) {
			builder.append(" and nome_fornecedor like ?");
			parametros.add("%" + nomeFornecedor.trim() + "%");
		}
		
		if (tipoFornecedor != null) {
			builder.append(" and tipo_fornecedor = ?");
			parametros.add(tipoFornecedor);
		}
		
		if (dtCadastroFornecedor != null) {
			builder.append(" and dtCadastro_fornecedor = ?");
			parametros.add(dtCadastroFornecedor);
		}
		
		return builder.toString();
		
	}
}
