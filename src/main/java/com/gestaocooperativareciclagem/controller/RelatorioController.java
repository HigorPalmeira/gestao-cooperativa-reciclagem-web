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
import com.gestaocooperativareciclagem.model.CategoriaProcessamento;
import com.gestaocooperativareciclagem.service.CategoriaProcessamentoService;
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
		urlPatterns={ "/RelatorioCategoriaProcessamento", "/RelatorioTipoMaterial" })
public class RelatorioController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CategoriaProcessamentoService categoriaProcessamentoService;

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

		} catch (Exception e) {
			throw new ServletException("Erro ao tentar inicializar CategoriaProcessamentoService");
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

				default:
					System.out.println("Servlet desconhecido");
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
				
				
				// rodape
				LocalDateTime dtHoraAgora = LocalDateTime.now();
				DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				String fmtDtHoraGeracao = dtHoraAgora.format(formatador);
				
				Font fonteRodape = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, new Color(102, 102, 102));
				
				Phrase textoRodape = new Phrase("Relatório gerado em: " + fmtDtHoraGeracao + " | Página: ", fonteRodape);
				HeaderFooter rodape = new HeaderFooter(textoRodape, true);
				
				rodape.setAlignment(Element.ALIGN_CENTER);
				rodape.setBorder(Rectangle.TOP);
				rodape.setBorderColor(new Color(222, 226, 230));
				
				document.setFooter(rodape);
				
				document.open();

				Color primaryColor = new Color(0, 86, 179);
				Color textColor = new Color(51, 51, 51);
				Color borderColor = new Color(222, 226, 230);
				Color alternateBg = new Color(244, 246, 249);

				Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, primaryColor);
				Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
				Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 11, textColor);

				// título
				Paragraph titulo = new Paragraph("Relatório de Categorias de Processamento", titleFont);
				titulo.setAlignment(Element.ALIGN_CENTER);
				titulo.setSpacingAfter(20f);
				document.add(titulo);


				PdfPTable table = new PdfPTable(new float[] {1f, 3f, 5f});
				table.setWidthPercentage(100);


				// cabeçalho
				String[] cabecalhos = {"ID", "Nome da Categoria", "Descrição"};
				for (String textoCabecalho : cabecalhos) {

					PdfPCell headerCell = new PdfPCell(new Phrase(textoCabecalho, headerFont));
					headerCell.setBackgroundColor(primaryColor);
					headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
					headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					headerCell.setPadding(8f);
					headerCell.setBorderColor(borderColor);
					table.addCell(headerCell);

				}


				boolean linhaAlternada = false;
				for (CategoriaProcessamento categoria : lista) {

					PdfPCell cellId = new PdfPCell(new Phrase(String.valueOf(categoria.getId()), cellFont));
					cellId.setHorizontalAlignment(Element.ALIGN_CENTER);

					PdfPCell cellNome = new PdfPCell(new Phrase(categoria.getNome(), cellFont));
					PdfPCell cellDesc = new PdfPCell(new Phrase(categoria.getDescricao(), cellFont));

					PdfPCell[] celulasLinha = {cellId, cellNome, cellDesc};
					for (PdfPCell cell : celulasLinha) {

						cell.setPadding(6f);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setBorderColor(borderColor);

						if (linhaAlternada) {
							cell.setBackgroundColor(alternateBg);
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

}
