<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-PT">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Produção - ERP Cooperativa</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System &rsaquo; Área de Produção</div>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="#" onclick="alert('Navegar para Relatórios de Operação')">Meus Relatórios</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- VISÃO 1: VISÃO GERAL (Abre por padrão) -->
        <div id="view-overview">
            <div class="page-header">
                <h1>Visão Geral da Produção</h1>
            </div>

            <div class="card" style="text-align: center; padding: 3rem 1rem;">
                <h2 style="border: none; margin-bottom: 10px;">Bem-vindo à Linha de Processamento</h2>
                <p style="color: var(--text-muted); margin-bottom: 2rem;">
                    Existem lotes aguardando movimentação no pátio. Selecione um material para iniciar o trabalho.
                </p>
                <button class="btn-submit" onclick="openStartModal()" style="font-size: 1.2rem; padding: 15px 30px;">
                    ▶ Iniciar Processamento
                </button>
            </div>
        </div>

        <!-- VISÃO 2: QUADRO KANBAN (Oculto até iniciar) -->
        <div id="view-kanban">
            <div class="page-header" style="flex-wrap: wrap; gap: 15px;">
                <h1>Quadro de Processamento</h1>
                
                <!-- O Select que troca dinamicamente a tela -->
                <div style="display: flex; align-items: center; gap: 10px;">
                    <label for="materialFilter" style="font-weight: bold;">Material Ativo:</label>
                    <select id="materialFilter" onchange="changeMaterial()" style="width: auto; padding: 8px;">
                        <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                        	<option value="${tipoMaterial.nome}">${tipoMaterial.nome}</option>
                        </c:forEach>
                        
                        <!-- 
                        <option value="Plástico PET">Plástico PET</option>
                        <option value="Alumínio">Alumínio</option>
                        <option value="Papelão">Papelão</option>
                         -->
                    </select>
                    <button class="btn-delete" onclick="exitKanban()" style="padding: 8px 15px;">Encerrar Sessão</button>
                </div>
            </div>

            <!-- O Quadro -->
            <div class="kanban-board" id="kanbanBoard">
                <!-- As colunas (Categorias de Processamento) serão injetadas aqui pelo JavaScript -->
            </div>
        </div>

    </main>

    <!-- MODAL 1: ESCOLHER MATERIAL PARA INICIAR -->
    <div id="startModal" class="modal-overlay">
        <div class="modal-content" style="max-width: 400px;">
            <div class="modal-header">
                <h2>Iniciar Trabalho</h2>
                <span class="close-icon" onclick="closeStartModal()">&times;</span>
            </div>
            <div class="form-group">
                <label for="initialMaterial">Qual material vai processar agora?</label>
                <select id="initialMaterial">
                	<c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                        <option value="${tipoMaterial.nome}">${tipoMaterial.nome}</option>
                    </c:forEach>
                    
                    <!-- 
                    <option value="Plástico PET">Plástico PET</option>
                    <option value="Alumínio">Alumínio</option>
                    <option value="Papelão">Papelão</option>
                     -->
                </select>
            </div>
            <div class="modal-footer">
                <button class="btn-cancel" onclick="closeStartModal()">Cancelar</button>
                <button class="btn-save" onclick="startProduction()">Avançar</button>
            </div>
        </div>
    </div>

    <!-- MODAL 2: CONFIRMAR TRANSIÇÃO (Disparado no Drop) -->
    <div id="transitionModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Registrar Etapa</h2>
                <span class="close-icon" onclick="cancelTransition()">&times;</span>
            </div>
            <div style="margin-bottom: 1.5rem;">
                <p>A mover o Lote <strong id="transLoteId" class="id-link"></strong> para a etapa <strong id="transTargetStage"></strong>.</p>
                <p style="font-size: 0.9rem; color: #666;">Por favor, atualize as informações de processamento antes de prosseguir.</p>
            </div>

            <form id="transitionForm" onsubmit="confirmTransition(event)">
                <div class="form-group">
                    <label for="newWeight">Novo Peso Atual (Kg) *</label>
                    <input type="number" id="newWeight" step="0.01" required placeholder="Peso após perdas/resíduos">
                </div>
                <div class="form-group">
                    <label for="operatorNotes">Observações do Operador</label>
                    <textarea id="operatorNotes" rows="3" placeholder="Houve quebra? Excesso de sujidade?"></textarea>
                </div>
                
                <div class="modal-footer">
                    <button type="button" class="btn-delete" onclick="cancelTransition()">Cancelar Movimento</button>
                    <button type="submit" class="btn-save">Salvar Etapa</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        /* =========================================================
           LÓGICA DO QUADRO KANBAN E MUDANÇA DE ESTADO
           ========================================================= */

        const contexto = "${pageContext.request.contextPath}";
           
        // 1. Variáveis de Estado
        const viewOverview = document.getElementById('view-overview');
        const viewKanban = document.getElementById('view-kanban');
        const kanbanBoard = document.getElementById('kanbanBoard');
        const materialFilter = document.getElementById('materialFilter');
        
        let draggedCardId = null; 
        let pendingTransition = null; // Guarda os dados temporários do Drag and Drop

        // 2. Mock de Dados (Simulando o que viria do Jakarta EE via fetch)
        
        // Categorias de Processamento (Serão as Colunas)
        const categorias = [
            { id: 1, nome: "Triagem" },
            { id: 2, nome: "Lavagem" },
            { id: 3, nome: "Trituração" },
            { id: 4, nome: "Prensagem" }
        ];

        // Lotes Processados em andamento
        let lotesDB = [
            { id: "LP-101", material: "Plástico PET", etapaAtual: "Triagem", peso: 500, fornecedor: "Coop A" },
            { id: "LP-102", material: "Plástico PET", etapaAtual: "Lavagem", peso: 480, fornecedor: "Coop B" },
            { id: "LP-105", material: "Plástico PET", etapaAtual: "Lavagem", peso: 200, fornecedor: "Avulso" },
            { id: "LP-201", material: "Alumínio", etapaAtual: "Prensagem", peso: 150, fornecedor: "Coop A" }
        ];

        /* --- NAVEGAÇÃO ENTRE VISTAS --- */

        function openStartModal() {
            document.getElementById('startModal').style.display = 'flex';
        }

        function closeStartModal() {
            document.getElementById('startModal').style.display = 'none';
        }

        function startProduction() {
            const matSelect = document.getElementById('initialMaterial').value;
            materialFilter.value = matSelect; // Sincroniza o select do topo
            
            closeStartModal();
            
            // Troca as Views
            viewOverview.style.display = 'none';
            viewKanban.style.display = 'block';

            // Carrega os dados
            renderKanban(matSelect);
        }

        function exitKanban() {
            if(confirm("Deseja voltar para a visão geral?")) {
                viewKanban.style.display = 'none';
                viewOverview.style.display = 'block';
            }
        }

        function changeMaterial() {
            const mat = materialFilter.value;
            // No mundo real, faria um fetch() aqui pedindo os lotes do material selecionado
            renderKanban(mat);
        }

        /* --- RENDERIZAÇÃO DO KANBAN --- */

        function renderKanban(material) {
            kanbanBoard.innerHTML = ''; // Limpa o quadro

            // Filtra os lotes pelo material selecionado
            const lotesDoMaterial = lotesDB.filter(lote => lote.material === material);

            // Cria cada coluna baseada na tabela Categorias
            categorias.forEach(cat => {
                // Filtra os lotes que estão nesta categoria
                const lotesNestaEtapa = lotesDoMaterial.filter(l => l.etapaAtual === cat.nome);

                const colDiv = document.createElement('div');
                colDiv.className = 'kanban-column';
                
                // HTML da Coluna e da Zona de Drop
                colDiv.innerHTML = `
                    <div class="kanban-col-header">
                        \${cat.nome}
                        <span class="kanban-col-count" id="count-\${cat.nome}">\${lotesNestaEtapa.length}</span>
                    </div>
                    <div class="kanban-dropzone" id="stage-\${cat.nome}" 
                         ondragover="onDragOver(event)" 
                         ondragleave="onDragLeave(event)" 
                         ondrop="onDrop(event, '\${cat.nome}')">
                    </div>
                `;

                kanbanBoard.appendChild(colDiv);

                const dropzone = colDiv.querySelector('.kanban-dropzone');

                // Adiciona os Cards dentro da Dropzone
                lotesNestaEtapa.forEach(lote => {
                    const card = criarCardHTML(lote);
                    dropzone.appendChild(card);
                });
            });
        }

        function criarCardHTML(lote) {
            const card = document.createElement('div');
            card.className = 'kanban-card';
            card.id = `card-\${lote.id}`;
            card.setAttribute('draggable', 'true');
            card.ondragstart = (event) => onDragStart(event, lote.id);

            card.innerHTML = `
                <div class="kanban-card-title">#\${lote.id}</div>
                <div class="kanban-card-info">
                    <span>\${lote.fornecedor}</span>
                    <span class="kanban-card-weight">\${lote.peso} Kg</span>
                </div>
            `;
            return card;
        }

        /* --- DRAG AND DROP API --- */

        function onDragStart(event, loteId) {
            draggedCardId = loteId;
            // Define o que está a ser arrastado (necessário para Firefox)
            event.dataTransfer.setData('text/plain', loteId);
            setTimeout(() => {
                document.getElementById(`card-\${loteId}`).style.opacity = '0.5';
            }, 0);
        }

        function onDragOver(event) {
            event.preventDefault(); // Necessário para permitir o Drop
            const dropzone = event.currentTarget;
            dropzone.classList.add('drag-over'); // Feedback visual
        }

        function onDragLeave(event) {
            const dropzone = event.currentTarget;
            dropzone.classList.remove('drag-over');
        }

        function onDrop(event, targetStageNome) {
            event.preventDefault();
            const dropzone = event.currentTarget;
            dropzone.classList.remove('drag-over');

            // Retorna a opacidade do card
            const cardElement = document.getElementById(`card-\${draggedCardId}`);
            cardElement.style.opacity = '1';

            // Localizar o Lote na "Base de Dados"
            const lote = lotesDB.find(l => l.id === draggedCardId);

            // Se soltou na mesma etapa que já estava, ignora
            if (lote.etapaAtual === targetStageNome) return;

            // Armazena a transação pendente e ABRE O MODAL
            pendingTransition = {
                loteId: lote.id,
                pesoAtual: lote.peso,
                origem: lote.etapaAtual,
                destino: targetStageNome
            };

            abrirModalTransicao();
        }

        /* --- LÓGICA DO MODAL DE TRANSIÇÃO --- */

        function abrirModalTransicao() {
            document.getElementById('transLoteId').textContent = pendingTransition.loteId;
            document.getElementById('transTargetStage').textContent = pendingTransition.destino;
            document.getElementById('newWeight').value = pendingTransition.pesoAtual;
            document.getElementById('operatorNotes').value = "";

            document.getElementById('transitionModal').style.display = 'flex';
        }

        function cancelTransition() {
            document.getElementById('transitionModal').style.display = 'none';
            pendingTransition = null;
            // O card continua visualmente na origem porque não mexemos no DOM ainda.
        }

        function confirmTransition(event) {
            event.preventDefault();
            
            const novoPeso = document.getElementById('newWeight').value;

            /* AQUI ENTRA O FETCH NO MUNDO REAL:
               fetch('/api/lote/etapa', {
                   method: 'POST',
                   body: JSON.stringify({ loteId: pendingTransition.loteId, etapaId: pendingTransition.destino, peso: novoPeso })
               }).then(...)
            */
            
            fetch((contexto + '/AtualizarEtapaProcessamento'), {
            	method: 'POST',
            	body: JSON.stringify({ // simulando um objeto EtapaProcessamento
            		loteProcessado: {
            			id: pendingTransition.loteId.replace("LP-", ""),
            			pesoAtualKg: novoPeso
            		},
            		categoriaProcessamento: {
            			id: pendingTransition.destino
            		}
            	})
            }).then(response => response.ok ? console.log('enviado') : console.log('alguma coisa'));

            // Simulando o Sucesso da Requisição:
            const lote = lotesDB.find(l => l.id === pendingTransition.loteId);
            lote.etapaAtual = pendingTransition.destino;
            lote.peso = novoPeso;

            // Atualiza a interface
            document.getElementById('transitionModal').style.display = 'none';
            pendingTransition = null;
            
            alert(`Lote processado salvo na etapa: ${lote.etapaAtual}`);
            
            // Re-renderiza o quadro para mover o card visualmente e atualizar as contagens
            renderKanban(materialFilter.value);
        }

    </script>
</body>
</html>