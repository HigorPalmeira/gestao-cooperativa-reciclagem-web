<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String servletPath = (String) request.getAttribute("javax.servlet.forward.servlet_path"); %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Venda</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div>&rsaquo; Nova Venda</div>
    </nav>

    <main class="container">
    
    	<c:if test="${not empty sessionScope.msgErro}">
    		<div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
    			${sessionScope.msgErro}
    		</div>
    		<% session.removeAttribute("msgErro"); %>
    	</c:if>
    	
    	<!-- 
	        <h1 style="color: #2c3e50; margin-bottom: 0.5rem;">Registrar Venda</h1>
	        <p style="color: #666; font-size: 0.9rem; margin-bottom: 2rem;">Preencha os dados do cliente e adicione os itens para finalizar a venda.</p>
 		-->
		
		<section class="form-card">
		
			<div class="form-header">
				<h1 style="color: #2c3e50; margin-bottom: 0.5rem;">Registrar Venda</h1>
		        <p style="color: #666; font-size: 0.9rem; margin-bottom: 2rem;">Preencha os dados do cliente e adicione os itens para finalizar a venda.</p>
			</div>
			
			<form id="createSale" onsubmit="handleRegister(event)">
			
				<input type="hidden" name="origem" value="<%= servletPath %>"> <!-- ${pageContext.request.servletPath} -->
				
				<h2 class="section-header">1. Dados do Cliente</h2>
				<section class="card">
				
					<div class="form-grid">
						<div class="form-group">
							<label for="clientCnpj">CNPJ do Cliente *</label>
		                    <input type="text" id="clientCnpj" name="clientCnpj" placeholder="Digite apenas números" onblur="fetchClientData()"
		                    	value="${not empty sessionScope.clienteEncontrado ? sessionScope.clienteEncontrado.cnpj : ''}">
		                    <span id="cnpjError" class="error-msg">Cliente não encontrado. Verifique o CNPJ.</span>
						</div>
						<div class="form-group">
		                    <label for="clientName">Nome da Empresa</label>
		                    <input type="text" id="clientName" name="clientName" readonly tabindex="-1"
		                    	value="${not empty sessionScope.clienteEncontrado ? sessionScope.clienteEncontrado.nomeEmpresa : ''}">
		                </div>
		                <div class="form-group">
		                    <label for="clientContact">Contato Principal</label>
		                    <input type="text" id="clientContact" name="clientContact" readonly tabindex="-1"
		                    	value="${not empty sessionScope.clienteEncontrado ? sessionScope.clienteEncontrado.contatoPrincipal : '' }">
		                </div>
					</div>
				
				</section>
				
				<h2 class="section-header">2. Itens da Venda</h2>
				<section class="card" style="padding-bottom: 0;">
					
					<div class="form-grid" style="align-items: end;">
						<div class="form-group">
		                    <label for="itemType">Tipo de Material</label>
		                    <select id="itemType" name="itemType">
		                        <option value="">Selecione...</option>
		                        
		                        <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
		                        	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
		                        </c:forEach>
		                        
		                        <!-- 
			                        <option value="Plástico PET">Plástico PET</option>
			                        <option value="Alumínio">Alumínio</option>
			                        <option value="Papelão">Papelão</option>
			                        <option value="Cobre">Cobre</option>
		                         -->
		                    </select>
		                </div>
		                <div class="form-group">
		                    <label for="itemWeight">Peso Vendido (Kg)</label>
		                    <input type="number" id="itemWeight" name="itemWeight" placeholder="0.00" step="0.01" min="0.1">
		                </div>
		                <div class="form-group">
		                    <label for="itemPrice">Preço Unitário (R$)</label>
		                    <input type="number" id="itemPrice" name="itemPrice" placeholder="0.00" step="0.01" min="0.01">
		                </div>
					</div>
					
					<div style="margin-top: 15px; margin-bottom: 20px; text-align: right;">
		                <button type="button" id="btnAddItem" class="btn-add-item" disabled onclick="addItemToTable()">
		                    + Adicionar Item
		                </button>
		            </div>
					
				</section>
				
				<input type="hidden" id="itensVendaJson" name="itensVendaJson">
			
		        <table id="itemsTable">
		            <thead>
		                <tr>
		                    <th>Tipo de Material</th>
		                    <th>Peso (Kg)</th>
		                    <th>Preço Unit. (R$)</th>
		                    <th>Valor Total (R$)</th>
		                    <th style="width: 150px;">Ações</th>
		                </tr>
		            </thead>
		            <tbody id="itemsTableBody">
		                <tr id="emptyRow">
		                    <td colspan="5" style="text-align: center; color: #999; padding: 2rem;">
		                        Nenhum item adicionado à venda.
		                    </td>
		                </tr>
		            </tbody>
		        </table>
	
		        <div style="text-align: right; margin-top: 1rem; font-size: 1.2rem; font-weight: bold; color: #2c3e50;">
		            Total da Venda: <span id="grandTotal">R$ 0,00</span>
		        </div>
	
		        <div class="footer-actions">
		            <button type="submit" id="btnSubmit" class="btn-submit" disabled > <!-- onclick="registerSale()" -->
		                Cadastrar Venda
		            </button>
		            <a href="${pageContext.request.contextPath}/ListarVendas" class="btn-cancel">Cancelar</a>
		        </div>
			</form>
		

		</section>

    </main>

    <script>
        /* --- JavaScript: Lógica da Página --- */

        const ctx = "${pageContext.request.contextPath}";
        // ESTADO GLOBAL
        let saleItems = [];
        let isClientValid = ${not empty sessionScope.clienteEncontrado ? 'true' : 'false'};

        // ELEMENTOS DOM
        const form = document.getElementById('createSale');
        
        const cnpjInput = document.getElementById('clientCnpj');
        const nameInput = document.getElementById('clientName');
        const contactInput = document.getElementById('clientContact');
        const cnpjError = document.getElementById('cnpjError');

        const itemTypeInput = document.getElementById('itemType');
        const itemWeightInput = document.getElementById('itemWeight');
        const itemPriceInput = document.getElementById('itemPrice');
        const btnAddItem = document.getElementById('btnAddItem');

        const tableBody = document.getElementById('itemsTableBody');
        const emptyRow = document.getElementById('emptyRow');
        const grandTotalDisplay = document.getElementById('grandTotal');
        const btnSubmit = document.getElementById('btnSubmit');

        /* --- 1. LÓGICA DE CLIENTE --- */
        
        
        function fetchClientData() {
            // Remove pontuação para busca
            const cleanCnpj = cnpjInput.value.replace(/\D/g, "");
            
            const isValidLength = cleanCnpj.length === 14;
            
            if (isValidLength) {
            	
            	form.action = ctx + "/VerificarCliente";
            	form.method = "GET";
            	
            	form.submit();
            	
            } else {
            	
            	if (cleanCnpj.length > 0) {
                    cnpjError.style.display = 'block';
                    cnpjInput.style.borderColor = 'var(--danger-color)';
                }
            	
            	nameInput.value = "";
                contactInput.value = "";
                isClientValid = false;
            }
            
            checkMainButton(); // Verifica se pode liberar o botão final

        }

        /* --- 2. LÓGICA DE ITENS --- */

        // Listener para ativar o botão "Adicionar"
        [itemTypeInput, itemWeightInput, itemPriceInput].forEach(input => {
            input.addEventListener('input', checkAddItemButton);
            input.addEventListener('change', checkAddItemButton);
        });

        function checkAddItemButton() {
            const hasType = itemTypeInput.value !== "";
            const hasWeight = parseFloat(itemWeightInput.value) > 0;
            const hasPrice = parseFloat(itemPriceInput.value) > 0;

            if (hasType && hasWeight && hasPrice) {
                btnAddItem.disabled = false;
            } else {
                btnAddItem.disabled = true;
            }
        }

        function addItemToTable() {
            // Pegar valores
            const materialId = parseInt(itemType.value);
            const materialName = itemTypeInput.options[itemTypeInput.selectedIndex].text;
            const weight = parseFloat(itemWeightInput.value);
            const price = parseFloat(itemPriceInput.value);
            const total = weight * price;
            const idJs = Date.now(); // ID temporário único

            // Adicionar ao array de estado
            saleItems.push({
            	idJs: idJs,
            	tipoMaterial: {
            		id: materialId,
            		nome: materialName
            	},
            	venda: null,
            	pesoVendidoKg: weight,
            	precoUnitarioKg: price,
            	total: total
            });
            // saleItems.push({ id, type, weight, price, total });

            // Limpar inputs
            itemTypeInput.value = "";
            itemWeightInput.value = "";
            itemPriceInput.value = "";
            btnAddItem.disabled = true;

            // Renderizar
            renderTable();
        }

        function renderTable() {
            // Limpa tabela
            tableBody.innerHTML = '';

            // Verifica se está vazia
            if (saleItems.length === 0) {
                tableBody.appendChild(emptyRow);
                grandTotalDisplay.innerText = "R$ 0,00";
                checkMainButton();
                return;
            }

            let totalSum = 0;

            // Cria linhas
            saleItems.forEach(item => {
                totalSum += item.total;

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>\${item.tipoMaterial.nome}</td>
                    <td>\${item.pesoVendidoKg.toFixed(2)}</td>
                    <td>R$ \${item.precoUnitarioKg.toFixed(2)}</td>
                    <td><strong>R$ \${item.total.toFixed(2)}</strong></td>
                    <td class="actions-col">
                        <button class="btn-edit" onclick="editItem(\${item.idJs})">Editar</button>
                        <button class="btn-remove" onclick="removeItem(\${item.idJs})">Remover</button>
                    </td>
                `;
                tableBody.appendChild(tr);
            });

            // Atualiza Total Geral
            grandTotalDisplay.innerText = totalSum.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
            
            checkMainButton();
        }

        /* --- 3. AÇÕES DA TABELA --- */

        function removeItem(idJs) {
            saleItems = saleItems.filter(item => item.idJs !== idJs);
            renderTable();
        }

        function editItem(idJs) {
            // Encontrar item
            const item = saleItems.find(i => i.idJs === idJs);
            
            // Devolver valores ao formulário
            itemTypeInput.value = item.tipoMaterial.id;
            itemWeightInput.value = item.pesoVendidoKg;
            itemPriceInput.value = item.precoUnitarioKg;

            // Remover da lista (para que o usuário adicione novamente atualizado)
            removeItem(idJs);
            
            // Focar no primeiro campo para edição rápida
            itemTypeInput.focus();
            checkAddItemButton(); // Revalida botão adicionar
        }

        /* --- 4. VALIDAÇÃO DO BOTÃO FINAL --- */

        function checkMainButton() {
            // Regra: Cliente Válido E Pelo menos 1 item na lista
            if (isClientValid && saleItems.length > 0) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }
        
        function handleRegister(event) {
        	
        	event.preventDefault();
        	
        	if (saleItems.length === 0) {
        		alert("Adicione pelo menos um item à venda.");
        		return;
        	}
        	
        	const jsonItens = JSON.stringify(saleItems);
        	
        	document.getElementById("itensVendaJson").value = jsonItens;
        	
        	form.action = ctx + "/InserirVenda";
        	form.method = "POST";
        	
        	form.submit();
        	
        }
        
        window.onload = checkMainButton();

    </script>
    
    <c:if test="${not empty sessionScope.clienteEncontrado}">
		<% session.removeAttribute("clienteEncontrado"); %>
	</c:if>
					
</body>
</html>