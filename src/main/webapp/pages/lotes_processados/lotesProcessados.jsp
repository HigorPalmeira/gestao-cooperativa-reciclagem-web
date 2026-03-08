<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.LoteProcessado" %>
<%@ page import="com.gestaocooperativareciclagem.model.TipoMaterial" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Lotes Processados</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
    <script>const ctx = "${pageContext.request.contextPath}";</script>

</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div>
            <!-- Links com alertas para simulação segura -->
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="${pageContext.request.contextPath}/Producao">Produção</a>
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a>
            <a href="${pageContext.request.contextPath}/ListarTiposMateriais">Tipos de Materiais</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Lotes Processados</h1>
            <!-- Botão Novo Lote -->
            <button class="btn-new" onclick="window.location.href='${pageContext.request.contextPath}/NovoLoteProcessado'"> <!-- pages/lotes_processados/novoLoteProcessado.jsp -->
                + Novo Lote Processado
            </button>
        </div>

        <!-- Formulário de Pesquisa (Filtros) -->
        <section class="search-card">
            <div class="search-form">
                
                <!-- 1. Intervalo de Datas -->
                <div class="filter-group">
                    <label>Intervalo de Datas</label>
                    <div class="inputs-row">
                        <input type="date" id="dateStart" title="Data Inicial">
                        <span class="separator">até</span>
                        <input type="date" id="dateEnd" title="Data Final">
                    </div>
                </div>

                <!-- 2. Intervalo de Peso -->
                <div class="filter-group">
                    <label>Intervalo de Peso Atual (Kg)</label>
                    <div class="inputs-row">
                        <input type="number" id="weightMin" placeholder="Mín" min="0" step="0.1">
                        <span class="separator">até</span>
                        <input type="number" id="weightMax" placeholder="Máx" min="0" step="0.1">
                    </div>
                </div>

                <!-- 3. Etapa de Processamento -->
                <div class="filter-group">
                    <label for="stageSelect">Etapa de Processamento</label>
                    <div class="inputs-row">
                        <select id="stageSelect">
                            <option value="">Todas as etapas...</option>

                            <c:forEach items="${listaCategoriasProcessamento}" var="categoria">
                                <option value="${categoria.id}">${categoria.nome}</option>
                            </c:forEach>

                        </select>
                    </div>
                </div>

                <!-- Botão Pesquisar -->
                <button class="btn-search" onclick="searchProcessedBatches()">Pesquisar</button>
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
                        <th style="width: 20%;">Peso Atual (Kg)</th>
                        <th style="width: 20%;">Data de Criação</th>
                        <th style="width: 20%;">Tipo de Material</th>
                        <th style="width: 25%;">Etapa de Processamento</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                
	                	<c:forEach items="${listaLotesProcessados}" var="loteProcessado" varStatus="status">
	                		
	                		<c:set var="etapaProcessamento" value="${listaEtapasProcessamento[status.index]}"/>
	                		
	                		<tr>
	                			<td><a href="${pageContext.request.contextPath}/DetalharLoteProcessado?id=${loteProcessado.id}" class="id-link">#LP-${String.format("%03d", loteProcessado.id)}</a></td>
	                			<td>${loteProcessado.pesoAtualKg}</td>
	                			<td>${loteProcessado.dtCriacao}</td>
	                			<td>${loteProcessado.tipoMaterial.nome}</td>

                                <c:choose>

                                    <c:when test="${etapaProcessamento.categoriaProcessamento.nome == 'Triagem'}">
                                        <td>
                                            <span class="stage-badge stage-triagem">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:when>

                                    <c:when test="${etapaProcessamento.categoriaProcessamento.nome == 'Trituração'}">
                                        <td>
                                            <span class="stage-badge stage-trituracao">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:when>

                                    <c:when test="${etapaProcessamento.categoriaProcessamento.nome == 'Limpeza'}">
                                        <td>
                                            <span class="stage-badge stage-lavagem">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:when>

                                    <c:when test="${etapaProcessamento.categoriaProcessamento.nome == 'Extrusão'}">
                                        <td>
                                            <span class="stage-badge stage-extrusao">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:when>

                                    <c:when test="${etapaProcessamento.categoriaProcessamento.nome == 'Armazenado'}">
                                        <td>
                                            <span class="stage-badge stage-armazenado">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:when>

                                    <c:when test="${etapaProcessamento.categoriaProcessamento.nome == 'Descarte'}">
                                        <td>
                                            <span class="stage-badge stage-descarte">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:when>

                                    <c:otherwise>
                                        <td>
                                            <span class="stage-badge badge-info">
                                                ${etapaProcessamento.categoriaProcessamento.nome}
                                            </span>
                                        </td>
                                    </c:otherwise>

                                </c:choose>

                                <!--
	                			<td><span class="stage-badge badge-info">${etapaProcessamento.categoriaProcessamento.nome}</span></td>
                                -->
	                		</tr>
	                	</c:forEach>
                
                </tbody>
            </table>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-lote-processado.js"></script>

</body>
</html>