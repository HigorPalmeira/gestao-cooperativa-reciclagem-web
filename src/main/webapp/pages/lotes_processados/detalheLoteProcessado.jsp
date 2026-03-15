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
        <div class="brand">ERP Reciclagem &rsaquo; Lote Processado ${String.format("#LP-%03d", loteProcessado.id)}</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarLotesProcessados">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
        
        <c:if test="${not empty msgErro}">
        	<div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
        		<strong>Erro:</strong> ${msgErro}
        	</div>
        </c:if>
        
        <c:if test="${not empty sessionScope.msgSucesso}">
        	<div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #c3e6cb;">
        		${sessionScope.msgSucesso}
        	</div>
        	<% session.removeAttribute("msgSucesso"); %>
        </c:if>
        

		<form id="mainForm" action="${pageContext.request.contextPath}/AtualizarLoteProcessado" method="POST">
		
			<input type="hidden" id="id" name="id" value="${loteProcessado.id}">
			
			<h2>Dados do Lote Processado</h2>
			<section class="card">
				
				<div class="form-grid">
				
					<div class="form-group">
						<label for="currentWeight">Peso Atual (Kg) *</label>
						<input type="number" id="currentWeight" name="currentWeight" step="0.01"
							value="${loteProcessado.pesoAtualKg}">
					</div>
					
					<div class="form-group">
						<label for="materialType">Tipo de Material *</label>
						<input type="text" id="materialType" name="materialType" readonly 
							value="${loteProcessado.tipoMaterial.nome}">
					</div>
					
					<div class="form-group">
                        <label for="creationDate">Data de Criação</label>
                        <input type="text" id="creationDate" name="creationDate" readonly
                        	value="${loteProcessado.dtCriacao}">
                    </div>
				
				</div>

				<div style="margin-top: 20px;">
				
					<button type="submit" class="btn-save">
						Salvar Alterações
					</button>
				
				</div>
				
			</section>
			
			<h2>Origem: Lote Bruto Relacionado</h2>
			<section class="card">
			
				<div class="form-grid">
				
					<div class="form-group">
						<label for="rawBatchId">ID do Lote Bruto *</label>
						
						<div style="display: flex; gap: 10px;">
		                    <input type="text" id="rawBatchId" name="rawBatchId" placeholder="Ex: LB-101"
		                    	value="${String.format('LB-%03d', loteProcessado.loteBruto.id)}" readonly> <!-- onblur="fetchRawBatch()" --> 
						</div>
						
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
		
		</form>        


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
                    			<span class="badge badge-info">${etapaProcessamento.status}</span>
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
        <div class="danger-zone" style="display: none;">
        
        	<form action="${pageContext.request.contextPath}/DeletarLoteProcessado" method="POST" onsubmit="return confirmDelete()">
        	
        		<input type="hidden" name="id" value="${loteProcessado.id}">
        		<span style="color: #666; margin-right: 15px;">Ação irreversível:</span>
        		<button type="submit" class="btn-delete">Excluir Lote Processado</button>
        	
        	</form>
        
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
        
        // Elementos DOM
        const rawBatchInput = document.getElementById('rawBatchId');
        const rawEntryWeight = document.getElementById('rawEntryWeight');
        const rawEntryDate = document.getElementById('rawEntryDate');
        const rawStatus = document.getElementById('rawStatus');
        const rawError = document.getElementById('rawBatchError');
        const modal = document.getElementById('stageModal');

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

        function confirmDelete() {
        	return confirm("Tem certeza que deseja excluir este Lote Processado e todos os vínculos?\nEsta ação não pode ser desfeita.");
        }

    </script>
</body>
</html>