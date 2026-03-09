package com.gestaocooperativareciclagem.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.gestaocooperativareciclagem.model.PrecoMaterial;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.utils.Conexao;

public class PrecoMaterialDAO {

	public void inserirPrecoMaterial(PrecoMaterial precoMaterial) throws SQLException {

		String insert = "insert into preco_material (preco_compra_kg_precomaterial, dtVigencia_precomaterial, tipo_material) values (?, ?, ?)";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(insert);) {

			pst.setBigDecimal(1, precoMaterial.getPrecoCompra());
			pst.setDate(2, precoMaterial.getDtVigencia());
			pst.setInt(3, precoMaterial.getTipoMaterial().getId());

			pst.executeUpdate();

		}

	}

	public List<PrecoMaterial> listarPrecosMaterial() throws SQLException {

		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();

		String select = "select * from info_preco_material";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));

				}

			}

		}

		return listaPrecoMaterial;

	}

	public List<PrecoMaterial> listarPrecosMaterialPorPrecoCompra(BigDecimal precoCompra) throws SQLException {

		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();

		String select = "select * from info_preco_material where preco_compra_kg_precomaterial = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setBigDecimal(1, precoCompra);

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));

				}

			}

		}

		return listaPrecoMaterial;

	}

	public List<PrecoMaterial> listarPrecosMaterialPorIntervaloPrecoCompra(BigDecimal precoCompraInicial, BigDecimal precoCompraFinal) throws SQLException {

		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();

		String select = "select * from info_preco_material where preco_compra_kg_precomaterial between ? and ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setBigDecimal(1, precoCompraInicial);
			pst.setBigDecimal(2, precoCompraFinal);

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));

				}

			}

		}

		return listaPrecoMaterial;

	}

	public List<PrecoMaterial> listarPrecosMaterialPorDataVigencia(Date dtVigencia) throws SQLException {

		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();

		String select = "select * from info_preco_material where dtVigencia_precomaterial = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setDate(1, dtVigencia);

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));

				}

			}

		}

		return listaPrecoMaterial;

	}

	public List<PrecoMaterial> listarPrecosMaterialPorIntervaloDataVigencia(Date dtVigenciaInicial, Date dtVigenciaFinal) throws SQLException {

		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();

		String select = "select * from info_preco_material where dtVigencia_precomaterial between ? and ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setDate(1, dtVigenciaInicial);
			pst.setDate(2, dtVigenciaFinal);

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));

				}

			}

		}

		return listaPrecoMaterial;

	}

	public List<PrecoMaterial> listarPrecosMaterialPorTipoMaterial(int idTipoMaterialBuscado) throws SQLException {

		List<PrecoMaterial> listaPrecoMaterial = new ArrayList<>();

		String select = "select * from info_preco_material where id_tipomaterial = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setInt(1, idTipoMaterialBuscado);

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecoMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));

				}

			}

		}

		return listaPrecoMaterial;

	}

	public void buscarPrecoMaterialPorId(PrecoMaterial precoMaterial) throws SQLException {

		String select = "select * from info_preco_material where id_precomaterial = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setInt(1, precoMaterial.getId());

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					precoMaterial.setId( rset.getInt("id_precomaterial") );
					precoMaterial.setPrecoCompra( rset.getBigDecimal("preco_compra_kg_precomaterial") );
					precoMaterial.setDtVigencia( rset.getDate("dtVigencia_precomaterial") );

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					precoMaterial.setTipoMaterial( new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial) );

				}

			}

		}

	}

	public void buscarPrecoMaterialVigentePorTipoMaterial(PrecoMaterial precoMaterial) throws SQLException {

		String select = "select * from info_preco_material where id_tipomaterial = ? "
				+ "and dtVigencia_precomaterial <= ? "
				+ "order by dtVigencia_precomaterial desc limit 1";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			pst.setInt(1, precoMaterial.getTipoMaterial().getId());
			pst.setDate(2, Date.valueOf(LocalDate.now()));

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {

					precoMaterial.setId( rset.getInt("id_precomaterial") );
					precoMaterial.setPrecoCompra( rset.getBigDecimal("preco_compra_kg_precomaterial") );
					precoMaterial.setDtVigencia( rset.getDate("dtVigencia_precomaterial") );

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					precoMaterial.setTipoMaterial( new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial) );

				}

			}

		}

	}

	public void atualizarPrecoMaterial(PrecoMaterial precoMaterial) throws SQLException {

		String update = "update preco_material set preco_compra_kg_precomaterial = ?, dtVigencia_precomaterial = ?, tipo_material = ? where id_precomaterial = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(update);) {

			pst.setBigDecimal(1, precoMaterial.getPrecoCompra());
			pst.setDate(2, precoMaterial.getDtVigencia());
			pst.setInt(3, precoMaterial.getTipoMaterial().getId());
			pst.setInt(4, precoMaterial.getId());

			pst.executeUpdate();

		}

	}

	public void deletarPrecoMaterial(int id) throws SQLException {

		String delete = "delete from preco_material where id_precomaterial = ?";

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(delete);) {

			pst.setInt(1, id);

			pst.executeUpdate();

		}

	}

	public List<PrecoMaterial> listarPrecosMaterialComParametro(Integer paramIdPrecoMaterial, BigDecimal paramPrecoMin,
			BigDecimal paramPrecoMax, Date dtInicial, Date dtFinal, Integer paramIdTipoMaterial, String paramNomeTipoMaterial) throws SQLException {

		List<PrecoMaterial> listaPrecosMaterial = new ArrayList<>();

		List<Object> parametros = new ArrayList<>();
		String select = buildQuerySelect(parametros, paramIdPrecoMaterial, paramPrecoMin, paramPrecoMax, dtInicial, dtFinal, paramIdTipoMaterial, paramNomeTipoMaterial);

		try (Connection conexao = Conexao.getConnection();
				PreparedStatement pst = conexao.prepareStatement(select);) {

			for (int i=0; i<parametros.size(); i++) {
				pst.setObject(i+1, parametros.get(i));
			}

			try (ResultSet rset = pst.executeQuery();) {

				while(rset.next()) {
					int idPrecoMaterial = rset.getInt("id_precomaterial");
					BigDecimal precoCompraMaterial = rset.getBigDecimal("preco_compra_kg_precomaterial");
					Date dtVigenciaPrecoMaterial = rset.getDate("dtVigencia_precomaterial");

					int idTipoMaterial = rset.getInt("id_tipomaterial");
					String nomeTipoMaterial = rset.getString("nome_tipomaterial");
					String descricaoTipoMaterial = rset.getString("descricao_tipomaterial");

					TipoMaterial tipoMaterial = new TipoMaterial(idTipoMaterial, nomeTipoMaterial, descricaoTipoMaterial);

					listaPrecosMaterial.add(new PrecoMaterial(idPrecoMaterial, precoCompraMaterial, dtVigenciaPrecoMaterial, tipoMaterial));
				}

			}

		}

		return listaPrecosMaterial;

	}

	private String buildQuerySelect(List<Object> parametros, Integer idPrecoMaterial, BigDecimal precoMin, BigDecimal precoMax,
			Date dtInicial, Date dtFinal, Integer idTipoMaterial, String nomeTipoMaterial) {

		StringBuilder builder = new StringBuilder();

		builder.append("select * from info_preco_material where 1=1");

		if (idPrecoMaterial != null && idPrecoMaterial != 0) {
			builder.append(" and id_precomaterial = ?");
			parametros.add(idPrecoMaterial);
		}

		if (precoMin != null) {
			builder.append(" and preco_compra_kg_precomaterial >= ?");
			parametros.add(precoMin);
		}

		if (precoMax != null) {
			builder.append(" and preco_compra_kg_precomaterial <= ?");
			parametros.add(precoMax);
		}

		if (dtInicial != null) {
			builder.append(" and dtVigencia_precomaterial >= ?");
			parametros.add(dtInicial);
		}

		if (dtFinal != null) {
			builder.append(" and dtVigencia_precomaterial <= ?");
			parametros.add(dtFinal);
		}

		if (idTipoMaterial != null && idTipoMaterial != 0) {
			builder.append(" and id_tipomaterial = ?");
			parametros.add(idTipoMaterial);
		}

		if (nomeTipoMaterial != null && !nomeTipoMaterial.isBlank()) {
			builder.append(" and nome_tipomaterial like ?");
			parametros.add("%" + nomeTipoMaterial + "%");
		}

		return builder.toString();

	}

}
