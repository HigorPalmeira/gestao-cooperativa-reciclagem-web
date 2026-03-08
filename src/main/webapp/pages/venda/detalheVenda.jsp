<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Venda ${String.format("#%03d", venda.id)}</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP Reciclagem &rsaquo; Venda ${String.format("#%03d", venda.id)}</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarVendas">Voltar para Gestão</a>
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
    	
    	<form id="mainForm" onsubmit="handleChanges(event)">
    	
    		<input type="hidden" name="id" id="id" value="${venda.id}">
    		
    		<h1>Editar Venda</h1>
    		
    		<section class="card">
    		
    			<h2>Dados do Cliente</h2>
    			<div class="form-grid" style="margin-bottom: 2rem;">
	    			<div class="form-group">
	                    <label for="clientCnpj">CNPJ *</label>
	                    <input type="text" id="clientCnpj" name="clientCnpj" onblur="fetchClientData()"
	                    	value="${venda.cliente.cnpj}">
	                    <span id="cnpjError" class="error-msg">Cliente não encontrado.</span>
	                </div>
	                
		            <div class="form-group">
	                    <label for="clientName">Nome da Empresa</label>
	                    <input type="text" id="clientName" name="clientName" readonly
	                    	value="${venda.cliente.nomeEmpresa}">
	                </div>
	                
	                <div class="form-group">
	                    <label for="clientEmail">E-mail</label>
	                    <input type="email" id="clientEmail" name="clientEmail" readonly
	                    	value="${venda.cliente.emailContato}">
	                </div>
	                
    			</div>
    			
    			<hr style="border: 0; border-top: 1px solid #eee; margin: 2rem 0;">
    		
    			<h2>Adicionar Novo Item</h2>
    			<div class="form-grid">
    			
    				<input type="hidden" id="itemId">
    				
    				<div class="form-group">
	                    <label for="itemType">Tipo de Material</label>
	                    <select id="itemType" name="itemType">
	                        <option value="">Selecione...</option>
	                        
	                        <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
	                        	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
	                        </c:forEach>
	                        
	                    </select>
	                </div>
    			
    				<div class="form-group">
	                    <label for="itemWeight">Peso Vendido (Kg)</label>
	                    <input type="number" id="itemWeight" name="itemWeight" placeholder="0.00" step="0.01">
	                </div>
	                
	                <div class="form-group">
	                    <label for="itemPrice">Preço Unitário (R$)</label>
	                    <input type="number" id="itemPrice" name="itemPrice" placeholder="0.00" step="0.01">
	                </div>
    			
    			</div>
    			
    			<div style="margin-top: 1rem; text-align: right;">
	                <button type="button" id="btnAddItem" class="btn-add" disabled onclick="addItem()">
	                    + Adicionar à Lista
	                </button>
	            </div>
	            
	            <div style="margin-top: 2rem; border-top: 1px solid #eee; padding-top: 1rem;">
	                <button type="submit" class="btn-save">
	                	Salvar Alterações
	                </button>
	            </div>
    		
				<h3 style="margin-bottom: 0.5rem; color: #444;">Itens da Venda</h3>
				<table id="itemsTable">
					<thead>
						<tr>
							<th>Tipo de Material</th>
							<th>Peso Vendido (Kg)</th>
							<th>Preço Unitário (R$)</th>
							<th>Valor Total (R$)</th>
							<th style="width: 150px;">Ações</th>
						</tr>
					</thead>
					<tbody id="tableBody">
					
					</tbody>
					<tfoot>
						<tr style="background-color: #fafafa; font-weight: bold;">
							<td colspan="3" style="text-align: right;">Total Geral:</td>
							<td id="grandTotal">R$ 0,00</td>
							<td></td>
						</tr>
					</tfoot>
				</table>
				
				<input type="hidden" name="itensVendaJson" id="itensVendaJson">
				<input type="hidden" name="itensVendaRemovidosJson" id="itensVendaRemovidosJson">
				
    		</section>
    	
    	
    	</form>


		<!-- 
        <div class="danger-zone">
        
        	<form action="${pageContext.request.contextPath}/DeletarVenda" method="POST" onsubmit="return confirmDelete()">
        	
        		<input type="hidden" name="id" value="${venda.id}">
	            <span style="color: #666; font-size: 0.9rem; margin-right: 15px;">Deseja apagar este registro permanentemente?</span>
	            <button type="submit" class="btn-delete">Excluir Venda</button>
        	
        	</form>
            
        </div>
         -->

    </main>

    <script>
            
    	const ctx = "${pageContext.request.contextPath}";
    
		let saleItems = [
        	
        	<c:forEach items="${listaItensVenda}" var="itemVenda">
        		{
        			id: ${itemVenda.id},
        			itemId: ${itemVenda.id},
        			tipoMaterial: {
        				id: ${itemVenda.tipoMaterial.id},
        				nome: '${itemVenda.tipoMaterial.nome}'
        			},
        			venda: {
        				id: ${itemVenda.venda.id}
        			},
        			pesoVendidoKg: ${itemVenda.pesoVendidoKg},
                	precoUnitarioKg: ${itemVenda.precoUnitarioKg},
                	total: ${itemVenda.pesoVendidoKg * itemVenda.precoUnitarioKg}
        		},
        	</c:forEach>
        	
        ];
		
		let removeItems = [
			
		];

        // 2. Elementos do DOM
        const form = document.getElementById('mainForm');
        
        const cnpjInput = document.getElementById('clientCnpj');
        const nameInput = document.getElementById('clientName');
        const emailInput = document.getElementById('clientEmail');
        const cnpjError = document.getElementById('cnpjError');

        const itemIdInput = document.getElementById('itemId');
        const itemType = document.getElementById('itemType');
        const itemWeight = document.getElementById('itemWeight');
        const itemPrice = document.getElementById('itemPrice');
        const btnAdd = document.getElementById('btnAddItem');
        const tableBody = document.getElementById('tableBody');
        const grandTotalDisplay = document.getElementById('grandTotal');

        // 3. Inicialização
        window.onload = function() {
            
            // Renderizar tabela inicial
            renderTable();
        };

        // 4. Lógica de Busca de Cliente (Mock)
        const mockClients = {
            "12345678000190": { name: "Supermercados Horizonte Ltda", email: "compras@horizonte.com.br" },
            "99888777000100": { name: "Indústria Metalurgica Aço", email: "contato@aco.com" }
        };

        function fetchClientData() {
            const cleanCnpj = cnpjInput.value.replace(/\D/g, "");
            
            if (mockClients[cleanCnpj]) {
                nameInput.value = mockClients[cleanCnpj].name;
                emailInput.value = mockClients[cleanCnpj].email;
                cnpjError.style.display = 'none';
                cnpjInput.style.borderColor = 'var(--border-color)';
            } else {
                if(cleanCnpj.length > 0) {
                    cnpjError.style.display = 'block';
                    cnpjInput.style.borderColor = 'var(--danger-color)';
                    // Limpa se não achar
                    nameInput.value = "";
                    emailInput.value = "";
                }
            }
        }

        // 5. Lógica de Item (Validação do Botão Adicionar)
        [itemType, itemWeight, itemPrice].forEach(input => {
            input.addEventListener('input', checkAddItemValidity);
            input.addEventListener('change', checkAddItemValidity);
        });

        function checkAddItemValidity() {
            const isType = itemType.value !== "";
            const isWeight = parseFloat(itemWeight.value) > 0;
            const isPrice = parseFloat(itemPrice.value) > 0;

            if (isType && isWeight && isPrice) {
                btnAdd.disabled = false;
            } else {
                btnAdd.disabled = true;
            }
        }

        // 6. Adicionar Item
        function addItem() {
        	const itemId = parseInt(itemIdInput.value);
        	const materialId = parseInt(itemType.value);
        	const materialName = itemType.options[itemType.selectedIndex].text;
            const weight = parseFloat(itemWeight.value);
            const price = parseFloat(itemPrice.value);
            
            const hasItem = removeItems.some(item => item.itemId === itemId);
            let item;
            
            if (hasItem) {
            	
            	const idxItem = removeItems.findIndex(item => item.itemId === itemId);
            	item = removeItems.splice(idxItem, 1)[0];
            	
            	item.tipoMaterial = {
            		id: materialId,
            		nome: materialName
            	};
            	item.pesoVendidoKg = weight,
            	item.precoUnitarioKg = price,
            	item.total = weight * price
            	
            } else {
            	
	            item = {
	                id: 0, 
	                itemId: Date.now(), // ID temporário
	                tipoMaterial: {
	                	id: materialId,
	                	nome: materialName
	                },
	                venda: null,
	                pesoVendidoKg: weight,
	                precoUnitarioKg: price,
	                total: weight * price
	            };

            }
            

            saleItems.push(item);
            renderTable();

            // Limpar form de item
            
            itemIdInput.value = "";
            itemType.value = "";
            itemWeight.value = "";
            itemPrice.value = "";
            btnAdd.disabled = true; // Botão volta a ficar inativo
        }

        // 7. Renderizar Tabela
        function renderTable() {
            tableBody.innerHTML = '';
            let totalSum = 0;

            saleItems.forEach(item => {
                totalSum += item.total;
                const tr = document.createElement('tr');
                
                tr.innerHTML = `
                    <td>\${item.tipoMaterial.nome}</td>
                    <td>\${item.pesoVendidoKg.toFixed(2)}</td>
                    <td>R$ \${item.precoUnitarioKg.toFixed(2)}</td>
                    <td><strong>R$ \${item.total.toFixed(2)}</strong></td>
                    <td>
                        <button class="action-btn text-edit" onclick="editItem(\${item.itemId})">Editar</button>
                        <button class="action-btn text-remove" onclick="removeItem(\${item.itemId})">Remover</button>
                    </td>
                `;
                tableBody.appendChild(tr);
            });

            grandTotalDisplay.innerText = totalSum.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        }

        // 8. Remover Item
        function removeItem(id) {
        	
        	const idx = saleItems.findIndex(item => item.itemId === id);
        	const item = saleItems.splice(idx, 1)[0];
            // saleItems = saleItems.filter(item => item.id !== id);
            if (item.id !== 0) {
            	removeItems.push(item);            	
            }
            renderTable();
        }

        // 9. Editar Item (Recupera dados para o form e remove da tabela)
        function editItem(id) {
            const item = saleItems.find(i => i.itemId === id);
            if (item) {
            	itemIdInput.value = item.itemId;
                itemType.value = item.tipoMaterial.id;
                itemWeight.value = item.pesoVendidoKg;
                itemPrice.value = item.precoUnitarioKg;
                
                // Remove da tabela para evitar duplicidade ao adicionar novamente
                removeItem(id);
                
                // Ativa o botão pois os campos estão preenchidos
                checkAddItemValidity();
                
                // Foco visual
                itemType.focus();
            }
        }
        
        function handleChanges(event) {
        	
        	event.preventDefault();
        	
        	if (saleItems.length === 0) {
        		alert("A venda não pode ser salva sem itens vendidos.");
        		return;
        	}
        	
        	const jsonItens = JSON.stringify(saleItems);
        	const jsonItensRemovidos = JSON.stringify(removeItems);
        	
        	document.getElementById("itensVendaJson").value = jsonItens;
        	document.getElementById("itensVendaRemovidosJson").value = jsonItensRemovidos;
        	
        	
        	form.action = ctx + "/AtualizarVenda";
        	form.method = "POST";
        	
        	form.submit();
        	
        }

        function confirmDelete() {
        	return confirm("Tem certeza que deseja excluir esta Venda e todos os vínculos?\nEsta ação não pode ser desfeita.");
        }

    </script>
</body>
</html>