<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.PrecoMaterial" %>
<%@ page import="com.gestaocooperativareciclagem.model.TipoMaterial" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Preços de Materiais</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
    <script>const ctx = "${pageContext.request.contextPath}";</script>

</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
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
                        <select id="searchMaterial" name="searchMaterial">
                            <option value="">Todos</option>
                            <!-- Preenchido via JS -->
                            <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                            	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                            </c:forEach>
                            
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

					<c:forEach items="${listaPrecosMateriais}" var="precoMaterial">
						<tr>
							<td>
								<span class="price-link" onclick="openPriceModal('edit', {id: ${precoMaterial.id}, price: ${precoMaterial.precoCompra}, date: '${precoMaterial.dtVigencia}', materialId: ${precoMaterial.tipoMaterial.id}})">
									${String.format("R$ %.2f/Kg", precoMaterial.precoCompra)}
								</span>
							</td>
							<td>
								<fmt:formatDate value="${precoMaterial.dtVigencia}" pattern="dd/MM/yyyy" />
							</td>
							<td>
								<span class="material-link" onclick="openMaterialModal({id: ${precoMaterial.tipoMaterial.id}, name: '${precoMaterial.tipoMaterial.nome}', desc: '${precoMaterial.tipoMaterial.descricao}'})">
									${precoMaterial.tipoMaterial.nome}
								</span>
							</td>
						</tr>
					</c:forEach>      
					
					             

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
            	
            	<input type="text" id="modalId" name="modalId" style="display:none;">
            	
                <div class="form-group">
                    <label for="modalPrice">Preço (R$/Kg) *</label>
                    <input type="number" id="modalPrice" name="modalPrice" step="0.01" required>
                </div>
                <div class="form-group">
                    <label for="modalDate">Data de Vigência *</label>
                    <input type="date" id="modalDate" name="modalDate" required>
                </div>
                <div class="form-group">
                    <label for="modalMaterial">Tipo de Material *</label>
                    <select id="modalMaterial" name="modalMaterial" required>
                        <option value="">Selecione...</option>
                        <!-- Preenchido via JS -->
                        <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                            	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                        </c:forEach>
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

    <script src="${pageContext.request.contextPath}/assets/_js/script-preco-material.js"></script>

</body>
</html>