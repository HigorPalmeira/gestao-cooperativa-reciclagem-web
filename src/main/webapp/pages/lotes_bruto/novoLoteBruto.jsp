<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Lote Bruto</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div>&rsaquo; Novo Lote Bruto</div>
    </nav>

    <main class="container">
    
    	<c:if test="${not empty sessionScope.msgErro}">
    		<div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
    			${sessionScope.msgErro}
    		</div>
    		<% session.removeAttribute("msgErro"); %>
    	</c:if>
    
        <section class="form-card">
            <div class="form-header">
                <h1>Entrada de Lote Bruto</h1>
                <p class="subtitle">Registre a entrada de materiais de fornecedores.</p>
            </div>

            <!-- Formulário -->
            <form id="createBatchForm" onsubmit="handleRegister(event)">
                
                <input type="hidden" name="origem" value="${pageContext.request.servletPath}">
                
                <!-- Seção Fornecedor -->
                <div class="form-group">
                    <label for="supplierDoc">Documento do Fornecedor (CPF/CNPJ) <span class="required">*</span></label>
                    <input type="text" id="supplierDoc" name="supplierDoc" placeholder="Digite apenas números" onblur="fetchSupplierData()"
                    	value="${not empty sessionScope.fornecedorEncontrado ? sessionScope.fornecedorEncontrado.documento : ''}">
                    <span id="docError" class="error-msg">Erro: Fornecedor não encontrado ou documento inválido.</span>
                </div>

                <div class="form-group">
                    <label for="supplierName">Nome do Fornecedor</label>
                    <input type="text" id="supplierName" name="supplierName" readonly tabindex="-1" placeholder="Preenchimento automático"
                    	value="${not empty sessionScope.fornecedorEncontrado ? sessionScope.fornecedorEncontrado.nome : ''}">
                </div>

                <div class="form-group">
                    <label for="supplierType">Tipo de Fornecedor</label>
                    <input type="text" id="supplierType" name="supplierType" readonly tabindex="-1" placeholder="Preenchimento automático"
                    	value="${not empty sessionScope.fornecedorEncontrado ? sessionScope.fornecedorEncontrado.tipo : ''}">
                </div>
                
                <c:set var="temFornecedor" value="${not empty sessionScope.fornecedorEncontrado}" />
                
                <c:if test="${not empty sessionScope.fornecedorEncontrado}">
                	<% session.removeAttribute("fornecedorEncontrado"); %>
                </c:if>

                <hr style="border: 0; border-top: 1px solid #eee; margin: 2rem 0;">

                <!-- Seção Detalhes do Lote -->
                <div class="form-group">
                    <label for="entryWeight">Peso de Entrada (Kg) <span class="required">*</span></label>
                    <input type="number" id="entryWeight" name="entryWeight" placeholder="0.00" step="0.01" min="0.1">
                </div>

                <!-- Botão de Ação -->
                <div class="form-actions">
                    <!-- O botão começa desativado -->
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="${pageContext.request.contextPath}/ListarLotesBruto" class="btn-back">
                        Cancelar e voltar
                    </a>
                </div>
            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Lógica de Validação e Registo --- */

        const ctx = "${pageContext.request.contextPath}";
        
        // Elementos DOM
        const form = document.getElementById('createBatchForm');
        
        const docInput = document.getElementById('supplierDoc');
        const nameInput = document.getElementById('supplierName');
        const typeInput = document.getElementById('supplierType');
        const weightInput = document.getElementById('entryWeight');
        const docError = document.getElementById('docError');
        const btnSubmit = document.getElementById('btnSubmit');

        let isSupplierValid = ${temFornecedor} ? true : false; //false;

        // 2. Função: Buscar Fornecedor (Blur Event)
        function fetchSupplierData() {
            // Remove caracteres não numéricos
            const rawDoc = docInput.value.replace(/\D/g, "");
            
            // Validação simples de tamanho (CPF=11 ou CNPJ=14)
            const isValidLength = rawDoc.length === 11 || rawDoc.length === 14;

            if (isValidLength/* && suppliersDB[rawDoc]*/) {
                
            	form.action = ctx + "/VerificarFornecedor";
            	form.method = "GET";
            	
            	form.submit();
            	
                isSupplierValid = true;
            } else {
                // ERRO: Não encontrou ou formato inválido
                nameInput.value = "";
                typeInput.value = "";
                
                // Só mostra erro se o campo não estiver vazio
                if (rawDoc.length > 0) {
                    docError.style.display = 'block';
                    docInput.style.borderColor = 'var(--danger-color)';
                } else {
                    docError.style.display = 'none';
                    docInput.style.borderColor = 'var(--border-color)';
                }
                isSupplierValid = false;
            }

            checkFormValidity();
        }

        // 3. Função: Validar Formulário Completo
        function checkFormValidity() {
            const weightValue = parseFloat(weightInput.value);
            const isWeightValid = !isNaN(weightValue) && weightValue > 0;

            // Ativa botão se Fornecedor for válido E Peso for válido
            if (isSupplierValid && isWeightValid) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // Listener para o campo de peso
        weightInput.addEventListener('input', checkFormValidity);

        // 4. Função: Simular Cadastro
        function handleRegister(event) {
            event.preventDefault();

            form.action = ctx + "/InserirLoteBruto";
            form.method = "POST";
            
            form.submit();
            
        }

    </script>
</body>
</html>