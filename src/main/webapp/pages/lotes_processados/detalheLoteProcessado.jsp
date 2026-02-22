<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Lote Processado ${String.format("#LP-%03d", loteProcessado.id)}</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System &rsaquo; Lote Processado ${String.format("#LP-%03d", loteProcessado.id)}</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarLotesProcessados">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- SEÇÃO 1: Edição do Lote Processado -->
        <h1>Dados do Lote Processado</h1>
        <section class="card">
            <form id="processedForm" onsubmit="saveChanges(event)">
            
            	<input type="hidden" id="id" name="id" value="${loteProcessado.id}">
            	
                <div class="form-grid">
                    <div class="form-group">
                        <label for="currentWeight">Peso Atual (Kg) *</label>
                        <!-- Habilitado para edição -->
                        <input type="number" id="currentWeight" name="currentWeight" step="0.01"
                        	value="${loteProcessado.pesoAtualKg}">
                    </div>
                    <div class="form-group">
                        <label for="materialType">Tipo de Material *</label>
                        <!-- Habilitado para edição -->
                        <select id="materialType" name="materialType">
                        	<c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                        		<option value="${tipoMaterial.id}" ${tipoMaterial.id == loteProcessado.tipoMaterial.id ? 'selected' : ''}>${tipoMaterial.nome}</option>
                        	</c:forEach>
                            
                            <!-- 
                            <option value="Plástico PET">Plástico PET</option>
                            <option value="Plástico HDPE">Plástico HDPE</option>
                            <option value="Alumínio">Alumínio</option>
                            <option value="Papelão">Papelão</option>
                             -->
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="creationDate">Data de Criação</label>
                        <!-- Readonly (não editável) -->
                        <input type="text" id="creationDate" name="creationDate" readonly
                        	value="${loteProcessado.dtCriacao}">
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
                    <input type="text" id="rawBatchId" name="rawBatchId" onblur="fetchRawBatch()" placeholder="Ex: LB-101"
                    	value="${loteProcessado.loteBruto.id}">
                    <span id="rawBatchError" class="error-msg">Lote bruto não encontrado.</span>
                </div>
                <div class="form-group">
                    <label for="rawEntryWeight">Peso de Entrada (Kg)</label>
                    <input type="text" id="rawEntryWeight" name="rawEntryWeight" readonly
                    	value="${loteProcessado.loteBruto.pesoEntradaKg}">
                </div>
                <div class="form-group">
                    <label for="rawEntryDate">Data de Entrada</label>
                    <input type="text" id="rawEntryDate" name="rawEntryDate" readonly
                    	value="${loteProcessado.loteBruto.dtEntrada}">
                </div>
                <div class="form-group">
                    <label for="rawStatus">Status</label>
                    <input type="text" id="rawStatus" name="rawStatus" readonly
                    	value="${loteProcessado.loteBruto.status.descricao}">
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
                    
                    <c:forEach items="${listaEtapasProcessamento}" var="etapaProcessamento">
                    	<tr>
                    		<td>
                    			<span class="category-link" onclick="openStageModal({name: '${etapaProcessamento.categoriaProcessamento.nome}', desc: '${etapaProcessamento.categoriaProcessamento.descricao}', status: '${etapaProcessamento.status}'})">${etapaProcessamento.categoriaProcessamento.nome}</span>
                    		</td>
                    		<td>${etapaProcessamento.dtProcessamento}</td>
                    		<td>
                    			<span class="badge ">${etapaProcessamento.status}</span>
                    			<!-- badge-success & badge-warning -->
                    		</td>
                    	</tr>
                    </c:forEach>
                    
                    <!-- 
                    <tr>
                        <td>
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
                     -->
                     
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
        function openStageModal(category = null) {
            const contentDiv = document.getElementById('modalBodyContent');

            if (category) {
                contentDiv.innerHTML = `
                    <p><strong>Categoria:</strong> \${category.name}</p>
                    <p><strong>Descrição:</strong> \${category.desc}</p>
                    <p><strong>Status:</strong> \${category.status}</p>
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
                window.location.href = 'ListarLotesProcessados';
            }
        }

    </script>
</body>
</html>