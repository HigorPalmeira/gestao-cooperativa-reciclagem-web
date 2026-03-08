<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Lote Processado</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div>&rsaquo; Novo Lote Processado</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Registro de Processamento</h1>
                <p class="subtitle">Crie um novo lote processado a partir de um lote bruto.</p>
            </div>

            <!-- Formulário -->
            <form id="createProcessedBatchForm" action="${pageContext.request.contextPath}/InserirLoteProcessado" method="POST">
                
                <!-- 1. Lote Bruto (Com Datalist para Sugestões) -->
                <div class="form-group">
                    <label for="rawBatchId">Lote Bruto (ID) <span class="required">*</span></label>
                    <input type="text" id="rawBatchId" name="rawBatchId" list="rawBatchesList" placeholder="Ex: LB-101">
                    <!-- Lista de Sugestões -->
                    <datalist id="rawBatchesList">
                    	
                    	<c:forEach items="${listaLotesBrutos}" var="loteBruto">
                    		<option value="${String.format('LB-%03d', loteBruto.id)}">
                    		<!-- onblur="fetchRawBatchInfo({id: '${String.format('LB-%03d', loteBruto.id)}', supplier: '${loteBruto.fornecedor.nome}', date: '${loteBruto.dtEntrada}', weight: ${loteBruto.pesoEntradaKg}})" -->
                    	</c:forEach>
                    
                    </datalist>
                    <span id="batchError" class="error-msg">Lote bruto não encontrado.</span>
                </div>

                <!-- 2. Tipo de Material -->
                <div class="form-group">
                    <label for="materialType">Tipo de Material <span class="required">*</span></label>
                    <select id="materialType" name="materialType">
                        <option value="">Selecione...</option>
                        
                        <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                        	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                        </c:forEach>
                     
                    </select>
                </div>

                <!-- 3. Etapa de Processamento -->
                <div class="form-group">
                    <label for="processStage">Etapa de Processamento <span class="required">*</span></label>
                    <select id="processStage" name="processStage">
                        <option value="">Selecione...</option>
                        
                        <c:forEach items="${listaCategoriasProcessamento}" var="categoriaProcessamento">
                        	<option value="${categoriaProcessamento.id}">${categoriaProcessamento.nome}</option>
                        </c:forEach>
                        
                    </select>
                </div>

                <!-- 4. Peso Atual -->
                <div class="form-group">
                    <label for="currentWeight">Peso Atual (Kg) <span class="required">*</span></label>
                    <input type="number" id="currentWeight" name="currentWeight" placeholder="0.00" step="0.01" min="0.1">
                </div>

                <!-- Botões de Ação -->
                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="${pageContext.request.contextPath}/ListarLotesProcessados" class="btn-back">
                        Cancelar e voltar
                    </a>
                </div>
            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Lógica de Negócio --- */

        // Elementos DOM
        const batchInput = document.getElementById('rawBatchId');
        const materialSelect = document.getElementById('materialType');
        const stageSelect = document.getElementById('processStage');
        const weightInput = document.getElementById('currentWeight');
        const btnSubmit = document.getElementById('btnSubmit');
        const batchError = document.getElementById('batchError');

        
        // 4. Validação Geral do Formulário
        function checkFormValidity() {
        	const isBatchValid = batchInput.value.trim() !== "";
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

    </script>
</body>
</html>