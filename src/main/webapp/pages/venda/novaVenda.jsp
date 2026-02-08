<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova Venda</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Padrão ERP) --- */
        :root {
            --primary-color: #0056b3;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --danger-color: #dc3545;
            --warning-color: #ffc107;
            --disabled-color: #95a5a6;
            --text-color: #333;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: var(--text-color);
        }

        /* Menu de Navegação */
        nav.main-nav {
            background-color: var(--primary-color);
            color: var(--white);
            padding: 1rem 2rem;
            display: flex;
            align-items: center;
        }
        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; margin-right: 20px; }
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; opacity: 0.8; }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 900px;
            margin: 2rem auto;
            padding: 0 1rem;
            padding-bottom: 4rem;
        }

        /* Títulos de Seção */
        .section-header {
            border-bottom: 2px solid var(--border-color);
            margin-bottom: 1.5rem;
            padding-bottom: 0.5rem;
            color: #495057;
            font-size: 1.2rem;
            margin-top: 2rem;
        }
        .section-header:first-of-type { margin-top: 0; }

        /* Cartões de Conteúdo */
        .card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
            margin-bottom: 2rem;
        }

        /* Grids de Formulário */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr;
            gap: 1.5rem;
            align-items: end; /* Alinha inputs e botões na base */
        }

        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; color: #555; font-size: 0.9rem;}
        
        .form-group input, .form-group select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            width: 100%;
            box-sizing: border-box;
        }
        
        /* Inputs Readonly (Dados do cliente) */
        .form-group input[readonly] {
            background-color: #e9ecef;
            color: #6c757d;
            cursor: not-allowed;
        }

        /* Botão Adicionar Item (Pequeno) */
        .btn-add-item {
            background-color: var(--primary-color);
            color: white;
            padding: 11px 20px;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            height: 42px; /* Altura igual aos inputs */
        }
        .btn-add-item:disabled { background-color: var(--disabled-color); cursor: default; opacity: 0.7;}
        .btn-add-item:hover:not(:disabled) { background-color: #004494; }

        /* Tabela de Itens */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
            background-color: var(--white);
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        }

        th, td { text-align: left; padding: 12px 15px; border-bottom: 1px solid var(--border-color); }
        th { background-color: #f8f9fa; font-weight: 600; color: #495057; font-size: 0.9rem;}
        td { font-size: 0.95rem; }
        
        .actions-col button {
            background: none;
            border: none;
            cursor: pointer;
            font-size: 0.85rem;
            margin-right: 10px;
            font-weight: bold;
        }
        .btn-edit { color: var(--primary-color); }
        .btn-remove { color: var(--danger-color); }

        /* Botão Final Cadastrar */
        .footer-actions {
            margin-top: 2rem;
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .btn-submit {
            background-color: var(--success-color);
            color: white;
            padding: 15px;
            border: none;
            border-radius: 4px;
            font-size: 1.1rem;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }
        .btn-submit:disabled { background-color: var(--disabled-color); cursor: not-allowed; }
        .btn-submit:hover:not(:disabled) { background-color: #218838; }

        .btn-cancel { text-align: center; color: #666; text-decoration: none; font-size: 0.9rem; display: block;}
        .btn-cancel:hover { text-decoration: underline; color: var(--danger-color); }

        /* Mensagens de Feedback */
        .error-msg { color: var(--danger-color); font-size: 0.85rem; margin-top: 5px; display: none; }

    </style>
     -->
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Nova Venda</div>
    </nav>

    <main class="container">
        <h1 style="color: #2c3e50; margin-bottom: 0.5rem;">Registrar Venda</h1>
        <p style="color: #666; font-size: 0.9rem; margin-bottom: 2rem;">Preencha os dados do cliente e adicione os itens para finalizar a venda.</p>

        <h2 class="section-header">1. Dados do Cliente</h2>
        <section class="card">
            <div class="form-grid">
                <div class="form-group">
                    <label for="clientCnpj">CNPJ do Cliente *</label>
                    <input type="text" id="clientCnpj" placeholder="Digite apenas números" onblur="fetchClientData()">
                    <span id="cnpjError" class="error-msg">Cliente não encontrado. Verifique o CNPJ.</span>
                </div>
                <div class="form-group">
                    <label for="clientName">Nome da Empresa</label>
                    <input type="text" id="clientName" readonly tabindex="-1">
                </div>
                <div class="form-group">
                    <label for="clientContact">Contato Principal</label>
                    <input type="text" id="clientContact" readonly tabindex="-1">
                </div>
            </div>
        </section>

        <h2 class="section-header">2. Itens da Venda</h2>
        <section class="card" style="padding-bottom: 0;">
            <div class="form-grid" style="align-items: end;">
                <div class="form-group">
                    <label for="itemType">Tipo de Material</label>
                    <select id="itemType">
                        <option value="">Selecione...</option>
                        <option value="Plástico PET">Plástico PET</option>
                        <option value="Alumínio">Alumínio</option>
                        <option value="Papelão">Papelão</option>
                        <option value="Cobre">Cobre</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="itemWeight">Peso Vendido (Kg)</label>
                    <input type="number" id="itemWeight" placeholder="0.00" step="0.01" min="0.1">
                </div>
                <div class="form-group">
                    <label for="itemPrice">Preço Unitário (R$)</label>
                    <input type="number" id="itemPrice" placeholder="0.00" step="0.01" min="0.01">
                </div>
            </div>
            
            <div style="margin-top: 15px; margin-bottom: 20px; text-align: right;">
                <button type="button" id="btnAddItem" class="btn-add-item" disabled onclick="addItemToTable()">
                    + Adicionar Item
                </button>
            </div>
        </section>

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
            <button id="btnSubmit" class="btn-submit" disabled onclick="registerSale()">
                Cadastrar Venda
            </button>
            <a href="./vendas.html" class="btn-cancel">Cancelar</a>
        </div>

    </main>

    <script>
        /* --- JavaScript: Lógica da Página --- */

        // ESTADO GLOBAL
        let saleItems = [];
        let isClientValid = false;

        // ELEMENTOS DOM
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
        
        // Mock Database de Clientes
        const mockClients = {
            "12345678000190": { name: "Supermercados Horizonte Ltda", contact: "Maria Oliveira" },
            "98765432000115": { name: "Auto Peças Silva", contact: "João Silva" }
        };

        function fetchClientData() {
            // Remove pontuação para busca
            const cleanCnpj = cnpjInput.value.replace(/\D/g, "");
            
            if (mockClients[cleanCnpj]) {
                // Sucesso
                nameInput.value = mockClients[cleanCnpj].name;
                contactInput.value = mockClients[cleanCnpj].contact;
                cnpjError.style.display = 'none';
                cnpjInput.style.borderColor = 'var(--success-color)';
                isClientValid = true;
            } else {
                // Erro
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
            const type = itemTypeInput.value;
            const weight = parseFloat(itemWeightInput.value);
            const price = parseFloat(itemPriceInput.value);
            const total = weight * price;
            const id = Date.now(); // ID temporário único

            // Adicionar ao array de estado
            saleItems.push({ id, type, weight, price, total });

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
                    <td>${item.type}</td>
                    <td>${item.weight.toFixed(2)}</td>
                    <td>R$ ${item.price.toFixed(2)}</td>
                    <td><strong>R$ ${item.total.toFixed(2)}</strong></td>
                    <td class="actions-col">
                        <button class="btn-edit" onclick="editItem(${item.id})">Editar</button>
                        <button class="btn-remove" onclick="removeItem(${item.id})">Remover</button>
                    </td>
                `;
                tableBody.appendChild(tr);
            });

            // Atualiza Total Geral
            grandTotalDisplay.innerText = totalSum.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
            
            checkMainButton();
        }

        /* --- 3. AÇÕES DA TABELA --- */

        function removeItem(id) {
            saleItems = saleItems.filter(item => item.id !== id);
            renderTable();
        }

        function editItem(id) {
            // Encontrar item
            const item = saleItems.find(i => i.id === id);
            
            // Devolver valores ao formulário
            itemTypeInput.value = item.type;
            itemWeightInput.value = item.weight;
            itemPriceInput.value = item.price;

            // Remover da lista (para que o usuário adicione novamente atualizado)
            removeItem(id);
            
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

        function registerSale() {
            alert(`Sucesso!\nVenda registrada para: ${nameInput.value}\nTotal de Itens: ${saleItems.length}`);
            window.location.href = './vendas.html';
        }

    </script>
</body>
</html>