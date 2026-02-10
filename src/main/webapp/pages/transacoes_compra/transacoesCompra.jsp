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
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <!-- Links com alertas para simulação -->
            <a href="index.jsp">Início</a>
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
                            <option value="Pago">Pago</option>
                            <option value="Pendente">Pendente</option>
                            <option value="Atrasado">Atrasado</option>
                            <option value="Cancelado">Cancelado</option>
                        </select>
                    </div>
                </div>

                <!-- Botão Pesquisar -->
                <button class="btn-search" onclick="searchTransactions()">Pesquisar</button>
            </div>
            
            <div id="feedback-message">
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
                			<td><a href="DetalharTransacaoCompra?id=${transacaoCompra.id}" class="id-link">#TR-${String.format("%03d", transacaoCompra.id)}</a></td>
                			<td>${ (transacaoCompra.dtPagamento != null ? transacaoCompra.dtPagamento : "---") }</td>
                			<td class="text-right">${String.format("R$ %.2f", transacaoCompra.valorTotalCalculado)}</td>
                			<td>
                				<c:choose>
                					<c:when test="${transacaoCompra.status == 'AGUARDANDO_PAGAMENTO'}">
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

    <script>
        /* --- JavaScript: Lógica de Filtragem --- */

        // Dados simulados (Mock Database)
        // Formato de data ISO (YYYY-MM-DD) para lógica
        const transactionsDB = [
            { id: 555, date: '2026-01-15', value: 1500.00, status: 'Pago' },
            { id: 558, date: '2026-01-20', value: 850.50, status: 'Pendente' },
            { id: 560, date: '2026-01-10', value: 3200.00, status: 'Atrasado' },
            { id: 565, date: '2026-02-01', value: 500.00, status: 'Pago' },
            { id: 570, date: '2025-12-20', value: 1200.00, status: 'Cancelado' }
        ];

        function searchTransactions() {
            // 1. Obter inputs
            const dateStart = document.getElementById('dateStart').value;
            const dateEnd = document.getElementById('dateEnd').value;
            const valMin = document.getElementById('valMin').value;
            const valMax = document.getElementById('valMax').value;
            const status = document.getElementById('statusSelect').value;
            
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

            // 2. Validação: Pelo menos um campo deve estar preenchido
            const isDateEmpty = !dateStart && !dateEnd;
            const isValEmpty = !valMin && !valMax;
            const isStatusEmpty = !status;

            if (isDateEmpty && isValEmpty && isStatusEmpty) {
                feedbackMsg.style.display = 'block';
                return;
            } else {
                feedbackMsg.style.display = 'none';
            }

            // 3. Filtragem
            const filteredData = transactionsDB.filter(item => {
                let dateValid = true;
                let valValid = true;
                let statusValid = true;

                // Filtro Data
                const itemDate = new Date(item.date);
                if (dateStart) dateValid = dateValid && (itemDate >= new Date(dateStart));
                if (dateEnd) dateValid = dateValid && (itemDate <= new Date(dateEnd));

                // Filtro Valor
                if (valMin) valValid = valValid && (item.value >= parseFloat(valMin));
                if (valMax) valValid = valValid && (item.value <= parseFloat(valMax));

                // Filtro Status
                if (status) statusValid = statusValid && (item.status === status);

                return dateValid && valValid && statusValid;
            });

            // 4. Renderização
            tbody.innerHTML = ''; // Limpar tabela

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="4" style="text-align: center; padding: 2rem; color: #777;">Nenhuma transação encontrada com os critérios selecionados.</td></tr>`;
                return;
            }

            filteredData.forEach(item => {
                // Formatação (pt-BR para data, BRL para moeda conforme pedido R$)
                const formattedDate = new Date(item.date).toLocaleDateString('pt-BR');
                const formattedValue = item.value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
                
                // Classe CSS do badge
                let statusClass = 'status-pendente';
                if (item.status === 'Pago') statusClass = 'status-pago';
                if (item.status === 'Atrasado') statusClass = 'status-atrasado';
                if (item.status === 'Cancelado') statusClass = 'status-cancelado';

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>
                        <a href="./transacao_compra.html?id=${item.id}" onclick="alert('Ver detalhes da transação #TR-${item.id}')" class="id-link">
                            #TR-${item.id}
                        </a>
                    </td>
                    <td>${formattedDate}</td>
                    <td class="text-right">${formattedValue}</td>
                    <td><span class="status-badge ${statusClass}">${item.status}</span></td>
                `;
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>