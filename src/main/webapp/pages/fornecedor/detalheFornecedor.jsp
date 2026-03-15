<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Fornecedor</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
     
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP Reciclagem &rsaquo; Fornecedor</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarFornecedores">Voltar para Gestão</a>
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a>
            <a href="${pageContext.request.contextPath}/ListarTransacoesCompra">Transações de Compra</a>
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
        
        <section class="card">
            <h1 style="margin-top:0; font-size: 1.5rem;">Editar Fornecedor</h1>
            
            <form id="formEditar" action="${pageContext.request.contextPath}/AtualizarFornecedor" method="POST">
            
            	<input type="hidden" name="doc" id="doc" value="${not empty fornecedor ? fornecedor.documento : ''}">
            
	            <div class="edit-form">
	                <div class="form-group">
	                    <label for="docEdit">Documento</label>
	                    <input type="text" name="docEdit" id="docEdit" 
	                    	value="${not empty fornecedor ? fornecedor.documento : ''}">
	                </div>
	                <div class="form-group">
	                    <label for="name">Nome</label>
	                    <input type="text" name="name" id="name"
	                    	value="${not empty fornecedor ? fornecedor.nome : ''}">
	                </div>
	                <div class="form-group">
	                    <label for="type">Tipo</label>
	                    <select name="type" id="type">
	                        <option value="Coletor" ${fornecedor.tipo == 'COLETOR' ? 'selected' : ''}>Coletor</option>
	                        <option value="Empresa" ${fornecedor.tipo == 'EMPRESA' ? 'selected' : ''}>Empresa</option>
	                        <option value="Municipio" ${fornecedor.tipo == 'MUNICIPIO' ? 'selected' : ''}>Municipio</option>
	                    </select>
	                </div>
	            </div>
	            <div style="margin-top: 1rem; display: flex; justify-content: space-between; align-items: center;">
	                <button type="button" class="btn-save" onclick="saveChanges()">Salvar Alterações</button>
	                <div>
	                    <span class="error-msg" id="error-msg">
	                    	Erro: Se alterar o Tipo, você deve alterar o Documento.
	                    </span>
	                </div>
	            </div>
			</form>
        </section>

        <h2>Lotes Brutos Recentes</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data de Entrada</th>
                    <th>Peso de Entrada (Kg)</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
            
	            <c:forEach items="${listaLotesBrutos}" var="loteBruto">
	            
	            	<tr>
	            		<td>
	            			<a href="${pageContext.request.contextPath}/DetalharLoteBruto?id=${loteBruto.id}" class="id-link">
	            				#LB-${String.format("%03d", loteBruto.id)}
	            			</a>
	            		</td>
	            		<td>${loteBruto.dtEntrada}</td>
	            		<td>${loteBruto.pesoEntradaKg}</td>
	            		
	            		<td>
	            			<c:choose>
	            				<c:when test="${loteBruto.status == 'RECEBIDO'}">
	            					<span class="status-badge status-recebido">${loteBruto.status}</span>
	            				</c:when>
	            				<c:when test="${loteBruto.status == 'EM_TRIAGEM'}">
	            					<span class="status-badge status-processamento">${loteBruto.status}</span>
	            				</c:when>
	            				<c:otherwise>
	            					<span class="status-badge status-concluido">${loteBruto.status}</span>
	            				</c:otherwise>
	            			</c:choose>
	            		</td>
	            		
	            	</tr>
	            
	            </c:forEach>
	            
            </tbody>
        </table>

        <h2>Transações de Compra</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data de Pagamento</th>
                    <th>Valor Total (R$)</th>
                    <th>Status de Pagamento</th>
                </tr>
            </thead>
            <tbody>
            
            	<c:forEach items="${listaTransacoesCompra}" var="transacaoCompra">
            		<tr>
            			<td>
            				<a href="${pageContext.request.contextPath}/DetalharTransacaoCompra?id=${transacaoCompra.id}" class="id-link">
            					#TR-${String.format("%03d", transacaoCompra.id)}
            				</a>
            			</td>
            			<td>${transacaoCompra.dtPagamento}</td>
            			<td>${String.format("R$ %.2f", transacaoCompra.valorTotalCalculado)}</td>
            			
            			<td>
            				<c:choose>
            					<c:when test="${transacaoCompra.status == 'PAGO'}">
            						<span class="status-badge status-ok">Pago</span>
            					</c:when>
            					<c:otherwise>
            						<span class="status-badge status-pend">Aguardando Pagamento</span>
            					</c:otherwise>
            				</c:choose>
            			</td>
            			
            		</tr>
            	</c:forEach>
           
            </tbody>
        </table>

        <div class="danger-zone">
        	<form action="${pageContext.request.contextPath}/DeletarFornecedor" method="POST" id="formDeletar">
        		
        		<input type="hidden" name="doc" value="${not empty fornecedor ? fornecedor.documento : ''}">
        		
	            <button type="button" class="btn-delete" onclick="deleteSupplier()">Excluir Fornecedor</button>
	            
        	</form>
        </div>

    </main>

    <script>
        
        const originalData = {
            doc: '<c:out value="${fornecedor.documento}" />',
            type: '<c:out value="${fornecedor.tipo}" />'
        };

        function saveChanges() {
            const currentDoc = document.getElementById('docEdit').value;
            const currentType = document.getElementById('type').value;
            
            const errorMsg = document.getElementById('error-msg');
            
            errorMsg.style.display = 'none';
            
            const typeChanged = currentType.toUpperCase() !== originalData.type.toUpperCase();
            const docChanged = currentDoc !== originalData.doc;

            if (typeChanged && !docChanged) {
                errorMsg.style.display = 'block';
                document.getElementById('docEdit').style.borderColor = 'var(--danger-color)';
                return;
            }

            document.getElementById('formEditar').submit();
            
        }

        // 3. Função de Exclusão
        function deleteSupplier() {

            const confirmed = confirm(`ATENÇÃO:\n\nTem certeza que deseja excluir o fornecedor "${document.getElementById('name').value}"?\n\nEsta ação removerá o histórico e não pode ser desfeita.`);

            if (confirmed) {                
            	document.getElementById('formDeletar').submit();
            }
        }
        
    </script>
</body>
</html>