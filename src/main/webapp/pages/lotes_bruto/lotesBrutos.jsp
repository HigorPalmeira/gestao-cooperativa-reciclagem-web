<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.LoteBruto" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Lotes Brutos</title>
    
     <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">

     <script>const ctx = "${pageContext.request.contextPath}";</script>
     
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div>
            <!-- Links atualizados para evitar erro de navegação no preview -->
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="${pageContext.request.contextPath}/ListarFornecedores">Fornecedores</a>
            <a href="${pageContext.request.contextPath}/ListarLotesProcessados">Lotes Processados</a>
            <a href="${pageContext.request.contextPath}/Producao">Produção</a>
            <a href="${pageContext.request.contextPath}/RelatorioLoteBruto" target="_blank">Relatório</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Lotes Brutos</h1>
            <!-- Botão Novo Lote (Safe Navigation) -->
            <button class="btn-new" onclick="window.location.href='pages/lotes_bruto/novoLoteBruto.jsp'">
                + Novo Lote Bruto
            </button>
        </div>

        <!-- Formulário de Pesquisa (Intervalos) -->
        <section class="search-card">
            <div class="search-form">
                
                <!-- Intervalo de Datas -->
                <div class="filter-group">
                    <label>Intervalo de Datas</label>
                    <div class="inputs-row">
                        <input type="date" id="dateStart" title="Data Inicial">
                        <span class="separator">até</span>
                        <input type="date" id="dateEnd" title="Data Final">
                    </div>
                </div>

                <!-- Intervalo de Peso -->
                <div class="filter-group">
                    <label>Intervalo de Peso (Kg)</label>
                    <div class="inputs-row">
                        <input type="number" id="weightMin" placeholder="Mín" min="0" step="0.1">
                        <span class="separator">até</span>
                        <input type="number" id="weightMax" placeholder="Máx" min="0" step="0.1">
                    </div>
                </div>

                <!-- Botão Pesquisar -->
                <button class="btn-search" onclick="searchBatches()">Pesquisar</button>
            </div>
            
            <div id="feedback-message" style="display: none;">
                Por favor, preencha pelo menos um campo para realizar a pesquisa.
            </div>
        </section>

        <!-- Tabela de Resultados -->
        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th style="width: 15%;">ID</th>
                        <th style="width: 25%;">Peso de Entrada (Kg)</th>
                        <th style="width: 30%;">Data de Entrada</th>
                        <th style="width: 30%;">Status</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                    
                    <c:forEach items="${listaLotesBrutos}" var="loteBruto">
                    	<tr>
                    		<td><a href="${pageContext.request.contextPath}/DetalharLoteBruto?id=${loteBruto.id}">#LB-${String.format("%03d", loteBruto.id)}</a></td>
                    		<td>${loteBruto.pesoEntradaKg}</td>
                    		<td>${loteBruto.dtEntrada}</td>
                    		<td>
                    			<c:choose>
                    				<c:when test="${loteBruto.status == 'RECEBIDO'}">
			                    		<span class="status-badge status-recebido">Recebido</span>
                    				</c:when>
                    				<c:when test="${loteBruto.status == 'EM_TRIAGEM'}">
			                    		<span class="status-badge status-processamento">Em Triagem</span>
                    				</c:when>
                    				<c:otherwise> <!-- otherwise -->
			                    		<span class="status-badge status-concluido">Processado</span>
                    				</c:otherwise>
                    			</c:choose>
	                    	</td>
                    	</tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-lote-bruto.js"></script>

</body>
</html>