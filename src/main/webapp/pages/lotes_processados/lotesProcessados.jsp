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
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <!-- Links com alertas para simulação segura -->
            <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
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
                
                	<c:forEach items="${listaLotesProcessados}" var="loteProcessado">
                		<tr>
                			<td><a href="${pageContext.request.contextPath}/DetalharLoteProcessado?id=${loteProcessado.id}">#LP-${String.format("%03d", loteProcessado.id)}</a></td>
                			<td>${loteProcessado.pesoAtualKg}</td>
                			<td>${loteProcessado.dtCriacao}</td>
                			<td>${loteProcessado.tipoMaterial.nome}</td>
                			<td><span class="stage-badge">Sem isso ainda :(</span></td>
                		</tr>
                	</c:forEach>
                
                    <!-- Conteúdo inicial (Mock) -->
                    <!--
                    <tr>
                        <td><a href="DetalharLoteProcessado?id=201" class="id-link">#LP-201</a></td>
                        <td>250,00</td>
                        <td>20/01/2026</td>
                        <td>Plástico PET</td>
                        <td><span class="stage-badge stage-lavagem">Lavagem</span></td>
                    </tr>
                    <tr>
                        <td><a href="DetalharLoteProcessado?id=205" class="id-link">#LP-205</a></td>
                        <td>120,50</td>
                        <td>22/01/2026</td>
                        <td>Alumínio</td>
                        <td><span class="stage-badge stage-armazenado">Armazenado</span></td>
                    </tr>
                    <tr>
                        <td><a href="DetalharLoteProcessado?id=210" class="id-link">#LP-210</a></td>
                        <td>500,00</td>
                        <td>25/01/2026</td>
                        <td>Plástico HDPE</td>
                        <td><span class="stage-badge stage-trituracao">Trituração</span></td>
                    </tr>
                     -->
                     
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
                        <a href="DetalharLoteProcessado?id=${batch.id}" class="id-link">
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