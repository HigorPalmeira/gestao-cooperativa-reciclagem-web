<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Lote Processado</title>
    
    <link rel="stylesheet" href="../../assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Consistente com o ERP) --- */
        :root {
            --primary-color: #0056b3;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --danger-color: #dc3545;
            --disabled-color: #95a5a6;
            --text-color: #333;
            --overlay-color: rgba(0, 0, 0, 0.5);
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
            align-items: center;
        }
        
        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; margin-right: 20px; }
        nav.main-nav a { 
            color: #fff; 
            text-decoration: none; 
            font-size: 0.9rem; 
            opacity: 0.8; 
            cursor: pointer;
        }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Centralizado */
        .container {
            max-width: 600px;
            margin: 3rem auto;
            padding: 0 1rem;
        }

        /* Cartão do Formulário */
        .form-card {
            background-color: var(--white);
            padding: 2.5rem;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }

        .form-header {
            margin-bottom: 2rem;
            text-align: center;
            border-bottom: 1px solid #eee;
            padding-bottom: 1rem;
        }

        h1 { margin: 0; font-size: 1.5rem; color: #2c3e50; }
        p.subtitle { margin: 5px 0 0; color: #666; font-size: 0.9rem; }

        /* Estilos dos Campos */
        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #444;
        }
        
        .form-group label span.required { color: #e74c3c; }

        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            box-sizing: border-box; 
            transition: border-color 0.2s;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: var(--primary-color);
        }

        /* Botão Cadastrar */
        .form-actions {
            margin-top: 2rem;
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        .btn-submit {
            background-color: var(--success-color);
            color: white;
            padding: 15px;
            border: none;
            border-radius: 4px;
            font-size: 1.1rem;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }

        .btn-submit:hover:not(:disabled) {
            background-color: #218838;
        }

        .btn-submit:disabled {
            background-color: var(--disabled-color);
            cursor: not-allowed;
            opacity: 0.7;
        }

        .btn-back {
            text-align: center;
            color: #666;
            text-decoration: none;
            font-size: 0.9rem;
            display: block;
            cursor: pointer;
        }
        .btn-back:hover { text-decoration: underline; color: var(--primary-color); }

        /* --- Estilos do Pop-up (Modal) --- */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: var(--overlay-color);
            display: none; /* Oculto por padrão */
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
            max-width: 400px;
            text-align: left;
            border-left: 5px solid var(--primary-color);
        }

        .modal-content h3 {
            margin-top: 0;
            color: var(--primary-color);
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }

        .info-row {
            margin-bottom: 10px;
            font-size: 0.95rem;
        }
        .info-label { font-weight: bold; color: #555; }

        .btn-close-modal {
            background-color: #6c757d;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 15px;
            float: right;
        }
        .btn-close-modal:hover { background-color: #5a6268; }

        /* Mensagem de Erro Inline */
        .error-msg {
            color: var(--danger-color);
            font-size: 0.85rem;
            margin-top: 5px;
            display: none;
        }

    </style>
     -->
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Novo Lote Processado</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Registo de Processamento</h1>
                <p class="subtitle">Crie um novo lote processado a partir de um lote bruto.</p>
            </div>

            <!-- Formulário -->
            <form id="createProcessedBatchForm" onsubmit="handleRegister(event)">
                
                <!-- 1. Lote Bruto (Com Datalist para Sugestões) -->
                <div class="form-group">
                    <label for="rawBatchId">Lote Bruto (ID) <span class="required">*</span></label>
                    <input type="text" id="rawBatchId" list="rawBatchesList" placeholder="Ex: LB-101" onblur="fetchRawBatchInfo()">
                    <!-- Lista de Sugestões -->
                    <datalist id="rawBatchesList">
                        <option value="LB-101">
                        <option value="LB-102">
                        <option value="LB-103">
                    </datalist>
                    <span id="batchError" class="error-msg">Lote bruto não encontrado.</span>
                </div>

                <!-- 2. Tipo de Material -->
                <div class="form-group">
                    <label for="materialType">Tipo de Material <span class="required">*</span></label>
                    <select id="materialType">
                        <option value="">Selecione...</option>
                        <option value="Plástico PET">Plástico PET</option>
                        <option value="Plástico HDPE">Plástico HDPE</option>
                        <option value="Alumínio">Alumínio</option>
                        <option value="Cobre">Cobre</option>
                        <option value="Papelão">Papelão</option>
                    </select>
                </div>

                <!-- 3. Etapa de Processamento -->
                <div class="form-group">
                    <label for="processStage">Etapa de Processamento <span class="required">*</span></label>
                    <select id="processStage">
                        <option value="">Selecione...</option>
                        <option value="Triagem">Triagem</option>
                        <option value="Trituração">Trituração</option>
                        <option value="Lavagem">Lavagem</option>
                        <option value="Extrusão">Extrusão</option>
                        <option value="Armazenado">Armazenado</option>
                    </select>
                </div>

                <!-- 4. Peso Atual -->
                <div class="form-group">
                    <label for="currentWeight">Peso Atual (Kg) <span class="required">*</span></label>
                    <input type="number" id="currentWeight" placeholder="0.00" step="0.01" min="0.1">
                </div>

                <!-- Botões de Ação -->
                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="../../ListarLotesProcessados" class="btn-back">
                        Cancelar e voltar
                    </a>
                </div>
            </form>
        </section>
    </main>

    <!-- Pop-up (Modal) de Informações do Lote Bruto -->
    <div id="infoModal" class="modal-overlay">
        <div class="modal-content">
            <h3>Detalhes do Lote Bruto</h3>
            
            <div class="info-row">
                <span class="info-label">ID do Lote:</span>
                <span id="modalBatchId">--</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Fornecedor:</span>
                <span id="modalSupplier">--</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Data de Entrada:</span>
                <span id="modalDate">--</span>
            </div>
            
            <div class="info-row">
                <span class="info-label">Peso de Entrada:</span>
                <span id="modalWeight">--</span> Kg
            </div>

            <button class="btn-close-modal" onclick="closeModal()">Fechar</button>
            <div style="clear: both;"></div>
        </div>
    </div>

    <script>
        /* --- JavaScript: Lógica de Negócio --- */

        // 1. Base de Dados Simulada de Lotes Brutos
        const rawBatchesDB = {
            "LB-101": { 
                supplier: "Indústrias Metalurgicas Aço", 
                date: "12/01/2026", 
                weight: 500.00 
            },
            "LB-102": { 
                supplier: "Transportadora Rápida", 
                date: "14/01/2026", 
                weight: 320.50 
            },
            "LB-103": { 
                supplier: "João Coletor", 
                date: "15/01/2026", 
                weight: 150.00 
            }
        };

        // Elementos DOM
        const batchInput = document.getElementById('rawBatchId');
        const materialSelect = document.getElementById('materialType');
        const stageSelect = document.getElementById('processStage');
        const weightInput = document.getElementById('currentWeight');
        const btnSubmit = document.getElementById('btnSubmit');
        const batchError = document.getElementById('batchError');

        // Elementos do Modal
        const modal = document.getElementById('infoModal');
        const modalBatchId = document.getElementById('modalBatchId');
        const modalSupplier = document.getElementById('modalSupplier');
        const modalDate = document.getElementById('modalDate');
        const modalWeight = document.getElementById('modalWeight');

        let isBatchValid = false;

        // 2. Função para buscar Lote Bruto e exibir Pop-up
        function fetchRawBatchInfo() {
            const batchId = batchInput.value.trim().toUpperCase(); // Normaliza input
            
            if (rawBatchesDB[batchId]) {
                // Sucesso: Dados encontrados
                const data = rawBatchesDB[batchId];
                
                // Preenche o modal
                modalBatchId.textContent = batchId;
                modalSupplier.textContent = data.supplier;
                modalDate.textContent = data.date;
                modalWeight.textContent = data.weight.toFixed(2);

                // Exibe o modal
                modal.style.display = 'flex';
                
                // Atualiza estado do campo
                batchError.style.display = 'none';
                batchInput.style.borderColor = 'var(--success-color)';
                isBatchValid = true;

            } else {
                // Erro: Lote não existe
                if (batchId.length > 0) {
                    batchError.style.display = 'block';
                    batchInput.style.borderColor = 'var(--danger-color)';
                } else {
                    // Campo vazio não é erro, é estado neutro
                    batchError.style.display = 'none';
                    batchInput.style.borderColor = 'var(--border-color)';
                }
                isBatchValid = false;
            }
            checkFormValidity();
        }

        // 3. Fechar Modal
        function closeModal() {
            modal.style.display = 'none';
        }

        // Fecha modal se clicar fora do conteúdo
        window.onclick = function(event) {
            if (event.target == modal) {
                closeModal();
            }
        }

        // 4. Validação Geral do Formulário
        function checkFormValidity() {
            const isMaterialSelected = materialSelect.value !== "";
            const isStageSelected = stageSelect.value !== "";
            const weightValue = parseFloat(weightInput.value);
            const isWeightValid = !isNaN(weightValue) && weightValue > 0;

            if (isBatchValid && isMaterialSelected && isStageSelected && isWeightValid) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // Adicionar Listeners para validação em tempo real
        materialSelect.addEventListener('change', checkFormValidity);
        stageSelect.addEventListener('change', checkFormValidity);
        weightInput.addEventListener('input', checkFormValidity);

        // 5. Cadastrar
        function handleRegister(event) {
            event.preventDefault();

            // Simula preenchimento automático da data de criação
            const creationDate = new Date().toLocaleDateString('pt-BR');
            const batchId = batchInput.value.toUpperCase();
            
            alert(`Lote Processado Cadastrado com Sucesso!\n\nOrigem: ${batchId}\nMaterial: ${materialSelect.value}\nEtapa: ${stageSelect.value}\nData de Criação: ${creationDate}`);
            
            // Opcional: Redirecionar
            window.location.href = '../../ListarLotesProcessados';
        }

    </script>
</body>
</html>