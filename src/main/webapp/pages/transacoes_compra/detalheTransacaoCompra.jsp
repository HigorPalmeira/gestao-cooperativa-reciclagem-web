<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transação de Compra ${String.format("#TR-%03d", transacaoCompra.id)}</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem &rsaquo; Transação ${String.format("#TR-%03d", transacaoCompra.id)}</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarTransacoesCompra">Voltar para Gestão</a>
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
        
        <!-- Aviso de Bloqueio (Dinâmico) -->
        <div id="lockMessage" class="info-msg">
            <strong>Aviso:</strong> Esta transação está com status "Pago". Nenhuma informação pode ser alterada.
        </div>

        <!-- SEÇÃO 1: Formulário de Edição -->
        <h1>Dados da Transação</h1>
        <section class="card">
            <form id="transactionForm" action="${pageContext.request.contextPath}/AtualizarTransacaoCompra" method="POST"> <!-- onsubmit="saveChanges(event)" -->
                
                <input type="hidden" id="id" name="id" value="${transacaoCompra.id}">
                
                <div class="form-grid">
                    <div class="form-group">
                        <label for="totalValue">Valor Total (R$)</label>
                        <!-- Readonly conforme regra: apenas Status é editável -->
                        <input type="text" id="totalValue" name="totalValue" readonly
                        	value="${transacaoCompra.valorTotalCalculado}">
                    </div>
                    
                    <div class="form-group">
                        <label for="calcDate">Data de Cálculo</label>
                         <!-- Readonly conforme regra -->
                        <input type="text" id="calcDate" name="calcDate" readonly
                        	value="${transacaoCompra.dtCalculo}">
                    </div>

                    <div class="form-group">
                        <label for="payDate">Data de Pagamento</label>
                         <!-- Readonly conforme regra -->
                        <input type="text" id="payDate" name="payDate" readonly placeholder="--/--/----"
                        	value="${not empty transacaoCompra.dtPagamento ? transacaoCompra.dtPagamento : '--/--/----'}">
                    </div>

                    <div class="form-group">
                        <label for="paymentStatus">Status de Pagamento</label>
                        <!-- Único campo editável (se não for Pago) -->
                        <select id="paymentStatus" name="paymentStatus" onchange="handleStatusChange()">
                            <option value="PENDENTE" ${transacaoCompra.status == 'PENDENTE' ? 'selected' : ''}>Pendente</option>
                            <option value="PAGO" ${transacaoCompra.status == 'PAGO' ? 'selected' : ''}>Pago</option>
                        </select>
                    </div>
                </div>
                
                <button type="submit" id="btnSave" class="btn-save">Salvar Alterações</button>
            </form>
        </section>

        <!-- SEÇÃO 2: Tabela Lote Bruto -->
        <h2>Origem: Lote Bruto</h2>
        <section class="card">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Peso de Entrada (Kg)</th>
                        <th>Data de Entrada</th>
                        <th>Status</th>
                        <th>Fornecedor</th>
                    </tr>
                </thead>
                <tbody id="rawBatchTableBody">
                    
					<tr>
						<td>
							<a class="id-link" href="${pageContext.request.contextPath}/DetalharLoteBruto?id=${transacaoCompra.loteBruto.id}">
								${String.format("#LB-%03d", transacaoCompra.loteBruto.id)}
							</a>
						</td>
						<td>${transacaoCompra.loteBruto.pesoEntradaKg}</td>
						<td>${transacaoCompra.loteBruto.dtEntrada}</td>

                        <c:choose>

                            <c:when test="${transacaoCompra.loteBruto.status == 'RECEBIDO'}">
                                <td>
                                    <span class="status-badge status-recebido">
                                        ${transacaoCompra.loteBruto.status.descricao}
                                    </span>
                                </td>
                            </c:when>

                            <c:when test="${transacaoCompra.loteBruto.status == 'EM_TRIAGEM'}">
                                <td>
                                    <span class="status-badge status-processamento">
                                        ${transacaoCompra.loteBruto.status.descricao}
                                    </span>
                                </td>
                            </c:when>

                            <c:otherwise>
                                <td>
                                    <span class="status-badge status-concluido">
                                        ${transacaoCompra.loteBruto.status.descricao}
                                    </span>
                                </td>
                            </c:otherwise>

                        </c:choose>

                        <!--
						<td>${transacaoCompra.loteBruto.status.descricao}</td>
                        -->
						<td>${transacaoCompra.loteBruto.fornecedor.nome}</td>
					</tr>

                </tbody>
            </table>
        </section>

        <!-- Rodapé: Excluir -->
        <div class="danger-zone">
        
        	<form action="${pageContext.request.contextPath}/DeletarTransacaoCompra" method="POST" onsubmit="return confirmDelete()">
        	
        		<input type="hidden" name="id" value="${transacaoCompra.id}">
	            <span style="color: #666; font-size: 0.9rem; margin-right: 15px;">Deseja remover este registro permanentemente?</span>
	            <button type="submit" id="btnDelete" class="btn-delete">Excluir Transação</button>
        	
        	</form>
        
        </div>

    </main>

    <script>
        
    

        // Elementos DOM
        const statusSelect = document.getElementById('paymentStatus');
        const btnSave = document.getElementById('btnSave');
        const btnDelete = document.getElementById('btnDelete');
        const lockMsg = document.getElementById('lockMessage');
        const payDateInput = document.getElementById('payDate');

        // Inicialização
        window.onload = function() {
            checkBusinessRules(); // Verifica se já começa bloqueado
        };

        // --- Renderização ---

        function renderForm() {
            document.getElementById('totalValue').value = transactionData.value.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
            document.getElementById('calcDate').value = transactionData.calcDate;
            document.getElementById('payDate').value = transactionData.payDate;
            statusSelect.value = transactionData.status;
        }

		// --- Regras de Negócio ---

        function checkBusinessRules() {
            const currentStatus = statusSelect.value;
            
            // Regra: Se status for 'Pago', bloqueia tudo.
            if (currentStatus === 'PAGO') {
                statusSelect.disabled = true;
                btnSave.disabled = true;
                btnDelete.disabled = true;
                lockMsg.style.display = 'block';
                
                // Simula preenchimento automático da data de pagamento se estiver vazio
                if(!payDateInput.value) {
                    payDateInput.value = new Date().toLocaleDateString('pt-BR');
                }
            } else {
                statusSelect.disabled = false;
                btnSave.disabled = false;
                btnDelete.disabled = false;
                lockMsg.style.display = 'none';
            }
        }

        // Monitora mudança no select antes de salvar
        function handleStatusChange() {
            if (statusSelect.value === 'PAGO') {
                alert("Atenção: Ao definir como 'Pago' e salvar, este registo será fechado para futuras edições.");
            }
        }

        // --- Ações ---

        function saveChanges(event) {
            event.preventDefault();
            
            // Aplica a lógica
            checkBusinessRules();

            alert("Transação atualizada com sucesso!");
        }

        function confirmDelete() {
        	return confirm("Tem certeza que deseja excluir esta Transação de Compra e todos os vínculos?\nEsta ação não pode ser desfeita.");
        }

    </script>
</body>
</html>