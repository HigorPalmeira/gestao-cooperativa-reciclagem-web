<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Preços de Materiais</title>
    
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
            --text-color: #333;
            --overlay-color: rgba(0, 0, 0, 0.5);
            --disabled-bg: #e9ecef;
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
            justify-content: space-between;
            align-items: center;
        }

        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; }
        nav.main-nav a { 
            color: #fff; text-decoration: none; font-size: 0.9rem; margin-left: 20px; cursor: pointer; opacity: 0.9;
        }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        /* Cabeçalho e Botão Novo */
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        h1 { margin: 0; font-size: 1.75rem; color: #2c3e50; }

        .btn-new {
            background-color: var(--success-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.2s;
        }
        .btn-new:hover { background-color: #218838; }

        /* Formulário de Pesquisa */
        .search-card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 1.5rem;
            border: 1px solid var(--border-color);
        }

        .search-form {
            display: flex;
            gap: 2rem;
            align-items: flex-end;
            flex-wrap: wrap;
        }

        .filter-group {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .filter-group label {
            font-weight: 600;
            font-size: 0.9rem;
            color: #555;
        }

        .inputs-row {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .inputs-row input, .inputs-row select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 0.95rem;
            min-width: 140px;
        }

        .separator { color: #888; font-size: 0.9rem; }

        .btn-search {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            height: 42px;
            margin-left: auto;
        }
        .btn-search:hover { background-color: #004494; }

        /* Tabela de Resultados */
        .table-container {
            background-color: var(--white);
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            overflow-x: auto;
            border: 1px solid var(--border-color);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            min-width: 600px;
        }

        th, td {
            text-align: left;
            padding: 12px 20px;
            border-bottom: 1px solid var(--border-color);
        }

        th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
        tr:hover { background-color: #f1f1f1; }

        /* Links Interativos na Tabela */
        .price-link {
            color: var(--primary-color);
            font-weight: bold;
            cursor: pointer;
            text-decoration: none;
        }
        .price-link:hover { text-decoration: underline; }

        .material-link {
            color: #495057; /* Cor neutra mas interativa */
            cursor: pointer;
            text-decoration: underline;
            text-decoration-style: dotted;
        }
        .material-link:hover { color: var(--primary-color); text-decoration-style: solid; }

        /* --- Estilos dos MODAIS --- */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: var(--overlay-color);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 1000;
        }

        .modal-content {
            background-color: var(--white);
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
            width: 90%;
            max-width: 500px;
            position: relative;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
            border-bottom: 1px solid #eee;
            padding-bottom: 0.5rem;
        }

        .modal-header h2 { margin: 0; font-size: 1.5rem; color: #333; }
        .close-icon { cursor: pointer; font-size: 1.5rem; color: #999; }
        .close-icon:hover { color: #333; }

        .form-group { margin-bottom: 1rem; display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; }
        .form-group input, .form-group select, .form-group textarea {
            padding: 10px; border: 1px solid var(--border-color); border-radius: 4px; width: 100%; box-sizing: border-box;
        }

        /* Inputs Readonly */
        input:disabled, select:disabled, textarea:disabled {
            background-color: var(--disabled-bg); color: #6c757d; border-color: #ced4da; cursor: not-allowed;
        }

        /* Botões do Modal */
        .modal-footer {
            margin-top: 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-top: 1px solid #eee;
            padding-top: 1rem;
        }

        .btn-modal-save { background-color: var(--primary-color); color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; margin-left: auto; }
        .btn-modal-create { background-color: var(--success-color); color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-weight: bold; margin-left: auto; }
        
        .btn-modal-delete {
            background-color: transparent; color: var(--danger-color); border: 1px solid var(--danger-color); padding: 10px 20px; border-radius: 4px; cursor: pointer; font-weight: bold;
        }
        .btn-modal-delete:hover { background-color: var(--danger-color); color: white; }

        /* Aviso de Bloqueio */
        .lock-warning {
            background-color: #fff3cd; color: #856404; padding: 10px; border-radius: 4px; font-size: 0.9rem; margin-bottom: 15px; display: none; border: 1px solid #ffeeba;
        }

    </style>
     -->
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="../index.html" onclick="alert('Voltar para Início')">Início</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Preços de Materiais</h1>
            <button class="btn-new" onclick="openPriceModal('new')">
                + Novo Preço
            </button>
        </div>

        <!-- Filtros -->
        <section class="search-card">
            <div class="search-form">
                <!-- Data -->
                <div class="filter-group">
                    <label>Intervalo de Datas (Vigência)</label>
                    <div class="inputs-row">
                        <input type="date" id="searchDateStart">
                        <span class="separator">até</span>
                        <input type="date" id="searchDateEnd">
                    </div>
                </div>
                <!-- Valor -->
                <div class="filter-group">
                    <label>Intervalo de Valores (R$)</label>
                    <div class="inputs-row">
                        <input type="number" id="searchValMin" placeholder="Mín" step="0.01">
                        <span class="separator">até</span>
                        <input type="number" id="searchValMax" placeholder="Máx" step="0.01">
                    </div>
                </div>
                <!-- Material -->
                <div class="filter-group">
                    <label>Tipo de Material</label>
                    <div class="inputs-row">
                        <select id="searchMaterial">
                            <option value="">Todos</option>
                            <!-- Preenchido via JS -->
                        </select>
                    </div>
                </div>

                <button class="btn-search" onclick="handleSearch()">Pesquisar</button>
            </div>
        </section>

        <!-- Tabela -->
        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th style="width: 30%;">Preço (R$/Kg)</th>
                        <th style="width: 30%;">Data de Vigência</th>
                        <th style="width: 40%;">Tipo de Material</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!-- Preenchido via JS -->
                </tbody>
            </table>
        </section>

    </main>

    <!-- MODAL 1: PREÇO (Edição/Criação) -->
    <div id="priceModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2 id="priceModalTitle">Preço de Material</h2>
                <span class="close-icon" onclick="closeModal('price')">&times;</span>
            </div>

            <!-- Aviso de data passada -->
            <div id="dateLockWarning" class="lock-warning">
                A data de vigência já ocorreu. A edição não é permitida.
            </div>

            <form id="priceForm" onsubmit="handlePriceSubmit(event)">
                <div class="form-group">
                    <label for="modalPrice">Preço (R$/Kg) *</label>
                    <input type="number" id="modalPrice" step="0.01" required>
                </div>
                <div class="form-group">
                    <label for="modalDate">Data de Vigência *</label>
                    <input type="date" id="modalDate" required>
                </div>
                <div class="form-group">
                    <label for="modalMaterial">Tipo de Material *</label>
                    <select id="modalMaterial" required>
                        <option value="">Selecione...</option>
                        <!-- Preenchido via JS -->
                    </select>
                </div>

                <div class="modal-footer">
                    <button type="button" id="btnDeletePrice" class="btn-modal-delete" onclick="deletePrice()">Excluir Preço</button>
                    <button type="submit" id="btnSavePrice" class="btn-modal-save">Salvar Alterações</button>
                    <button type="submit" id="btnCreatePrice" class="btn-modal-create">Cadastrar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- MODAL 2: TIPO DE MATERIAL (Leitura) -->
    <div id="materialInfoModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Detalhes do Material</h2>
                <span class="close-icon" onclick="closeModal('material')">&times;</span>
            </div>
            <div class="form-group">
                <label>Nome</label>
                <input type="text" id="infoMatName" disabled>
            </div>
            <div class="form-group">
                <label>Descrição</label>
                <textarea id="infoMatDesc" rows="4" disabled></textarea>
            </div>
            <div class="modal-footer" style="justify-content: flex-end;">
                <button type="button" class="btn-modal-save" style="background-color: #6c757d;" onclick="closeModal('material')">Fechar</button>
            </div>
        </div>
    </div>

    <script>
        /* --- Lógica da Aplicação --- */

        // 1. Dados Simulados
        const materialsDB = [
            { id: 1, name: "Plástico PET", desc: "Polímero termoplástico da família dos poliésteres." },
            { id: 2, name: "Alumínio", desc: "Metal leve, macio e resistente. Altamente reciclável." },
            { id: 3, name: "Cobre", desc: "Metal de transição avermelhado, excelente condutor." }
        ];

        let pricesDB = [
            { id: 101, price: 2.50, date: "2025-01-15", materialId: 1 }, // Passado
            { id: 102, price: 4.80, date: "2026-05-20", materialId: 2 }, // Futuro
            { id: 103, price: 15.00, date: "2026-06-01", materialId: 3 } // Futuro
        ];

        // Estado
        let currentEditPriceId = null;

        // Elementos Globais
        const tableBody = document.getElementById('tableBody');
        
        // Inicialização
        window.onload = () => {
            populateMaterialSelects();
            renderTable(pricesDB);
        };

        // Preencher selects com materiais
        function populateMaterialSelects() {
            const options = materialsDB.map(m => `<option value="${m.id}">${m.name}</option>`).join('');
            document.getElementById('searchMaterial').innerHTML += options;
            document.getElementById('modalMaterial').innerHTML += options;
        }

        // 2. Renderizar Tabela
        function renderTable(data) {
            tableBody.innerHTML = '';

            if (data.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="3" style="text-align:center; padding: 2rem; color: #777;">Nenhum registo encontrado.</td></tr>';
                return;
            }

            data.forEach(item => {
                const material = materialsDB.find(m => m.id === item.materialId);
                const matName = material ? material.name : 'Desconhecido';
                
                // Formatações
                const fmtPrice = item.price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + "/Kg";
                const fmtDate = new Date(item.date).toLocaleDateString('pt-BR', {timeZone: 'UTC'});

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>
                        <span class="price-link" onclick="openPriceModal('edit', ${item.id})">
                            ${fmtPrice}
                        </span>
                    </td>
                    <td>${fmtDate}</td>
                    <td>
                        <span class="material-link" onclick="openMaterialModal(${item.materialId})">
                            ${matName}
                        </span>
                    </td>
                `;
                tableBody.appendChild(tr);
            });
        }

        // 3. Lógica Modal Preço
        function openPriceModal(mode, id = null) {
            const modal = document.getElementById('priceModal');
            const title = document.getElementById('priceModalTitle');
            const inpPrice = document.getElementById('modalPrice');
            const inpDate = document.getElementById('modalDate');
            const inpMat = document.getElementById('modalMaterial');
            const warning = document.getElementById('dateLockWarning');

            // Botões
            const btnDel = document.getElementById('btnDeletePrice');
            const btnSave = document.getElementById('btnSavePrice');
            const btnCreate = document.getElementById('btnCreatePrice');

            currentEditPriceId = id;
            modal.style.display = 'flex';

            // Resetar estados
            inpPrice.disabled = false;
            inpDate.disabled = false;
            inpMat.disabled = false;
            warning.style.display = 'none';

            if (mode === 'new') {
                title.innerText = "Novo Preço";
                inpPrice.value = "";
                inpDate.value = "";
                inpMat.value = "";

                btnDel.style.display = 'none';
                btnSave.style.display = 'none';
                btnCreate.style.display = 'block';

            } else {
                title.innerText = "Editar Preço";
                const item = pricesDB.find(p => p.id === id);
                if (!item) return;

                inpPrice.value = item.price;
                inpDate.value = item.date;
                inpMat.value = item.materialId;

                // Verificar Data de Vigência
                const today = new Date();
                today.setHours(0,0,0,0);
                const vigencia = new Date(item.date);
                vigencia.setHours(0,0,0,0); // Ignorar hora, comparar apenas data

                // Se vigência já ocorreu (< hoje), bloqueia edição
                if (vigencia < today) {
                    warning.style.display = 'block';
                    inpPrice.disabled = true;
                    inpDate.disabled = true;
                    inpMat.disabled = true;
                    btnSave.style.display = 'none'; // Não pode salvar
                    // Nota: O prompt diz "solicitará confirmação... para excluir", não diz que exclusão é bloqueada, mas edição sim. 
                    // Vou manter Excluir visível, mas edição bloqueada.
                } else {
                    btnSave.style.display = 'block';
                }

                btnDel.style.display = 'block';
                btnCreate.style.display = 'none';
            }
        }

        // 4. Lógica Modal Material (Read-only)
        function openMaterialModal(matId) {
            const modal = document.getElementById('materialInfoModal');
            const item = materialsDB.find(m => m.id === matId);
            
            if (item) {
                document.getElementById('infoMatName').value = item.name;
                document.getElementById('infoMatDesc').value = item.desc;
                modal.style.display = 'flex';
            }
        }

        function closeModal(type) {
            if (type === 'price') document.getElementById('priceModal').style.display = 'none';
            if (type === 'material') document.getElementById('materialInfoModal').style.display = 'none';
        }

        // 5. CRUD Operations
        function handlePriceSubmit(event) {
            event.preventDefault();

            const priceVal = parseFloat(document.getElementById('modalPrice').value);
            const dateVal = document.getElementById('modalDate').value;
            const matId = parseInt(document.getElementById('modalMaterial').value);

            if (currentEditPriceId) {
                // Editar
                const index = pricesDB.findIndex(p => p.id === currentEditPriceId);
                if (index !== -1) {
                    pricesDB[index].price = priceVal;
                    pricesDB[index].date = dateVal;
                    pricesDB[index].materialId = matId;
                    alert("Preço atualizado com sucesso!");
                }
            } else {
                // Criar
                const newId = Date.now(); // Simples ID
                pricesDB.push({ id: newId, price: priceVal, date: dateVal, materialId: matId });
                alert("Preço cadastrado com sucesso!");
            }

            renderTable(pricesDB);
            closeModal('price');
        }

        function deletePrice() {
            if (!currentEditPriceId) return;
            if (confirm("Tem a certeza que deseja excluir este registo de preço?")) {
                pricesDB = pricesDB.filter(p => p.id !== currentEditPriceId);
                alert("Registo excluído.");
                renderTable(pricesDB);
                closeModal('price');
            }
        }

        // 6. Pesquisa
        function handleSearch() {
            const dateStart = document.getElementById('searchDateStart').value;
            const dateEnd = document.getElementById('searchDateEnd').value;
            const valMin = document.getElementById('searchValMin').value;
            const valMax = document.getElementById('searchValMax').value;
            const matId = document.getElementById('searchMaterial').value;

            // Validação simples: exigir pelo menos 1
            if (!dateStart && !dateEnd && !valMin && !valMax && !matId) {
                alert("Preencha pelo menos um campo para pesquisar.");
                return;
            }

            const filtered = pricesDB.filter(item => {
                let validDate = true;
                let validVal = true;
                let validMat = true;

                if (dateStart) validDate = validDate && (new Date(item.date) >= new Date(dateStart));
                if (dateEnd) validDate = validDate && (new Date(item.date) <= new Date(dateEnd));

                if (valMin) validVal = validVal && (item.price >= parseFloat(valMin));
                if (valMax) validVal = validVal && (item.price <= parseFloat(valMax));

                if (matId) validMat = validMat && (item.materialId == matId);

                return validDate && validVal && validMat;
            });

            renderTable(filtered);
        }

        // Fechar modais ao clicar fora
        window.onclick = function(event) {
            if (event.target.classList.contains('modal-overlay')) {
                event.target.style.display = 'none';
            }
        }

    </script>
</body>
</html>