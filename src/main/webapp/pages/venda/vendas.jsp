<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.Venda" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Vendas</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">

    <script>const ctx = "${pageContext.request.contextPath}";</script>
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="${pageContext.request.contextPath}/ListarClientes">Clientes</a>
            <a href="#">Relatórios</a>
        </div>
    </nav>

    <main class="container">
        
        <div class="page-header">
            <h1>Gestão de Vendas</h1>
            <button class="btn-new" onclick="window.location.href='${pageContext.request.contextPath}/NovaVenda'">
                + Nova Venda
            </button>
        </div>

        <section class="search-card">
            <div class="search-form">
                
                <div class="filter-group">
                    <label>Intervalo de Datas</label>
                    <div class="inputs-row">
                        <input type="date" id="dateStart" title="Data Inicial">
                        <span class="separator">até</span>
                        <input type="date" id="dateEnd" title="Data Final">
                    </div>
                </div>

                <div class="filter-group">
                    <label>Intervalo de Valores (R$)</label>
                    <div class="inputs-row">
                        <input type="number" id="valMin" placeholder="Mínimo" min="0" step="0.01">
                        <span class="separator">até</span>
                        <input type="number" id="valMax" placeholder="Máximo" min="0" step="0.01">
                    </div>
                </div>

                <button class="btn-search" onclick="searchSales()">Pesquisar</button>
            </div>
            
            <div id="feedback-message" style="display: none;">
                Por favor, preencha pelo menos um campo (data ou valor) para realizar a pesquisa.
            </div>
        </section>

        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th style="width: 20%;">ID</th>
                        <th style="width: 40%;">Data da Venda</th>
                        <th style="width: 40%;" class="text-right">Valor Total (R$)</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                
                	<c:forEach items="${listaVendas}" var="venda">
                		<tr>
                			<td><a href="${pageContext.request.contextPath}/DetalharVenda?id=${venda.id}" class="id-link">${String.format("#VD-%03d", venda.id)}</a></td>
                			<td>${venda.dtVenda}</td>
                			<td class="text-right">${String.format("R$ %.2f", venda.valorTotal)}</td>
                		</tr>
                	</c:forEach>
                
                </tbody>
            </table>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-venda.js"></script>
</body>
</html>