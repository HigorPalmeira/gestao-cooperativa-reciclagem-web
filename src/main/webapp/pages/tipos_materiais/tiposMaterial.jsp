<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.TipoMaterial" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Tipos de Materiais</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="index.jsp">Início</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Tipos de Materiais</h1>
            <!-- Botão Novo Tipo (Trigger) -->
            <button class="btn-new" onclick="openModal('new')">
                + Novo Tipo
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
            <div id="feedback-msg">Preencha pelo menos um campo para realizar a pesquisa.</div>
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
                    
                    <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                    	<td><span class="name-link" onclick="openModal('edit', {id: ${tipoMaterial.id}, name: '${tipoMaterial.nome}', desc: '${tipoMaterial.descricao}'})">${tipoMaterial.nome}</span></td>
                    	<td>${tipoMaterial.descricao}</td>
                    </c:forEach>

                </tbody>
            </table>
        </section>

    </main>

    <!-- MODAL DE EDIÇÃO/CRIAÇÃO -->
    <div id="materialModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2 id="modalTitle">Tipo de Material</h2>
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
                    <button type="button" id="btnDelete" class="btn-modal-delete" onclick="deleteType()">
                        Excluir Tipo
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

        // 1. Dados Simulados (Mock Data)
        /*
        let materialsDB = [
            { id: 1, name: "Plástico PET", desc: "Polietileno Tereftalato, comumente usado em garrafas de bebidas e embalagens." },
            { id: 2, name: "Plástico HDPE", desc: "Polietileno de Alta Densidade, resistente, usado em frascos de detergente e tubos." },
            { id: 3, name: "Alumínio", desc: "Metal leve e reciclável, proveniente de latas de bebidas e esquadrias." },
            { id: 4, name: "Papelão", desc: "Papel de alta gramagem, proveniente de caixas de transporte e embalagens." },
            { id: 5, name: "Cobre", desc: "Metal condutor avermelhado, proveniente de fios elétricos e tubagens." }
        ];
        */

        // Estado Atual
        let currentEditId = null;

        // Elementos DOM
        const tableBody = document.getElementById('tableBody');
        const modal = document.getElementById('materialModal');
        const modalTitle = document.getElementById('modalTitle');
        const inpName = document.getElementById('modalName');
        const inpDesc = document.getElementById('modalDesc');
        
        // Botões do Modal
        const btnDelete = document.getElementById('btnDelete');
        const btnSave = document.getElementById('btnSave');
        const btnCreate = document.getElementById('btnCreate');

        // Inicializar tabela
        // window.onload = () => renderTable(materialsDB);

        // 2. Renderizar Tabela
        function renderTable(data) {
            tableBody.innerHTML = '';
            
            if (data.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="2" style="text-align:center; padding: 2rem; color: #777;">Nenhum tipo de material encontrado.</td></tr>';
                return;
            }

            data.forEach(item => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>
                        <span class="name-link" onclick="openModal('edit', ${item.id})">
                            ${item.name}
                        </span>
                    </td>
                    <td>${item.desc}</td>
                `;
                tableBody.appendChild(tr);
            });
        }

        // 3. Abrir Modal (Lógica Central)
        function openModal(mode, item = null) { // id = null
            modal.style.display = 'flex';
            currentEditId = !item ? null : item.id;

            if (mode === 'new') {
                // MODO: NOVO TIPO
                modalTitle.innerText = "Novo Tipo";
                inpName.value = "";
                inpDesc.value = "";
                
                // Configuração de Botões
                btnDelete.style.display = 'none'; // Esconde Excluir
                btnSave.style.display = 'none';   // Esconde Salvar
                btnCreate.style.display = 'block'; // Mostra Cadastrar

            } else if (mode === 'edit') {
                // MODO: EDITAR TIPO
                // const item = materialsDB.find(m => m.id === id);
                if (!item) return;

                modalTitle.innerText = "Editar Tipo";
                inpName.value = item.name;
                inpDesc.value = item.desc;

                // Configuração de Botões
                btnDelete.style.display = 'block'; // Mostra Excluir (Esquerda)
                btnSave.style.display = 'block';   // Mostra Salvar (Direita)
                btnCreate.style.display = 'none';  // Esconde Cadastrar
            }
        }

        // 4. Fechar Modal
        function closeModal() {
        	inpName.value = "";
            inpDesc.value = "";
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
                const index = 0; // materialsDB.findIndex(m => m.id === currentEditId);
                if (index !== -1) {
                	/*
                    materialsDB[index].name = newName;
                    materialsDB[index].desc = newDesc;
                    */
                    alert("CRIAR REQUISIÇÃO DE ATUALIZAÇÃO.");
                }
            } else {
                // Lógica de CRIAR (Cadastrar)
                const newId = materialsDB.length > 0 ? Math.max(...materialsDB.map(m => m.id)) + 1 : 1;
                materialsDB.push({
                    id: newId,
                    name: newName,
                    desc: newDesc
                });
                alert("Novo tipo de material cadastrado com sucesso!");
            }

            // Atualiza interface
            renderTable(materialsDB);
            closeModal();
        }

        // 6. Excluir Tipo
        function deleteType() {
            if (!currentEditId) return;

            const confirmDelete = confirm("Tem a certeza que deseja excluir este tipo de material?\nEsta ação não pode ser desfeita.");
            
            if (confirmDelete) {
                materialsDB = materialsDB.filter(m => m.id !== currentEditId);
                alert("Tipo de material excluído.");
                renderTable(materialsDB);
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

            const filtered = materialsDB.filter(item => {
                const matchName = item.name.toLowerCase().includes(sName);
                const matchDesc = item.desc.toLowerCase().includes(sDesc);
                return matchName && matchDesc;
            });

            renderTable(filtered);
        }

    </script>
</body>
</html>