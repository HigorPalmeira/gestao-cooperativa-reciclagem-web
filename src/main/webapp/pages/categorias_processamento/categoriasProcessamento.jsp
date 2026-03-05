<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.CategoriaProcessamento" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Categorias de Processamento</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
        </div>
    </nav>

    <main class="container">
    
    	<c:if test="${not empty sessionScope.msgSucesso}">
    		<div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #c3e6cb;">
    			${sessionScope.msgSucesso}
    		</div>
    		<% session.removeAttribute("msgSucesso"); %>
    	</c:if>
    	
    	<c:if test="${not empty sessionScope.msgErro}">
    		<div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
    			<strong>Erro:</strong> ${sessionScope.msgErro}
    		</div>
    		<% session.removeAttribute("msgErro"); %>
    	</c:if>
        
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
            <div id="feedback-msg" style="display: none;">Preencha pelo menos um campo.</div>
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
                    
					<c:forEach items="${listaCategoriasProcessamento}" var="categoriaProcessamento">
					
						<tr> <!-- .id -->
							<td><span class="name-link" onclick="openModal('edit', {id:${categoriaProcessamento.id}, name: '${categoriaProcessamento.nome}', desc: '${categoriaProcessamento.descricao}'})">${categoriaProcessamento.nome}</span></td>
							<td>${categoriaProcessamento.descricao}</td>
						</tr>
					
					</c:forEach>

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
                
                <input type="hidden" id="modalId" name="modalId">
                
                <div class="form-group" style="margin-bottom: 1rem;">
                    <label for="modalName">Nome <span style="color: red">*</span></label>
                    <input type="text" id="modalName" name="modalName" required>
                </div>
                <div class="form-group">
                    <label for="modalDesc">Descrição <span style="color: red">*</span></label>
                    <textarea id="modalDesc" name="modalDesc" rows="4" required></textarea>
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

    	const ctx = "${pageContext.request.contextPath}";

        // Estado Atual
        let currentEditId = null;

        // Elementos DOM
        const tableBody = document.getElementById('tableBody');
        const modal = document.getElementById('categoryModal');
        const modalTitle = document.getElementById('modalTitle');
        const modalForm = document.getElementById('modalForm');
        const inpId = document.getElementById('modalId');
        const inpName = document.getElementById('modalName');
        const inpDesc = document.getElementById('modalDesc');
        
        // Botões do Modal
        const btnDelete = document.getElementById('btnDelete');
        const btnSave = document.getElementById('btnSave');
        const btnCreate = document.getElementById('btnCreate');

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
                        <span class="name-link" onclick="openModal('edit', \${cat.id})">
                            \${cat.nome}
                        </span>
                    </td>
                    <td>\${cat.descricao}</td>
                `;
                tableBody.appendChild(tr);
            });
        }

        // 3. Abrir Modal (Lógica Central)
        function openModal(mode, cat = null) { // id = null
            modal.style.display = 'flex';
            currentEditId = !cat ? null : cat.id;

            if (mode === 'new') {
                // MODO: NOVA CATEGORIA
                modalTitle.innerText = "Nova Categoria";
            	modalForm.action = ctx + "/InserirCategoriaProcessamento";
            	modalForm.method = "POST";
                inpName.value = "";
                inpDesc.value = "";
                
                // Configuração de Botões
                btnDelete.style.display = 'none'; // Esconde Excluir
                btnSave.style.display = 'none';   // Esconde Salvar
                btnCreate.style.display = 'block'; // Mostra Cadastrar

            } else if (mode === 'edit') {
                // MODO: EDITAR CATEGORIA
                //const cat = categoriesDB.find(c => c.id === id);
                if (!cat) return;

                modalTitle.innerText = "Editar Categoria";
                modalForm.action = "";
                modalForm.method = "POST";
                inpId.value = cat.id;
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
        	inpId.value = "";
        	inpName.value = "";
        	inpDesc.value = "";
            modal.style.display = 'none';
            modalForm.action = "";
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

            	modalForm.action = ctx + "/AtualizarCategoriaProcessamento";
            	modalForm.submit();

            } else {
                
            	modalForm.submit();
            	
            }

            closeModal();
        }

        // 6. Excluir Categoria
        function deleteCategory() {
            if (!currentEditId) return;

            const confirmDelete = confirm("Tem a certeza que deseja excluir esta categoria?\nEsta ação não pode ser desfeita.");
            
            if (confirmDelete) {

				modalForm.action = ctx + "/DeletarCategoriaProcessamento";
				modalForm.submit();
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
            
            let parametros = `nome=\${sName}&descricao=\${sDesc}`;
            
            fetch((ctx + '/ListagemCategoriasProcessamento?' + parametros))
            	.then(response => {
            		
            		if (!response.ok) {
            			return response.json().then(err => {
            				throw new Error(err.error);
            			});
            		}
            		
            		return response.json();
            		
            	})
            	.then(data => {
            		console.log('Categorias: ', data);
            		renderTable(data);
            	})
            	.catch(error => {
            		console.error('Erro no fetch:', error.message);
            		alert('Erro: ' + error.message);
            	})
/*
            const filtered = categoriesDB.filter(cat => {
                const matchName = cat.name.toLowerCase().includes(sName);
                const matchDesc = cat.desc.toLowerCase().includes(sDesc);
                return matchName && matchDesc; // Lógica E, pode ser alterada para OU se preferir
            });
*/
            // renderTable(filtered);
        }

    </script>
</body>
</html>