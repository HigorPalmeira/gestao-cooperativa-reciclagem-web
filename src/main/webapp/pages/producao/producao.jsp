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
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a>
            <a href="${pageContext.request.contextPath}/ListarLotesProcessados">Lotes Processados</a>
            <a href="#" onclick="alert('Navegar para Relatórios de Operação')">Meus Relatórios</a>
        </div>
    </nav>

    <main class="container">
    
    	<c:if test="${not empty sessionScope.msgSucesso}">
    		<div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #c3e6cb;">
    			${sessionScope.msgSucesso}
    		</div>
    		<% session.removeAttribute("msgSucesso"); %>
    	</c:if>
    	
    	<c:if test="${not empty sessionScope.msgErro}">
    		<div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
    			<strong>Erro:</strong> ${sessionScope.msgErro}
    		</div>
    		<% session.removeAttribute("msgErro"); %>
    	</c:if>
        
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
                        	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                        </c:forEach>
                        
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
                        <option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                    </c:forEach>
                    
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
        

 // /gestao-cooperativa-reciclagem-web
 const contexto = '${pageContext.request.contextPath}';

 const viewOverview = document.getElementById('view-overview');
 const viewKanban = document.getElementById('view-kanban');
 const kanbanBoard = document.getElementById('kanbanBoard');
 const materialFilter = document.getElementById('materialFilter');

 let draggedCardId = null;
 let pendingTransition = null;

 let categorias = [];
 let etapasProcessamento = [];
 let lotesDB = [];

 async function carregarCategorias() {
 	
     await fetch((contexto + '/ListarCategoriasProducao'))
     	.then(response => {
     		if (!response.ok) {
     			throw new Error('Erro no servidor');
     		}
     		
     		return response.json();
     	})
     	.then(data => {
     		categorias = data;
     	})
     	.catch(error => console.error('Erro:', error));
 	
 }

 window.onload = () => {
     carregarCategorias();
 };

 /* NAVEGAÇÃO ENTRE VISITAS */

 function openStartModal() {
     document.getElementById('startModal').style.display = 'flex';
 }

 function closeStartModal() {
     document.getElementById('startModal').style.display = 'none';
 }

 function startProduction() {

     const materialSelect = document.getElementById('initialMaterial').value;
     materialFilter.value = materialSelect;

     closeStartModal();

     viewOverview.style.display = 'none';
     viewKanban.style.display = 'block';

     changeMaterial();

 }

 function exitKanban() {

     if (confirm('Deseja voltar para a visão geral?')) {
         viewKanban.style.display = 'none';
         viewOverview.style.display = 'block';
     }

 }


 function changeMaterial() {

     const material = materialFilter.value;

     // carregar itens relacionados ao material selecionado
     // var etapasProcessamento = [];

     fetch((contexto + '/ListarEtapasProducao?idTipoMaterial=' + material))
         .then(response => {

             if (!response.ok) {
                 throw new Error('Erro no servidor');
             }

             return response.json();

         })
         .then(data => {
             etapasProcessamento = data;
             renderKanban();
         })
         .catch(error => console.error('Erro: ', error));

     // renderKanban(/*etapasProcessamento*/);

 }

 function renderKanban(/*etapas*/) {

     kanbanBoard.innerHTML = '';

     categorias.forEach(categoria => {

         const lotesNaEtapa = etapasProcessamento.filter(etapa =>
             etapa.categoriaProcessamento.id === categoria.id
         );

         const colDiv = document.createElement('div');
         colDiv.className = 'kanban-column';

         colDiv.innerHTML = `
             <div class="kanban-col-header">
                 \${categoria.nome}
                 <span class="kanban-col-count" id="count-\${categoria.nome}">\${lotesNaEtapa.length}</span>
             </div>
             <div class="kanban-dropzone" id="stage-\${categoria.nome}"
                 ondragover="onDragOver(event)"
                 ondragleave="onDragLeave(event)"
                 ondrop="onDrop(event, '\${categoria.id}')">
             </div>
         `

         kanbanBoard.appendChild(colDiv);

         const dropzone = colDiv.querySelector('.kanban-dropzone');

         lotesNaEtapa.forEach(etapa => {

             const card = criarCardHTML(etapa.loteProcessado);
             dropzone.appendChild(card);

         });

     });

 }


 function criarCardHTML(loteProcessado) {

     const card = document.createElement('div');
     card.className = 'kanban-card';
     card.id = `card-\${loteProcessado.id}`;
     card.setAttribute('draggable', 'true');
     card.ondragstart = (event) => onDragStart(event, loteProcessado.id);

     let idString = `\${loteProcessado.id}`.padStart(3, "0");
     
     card.innerHTML = `
         <div class="kanban-card-title">#LP-\${idString}</div>
         <div class="kanban-card-info">
             <span>\${loteProcessado.loteBruto.fornecedor.nome}</span>
             <span class="kanban-card-weight">\${loteProcessado.pesoAtualKg} Kg</span>
         </div>
     `

     return card;

 }


 /* DRAG AND DROP API */

 function onDragStart(event, loteId) {

     draggedCardId = loteId;

     event.dataTransfer.setData('text/plain', loteId);
     setTimeout(() => {
         document.getElementById(`card-\${loteId}`).style.opacity = '0.5';
     }, 0);

 }

 function onDragOver(event) {

     event.preventDefault();
     const dropzone = event.currentTarget;
     dropzone.classList.add('drag-over');

 }

 function onDragLeave(event) {

     const dropzone = event.currentTarget;
     dropzone.classList.remove('drag-over');

 }

 function onDrop(event, targetStageId) {

     event.preventDefault();

     const dropzone = event.currentTarget;
     dropzone.classList.remove('drag-over');

     const cardElement = document.getElementById(`card-\${draggedCardId}`);
     cardElement.style.opacity = '1';

     const etapaLote = etapasProcessamento.find(etapa => etapa.loteProcessado.id === draggedCardId);

     if (etapaLote.categoriaProcessamento.id == targetStageId) return

     pendingTransition = {
         loteId: etapaLote.loteProcessado.id,
         pesoAtual: etapaLote.loteProcessado.pesoAtualKg,
         idCategoriaOrigem: etapaLote.categoriaProcessamento.id,
         idCategoriaDestino: targetStageId
     };

     abrirModalTransicao();

 }


 /* LÓGICA DO MODAL DE TRANSIÇÃO */

 function abrirModalTransicao() {

     document.getElementById('transLoteId').textContent = pendingTransition.loteId;
     document.getElementById('transTargetStage').textContent = categorias.find(cat => cat.id === parseInt(pendingTransition.idCategoriaDestino, 10)).nome;
     document.getElementById('newWeight').value = pendingTransition.pesoAtual;
     document.getElementById('operatorNotes').value = 'RETIRAR ISSO';
     document.getElementById('transitionModal').style.display = 'flex';

 }

 function cancelTransition() {

     document.getElementById('transitionModal').style.display = 'none';
     pendingTransition = null;

 }

 function confirmTransition(event) {

     event.preventDefault();

     const novoPeso = parseFloat(document.getElementById('newWeight').value);

     fetch((contexto + '/AtualizarEtapaProcessamento'), {
         method: 'POST',
         headers: {
        	 'Content-Type': 'application/json' 
         },
         body: JSON.stringify({
             loteProcessado: {
                 id: pendingTransition.loteId,
                 pesoAtualKg: novoPeso
             },
             categoriaProcessamento: {
                 id: pendingTransition.idCategoriaDestino
             }
         })
     })
         .then(response => {

             if (!response.ok) {
                 throw new Error('Erro no servidor');
             }
             
             const etapaLote = etapasProcessamento.find(etapa =>
	             etapa.loteProcessado.id === parseInt(pendingTransition.loteId, 10)
	         );
             
             etapaLote.categoriaProcessamento = categorias.find(categoria => categoria.id === parseInt(pendingTransition.idCategoriaDestino, 10));
             etapaLote.loteProcessado.pesoAtualKg = novoPeso;

             document.getElementById(`transitionModal`).style.display = 'none';
             pendingTransition = null;

             alert(`Lote processado salvo na etapa: \${etapaLote.categoriaProcessamento.nome}`);

             renderKanban(materialFilter.value);

         })
         .catch(error => {
        	 console.error('Erro ao salvar etapa: ', error);
        	 alert('Ocorreu um erro ao tentar salvar. Tente novamente.');
         });

 }


    
    </script>
</body>
</html>