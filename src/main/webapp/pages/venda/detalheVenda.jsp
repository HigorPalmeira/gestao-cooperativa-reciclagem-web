<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Venda #1020</title>
    
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
            justify-content: space-between;
        }
        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; }
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; opacity: 0.9; margin-left: 20px;}
        nav.main-nav a:hover { text-decoration: underline; opacity: 1; }

        /* Container Principal */
        .container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 0 1rem;
            padding-bottom: 5rem;
        }

        h1 { color: #2c3e50; margin-bottom: 2rem; border-bottom: 1px solid #ddd; padding-bottom: 10px; }
        h2 { font-size: 1.1rem; color: #555; margin-bottom: 1rem; margin-top: 0; }

        /* Cartões de Conteúdo */
        .card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
            margin-bottom: 2rem;
        }

        /* Layout de Grid para Formulários */
        .form-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 1.5rem;
            align-items: end;
        }

        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; font-size: 0.9rem; color: #444; }
        
        .form-group input, .form-group select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            width: 100%;
            box-sizing: border-box;
        }

        /* Inputs Readonly */
        .form-group input[readonly] { background-color: #e9ecef; color: #6c757d; }

        /* Botão Adicionar (Pequeno) */
        .btn-add {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            height: 42px; /* Altura igual ao input */
        }
        .btn-add:disabled { background-color: var(--disabled-color); cursor: default; }
        .btn-add:hover:not(:disabled) { background-color: #004494; }

        /* Botão Salvar (Principal da Seção Superior) */
        .btn-save {
            background-color: var(--success-color);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 1rem;
        }
        .btn-save:hover { background-color: #218838; }

        /* Tabela */
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
        th { background-color: #f8f9fa; font-weight: 600; color: #495057; font-size: 0.9rem; }
        
        /* Ações da Tabela */
        .action-btn {
            background: none;
            border: none;
            cursor: pointer;
            font-weight: bold;
            font-size: 0.85rem;
            margin-right: 10px;
        }
        .text-edit { color: var(--primary-color); }
        .text-remove { color: var(--danger-color); }
        .text-edit:hover, .text-remove:hover { text-decoration: underline; }

        /* Rodapé com Botão Excluir */
        .danger-zone {
            margin-top: 4rem;
            border-top: 1px solid #ddd;
            padding-top: 1.5rem;
            text-align: right;
        }

        .btn-delete {
            background-color: transparent;
            color: var(--danger-color);
            border: 1px solid var(--danger-color);
            padding: 10px 25px;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.2s;
        }
        .btn-delete:hover { background-color: var(--danger-color); color: white; }

        /* Mensagens de Erro */
        .error-msg { color: var(--danger-color); font-size: 0.85rem; margin-top: 5px; display: none; }
        
    </style>
     -->
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System &rsaquo; Venda #1020</div>
        <div>
            <a href="ListarVendas">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
        <h1>Editar Venda</h1>

        <section class="card">
            
            <h2>Dados do Cliente</h2>
            <div class="form-grid" style="margin-bottom: 2rem;">
                <div class="form-group">
                    <label for="clientCnpj">CNPJ *</label>
                    <input type="text" id="clientCnpj" onblur="fetchClientData()">
                    <span id="cnpjError" class="error-msg">Cliente não encontrado.</span>
                </div>
                <div class="form-group">
                    <label for="clientName">Nome da Empresa</label>
                    <input type="text" id="clientName">
                </div>
                <div class="form-group">
                    <label for="clientEmail">E-mail</label>
                    <input type="email" id="clientEmail">
                </div>
            </div>

            <hr style="border: 0; border-top: 1px solid #eee; margin: 2rem 0;">

            <h2>Adicionar Novo Item</h2>
            <div class="form-grid">
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
                    <input type="number" id="itemWeight" placeholder="0.00" step="0.01">
                </div>
                <div class="form-group">
                    <label for="itemPrice">Preço Unitário (R$)</label>
                    <input type="number" id="itemPrice" placeholder="0.00" step="0.01">
                </div>
            </div>
            
            <div style="margin-top: 1rem; text-align: right;">
                <button type="button" id="btnAddItem" class="btn-add" disabled onclick="addItem()">
                    + Adicionar à Lista
                </button>
            </div>

            <div style="margin-top: 2rem; border-top: 1px solid #eee; padding-top: 1rem;">
                <button class="btn-save" onclick="saveChanges()">Salvar Alterações</button>
            </div>
        </section>

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

        <div class="danger-zone">
            <span style="color: #666; font-size: 0.9rem; margin-right: 15px;">Deseja apagar este registro permanentemente?</span>
            <button class="btn-delete" onclick="deleteSale()">Excluir Venda</button>
        </div>

    </main>

    <script>
        /* --- JavaScript: Lógica de Venda --- */

        // 1. Estado Inicial (Simulando uma venda carregada do banco de dados)
        // Isso difere da página "Nova Venda", pois já começa com dados.
        let saleItems = [
            { id: 101, type: "Alumínio", weight: 50.00, price: 4.50, total: 225.00 },
            { id: 102, type: "Plástico PET", weight: 100.00, price: 2.10, total: 210.00 }
        ];

        const initialClient = {
            cnpj: "12.345.678/0001-90",
            name: "Supermercados Horizonte Ltda",
            email: "compras@horizonte.com.br"
        };

        // 2. Elementos do DOM
        const cnpjInput = document.getElementById('clientCnpj');
        const nameInput = document.getElementById('clientName');
        const emailInput = document.getElementById('clientEmail');
        const cnpjError = document.getElementById('cnpjError');

        const itemType = document.getElementById('itemType');
        const itemWeight = document.getElementById('itemWeight');
        const itemPrice = document.getElementById('itemPrice');
        const btnAdd = document.getElementById('btnAddItem');
        const tableBody = document.getElementById('tableBody');
        const grandTotalDisplay = document.getElementById('grandTotal');

        // 3. Inicialização
        window.onload = function() {
            // Preencher form de cliente
            cnpjInput.value = initialClient.cnpj;
            nameInput.value = initialClient.name;
            emailInput.value = initialClient.email;

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
            const weight = parseFloat(itemWeight.value);
            const price = parseFloat(itemPrice.value);
            
            const newItem = {
                id: Date.now(), // ID temporário
                type: itemType.value,
                weight: weight,
                price: price,
                total: weight * price
            };

            saleItems.push(newItem);
            renderTable();

            // Limpar form de item
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
                    <td>${item.type}</td>
                    <td>${item.weight.toFixed(2)}</td>
                    <td>R$ ${item.price.toFixed(2)}</td>
                    <td><strong>R$ ${item.total.toFixed(2)}</strong></td>
                    <td>
                        <button class="action-btn text-edit" onclick="editItem(${item.id})">Editar</button>
                        <button class="action-btn text-remove" onclick="removeItem(${item.id})">Remover</button>
                    </td>
                `;
                tableBody.appendChild(tr);
            });

            grandTotalDisplay.innerText = totalSum.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        }

        // 8. Remover Item
        function removeItem(id) {
            saleItems = saleItems.filter(item => item.id !== id);
            renderTable();
        }

        // 9. Editar Item (Recupera dados para o form e remove da tabela)
        function editItem(id) {
            const item = saleItems.find(i => i.id === id);
            if (item) {
                itemType.value = item.type;
                itemWeight.value = item.weight;
                itemPrice.value = item.price;
                
                // Remove da tabela para evitar duplicidade ao adicionar novamente
                removeItem(id);
                
                // Ativa o botão pois os campos estão preenchidos
                checkAddItemValidity();
                
                // Foco visual
                itemType.focus();
            }
        }

        // 10. Ações Globais (Salvar / Excluir)
        function saveChanges() {
            // Lógica simulada de salvamento
            alert("Venda atualizada com sucesso!");
        }

        function deleteSale() {
            const confirmDelete = confirm("Tem certeza que deseja excluir esta venda permanentemente?");
            if (confirmDelete) {
                alert("Venda excluída.");
                window.location.href = "ListarVendas";
            }
        }

    </script>
</body>
</html>