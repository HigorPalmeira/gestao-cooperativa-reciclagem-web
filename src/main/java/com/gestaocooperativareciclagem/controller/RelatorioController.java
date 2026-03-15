package com.gestaocooperativareciclagem.controller;

import java.awt.Color;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gestaocooperativareciclagem.dao.CategoriaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.ClienteDAO;
import com.gestaocooperativareciclagem.dao.EtapaProcessamentoDAO;
import com.gestaocooperativareciclagem.dao.FornecedorDAO;
import com.gestaocooperativareciclagem.dao.ItemVendaDAO;
import com.gestaocooperativareciclagem.dao.LoteBrutoDAO;
import com.gestaocooperativareciclagem.dao.LoteProcessadoDAO;
import com.gestaocooperativareciclagem.dao.PrecoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TipoMaterialDAO;
import com.gestaocooperativareciclagem.dao.TransacaoCompraDAO;
import com.gestaocooperativareciclagem.dao.UsuarioDAO;
import com.gestaocooperativareciclagem.dao.VendaDAO;
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.model.Cliente;
import com.gestaocooperativareciclagem.model.EtapaProcessamento;
import com.gestaocooperativareciclagem.model.Fornecedor;
import com.gestaocooperativareciclagem.model.LoteBruto;
import com.gestaocooperativareciclagem.model.LoteProcessado;
import com.gestaocooperativareciclagem.model.PrecoMaterial;
import com.gestaocooperativareciclagem.model.TipoMaterial;
import com.gestaocooperativareciclagem.model.TransacaoCompra;
import com.gestaocooperativareciclagem.model.Usuario;
import com.gestaocooperativareciclagem.model.Venda;
import com.gestaocooperativareciclagem.service.CategoriaProcessamentoService;
import com.gestaocooperativareciclagem.service.ClienteService;
import com.gestaocooperativareciclagem.service.EtapaProcessamentoService;
import com.gestaocooperativareciclagem.service.FornecedorService;
import com.gestaocooperativareciclagem.service.LoteBrutoService;
import com.gestaocooperativareciclagem.service.LoteProcessadoService;
import com.gestaocooperativareciclagem.service.PrecoMaterialService;
import com.gestaocooperativareciclagem.service.TipoMaterialService;
import com.gestaocooperativareciclagem.service.TransacaoCompraService;
import com.gestaocooperativareciclagem.service.UsuarioService;
import com.gestaocooperativareciclagem.service.VendaService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Servlet implementation class RelatorioController
 */
@WebServlet(
		name="RelatorioController",
		urlPatterns={ "/RelatorioCategoriaProcessamento", "/RelatorioTipoMaterial",
				"/RelatorioPrecoMaterial", "/RelatorioCliente",
				"/RelatorioFornecedor", "/RelatorioUsuario",
				"/RelatorioVenda", "/RelatorioTransacaoCompra",
				"/RelatorioLoteBruto", "/RelatorioLoteProcessado",
				"/RelatorioProducao"})
public class RelatorioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final Color PRIMARY_COLOR = new Color(0, 86, 179);
	private final Color TEXT_COLOR = new Color(51, 51, 51);
	private final Color BORDER_COLOR = new Color(222, 226, 230);
	private final Color ALTERNATE_BG = new Color(244, 246, 249);

	private final Font TITLE_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, PRIMARY_COLOR);
	private final Font HEADER_FONT = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
	private final Font CELL_FONT = FontFactory.getFont(FontFactory.HELVETICA, 11, TEXT_COLOR);


	private CategoriaProcessamentoService categoriaProcessamentoService;
	private TipoMaterialService tipoMaterialService;
	private PrecoMaterialService precoMaterialService;
	private ClienteService clienteService;
	private FornecedorService fornecedorService;
	private UsuarioService usuarioService;
	private VendaService vendaService;
	private TransacaoCompraService transacaoCompraService;
	private LoteBrutoService loteBrutoService;
	private EtapaProcessamentoService etapaProcessamentoService;
	private LoteProcessadoService loteProcessadoService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RelatorioController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {

		try {

			categoriaProcessamentoService = new CategoriaProcessamentoService(new CategoriaProcessamentoDAO());
			tipoMaterialService = new TipoMaterialService(new TipoMaterialDAO());
			precoMaterialService = new PrecoMaterialService(new PrecoMaterialDAO(), tipoMaterialService);
			clienteService = new ClienteService(new ClienteDAO());
			fornecedorService = new FornecedorService(new FornecedorDAO());
			usuarioService = new UsuarioService(new UsuarioDAO());
			vendaService = new VendaService(new VendaDAO(), new ItemVendaDAO(), clienteService);
			transacaoCompraService = new TransacaoCompraService(new TransacaoCompraDAO());
			loteBrutoService = new LoteBrutoService(new LoteBrutoDAO());
			etapaProcessamentoService = new EtapaProcessamentoService(new EtapaProcessamentoDAO());
			loteProcessadoService = new LoteProcessadoService(new LoteProcessadoDAO(), transacaoCompraService, precoMaterialService, etapaProcessamentoService, loteBrutoService);

		} catch (Exception e) {
			throw new ServletException("Erro ao tentar inicializar CategoriaProcessamentoService e/ou TipoMaterialService e/ou PrecoMaterialService e/ou ClienteService e/ou FornecedorService e/ou UsuarioService e/ou VendaService e/ou TransacaoCompraService e/ou EtapaProcessamentoService e/ou LoteBrutoService e/ou LoteProcessadoService");
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String path = request.getServletPath();

		try {

			switch(path) {
				case "/RelatorioCategoriaProcessamento":
					gerarRelatorioCategoriaProcessamento(request, response);
					break;

				case "/RelatorioTipoMaterial":
					gerarRelatorioTipoMaterial(request, response);
					break;

				case "/RelatorioPrecoMaterial":
					gerarRelatorioPrecoMaterial(request, response);
					break;

				case "/RelatorioCliente":
					gerarRelatorioCliente(request, response);
					break;

				case "/RelatorioFornecedor":
					gerarRelatorioFornecedor(request, response);
					break;

				case "/RelatorioUsuario":
					gerarRelatorioUsuario(request, response);
					break;

				case "/RelatorioVenda":
					gerarRelatorioVenda(request, response);
					break;

				case "/RelatorioTransacaoCompra":
					gerarRelatorioTransacaoCompra(request, response);
					break;

				case "/RelatorioLoteBruto":
					gerarRelatorioLoteBruto(request, response);
					break;

				case "/RelatorioLoteProcessado":
					gerarRelatorioLoteProcessado(request, response);
					break;

				case "/RelatorioProducao":
					gerarRelatorioProducao(request, response);
					break;
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}

	}

	protected void gerarRelatorioCategoriaProcessamento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_categoria_processamento.pdf\"");

		try {

			List<CategoriaProcessamento> lista = categoriaProcessamentoService.listarCategoriasProcessamento();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Categorias de Processamento", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 3f, 5f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Nome da Categoria", "Descrição"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}


				boolean linhaAlternada = false;
				for (CategoriaProcessamento categoria : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(categoria.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellNome = new PdfPCell(new Phrase(categoria.getNome(), CELL_FONT));
					PdfPCell cellDesc = new PdfPCell(new Phrase(categoria.getDescricao(), CELL_FONT));

					PdfPCell[] celulasLinha = {cellId, cellNome, cellDesc};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioTipoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_tipo_material.pdf\"");

		try {

			List<TipoMaterial> lista = tipoMaterialService.listarTiposMaterial();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Tipos de Material", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 3f, 5f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Nome do Material", "Descrição"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}


				boolean linhaAlternada = false;
				for (TipoMaterial tipo : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(tipo.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellNome = new PdfPCell(new Phrase(tipo.getNome(), CELL_FONT));
					PdfPCell cellDesc = new PdfPCell(new Phrase(tipo.getDescricao(), CELL_FONT));

					PdfPCell[] celulasLinha = {cellId, cellNome, cellDesc};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioPrecoMaterial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_preco_material.pdf\"");

		try {

			List<PrecoMaterial> lista = precoMaterialService.listarPrecosMaterial();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório dos Preços dos Materiais", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 2f, 3f, 3f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Preço (R$/Kg)", "Data de Vigência", "Tipo de Material"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (PrecoMaterial preco : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(preco.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellPreco = new PdfPCell(new Phrase( String.format("R$ %.2f", preco.getPrecoCompra().doubleValue()) , CELL_FONT));
					cellPreco.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					String fmtDtVigencia = preco.getDtVigencia().toLocalDate().format(formatador);
					PdfPCell cellDtVigencia = new PdfPCell(new Phrase(fmtDtVigencia, CELL_FONT));
					cellDtVigencia.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					PdfPCell cellTipoMaterial = new PdfPCell(new Phrase( preco.getTipoMaterial().getNome() , CELL_FONT));
					cellTipoMaterial.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					PdfPCell[] celulasLinha = {cellId, cellPreco, cellDtVigencia, cellTipoMaterial};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_cliente.pdf\"");

		try {

			List<Cliente> lista = clienteService.listarClientes();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Clientes", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 2f, 1f, 3f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"CNPJ", "Empresa", "Contato", "E-mail"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				boolean linhaAlternada = false;
				for (Cliente cliente : lista) {

					PdfPCell cellCnpj = new PdfPCell(new Phrase(cliente.getCnpj(), CELL_FONT));
					cellCnpj.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellEmpresa = new PdfPCell(new Phrase( cliente.getNomeEmpresa() , CELL_FONT));

					PdfPCell cellContato = new PdfPCell(new Phrase(cliente.getContatoPrincipal(), CELL_FONT));
					PdfPCell cellEmail = new PdfPCell(new Phrase( cliente.getEmailContato(), CELL_FONT));

					PdfPCell[] celulasLinha = {cellCnpj, cellEmpresa, cellContato, cellEmail};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioFornecedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_fornecedor.pdf\"");

		try {

			List<Fornecedor> lista = fornecedorService.listarFornecedores();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Fornecedores", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {2f, 3f, 1f, 1f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"Documento", "Nome do Fornecedor", "Tipo", "Data de Cadastro"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (Fornecedor fornecedor : lista) {

					PdfPCell cellDocumento = new PdfPCell(new Phrase(fornecedor.getDocumento(), CELL_FONT));
					cellDocumento.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellNome = new PdfPCell(new Phrase( fornecedor.getNome() , CELL_FONT));

					PdfPCell cellTipo = new PdfPCell(new Phrase(fornecedor.getTipo().getDescricao(), CELL_FONT));
					cellTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					String fmtDtCadastro = fornecedor.getDtCadastro().toLocalDate().format(formatador);
					
					PdfPCell cellDtCadastro = new PdfPCell(new Phrase( fmtDtCadastro, CELL_FONT));
					cellDtCadastro.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell[] celulasLinha = {cellDocumento, cellNome, cellTipo, cellDtCadastro};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_usuario.pdf\"");

		try {

			List<Usuario> lista = usuarioService.listarUsuarios();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Usuários", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 3f, 3f, 2f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Nome do Usuário", "E-mail", "Papel"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				boolean linhaAlternada = false;
				for (Usuario usuario : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(usuario.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellNome = new PdfPCell(new Phrase( usuario.getNome() , CELL_FONT));
					PdfPCell cellEmail = new PdfPCell(new Phrase(usuario.getEmail(), CELL_FONT));
					
					PdfPCell cellPapel = new PdfPCell(new Phrase( usuario.getPapel(), CELL_FONT));
					cellPapel									.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell[] celulasLinha = {cellId, cellNome, cellEmail, cellPapel};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioVenda(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_venda.pdf\"");

		try {

			List<Venda> lista = vendaService.listarVendas();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Vendas", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 1f, 2f, 3f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Data da Venda", "Valor Total (R$)", "Cliente"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (Venda venda: lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(venda.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					String fmtDtVenda = venda.getDtVenda().toLocalDate().format(formatador);
					PdfPCell cellDtVenda = new PdfPCell(new Phrase( fmtDtVenda , CELL_FONT));
					cellDtVenda.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					PdfPCell cellValorTotal = new PdfPCell(new Phrase( String.format("R$ %.2f", venda.getValorTotal().doubleValue()) , CELL_FONT));
					cellValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					PdfPCell cellCliente = new PdfPCell(new Phrase( venda.getCliente().getNomeEmpresa(), CELL_FONT));

					PdfPCell[] celulasLinha = {cellId, cellDtVenda, cellValorTotal, cellCliente};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioTransacaoCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_transacao_compra.pdf\"");

		try {

			List<TransacaoCompra> lista = transacaoCompraService.listarTransacoesCompra();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório de Transações de Compra", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 2f, 2f, 2f, 2f, 1f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Valor (R$)", "Status", "Data do Cálculo", "Data do Pagamento", "Lote Bruto"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (TransacaoCompra transacao : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(transacao.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellValorTotal = new PdfPCell(new Phrase(String.format("R$ %.2f", transacao.getValorTotalCalculado().doubleValue()), CELL_FONT));
					cellValorTotal.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellStatusPagamento = new PdfPCell(new Phrase(transacao.getStatus().getDescricao(), CELL_FONT));
					cellStatusPagamento.setHorizontalAlignment(Element.ALIGN_CENTER);

					String fmtDtCalculo = transacao.getDtCalculo().toLocalDate().format(formatador);
					PdfPCell cellDtCalculo = new PdfPCell(new Phrase( fmtDtCalculo , CELL_FONT));
					cellDtCalculo.setHorizontalAlignment(Element.ALIGN_CENTER);

					String fmtDtPagamento = transacao.getDtPagamento() != null 
							? transacao.getDtPagamento().toLocalDate().format(formatador) 
							: "---";
					PdfPCell cellDtPagamento = new PdfPCell(new Phrase( fmtDtPagamento , CELL_FONT));
					cellDtPagamento.setHorizontalAlignment(Element.ALIGN_CENTER);
					
					PdfPCell cellLoteBruto = new PdfPCell(new Phrase( String.format("#LB-%03d", transacao.getId()) , CELL_FONT));

					PdfPCell[] celulasLinha = {cellId, cellValorTotal, cellStatusPagamento, cellDtCalculo, cellDtPagamento, cellLoteBruto};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioLoteBruto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_lote_bruto.pdf\"");

		try {

			List<LoteBruto> lista = loteBrutoService.listarLotesBrutos();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório dos Lotes Brutos", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 2f, 3f, 3f, 1f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Peso de Entrada (Kg)", "Status", "Nome do Fornecedor", "Data da Entrada"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (LoteBruto lote : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(lote.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellPesoEntrada = new PdfPCell(new Phrase(String.format("%.2f Kg", lote.getPesoEntradaKg().doubleValue()), CELL_FONT));
					cellPesoEntrada.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellStatus = new PdfPCell(new Phrase(lote.getStatus().getDescricao(), CELL_FONT));
					cellStatus.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellFornecedor = new PdfPCell(new Phrase( lote.getFornecedor().getNome() , CELL_FONT));

					String fmtDtEntrada = lote.getDtEntrada().toLocalDate().format(formatador);
					PdfPCell cellDtCalculo = new PdfPCell(new Phrase( fmtDtEntrada , CELL_FONT));


					PdfPCell[] celulasLinha = {cellId, cellPesoEntrada, cellStatus, cellFornecedor, cellDtCalculo};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioLoteProcessado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_lote_processado.pdf\"");

		try {

			List<LoteProcessado> lista = loteProcessadoService.listarLotesProcessado();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório dos Lotes Processados", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 2f, 2f, 1f, 1f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Peso Atual (Kg)", "Tipo de Material", "Lote Bruto", "Data da Criação"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (LoteProcessado lote : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(lote.getId()), CELL_FONT));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellPesoAtual = new PdfPCell(new Phrase(String.format("%.2f Kg", lote.getPesoAtualKg().doubleValue()), CELL_FONT));
					cellPesoAtual.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellTipoMaterial = new PdfPCell(new Phrase(lote.getTipoMaterial().getNome(), CELL_FONT));
					cellTipoMaterial.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellLoteBruto = new PdfPCell(new Phrase(String.format("#LB-%03d", lote.getLoteBruto().getId()), CELL_FONT));
					cellLoteBruto.setHorizontalAlignment(Element.ALIGN_CENTER);

					String fmtDtCriacao = lote.getDtCriacao().toLocalDate().format(formatador);
					PdfPCell cellDtCriacao = new PdfPCell(new Phrase( fmtDtCriacao , CELL_FONT));
					cellDtCriacao.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell[] celulasLinha = {cellId, cellPesoAtual, cellTipoMaterial, cellLoteBruto, cellDtCriacao};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	protected void gerarRelatorioProducao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=\"relatorio_producao.pdf\"");

		try {

			List<EtapaProcessamento> lista = etapaProcessamentoService.listarEtapasProcessamento();

			try (ServletOutputStream out = response.getOutputStream();) {

				Document document = new Document();
				PdfWriter.getInstance(document, out);

				HeaderFooter rodape = criarRodape();

				document.setFooter(rodape);

				document.open();

				// título
				Paragraph titulo = new Paragraph("Relatório da Produção", TITLE_FONT);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {2f, 2f, 2f, 1f, 2f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"Lote Processado", "Tipo de Material", "Categoria", "Status", "Data do Processamento"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, HEADER_FONT));
					headerCell.setBackgroundColor(PRIMARY_COLOR);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(BORDER_COLOR);
					table.addCell(headerCell);

				}

				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				boolean linhaAlternada = false;
				for (EtapaProcessamento etapa : lista) {

					PdfPCell cellLoteProcessado = new PdfPCell(new Phrase(String.format("#LP-%03d", etapa.getLoteProcessado().getId()), CELL_FONT));
					cellLoteProcessado.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellTipoMaterial = new PdfPCell(new Phrase(etapa.getLoteProcessado().getTipoMaterial().getNome(), CELL_FONT));
					cellTipoMaterial.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellCategoriaProcessamento = new PdfPCell(new Phrase(etapa.getCategoriaProcessamento().getNome(), CELL_FONT));
					cellCategoriaProcessamento.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellStatusProcessamento = new PdfPCell(new Phrase(etapa.getStatus(), CELL_FONT));
					cellStatusProcessamento.setHorizontalAlignment(Element.ALIGN_CENTER);

					String fmtDtProcessamento = etapa.getDtProcessamento().toLocalDate().format(formatador);
					PdfPCell cellDtCriacao = new PdfPCell(new Phrase( fmtDtProcessamento , CELL_FONT));


					PdfPCell[] celulasLinha = {cellLoteProcessado, cellTipoMaterial, cellCategoriaProcessamento, cellStatusProcessamento, cellDtCriacao};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(BORDER_COLOR);

						if (linhaAlternada) {
							cell.setBackgroundColor(ALTERNATE_BG);
						}

						table.addCell(cell);

					}

					linhaAlternada = !linhaAlternada;

				}

				document.add(table);
				document.close();
				out.flush();

			}


		} catch (Exception e) {

			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar o PDF");

		}

	}

	private HeaderFooter criarRodape() {

		LocalDateTime dtHoraAgora = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String fmtDtHoraGeracao = dtHoraAgora.format(formatador);

		Font fonteRodape = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, new Color(102, 102, 102));

		Phrase textoRodape = new Phrase("Relatório gerado em: " + fmtDtHoraGeracao + " | Página: ", fonteRodape);
		HeaderFooter rodape = new HeaderFooter(textoRodape, true);

		rodape.setAlignment(Element.ALIGN_CENTER);
		rodape.setBorder(Rectangle.TOP);
		rodape.setBorderColor(new Color(222, 226, 230));

		return rodape;

	}

}
