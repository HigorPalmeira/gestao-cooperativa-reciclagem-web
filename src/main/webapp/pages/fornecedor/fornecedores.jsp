<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.Fornecedor" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Fornecedores</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">

    <script>const ctx = "${pageContext.request.contextPath}";</script>
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a>
            <a href="${pageContext.request.contextPath}/ListarTransacoesCompra">Transações de Compra</a>
        </div>
    </nav>

    <main class="container">
        <div class="page-header">
            <h1>Gestão de Fornecedores</h1>
            <button class="btn-new" onclick="window.location.href='pages/fornecedor/novoFornecedor.jsp'"> <!-- '/criar-fornecedor' -->
                + Novo Fornecedor
            </button>
        </div>

        <section class="search-card">
            <div class="search-form">
                <div class="form-group">
                    <label for="searchName">Nome do Fornecedor</label>
                    <input type="text" id="searchName" placeholder="Digite o nome...">
                </div>
                <div class="form-group">
                    <label for="searchType">Tipo de Fornecedor</label>
                    <select id="searchType">
                        <option value="">Selecione...</option>
                        <option value="COLETOR">Coletor</option>
                        <option value="EMPRESA">Empresa</option>
                        <option value="MUNICIPIO">Municipio</option>
                    </select>
                </div>
                <button class="btn-search" onclick="searchSuppliers()">Pesquisar</button>
            </div>
            <div id="feedback-message">Preencha pelo menos um campo para pesquisar.</div>
        </section>

        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Documento (CNPJ/CPF)</th>
                        <th>Nome do Fornecedor</th>
                        <th>Tipo de Fornecedor</th>
                        <th>Data de Cadastro</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                	<c:forEach items="${listaFornecedores}" var="fornecedor">
                		<tr>
                			<td><a href="${pageContext.request.contextPath}/DetalharFornecedor?doc=${fornecedor.documento}">${fornecedor.documento}</a></td>
                			<td>${fornecedor.nome}</td>
                			<td>${fornecedor.tipo}</td>
                			<td>${fornecedor.dtCadastro}</td>
                		</tr>
                    </c:forEach>
                </tbody>
            </table>
        </section>

        <div class="quick-links">
            Navegar para: 
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a> | 
            <a href="${pageContext.request.contextPath}/ListarTransacoesCompra">Transações de Compra</a>
        </div>
    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-fornecedor.js"></script>
</body>
</html>