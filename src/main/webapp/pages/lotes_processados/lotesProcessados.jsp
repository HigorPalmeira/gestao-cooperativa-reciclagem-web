<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Lotes Processados</title>
    
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

        /* --- Formulário de Pesquisa (Filtros Avançados) --- */
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
            min-width: 140px;
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
            height: 42px; /* Altura alinhada com inputs */
            margin-left: auto;
        }
        .btn-search:hover { background-color: #004494; }

        /* Mensagem de Erro/Validação */
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

        /* Badges de Etapa */
        .stage-badge {
            padding: 5px 10px;
            border-radius: 12px;
            font-size: 0.85rem;
            font-weight: bold;
            text-transform: capitalize;
        }
        .stage-triagem { background-color: #e2e3e5; color: #383d41; }
        .stage-trituracao { background-color: #fff3cd; color: #856404; }
        .stage-lavagem { background-color: #d1ecf1; color: #0c5460; }
        .stage-extrusao { background-color: #d4edda; color: #155724; }
        .stage-armazenado { background-color: #cce5ff; color: #004085; }

    </style>
    
     -->
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <!-- Links com alertas para simulação segura -->
            <a href="../index.html" onclick="alert('Navegar para Início')">Início</a>
            <a href="../lotes_brutos/lotes_brutos.html" onclick="alert('Navegar para Lotes Brutos')">Lotes Brutos</a>
            <a href="../tipos_materiais/tipos_materiais.html" onclick="alert('Navegar para Tipos de Materiais')">Tipos de Materiais</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Lotes Processados</h1>
            <!-- Botão Novo Lote -->
            <button class="btn-new" onclick="window.location.href='./novo_lote_processado.html'">
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
                            <option value="Triagem">Triagem</option>
                            <option value="Trituração">Trituração</option>
                            <option value="Lavagem">Lavagem</option>
                            <option value="Extrusão">Extrusão</option>
                            <option value="Armazenado">Armazenado</option>
                        </select>
                    </div>
                </div>

                <!-- Botão Pesquisar -->
                <button class="btn-search" onclick="searchProcessedBatches()">Pesquisar</button>
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
                        <th style="width: 20%;">Peso Atual (Kg)</th>
                        <th style="width: 20%;">Data de Criação</th>
                        <th style="width: 20%;">Tipo de Material</th>
                        <th style="width: 25%;">Etapa de Processamento</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                    <!-- Conteúdo inicial (Mock) -->
                    <tr>
                        <td><a href="./lote_processado.html?id=201" onclick="alert('Ver detalhes do lote #LP-201')" class="id-link">#LP-201</a></td>
                        <td>250,00</td>
                        <td>20/01/2026</td>
                        <td>Plástico PET</td>
                        <td><span class="stage-badge stage-lavagem">Lavagem</span></td>
                    </tr>
                    <tr>
                        <td><a href="./lote_processado.html?id=205" onclick="alert('Ver detalhes do lote #LP-205')" class="id-link">#LP-205</a></td>
                        <td>120,50</td>
                        <td>22/01/2026</td>
                        <td>Alumínio</td>
                        <td><span class="stage-badge stage-armazenado">Armazenado</span></td>
                    </tr>
                    <tr>
                        <td><a href="./lote_processado.html?id=210" onclick="alert('Ver detalhes do lote #LP-210')" class="id-link">#LP-210</a></td>
                        <td>500,00</td>
                        <td>25/01/2026</td>
                        <td>Plástico HDPE</td>
                        <td><span class="stage-badge stage-trituracao">Trituração</span></td>
                    </tr>
                </tbody>
            </table>
        </section>

    </main>

    <script>
        /* --- JavaScript: Lógica de Interação --- */

        // Dados simulados (Mock Database)
        // Formato de data ISO (YYYY-MM-DD) para facilitar comparações
        const processedDB = [
            { id: 201, weight: 250.00, date: '2026-01-20', material: 'Plástico PET', stage: 'Lavagem' },
            { id: 205, weight: 120.50, date: '2026-01-22', material: 'Alumínio', stage: 'Armazenado' },
            { id: 210, weight: 500.00, date: '2026-01-25', material: 'Plástico HDPE', stage: 'Trituração' },
            { id: 215, weight: 80.00, date: '2026-01-28', material: 'Cobre', stage: 'Triagem' },
            { id: 220, weight: 300.00, date: '2026-02-01', material: 'Plástico PET', stage: 'Extrusão' }
        ];

        function searchProcessedBatches() {
            // 1. Obter inputs
            const dateStart = document.getElementById('dateStart').value;
            const dateEnd = document.getElementById('dateEnd').value;
            const weightMin = document.getElementById('weightMin').value;
            const weightMax = document.getElementById('weightMax').value;
            const stage = document.getElementById('stageSelect').value;
            
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

            // 2. Validação: A pesquisa só é executada quando pelo menos um campo for preenchido
            const isDateEmpty = !dateStart && !dateEnd;
            const isWeightEmpty = !weightMin && !weightMax;
            const isStageEmpty = !stage;

            if (isDateEmpty && isWeightEmpty && isStageEmpty) {
                feedbackMsg.style.display = 'block';
                return; // Interrompe a execução
            } else {
                feedbackMsg.style.display = 'none';
            }

            // 3. Filtragem Lógica
            const filteredData = processedDB.filter(batch => {
                let dateValid = true;
                let weightValid = true;
                let stageValid = true;

                // Filtrar Data
                const batchDate = new Date(batch.date);
                if (dateStart) dateValid = dateValid && (batchDate >= new Date(dateStart));
                if (dateEnd) dateValid = dateValid && (batchDate <= new Date(dateEnd));

                // Filtrar Peso
                if (weightMin) weightValid = weightValid && (batch.weight >= parseFloat(weightMin));
                if (weightMax) weightValid = weightValid && (batch.weight <= parseFloat(weightMax));

                // Filtrar Etapa
                if (stage) stageValid = stageValid && (batch.stage === stage);

                return dateValid && weightValid && stageValid;
            });

            // 4. Renderizar Tabela
            tbody.innerHTML = ''; // Limpar tabela atual

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="5" style="text-align: center; padding: 2rem; color: #777;">Nenhum lote encontrado com os critérios selecionados.</td></tr>`;
                return;
            }

            filteredData.forEach(batch => {
                // Formatação de Data e Peso
                const formattedDate = new Date(batch.date).toLocaleDateString('pt-BR');
                const formattedWeight = batch.weight.toLocaleString('pt-BR', { minimumFractionDigits: 2 });
                
                // Define classe CSS para a etapa (cor do badge)
                let stageClass = '';
                const s = batch.stage.toLowerCase();
                if (s.includes('triagem')) stageClass = 'stage-triagem';
                else if (s.includes('trituração')) stageClass = 'stage-trituracao';
                else if (s.includes('lavagem')) stageClass = 'stage-lavagem';
                else if (s.includes('extrusão')) stageClass = 'stage-extrusao';
                else if (s.includes('armazenado')) stageClass = 'stage-armazenado';

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>
                        <a href="./lote_processado.html?id=${batch.id}" onclick="alert('Ver detalhes do lote #LP-${batch.id}')" class="id-link">
                            #LP-${batch.id}
                        </a>
                    </td>
                    <td>${formattedWeight}</td>
                    <td>${formattedDate}</td>
                    <td>${batch.material}</td>
                    <td><span class="stage-badge ${stageClass}">${batch.stage}</span></td>
                `;
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>