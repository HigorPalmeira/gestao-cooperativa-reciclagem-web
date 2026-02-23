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
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
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
            
            <div id="feedback-message">
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
                			<td><a href="${pageContext.request.contextPath}/DetalharVenda?id=${venda.id}">${String.format("#VD-%03d", venda.id)}</a></td>
                			<td>${venda.dtVenda}</td>
                			<td>${String.format("R$ %.2f", venda.valorTotal)}</td>
                		</tr>
                	</c:forEach>
                
                <!--
                    <tr>
                        <td colspan="3" style="text-align: center; padding: 3rem; color: #777;">
                            Utilize os filtros acima para buscar registros de vendas.
                        </td>
                    </tr>
                 -->
                    
                </tbody>
            </table>
        </section>

    </main>

    <script>
        /* --- JavaScript: Lógica de Negócio --- */

        // Dados simulados (Base de dados)
        // Datas no formato ISO (YYYY-MM-DD) para facilitar comparação
        const salesDatabase = [
            { id: 1001, date: '2024-01-10', value: 1500.00 },
            { id: 1002, date: '2024-01-15', value: 250.50 },
            { id: 1003, date: '2024-02-01', value: 5000.00 },
            { id: 1004, date: '2024-02-20', value: 890.90 },
            { id: 1005, date: '2024-03-05', value: 12000.00 }
        ];

        function searchSales() {
            // 1. Obter Valores dos Inputs
            const dateStart = document.getElementById('dateStart').value;
            const dateEnd = document.getElementById('dateEnd').value;
            const valMin = document.getElementById('valMin').value;
            const valMax = document.getElementById('valMax').value;
            
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

            // 2. Validação: Verifica se TODOS estão vazios
            const isDateEmpty = !dateStart && !dateEnd;
            const isValEmpty = !valMin && !valMax;

            if (isDateEmpty && isValEmpty) {
                feedbackMsg.style.display = 'block';
                return; // Para a execução
            } else {
                feedbackMsg.style.display = 'none';
            }

            // 3. Filtragem Lógica
            const filteredData = salesDatabase.filter(sale => {
                let dateValid = true;
                let valueValid = true;

                // Comparação de Datas
                const saleDateObj = new Date(sale.date);
                if (dateStart) {
                    dateValid = dateValid && (saleDateObj >= new Date(dateStart));
                }
                if (dateEnd) {
                    dateValid = dateValid && (saleDateObj <= new Date(dateEnd));
                }

                // Comparação de Valores
                if (valMin) {
                    valueValid = valueValid && (sale.value >= parseFloat(valMin));
                }
                if (valMax) {
                    valueValid = valueValid && (sale.value <= parseFloat(valMax));
                }

                return dateValid && valueValid;
            });

            // 4. Renderização da Tabela
            tbody.innerHTML = ''; // Limpa resultados anteriores

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="3" style="text-align: center; padding: 2rem; color: #777;">Nenhuma venda encontrada com estes critérios.</td></tr>`;
                return;
            }

            filteredData.forEach(sale => {
                // Formatação de data (pt-BR)
                const formattedDate = new Date(sale.date).toLocaleDateString('pt-BR', { timeZone: 'UTC' });
                
                // Formatação de moeda (BRL)
                const formattedValue = sale.value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>
                        <a href="DetalharVenda?id=${sale.id}" class="id-link">
                            #${sale.id}
                        </a>
                    </td>
                    <td>${formattedDate}</td>
                    <td class="text-right">${formattedValue}</td>
                `;
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>