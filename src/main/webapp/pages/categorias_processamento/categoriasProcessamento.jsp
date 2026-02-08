<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Categorias de Processamento</title>
    
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
            --text-color: #333;
            --overlay-color: rgba(0, 0, 0, 0.5);
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
            gap: 1.5rem;
            align-items: flex-end;
            flex-wrap: wrap;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            flex: 1;
            min-width: 250px;
        }

        .form-group label {
            margin-bottom: 0.5rem;
            font-weight: 600;
            font-size: 0.9rem;
            color: #555;
        }

        .form-group input, .form-group textarea {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            width: 100%;
            box-sizing: border-box; /* Garante que padding não quebre layout */
        }

        .btn-search {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            height: 42px;
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

        /* Link no Nome (Trigger do Modal) */
        .name-link {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: bold;
            cursor: pointer;
        }
        .name-link:hover { text-decoration: underline; }

        /* --- Estilos do MODAL --- */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: var(--overlay-color);
            display: none; /* Oculto por padrão */
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

        .close-icon {
            cursor: pointer;
            font-size: 1.5rem;
            color: #999;
        }
        .close-icon:hover { color: #333; }

        /* Rodapé do Modal (Botões) */
        .modal-footer {
            margin-top: 2rem;
            display: flex;
            justify-content: space-between; /* Botões nas extremidades */
            align-items: center;
            border-top: 1px solid #eee;
            padding-top: 1rem;
        }

        /* Botões do Modal */
        .btn-modal-save {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            margin-left: auto; /* Empurra para a direita se estiver sozinho */
        }
        .btn-modal-create {
            background-color: var(--success-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            margin-left: auto; 
        }

        .btn-modal-delete {
            background-color: transparent;
            color: var(--danger-color);
            border: 1px solid var(--danger-color);
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .btn-modal-delete:hover { background-color: var(--danger-color); color: white; }

        #feedback-msg { display: none; color: #dc3545; margin-top: 10px; }

    </style>
    
    -->
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="../index.html">Início</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Categorias de Processamento</h1>
            <!-- Botão Nova Categoria (Trigger) -->
            <button class="btn-new" onclick="openModal('new')">
                + Nova Categoria
            </button>
        </div>

        <!-- Formulário de Pesquisa -->
        <section class="search-card">
            <div class="search-form">
                <div class="form-group">
                    <label for="searchName">Nome</label>
                    <input type="text" id="searchName" placeholder="Pesquisar por nome...">
                </div>
                <div class="form-group">
                    <label for="searchDesc">Descrição</label>
                    <input type="text" id="searchDesc" placeholder="Pesquisar por descrição...">
                </div>
                <button class="btn-search" onclick="handleSearch()">Pesquisar</button>
            </div>
            <div id="feedback-msg">Preencha pelo menos um campo.</div>
        </section>

        <!-- Tabela de Resultados -->
        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th style="width: 30%;">Nome</th>
                        <th style="width: 70%;">Descrição</th>
                    </tr>
                </thead>
                <tbody id="tableBody">
                    <!-- Preenchido via JS -->
                </tbody>
            </table>
        </section>

    </main>

    <!-- MODAL DE EDIÇÃO/CRIAÇÃO -->
    <div id="categoryModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2 id="modalTitle">Categoria</h2>
                <span class="close-icon" onclick="closeModal()">&times;</span>
            </div>

            <form id="modalForm" onsubmit="handleFormSubmit(event)">
                <div class="form-group" style="margin-bottom: 1rem;">
                    <label for="modalName">Nome <span style="color: red">*</span></label>
                    <input type="text" id="modalName" required>
                </div>
                <div class="form-group">
                    <label for="modalDesc">Descrição <span style="color: red">*</span></label>
                    <textarea id="modalDesc" rows="4" required></textarea>
                </div>

                <!-- Botões do Rodapé (Visibilidade controlada por JS) -->
                <div class="modal-footer">
                    <!-- Botão Esquerda: Excluir -->
                    <button type="button" id="btnDelete" class="btn-modal-delete" onclick="deleteCategory()">
                        Excluir Categoria
                    </button>

                    <!-- Botão Direita: Salvar Alterações -->
                    <button type="submit" id="btnSave" class="btn-modal-save">
                        Salvar Alterações
                    </button>

                    <!-- Botão Direita: Cadastrar (Alternativo ao Salvar) -->
                    <button type="submit" id="btnCreate" class="btn-modal-create">
                        Cadastrar
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        /* --- Lógica da Aplicação --- */

        // 1. Dados Simulados
        let categoriesDB = [
            { id: 1, name: "Triagem", desc: "Separação inicial de materiais recicláveis por tipo e remoção de contaminantes grosseiros." },
            { id: 2, name: "Lavagem", desc: "Processo de limpeza com água e soluções químicas para remover sujidade e rótulos." },
            { id: 3, name: "Trituração", desc: "Redução do material em pequenos fragmentos ou flakes para processamento posterior." },
            { id: 4, name: "Extrusão", desc: "Derretimento e moldagem do plástico em novos formatos ou pellets." },
            { id: 5, name: "Prensagem", desc: "Compactação do material para facilitar o transporte e armazenamento." }
        ];

        // Estado Atual
        let currentEditId = null;

        // Elementos DOM
        const tableBody = document.getElementById('tableBody');
        const modal = document.getElementById('categoryModal');
        const modalTitle = document.getElementById('modalTitle');
        const inpName = document.getElementById('modalName');
        const inpDesc = document.getElementById('modalDesc');
        
        // Botões do Modal
        const btnDelete = document.getElementById('btnDelete');
        const btnSave = document.getElementById('btnSave');
        const btnCreate = document.getElementById('btnCreate');

        // Inicializar tabela
        window.onload = () => renderTable(categoriesDB);

        // 2. Renderizar Tabela
        function renderTable(data) {
            tableBody.innerHTML = '';
            
            if (data.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="2" style="text-align:center; padding: 2rem; color: #777;">Nenhuma categoria encontrada.</td></tr>';
                return;
            }

            data.forEach(cat => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>
                        <span class="name-link" onclick="openModal('edit', ${cat.id})">
                            ${cat.name}
                        </span>
                    </td>
                    <td>${cat.desc}</td>
                `;
                tableBody.appendChild(tr);
            });
        }

        // 3. Abrir Modal (Lógica Central)
        function openModal(mode, id = null) {
            modal.style.display = 'flex';
            currentEditId = id;

            if (mode === 'new') {
                // MODO: NOVA CATEGORIA
                modalTitle.innerText = "Nova Categoria";
                inpName.value = "";
                inpDesc.value = "";
                
                // Configuração de Botões
                btnDelete.style.display = 'none'; // Esconde Excluir
                btnSave.style.display = 'none';   // Esconde Salvar
                btnCreate.style.display = 'block'; // Mostra Cadastrar

            } else if (mode === 'edit') {
                // MODO: EDITAR CATEGORIA
                const cat = categoriesDB.find(c => c.id === id);
                if (!cat) return;

                modalTitle.innerText = "Editar Categoria";
                inpName.value = cat.name;
                inpDesc.value = cat.desc;

                // Configuração de Botões
                btnDelete.style.display = 'block'; // Mostra Excluir (Esquerda)
                btnSave.style.display = 'block';   // Mostra Salvar (Direita)
                btnCreate.style.display = 'none';  // Esconde Cadastrar
            }
        }

        // 4. Fechar Modal
        function closeModal() {
            modal.style.display = 'none';
            currentEditId = null;
        }

        // Fechar ao clicar fora
        window.onclick = function(event) {
            if (event.target == modal) closeModal();
        }

        // 5. Submissão do Formulário (Salvar ou Cadastrar)
        function handleFormSubmit(event) {
            event.preventDefault(); // Impede reload da página

            const newName = inpName.value.trim();
            const newDesc = inpDesc.value.trim();

            if (currentEditId) {
                // Lógica de ATUALIZAR (Salvar Alterações)
                const index = categoriesDB.findIndex(c => c.id === currentEditId);
                if (index !== -1) {
                    categoriesDB[index].name = newName;
                    categoriesDB[index].desc = newDesc;
                    alert("Categoria atualizada com sucesso!");
                }
            } else {
                // Lógica de CRIAR (Cadastrar)
                const newId = categoriesDB.length > 0 ? Math.max(...categoriesDB.map(c => c.id)) + 1 : 1;
                categoriesDB.push({
                    id: newId,
                    name: newName,
                    desc: newDesc
                });
                alert("Nova categoria cadastrada com sucesso!");
            }

            // Atualiza interface
            renderTable(categoriesDB);
            closeModal();
        }

        // 6. Excluir Categoria
        function deleteCategory() {
            if (!currentEditId) return;

            const confirmDelete = confirm("Tem a certeza que deseja excluir esta categoria?\nEsta ação não pode ser desfeita.");
            
            if (confirmDelete) {
                categoriesDB = categoriesDB.filter(c => c.id !== currentEditId);
                alert("Categoria excluída.");
                renderTable(categoriesDB);
                closeModal();
            }
        }

        // 7. Pesquisa
        function handleSearch() {
            const sName = document.getElementById('searchName').value.toLowerCase();
            const sDesc = document.getElementById('searchDesc').value.toLowerCase();
            const feedback = document.getElementById('feedback-msg');

            // Validação mínima
            if (!sName && !sDesc) {
                feedback.style.display = 'block';
                return;
            } else {
                feedback.style.display = 'none';
            }

            const filtered = categoriesDB.filter(cat => {
                const matchName = cat.name.toLowerCase().includes(sName);
                const matchDesc = cat.desc.toLowerCase().includes(sDesc);
                return matchName && matchDesc; // Lógica E, pode ser alterada para OU se preferir
            });

            renderTable(filtered);
        }

    </script>
</body>
</html>