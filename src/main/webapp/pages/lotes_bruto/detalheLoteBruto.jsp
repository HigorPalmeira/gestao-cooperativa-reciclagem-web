<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Lote Bruto ${String.format("#LB-%03d",loteBruto.id)}</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

	<c:set var="isProcessado" value="${loteBruto.status == 'PROCESSADO'}" />
	
	<c:set var="temPagamentoPago" value="false" />
	<c:forEach items="${listaTransacoesCompra}" var="transacao">
		<c:if test="${transacao.status == 'PAGO'}">
			<c:set var="temPagamentoPago" value="true" />
		</c:if>
	</c:forEach>
	
	<c:set var="bloquearFornecedor" value="${isProcessado or temPagamentoPago}" />
	
    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem &rsaquo; Lote Bruto ${String.format("#LB-%03d",loteBruto.id)}</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Voltar para Gestão</a>
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
    	
    	<c:if test="${isProcessado}">
    		<div class="info-msg" style="display: block; background-color: #fff3cd; color: #856404; border: 1px solid #ffeeba;">
    			<strong>Aviso:</strong> Este lote está "Processado", a edição está restrita.
    		</div>
    	</c:if>
        
		
		<form id="mainForm" action="${pageContext.request.contextPath}/AtualizarLoteBruto" method="POST">
		
			<input type="hidden" name="id" id="id" value="${loteBruto.id}">
			
        	<h2>Dados do Lote</h2>
			<section class="card">
				
				<div class="form-grid">
				
					<div class="form-group">
						<label>ID</label>
						<input type="text" value="${String.format('#LB-%03d', loteBruto.id)}" readonly>
					</div>
					
					<div class="form-group">
						<label>Data de Entrada</label>
						<input type="text" value="${loteBruto.dtEntrada}" readonly>
					</div>
					
					<div class="form-group">
						<label for="entryWeight">Peso de Entrada (Kg)</label>
						<input type="number" id="entryWeight" name="entryWeight" step="0.01"
								value="${loteBruto.pesoEntradaKg}"
								${isProcessado ? 'readonly' : ''}>
					</div>
					
					<div class="form-group">
						<label for="batchStatus">Status</label>
						<c:choose>
							<c:when test="${isProcessado}">
								<input type="text" value="Processado" readonly class="form-control-plaintext">
								<input type="hidden" name="batchStatus" value="PROCESSADO">
							</c:when>
							<c:otherwise>
								<select id="batchStatus" name="batchStatus">
									<option value="Recebido" ${loteBruto.status == 'RECEBIDO' ? 'selected' : ''}>Recebido</option>
									<option value="Em Triagem" ${loteBruto.status == 'EM_TRIAGEM' ? 'selected' : ''}>Em Triagem</option>
									<option value="Processado" ${loteBruto.status == 'PROCESSADO' ? 'selected' : ''}>Processado</option>
								</select>
							</c:otherwise>
						</c:choose>
					</div>
				
				</div>
				
			</section>
			
			<h2>Fornecedor Associado</h2>
			<section class="card">
			
				<c:if test="${bloquearFornecedor}">
					<div class="error-msg" style="display: block; margin-bottom: 10px;">
						A edição do fornecedor está bloqueada (Lote processado ou pagamentos realizados).
					</div>
				</c:if>
				
				<div class="form-grid">
				
					<div class="form-group">
						<label for="supplierDoc">Documento (CPF/CNPJ)</label>
						
						<div style="display: flex; gap: 10px;">
							
							<input type="text" id="supplierDoc" name="supplierDoc"
									value="${loteBruto.fornecedor.documento}"
									${bloquearFornecedor ? 'readonly' : ''}>
									
							<c:if test="${not bloquearFornecedor}">
								<button type="submit"
										formaction="${pageContext.request.contextPath}/BuscarFornecedorLote" 
										formmethod="GET"
										class="btn-search"
										title="Buscar Nome do Fornecedor">
									?
								</button>
							</c:if>
						</div>
					</div>
					
					<div class="form-group">
						<label for="supplierName">Nome do Fornecedor</label>
						<input type="text" id="supplierName" name="supplierName" readonly
								value="${loteBruto.fornecedor.nome}">
					</div>
				
				</div>
				
				<div style="margin-top: 20px;">
					<button type="submit" class="btn-save" ${isProcessado ? 'disabled' : ''}>
						Salvar Alterações
					</button>
				</div>
			
			</section>
		
		</form>


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
                    
                    <c:forEach items="${listaLotesProcessados}" var="loteProcessado" varStatus="status">
                    
                    	<c:set var="etapaProcessamento" value="${listaEtapasProcessamento[status.index]}"/>
                    
                    	<tr>
                    		<td><a href="${pageContext.request.contextPath}/DetalharLoteProcessado?id=${loteProcessado.id}" class="table-link">${String.format("#LP-%03d", loteProcessado.id)}</a></td>
                    		<td>${String.format("%.2f", loteProcessado.pesoAtualKg)}</td>
                    		<td>${loteProcessado.tipoMaterial.nome}</td>
                    		<td><span class="badge badge-info">${etapaProcessamento.categoriaProcessamento.nome}</span></td>
                    	</tr>
                    </c:forEach>

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
                    
                    <c:forEach items="${listaTransacoesCompra}" var="transacaoCompra">
                    	<tr>
                    		<td><a href="${pageContext.request.contextPath}/DetalharTransacaoCompra?id=${transacaoCompra.id}" class="table-link">${String.format("#TR-%03d", transacaoCompra.id)}</a></td>
                    		<td>${not empty transacaoCompra.dtPagamento ? transacaoCompra.dtPagamento : '---'}</td>
                    		<td>${String.format("R$ %.2f", transacaoCompra.valorTotalCalculado)}</td>
                    		<td><span class="badge ${transacaoCompra.status == 'PAGO' ? 'badge-success' : 'badge-warning'}">${transacaoCompra.status}</span></td>
                    	</tr>
                    </c:forEach>

                </tbody>
            </table>
        </section>

        <!-- 5. Excluir (Rodapé) -->
        <div class="danger-zone">
        
        	<form action="${pageContext.request.contextPath}/DeletarLoteBruto" method="POST" onsubmit="return confirmDelete()">
        	
        		<input type="hidden" name="id" value="${loteBruto.id}">
        		<span style="color: #666; margin-right: 15px;">Ação irreversível:</span>
        		<button type="submit" class="btn-delete" ${isProcessado ? 'disabled' : ''}>Excluir Lote Bruto</button>
        	
        	</form>
            
        </div>

    </main>

    <script>
    
    	function confirmDelete() {
    		return confirm("Tem certeza que deseja excluir este Lote Bruto e todos os vínculos?\nEsta ação não pode ser desfeita.");
    	}
    	
    	const statusSelect = document.getElementById('batchStatus');
    	if (statusSelect) {
    		statusSelect.addEventListener('change', function() {
    			if (this.value === 'PROCESSADO') {
    				alert("ATENÇÃO: Ao salvar como 'Processado', o lote será fechado para edições.");
    			}
    		});
    	}
        
    </script>
</body>
</html>