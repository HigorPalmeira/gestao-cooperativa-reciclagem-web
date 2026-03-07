<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.TransacaoCompra" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Transações de Compra</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">

    <script>const ctx = "${pageContext.request.contextPath}";</script>
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <!-- Links com alertas para simulação -->
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="#" onclick="alert('Navegar para Relatórios')">Relatórios Financeiros</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Transações de Compra</h1>
        </div>

        <!-- Formulário de Pesquisa -->
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

                <!-- 2. Intervalo de Valores -->
                <div class="filter-group">
                    <label>Intervalo de Valores (R$)</label>
                    <div class="inputs-row">
                        <input type="number" id="valMin" placeholder="Mín" min="0" step="0.01">
                        <span class="separator">até</span>
                        <input type="number" id="valMax" placeholder="Máx" min="0" step="0.01">
                    </div>
                </div>

                <!-- 3. Status de Pagamento -->
                <div class="filter-group">
                    <label for="statusSelect">Status de Pagamento</label>
                    <div class="inputs-row">
                        <select id="statusSelect" style="width: 100%;">
                            <option value="">Todos</option>
                            <option value="PAGO">Pago</option>
                            <option value="PENDENTE">Pendente</option>
                            <option value="ATRASADO">Atrasado</option>
                            <option value="CANCELADO">Cancelado</option>
                        </select>
                    </div>
                </div>

                <!-- Botão Pesquisar -->
                <button class="btn-search" onclick="searchTransactions()">Pesquisar</button>
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
                        <th style="width: 20%;">ID</th>
                        <th style="width: 25%;">Data de Pagamento</th>
                        <th style="width: 25%;" class="text-right">Valor Total (R$)</th>
                        <th style="width: 30%;">Status de Pagamento</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                	
                	<c:forEach items="${listaTransacoesCompra}" var="transacaoCompra">
                		<tr>
                			<td><a href="${pageContext.request.contextPath}/DetalharTransacaoCompra?id=${transacaoCompra.id}" class="id-link">#TR-${String.format("%03d", transacaoCompra.id)}</a></td>
                			<td>${ (transacaoCompra.dtPagamento != null ? transacaoCompra.dtPagamento : "---") }</td>
                			<td class="text-right">${String.format("R$ %.2f", transacaoCompra.valorTotalCalculado)}</td>
                			<td>
                				<c:choose>
                					<c:when test="${transacaoCompra.status == 'PENDENTE'}">
                						<span class="status-badge status-pendente">${transacaoCompra.status}</span>
                					</c:when>
                					<c:otherwise>
                						<span class="status-badge status-pago">${transacaoCompra.status}</span>
                					</c:otherwise>
                				</c:choose>
                			</td>
                		</tr>
                	</c:forEach>
                
                    <!-- Mock Data Inicial -->
                    <!--
                    <tr>
                        <td><a href="DetalharTransacaoCompra?id=555" onclick="alert('Ver detalhes da transação #TR-555')" class="id-link">#TR-555</a></td>
                        <td>15/01/2026</td>
                        <td class="text-right">R$ 1.500,00</td>
                        <td><span class="status-badge status-pago">Pago</span></td>
                    </tr>
                    <tr>
                        <td><a href="./transacao_compra.html?id=558" onclick="alert('Ver detalhes da transação #TR-558')" class="id-link">#TR-558</a></td>
                        <td>20/01/2026</td>
                        <td class="text-right">R$ 850,50</td>
                        <td><span class="status-badge status-pendente">Pendente</span></td>
                    </tr>
                    <tr>
                        <td><a href="./transacao_compra.html?id=560" onclick="alert('Ver detalhes da transação #TR-560')" class="id-link">#TR-560</a></td>
                        <td>10/01/2026</td>
                        <td class="text-right">R$ 3.200,00</td>
                        <td><span class="status-badge status-atrasado">Atrasado</span></td>
                    </tr>
                    -->
                    
                </tbody>
            </table>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-transacao-compra.js"></script>

</body>
</html>