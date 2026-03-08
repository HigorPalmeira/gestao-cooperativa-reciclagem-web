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
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
    <script>const ctx = "${pageContext.request.contextPath}";</script>
    
</head>
<body>

    <!-- Navegação Global -->
    <nav class="main-nav">
        <div class="brand">ERP Reciclagem</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="${pageContext.request.contextPath}/ListarPrecosMateriais">Preços de Materiais</a>
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

                <div class="form-group">
                    <button class="btn-search" onclick="handleSearch()">Pesquisar</button>
                </div>
            </div>
            <div id="feedback-msg" style="display: none;">Preencha pelo menos um campo para realizar a pesquisa.</div>
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
                    	<tr>
                    		<td><span class="name-link" onclick="openModal('edit', {id: ${tipoMaterial.id}, name: '${tipoMaterial.nome}', desc: '${tipoMaterial.descricao}'})">${tipoMaterial.nome}</span></td>
                    		<td>${tipoMaterial.descricao}</td>
                    	</tr>
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
                
                <input type="hidden" id="modalId" name="modalId" style="display: none;">
                
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

	<script src="${pageContext.request.contextPath}/assets/_js/script-tipo-material.js"></script>

</body>
</html>