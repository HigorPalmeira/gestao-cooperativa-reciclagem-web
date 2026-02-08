<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Transações de Compra</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Padrão ERP) --- */
        :root {
            --primary-color: #0056b3;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --warning-color: #ffc107;
            --danger-color: #dc3545;
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

        /* Cabeçalho */
        .page-header {
            margin-bottom: 1.5rem;
        }

        h1 { margin: 0; font-size: 1.75rem; color: #2c3e50; }

        /* --- Formulário de Pesquisa (Filtros) --- */
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

        .inputs-row input, .inputs-row select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 0.95rem;
            min-width: 130px;
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
            height: 42px;
            margin-left: auto; /* Empurra para a direita */
        }
        .btn-search:hover { background-color: #004494; }

        /* Mensagem de Validação */
        #feedback-message {
            margin-top: 15px;
            color: var(--danger-color);
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
            min-width: 800px;
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

        /* Badges de Status */
        .status-badge {
            padding: 5px 10px;
            border-radius: 12px;
            font-size: 0.85rem;
            font-weight: bold;
            text-transform: capitalize;
        }
        .status-pago { background-color: #d4edda; color: #155724; }
        .status-pendente { background-color: #fff3cd; color: #856404; }
        .status-atrasado { background-color: #f8d7da; color: #721c24; }
        .status-cancelado { background-color: #e2e3e5; color: #383d41; }

        /* Alinhamento de valores */
        .text-right { text-align: right; }

    </style>
     -->
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <!-- Links com alertas para simulação -->
            <a href="../index.html" onclick="alert('Navegar para Início')">Início</a>
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
                    <!-- Mock Data Inicial -->
                    <tr>
                        <td><a href="./transacao_compra.html?id=555" onclick="alert('Ver detalhes da transação #TR-555')" class="id-link">#TR-555</a></td>
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