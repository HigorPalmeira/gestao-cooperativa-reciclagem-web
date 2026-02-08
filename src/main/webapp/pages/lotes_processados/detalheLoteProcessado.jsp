<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Lote Processado #LP-201</title>
    
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
            --info-color: #17a2b8;
            --text-color: #333;
            --overlay-color: rgba(0, 0, 0, 0.5);
            --disabled-bg: #e9ecef;
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
            color: #fff; text-decoration: none; font-size: 0.9rem; margin-left: 20px; cursor: pointer; opacity: 0.9;
        }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 0 1rem;
            padding-bottom: 5rem;
        }

        /* Cabeçalhos */
        h1 { color: #2c3e50; margin-bottom: 0.5rem; }
        h2 { 
            font-size: 1.1rem; 
            color: #555; 
            margin-bottom: 1rem; 
            border-bottom: 2px solid #eee; 
            padding-bottom: 0.5rem; 
            margin-top: 2.5rem;
        }

        /* Cartões (Cards) */
        .card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
            margin-bottom: 1.5rem;
        }

        /* Grids de Formulário */
        .form-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 1.5rem;
        }

        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; font-size: 0.9rem; color: #444; }
        
        .form-group input, .form-group select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            width: 100%;
            box-sizing: border-box;
        }

        /* Inputs de Leitura (Read-only/Disabled) */
        input:read-only, input:disabled {
            background-color: var(--disabled-bg);
            color: #6c757d;
            cursor: not-allowed;
            border-color: #ced4da;
        }

        /* Botão Salvar */
        .btn-save {
            background-color: var(--primary-color);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 1.5rem;
        }
        .btn-save:hover { background-color: #004494; }

        /* Tabela */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--white);
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }

        th, td { text-align: left; padding: 12px 15px; border-bottom: 1px solid var(--border-color); }
        th { background-color: #f8f9fa; font-weight: 600; color: #495057; font-size: 0.9rem; }
        
        /* Links na Tabela (Abrem Modal) */
        .category-link { 
            color: var(--primary-color); 
            font-weight: bold; 
            text-decoration: none; 
            cursor: pointer; 
        }
        .category-link:hover { text-decoration: underline; }

        /* Status Badges */
        .badge { padding: 4px 8px; border-radius: 4px; font-size: 0.8rem; font-weight: bold; }
        .badge-success { background-color: #d4edda; color: #155724; }
        .badge-warning { background-color: #fff3cd; color: #856404; }

        /* Rodapé com Exclusão */
        .danger-zone {
            margin-top: 4rem;
            border-top: 1px solid #ddd;
            padding-top: 1.5rem;
            text-align: right;
        }
        .btn-delete {
            background-color: transparent;
            color: var(--danger-color);
            border: 1px solid var(--danger-color);
            padding: 10px 25px;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.2s;
        }
        .btn-delete:hover { background-color: var(--danger-color); color: white; }

        /* Mensagens de Erro */
        .error-msg { color: var(--danger-color); font-size: 0.85rem; margin-top: 5px; display: none; }

        /* --- Estilos do Pop-up (Modal) --- */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: var(--overlay-color);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }

        .modal-content {
            background-color: var(--white);
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            width: 90%;
            max-width: 500px;
            position: relative;
        }

        .modal-header {
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
            margin-bottom: 15px;
            color: var(--primary-color);
        }

        .btn-close-modal {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            float: right;
        }
        .btn-close-modal:hover { background-color: #5a6268; }

        /* Conteúdo do Modal */
        .modal-body p { margin: 8px 0; font-size: 0.95rem; }
        .modal-body strong { color: #555; }

    </style>
    
     -->
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System &rsaquo; Lote Processado #LP-201</div>
        <div>
            <a href="./lotes_processados.html" onclick="alert('Voltar para Gestão de Lotes Processados')">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- SEÇÃO 1: Edição do Lote Processado -->
        <h1>Dados do Lote Processado</h1>
        <section class="card">
            <form id="processedForm" onsubmit="saveChanges(event)">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="currentWeight">Peso Atual (Kg) *</label>
                        <!-- Habilitado para edição -->
                        <input type="number" id="currentWeight" step="0.01">
                    </div>
                    <div class="form-group">
                        <label for="materialType">Tipo de Material *</label>
                        <!-- Habilitado para edição -->
                        <select id="materialType">
                            <option value="Plástico PET">Plástico PET</option>
                            <option value="Plástico HDPE">Plástico HDPE</option>
                            <option value="Alumínio">Alumínio</option>
                            <option value="Papelão">Papelão</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="creationDate">Data de Criação</label>
                        <!-- Readonly (não editável) -->
                        <input type="text" id="creationDate" readonly>
                    </div>
                </div>
                <button type="submit" class="btn-save">Salvar Alterações</button>
            </form>
        </section>

        <!-- SEÇÃO 2: Lote Bruto Relacionado -->
        <h2>Origem: Lote Bruto Relacionado</h2>
        <section class="card">
            <div class="form-grid">
                <div class="form-group">
                    <label for="rawBatchId">ID do Lote Bruto *</label>
                    <!-- Único campo editável nesta seção -->
                    <input type="text" id="rawBatchId" onblur="fetchRawBatch()" placeholder="Ex: LB-101">
                    <span id="rawBatchError" class="error-msg">Lote bruto não encontrado.</span>
                </div>
                <div class="form-group">
                    <label for="rawEntryWeight">Peso de Entrada (Kg)</label>
                    <input type="text" id="rawEntryWeight" readonly>
                </div>
                <div class="form-group">
                    <label for="rawEntryDate">Data de Entrada</label>
                    <input type="text" id="rawEntryDate" readonly>
                </div>
                <div class="form-group">
                    <label for="rawStatus">Status</label>
                    <input type="text" id="rawStatus" readonly>
                </div>
            </div>
        </section>

        <!-- SEÇÃO 3: Tabela de Etapas -->
        <h2>Etapas de Processamento</h2>
        <section>
            <table>
                <thead>
                    <tr>
                        <th>Categoria de Processamento</th>
                        <th>Data do Processamento</th>
                        <th>Status de Processamento</th>
                    </tr>
                </thead>
                <tbody id="stagesTableBody">
                    <!-- Conteúdo Mock -->
                    <tr>
                        <td>
                            <!-- Clique abre Pop-up -->
                            <span class="category-link" onclick="openStageModal('Triagem')">Triagem</span>
                        </td>
                        <td>20/01/2026</td>
                        <td><span class="badge badge-success">Concluído</span></td>
                    </tr>
                    <tr>
                        <td>
                            <span class="category-link" onclick="openStageModal('Lavagem')">Lavagem</span>
                        </td>
                        <td>21/01/2026</td>
                        <td><span class="badge badge-success">Concluído</span></td>
                    </tr>
                    <tr>
                        <td>
                            <span class="category-link" onclick="openStageModal('Secagem')">Secagem</span>
                        </td>
                        <td>22/01/2026</td>
                        <td><span class="badge badge-warning">Em Andamento</span></td>
                    </tr>
                </tbody>
            </table>
        </section>

        <!-- SEÇÃO 4: Excluir (Rodapé) -->
        <div class="danger-zone">
            <span style="color: #666; font-size: 0.9rem; margin-right: 15px;">Deseja apagar este registo permanentemente?</span>
            <button class="btn-delete" onclick="deleteProcessedBatch()">Excluir Lote Processado</button>
        </div>

    </main>

    <!-- Modal (Pop-up) -->
    <div id="stageModal" class="modal-overlay">
        <div class="modal-content">
            <h3 class="modal-header">Detalhes da Etapa</h3>
            <div class="modal-body" id="modalBodyContent">
                <!-- Conteúdo injetado via JS -->
            </div>
            <div style="margin-top: 20px;">
                <button class="btn-close-modal" onclick="closeModal()">Fechar</button>
                <div style="clear: both;"></div>
            </div>
        </div>
    </div>

    <script>
        /* --- Lógica de Negócio --- */

        // 1. Dados Iniciais Simulados (Mock)
        const processedBatchData = {
            id: "LP-201",
            weight: 480.50,
            material: "Plástico PET",
            date: "20/01/2026",
            rawBatchId: "LB-101"
        };

        // 2. Base de Dados de Lotes Brutos para Busca
        const rawBatchesDB = {
            "LB-101": { weight: 500.00, date: "12/01/2026", status: "Processado" },
            "LB-102": { weight: 300.00, date: "15/01/2026", status: "Em Processamento" }
        };

        // 3. Detalhes das Etapas (Para o Modal)
        const stageDetails = {
            "Triagem": {
                desc: "Separação manual de materiais contaminantes.",
                responsable: "Carlos Silva",
                notes: "Lote com 98% de pureza inicial."
            },
            "Lavagem": {
                desc: "Remoção de resíduos químicos e orgânicos com água quente.",
                responsable: "Ana Sousa",
                notes: "Temperatura da água a 80ºC."
            },
            "Secagem": {
                desc: "Remoção de humidade em centrífuga industrial.",
                responsable: "Roberto Dias",
                notes: "Processo iniciado às 08:00."
            }
        };

        // Elementos DOM
        const rawBatchInput = document.getElementById('rawBatchId');
        const rawEntryWeight = document.getElementById('rawEntryWeight');
        const rawEntryDate = document.getElementById('rawEntryDate');
        const rawStatus = document.getElementById('rawStatus');
        const rawError = document.getElementById('rawBatchError');
        const modal = document.getElementById('stageModal');

        // Inicialização
        window.onload = function() {
            // Preencher Formulário Principal
            document.getElementById('currentWeight').value = processedBatchData.weight;
            document.getElementById('materialType').value = processedBatchData.material;
            document.getElementById('creationDate').value = processedBatchData.date;
            
            // Preencher ID do Lote Bruto e buscar dados
            rawBatchInput.value = processedBatchData.rawBatchId;
            fetchRawBatch(); // Trigger inicial para preencher os readonly
        };

        // --- Função de Busca de Lote Bruto ---
        function fetchRawBatch() {
            const id = rawBatchInput.value.trim().toUpperCase();

            if (rawBatchesDB[id]) {
                // Sucesso
                const data = rawBatchesDB[id];
                rawEntryWeight.value = data.weight.toFixed(2);
                rawEntryDate.value = data.date;
                rawStatus.value = data.status;
                
                // Estilo Sucesso
                rawError.style.display = 'none';
                rawBatchInput.style.borderColor = 'var(--border-color)';
            } else {
                // Erro: Não encontrado
                if (id.length > 0) {
                    rawError.style.display = 'block';
                    rawBatchInput.style.borderColor = 'var(--danger-color)';
                }
                // Limpar campos
                rawEntryWeight.value = "";
                rawEntryDate.value = "";
                rawStatus.value = "";
            }
        }

        // --- Lógica do Modal ---
        function openStageModal(category) {
            const contentDiv = document.getElementById('modalBodyContent');
            const info = stageDetails[category];

            if (info) {
                contentDiv.innerHTML = `
                    <p><strong>Categoria:</strong> ${category}</p>
                    <p><strong>Descrição:</strong> ${info.desc}</p>
                    <p><strong>Responsável:</strong> ${info.responsable}</p>
                    <p><strong>Notas:</strong> ${info.notes}</p>
                `;
            } else {
                contentDiv.innerHTML = `<p>Informações detalhadas não disponíveis para esta etapa.</p>`;
            }

            modal.style.display = 'flex';
        }

        function closeModal() {
            modal.style.display = 'none';
        }

        // Fechar ao clicar fora
        window.onclick = function(event) {
            if (event.target == modal) {
                closeModal();
            }
        }

        // --- Ações de Botões ---
        function saveChanges(event) {
            event.preventDefault();
            alert("Alterações no Lote Processado foram guardadas com sucesso.");
        }

        function deleteProcessedBatch() {
            if(confirm("Tem a certeza que deseja excluir este registo?\nEsta ação é irreversível.")) {
                alert("Lote Processado excluído.");
                window.location.href = './lotes_processados.html';
            }
        }

    </script>
</body>
</html>