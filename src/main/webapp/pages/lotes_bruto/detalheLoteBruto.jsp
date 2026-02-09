<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Lote Bruto #LB-101</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System &rsaquo; Lote Bruto #LB-101</div>
        <div>
            <a href="ListarLotesBrutos">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Aviso de Bloqueio (Dinâmico) -->
        <div id="lockMessage" class="info-msg" style="display: none;">
            <strong>Aviso:</strong> Este lote está "Processado", portanto a edição está bloqueada.
        </div>

        <!-- 1. Formulário de Edição do Lote -->
        <h2>Dados do Lote</h2>
        <section class="card">
            <form id="batchForm" onsubmit="saveBatch(event)">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="batchId">ID</label>
                        <input type="text" id="batchId" value="#LB-101" readonly>
                    </div>
                    <div class="form-group">
                        <label for="entryDate">Data de Entrada</label>
                        <input type="text" id="entryDate" readonly> <!-- Somente leitura conforme regra -->
                    </div>
                    <div class="form-group">
                        <label for="entryWeight">Peso de Entrada (Kg)</label>
                        <input type="number" id="entryWeight" step="0.01">
                    </div>
                    <div class="form-group">
                        <label for="batchStatus">Status</label>
                        <select id="batchStatus" onchange="checkStatusLock()">
                            <option value="Recebido">Recebido</option>
                            <option value="Em Processamento">Em Processamento</option>
                            <option value="Processado">Processado</option>
                        </select>
                    </div>
                </div>
                <!-- Botão Salvar -->
                <div>
                    <button type="submit" id="btnSave" class="btn-save">Salvar Alterações</button>
                </div>
            </form>
        </section>

        <!-- 2. Formulário do Fornecedor -->
        <h2>Fornecedor Associado</h2>
        <section class="card">
            <div id="supplierLockMsg" class="error-msg" style="display: none; margin-bottom: 10px;">
                A edição do fornecedor está bloqueada pois existem pagamentos confirmados ("Pago").
            </div>
            <div class="form-grid">
                <div class="form-group">
                    <label for="supplierDoc">Documento do Fornecedor (CPF/CNPJ)</label>
                    <input type="text" id="supplierDoc" onblur="fetchSupplier()">
                    <span id="supplierError" class="error-msg">Erro: Fornecedor não encontrado.</span>
                </div>
                <div class="form-group">
                    <label for="supplierName">Nome do Fornecedor</label>
                    <input type="text" id="supplierName" readonly>
                </div>
            </div>
        </section>

        <!-- 3. Tabela de Lotes Processados -->
        <h2>Lotes Processados Derivados</h2>
        <section>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Peso Atual (Kg)</th>
                        <th>Tipo de Material</th>
                        <th>Etapa de Processamento</th>
                    </tr>
                </thead>
                <tbody id="processedTableBody">
                    <!-- Preenchido via JS -->
                </tbody>
            </table>
        </section>

        <!-- 4. Tabela de Transações de Compra -->
        <h2>Transações de Compra</h2>
        <section>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Data de Pagamento</th>
                        <th>Valor Total (R$)</th>
                        <th>Status de Pagamento</th>
                    </tr>
                </thead>
                <tbody id="transactionsTableBody">
                    <!-- Preenchido via JS -->
                </tbody>
            </table>
        </section>

        <!-- 5. Excluir (Rodapé) -->
        <div class="danger-zone">
            <span style="color: #666; font-size: 0.9rem; margin-right: 15px;">Ação irreversível:</span>
            <button id="btnDelete" class="btn-delete" onclick="deleteBatch()">Excluir Lote Bruto</button>
        </div>

    </main>

    <script>
        /* --- Lógica de Negócio e Dados Mockados --- */

        // 1. Dados do Lote (Mock Data)
        // Mude o status para 'Processado' para testar o bloqueio total
        const batchData = {
            id: "LB-101",
            entryDate: "12/01/2026",
            weight: 500.00,
            status: "Em Processamento", // Opções: 'Recebido', 'Em Processamento', 'Processado'
            supplier: {
                doc: "55123456000100",
                name: "Indústrias Metalurgicas Aço"
            }
        };

        // 2. Dados Relacionados (Tabelas)
        const processedBatches = [
            { id: "LP-201", weight: 200.00, type: "Alumínio", stage: "Triagem" },
            { id: "LP-202", weight: 280.00, type: "Plástico Misto", stage: "Lavagem" }
        ];

        // Mude o status para 'Pago' para testar o bloqueio do fornecedor
        const transactions = [
            { id: "TR-555", date: "15/01/2026", value: 1500.00, status: "Pendente" }, 
            { id: "TR-556", date: "-", value: 500.00, status: "Agendado" }
        ];

        // 3. Base de Fornecedores para Busca
        const suppliersDB = {
            "55123456000100": "Indústrias Metalurgicas Aço",
            "99888777000122": "Transportadora Rápida"
        };

        // Elementos DOM
        const weightInput = document.getElementById('entryWeight');
        const statusSelect = document.getElementById('batchStatus');
        const supplierDocInput = document.getElementById('supplierDoc');
        const supplierNameInput = document.getElementById('supplierName');
        const btnSave = document.getElementById('btnSave');
        const btnDelete = document.getElementById('btnDelete');

        // Inicialização
        window.onload = function() {
            loadBatchData();
            renderTables();
            applyBusinessRules(); // Aplica bloqueios
        };

        function loadBatchData() {
            document.getElementById('entryDate').value = batchData.entryDate;
            weightInput.value = batchData.weight;
            statusSelect.value = batchData.status;
            supplierDocInput.value = batchData.supplier.doc;
            supplierNameInput.value = batchData.supplier.name;
        }

        // --- Regras de Negócio e Bloqueios ---

        function applyBusinessRules() {
            const isProcessed = statusSelect.value === 'Processado';
            
            // REGRA 1: Se Status = 'Processado', nada pode ser alterado no Lote.
            if (isProcessed) {
                document.getElementById('lockMessage').style.display = 'block';
                weightInput.disabled = true;
                statusSelect.disabled = true;
                btnSave.disabled = true;
                btnDelete.disabled = true; // Geralmente não se exclui lotes já processados
            } else {
                document.getElementById('lockMessage').style.display = 'none';
                weightInput.disabled = false;
                statusSelect.disabled = false;
                btnSave.disabled = false;
                btnDelete.disabled = false;
            }

            // REGRA 2: Bloqueio do Fornecedor se houver pagamento 'Pago'
            // Verifica se existe ALGUMA transação com status 'Pago'
            const hasPaidTransaction = transactions.some(t => t.status === 'Pago');

            if (hasPaidTransaction || isProcessed) {
                // Bloqueia edição do documento
                supplierDocInput.disabled = true;
                if(hasPaidTransaction) {
                    document.getElementById('supplierLockMsg').style.display = 'block';
                }
            } else {
                supplierDocInput.disabled = false;
                document.getElementById('supplierLockMsg').style.display = 'none';
            }
        }

        // Monitora mudança de status em tempo real para avisar o utilizador
        function checkStatusLock() {
            if (statusSelect.value === 'Processado') {
                alert("Atenção: Ao mudar para 'Processado' e salvar, o registo ficará bloqueado para futuras edições.");
            }
        }

        // --- Busca de Fornecedor ---

        function fetchSupplier() {
            const doc = supplierDocInput.value.replace(/\D/g, "");
            const errorSpan = document.getElementById('supplierError');

            if (suppliersDB[doc]) {
                supplierNameInput.value = suppliersDB[doc];
                errorSpan.style.display = 'none';
                supplierDocInput.style.borderColor = 'var(--border-color)';
            } else {
                if (doc.length > 0) {
                    errorSpan.style.display = 'block';
                    supplierDocInput.style.borderColor = 'var(--danger-color)';
                    supplierNameInput.value = ""; // Limpa nome se não achar
                }
            }
        }

        // --- Renderização de Tabelas ---

        function renderTables() {
            // Tabela Processados
            const procBody = document.getElementById('processedTableBody');
            processedBatches.forEach(item => {
                procBody.innerHTML += `
                    <tr>
                        <td><a href="DetalharLoteProcessado?id=${item.id}" class="table-link">#${item.id}</a></td>
                        <td>${item.weight.toFixed(2)}</td>
                        <td>${item.type}</td>
                        <td><span class="badge badge-info">${item.stage}</span></td>
                    </tr>
                `;
            });

            // Tabela Transações
            const transBody = document.getElementById('transactionsTableBody');
            transactions.forEach(item => {
                let badgeClass = item.status === 'Pago' ? 'badge-success' : 'badge-warning';
                
                transBody.innerHTML += `
                    <tr>
                        <td><a href="DetalharTransacaoCompra?id=${item.id}" class="table-link">#${item.id}</a></td>
                        <td>${item.date}</td>
                        <td>R$ ${item.value.toFixed(2)}</td>
                        <td><span class="badge ${badgeClass}">${item.status}</span></td>
                    </tr>
                `;
            });
        }

        // --- Ações de Botões ---

        function saveBatch(event) {
            event.preventDefault();
            alert("Alterações guardadas com sucesso na base de dados.");
            // Recarregar regras caso o status tenha mudado para Processado
            applyBusinessRules();
        }

        function deleteBatch() {
            if (confirm("Tem a certeza que deseja excluir este Lote Bruto e todos os vínculos?\nEsta ação não pode ser desfeita.")) {
                alert("Lote excluído.");
                window.location.href = 'ListarLotesBrutos';
            }
        }

    </script>
</body>
</html>