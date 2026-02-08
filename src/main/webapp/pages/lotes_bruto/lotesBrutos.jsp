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
    
     <link rel="stylesheet" href="assets/_css/styles.css">
     
     <!-- 
    <style>
        /* --- CSS: Estilização Visual (Padrão ERP) --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --warning-color: #ffc107;
            --info-color: #17a2b8;
            --text-color: #333;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: var(--text-color);
        }

        /* Menu de Navegação */
        nav.main-nav {
            background-color: var(--primary-color);
            color: var(--white);
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; }
        nav.main-nav a {
            color: var(--white);
            text-decoration: none;
            margin-left: 20px;
            font-size: 0.9rem;
            opacity: 0.9;
            cursor: pointer;
        }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        /* Cabeçalho e Botão Novo */
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        h1 { margin: 0; font-size: 1.75rem; color: #2c3e50; }

        .btn-new {
            background-color: var(--success-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.2s;
        }
        .btn-new:hover { background-color: #218838; }

        /* --- Formulário de Pesquisa (Intervalos) --- */
        .search-card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 1.5rem;
            border: 1px solid var(--border-color);
        }

        .search-form {
            display: flex;
            gap: 2rem;
            align-items: flex-end;
            flex-wrap: wrap;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .filter-group label {
            font-weight: 600;
            font-size: 0.9rem;
            color: #555;
        }

        .inputs-row {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .inputs-row input {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 0.95rem;
            width: 140px;
        }

        .separator { color: #888; font-size: 0.9rem; }

        .btn-search {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            height: 40px;
            margin-left: auto;
        }
        .btn-search:hover { background-color: #004494; }

        /* Mensagem de Erro */
        #feedback-message {
            margin-top: 15px;
            color: #dc3545;
            font-size: 0.9rem;
            display: none;
            border-top: 1px solid #eee;
            padding-top: 10px;
        }

        /* --- Tabela de Resultados --- */
        .table-container {
            background-color: var(--white);
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            overflow-x: auto;
            border: 1px solid var(--border-color);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            min-width: 600px;
        }

        th, td {
            text-align: left;
            padding: 12px 20px;
            border-bottom: 1px solid var(--border-color);
        }

        th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
        tr:hover { background-color: #f1f1f1; }

        /* Link no ID */
        .id-link {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: bold;
            font-family: monospace;
            font-size: 1.1rem;
            cursor: pointer;
        }
        .id-link:hover { text-decoration: underline; }

        /* Status Badges */
        .status-badge {
            padding: 5px 10px;
            border-radius: 12px;
            font-size: 0.85rem;
            font-weight: bold;
            text-transform: uppercase;
        }
        .status-recebido { background-color: #e2e3e5; color: #383d41; }
        .status-processamento { background-color: #fff3cd; color: #856404; }
        .status-concluido { background-color: #d4edda; color: #155724; }

    </style>
     -->
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <!-- Links atualizados para evitar erro de navegação no preview -->
            <a href="index.html">Início</a>
            <a href="pages/lotes_processados/lotes_processados.html">Lotes Processados</a>
            <a href="pages/fornecedores/fornecedores.html">Fornecedores</a>
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
            
            <div id="feedback-message">
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
                    		<td><a href="DetalharLoteBruto?id=${loteBruto.id}">#LB-${loteBruto.id}</a></td>
                    		<td>${loteBruto.pesoEntradaKg}</td>
                    		<td>${loteBruto.dtEntrada}</td>
                    		<td><span class="status-badge ">${loteBruto.status}</span></td>
                    	</tr>
                    </c:forEach>
                    
                    <!-- 
                    <tr>
                        <td><a href="./lote_bruto.html?id=101" onclick="alert('Ver detalhes do lote #LB-101')" class="id-link">#LB-101</a></td>
                        <td>500,00</td>
                        <td>12/01/2026</td>
                        <td><span class="status-badge status-processamento">Em Processamento</span></td>
                    </tr>
                    <tr>
                        <td><a href="./lote_bruto.html?id=98" onclick="alert('Ver detalhes do lote #LB-098')" class="id-link">#LB-098</a></td>
                        <td>1.200,50</td>
                        <td>05/01/2026</td>
                        <td><span class="status-badge status-concluido">Concluído</span></td>
                    </tr>
                    <tr>
                        <td><a href="./lote_bruto.html?id=95" onclick="alert('Ver detalhes do lote #LB-095')" class="id-link">#LB-095</a></td>
                        <td>320,00</td>
                        <td>02/01/2026</td>
                        <td><span class="status-badge status-recebido">Recebido</span></td>
                    </tr>
                     -->
                </tbody>
            </table>
        </section>

    </main>

    <script>
        /* --- JavaScript: Lógica de Interação --- */

        // Dados simulados (Mock Database)
        // Formato de data ISO para facilitar filtragem: YYYY-MM-DD
        /*
        const batchesDatabase = [
            { id: 101, weight: 500.00, date: '2026-01-12', status: 'Em Processamento' },
            { id: 98, weight: 1200.50, date: '2026-01-05', status: 'Concluído' },
            { id: 95, weight: 320.00, date: '2026-01-02', status: 'Recebido' },
            { id: 90, weight: 800.00, date: '2025-12-20', status: 'Concluído' }
        ];
        */

        function searchBatches() {
            // 1. Obter inputs
            const dateStart = document.getElementById('dateStart').value;
            const dateEnd = document.getElementById('dateEnd').value;
            const weightMin = document.getElementById('weightMin').value;
            const weightMax = document.getElementById('weightMax').value;
            
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

        const listaLotesBruto = [
        	<c:forEach var="loteBruto" items="${listaLotesBrutos}" varStatus="loop">
        		{
        			"id": "${loteBruto.id}",
        			"weight": "${loteBruto.pesoEntradaKg}",
        			"date": "${loteBruto.dtEntrada}",
        			"status": "${loteBruto.status}"
        		}
        	</c:forEach>
        ];

        	// 2. Validação: A pesquisa só é executada quando pelo menos um campo for preenchido
            const isDateEmpty = !dateStart && !dateEnd;
            const isWeightEmpty = !weightMin && !weightMax;

            if (isDateEmpty && isWeightEmpty) {
                feedbackMsg.style.display = 'block';
                return; // Interrompe a execução
            } else {
                feedbackMsg.style.display = 'none';
            }

            // 3. Filtragem
            const filteredData = listaLotesBruto.filter(batch => {
                let dateValid = true;
                let weightValid = true;

                // Filtrar Data
                const batchDate = new Date(batch.date);
                if (dateStart) dateValid = dateValid && (batchDate >= new Date(dateStart));
                if (dateEnd) dateValid = dateValid && (batchDate <= new Date(dateEnd));

                // Filtrar Peso
                if (weightMin) weightValid = weightValid && (batch.weight >= parseFloat(weightMin));
                if (weightMax) weightValid = weightValid && (batch.weight <= parseFloat(weightMax));

                return dateValid && weightValid;
            });

            // 4. Renderizar Tabela
            tbody.innerHTML = ''; // Limpar

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="4" style="text-align: center; padding: 2rem; color: #777;">Nenhum lote encontrado.</td></tr>`;
                return;
            }

            filteredData.forEach(batch => {
                // Formatação
                const formattedDate = new Date(batch.date).toLocaleDateString('pt-BR');
                const formattedWeight = batch.weight.toLocaleString('pt-BR', { minimumFractionDigits: 2 });
                
                // Define classe CSS do status
                let statusClass = 'status-recebido';
                if (batch.status === 'Concluído') statusClass = 'status-concluido';
                if (batch.status === 'Em Processamento') statusClass = 'status-processamento';

                const row = document.createElement('tr');
                row.innerHTML = ```
                    <td>
                        <a href="./detalheLoteBruto.jsp?id=${batch.id}" onclick="alert('Ver detalhes do lote #LB-${batch.id}')" class="id-link">
                            #LB-\${String(batch.id).padStart(3, '0')}
                        </a>
                    </td>
                    <td>${formattedWeight}</td>
                    <td>${formattedDate}</td>
                    <td><span class="status-badge ${statusClass}">${batch.status}</span></td>
                ```;
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>