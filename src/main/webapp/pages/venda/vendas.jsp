<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Vendas</title>
    
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

        nav.main-nav a {
            color: var(--white);
            text-decoration: none;
            margin-left: 20px;
            font-size: 0.9rem;
            opacity: 0.9;
        }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        /* Cabeçalho e Botão Nova Venda */
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
            gap: 2rem; /* Espaço maior entre os grupos de intervalo */
            align-items: flex-end;
            flex-wrap: wrap;
        }

        /* Grupo de campos (Ex: Intervalo de Datas) */
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
        
        /* Separador visual "até" */
        .separator { color: #888; font-size: 0.9rem; }

        .btn-search {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            height: 40px; /* Alinhar visualmente com inputs */
            margin-left: auto; /* Empurra o botão para a direita se houver espaço */
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
        }
        .id-link:hover { text-decoration: underline; }

        /* Alinhamento de valores monetários à direita */
        .text-right { text-align: right; }

    </style>
     -->
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="../index.html">Início</a>
            <a href="../clientes/clientes.html">Clientes</a>
            <a href="#">Relatórios</a>
        </div>
    </nav>

    <main class="container">
        
        <div class="page-header">
            <h1>Gestão de Vendas</h1>
            <button class="btn-new" onclick="window.location.href='./nova_venda.html'">
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
                    <tr>
                        <td colspan="3" style="text-align: center; padding: 3rem; color: #777;">
                            Utilize os filtros acima para buscar registros de vendas.
                        </td>
                    </tr>
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
                        <a href="./detalhes_venda.html?id=${sale.id}" class="id-link">
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