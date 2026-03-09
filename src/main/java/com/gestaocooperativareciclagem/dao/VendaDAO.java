package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

			try (ResultSet rset = pst.executeQuery();) {

				if (rset.next()) {
					valorTotal = rset.getBigDecimal(1);
				}

			}

		}

		return valorTotal;

	}
	
	public Map<LocalDate, BigDecimal> buscarSaidasUltimos7Dias() throws SQLException {
		
		Map<LocalDate, BigDecimal> saidas = new HashMap<>();
		
		StringBuilder builder = new StringBuilder();
		builder.append("select date(v.dtVenda_venda) as data, coalesce(sum(iv.peso_vendido_kg_itemvenda), 0) as total ")
			.append("from venda as v ")
			.append("left join item_venda as iv ")
			.append("on v.id_venda = iv.venda ")
			.append("where v.dtVenda_venda >= date_sub(curdate(), interval 6 day)")
			.append("group by date(v.dtVenda_venda)");
		
		String select = builder.toString();
		
		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);
				ResultSet rset = pst.executeQuery();) {
			
			while(rset.next()) {
				
				LocalDate data = rset.getDate("data").toLocalDate();
				BigDecimal total = rset.getBigDecimal("total");
				
				saidas.put(data, total);
				
			}
			
		}
		
		return saidas;
		
	}

	public void inserirVenda(Venda venda) throws SQLException {

		String insert = "insert into venda (dtVenda_venda, valor_total_venda, cliente) values (?, ?, ?)";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);) {

			pst.setDate(1, venda.getDtVenda());
			pst.setBigDecimal(2, venda.getValorTotal());
			pst.setString(3, venda.getCliente().getCnpj());

			int linhasAfetadas = pst.executeUpdate();

			if (linhasAfetadas > 0) {

				try (ResultSet rset = pst.getGeneratedKeys();) {

					if (rset.next()) {
						venda.setId(rset.getInt(1));
					}

				}

			}

		}

	}

	public List<Venda> listarVendas() throws SQLException {

		List<Venda> listaVenda = new ArrayList<>();

		String select = "select * from info_venda";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			try (ResultSet rset = pst.executeQuery();) {

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

			}

		}

		return listaVenda;

	}

	public List<Venda> listarVendasPorCliente(Cliente clienteBuscado) throws SQLException {

		List<Venda> listaVenda = new ArrayList<>();

		String select = "select * from info_venda where cnpj_cliente = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setString(1, clienteBuscado.getCnpj());

			try (ResultSet rset = pst.executeQuery();) {

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

			}

		}

		return listaVenda;

	}

	public void buscarVendaPorId(Venda venda) throws SQLException {

		String select = "select * from info_venda where id_venda = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setInt(1, venda.getId());

			try (ResultSet rset = pst.executeQuery();) {

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

			}

		}

	}

	public void atualizarVenda(Venda venda) throws SQLException {

		String update = "update venda set valor_total_venda = ?, cliente = ? where id_venda = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {

			pst.setBigDecimal(1, venda.getValorTotal());
			pst.setString(2, venda.getCliente().getCnpj());
			pst.setInt(3, venda.getId());

			pst.executeUpdate();

		}

	}

	public void deletarVenda(int id) throws SQLException {

		String delete = "delete from venda where id_venda = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {

			pst.setInt(1, id);

			pst.executeUpdate();

		}

	}

	public List<Venda> listarVendasComParametro(Integer paramIdVenda, Date paramDtInicial, Date paramDtFinal, BigDecimal paramValorMin,
			BigDecimal paramValorMax, String paramCnpjCliente) throws SQLException {

		List<Venda> listaVendas = new ArrayList<>();

		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdVenda, paramDtInicial, paramDtFinal, paramValorMin, paramValorMax, paramCnpjCliente);

		try(Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}

			try(ResultSet rset = pst.executeQuery();) {

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

					listaVendas.add(new Venda(idVenda, dtVenda, valorTotalVenda, cliente));

				}

			}

		}

		return listaVendas;

	}

	private String buildQuerySelect(List<Object> parametros, Integer idVenda, Date dtInicial, Date dtFinal, BigDecimal valorMin,
			BigDecimal valorMax, String cnpjCliente) {

		StringBuilder builder = new StringBuilder();

		builder.append("select * from info_venda where 1=1");

		if (idVenda != null && idVenda != 0) {
			builder.append(" and id_venda = ?");
			parametros.add(idVenda);
		}

		if (dtInicial != null) {
			builder.append(" and dtVenda_venda >= ?");
			parametros.add(dtInicial);
		}

		if (dtFinal != null) {
			builder.append(" and dtVenda_venda <= ?");
			parametros.add(dtFinal);
		}

		if (valorMin != null) {
			builder.append(" and valor_total_venda >= ?");
			parametros.add(valorMin);
		}

		if (valorMax != null) {
			builder.append(" and valor_total_venda <= ?");
			parametros.add(valorMax);
		}

		if (cnpjCliente != null && !cnpjCliente.isBlank()) {
			builder.append(" and cnpj_cliente like ?");
			parametros.add("%" + cnpjCliente.trim() + "%");
		}

		return builder.toString();

	}

}
