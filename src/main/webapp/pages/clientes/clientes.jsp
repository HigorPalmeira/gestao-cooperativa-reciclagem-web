<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.Cliente" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Clientes</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
     
    <script>const ctx = "${pageContext.request.contextPath}";</script>

</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP Reciclagem</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="${pageContext.request.contextPath}/ListarVendas">Vendas</a>
            <a href="#">Relatórios</a>
        </div>
    </nav>

    <main class="container">
        <div class="page-header">
            <h1>Gestão de Clientes</h1>
            <button class="btn-new" onclick="window.location.href='pages/clientes/novoCliente.jsp'">
                + Novo Cliente
            </button>
        </div>

        <section class="search-card">
            <div class="search-form">
                <div class="form-group">
                    <label for="searchCnpj">CNPJ</label>
                    <input type="text" id="searchCnpj" placeholder="00.000.000/0000-00">
                </div>
                <div class="form-group">
                    <label for="searchName">Nome</label>
                    <input type="text" id="searchName" placeholder="Nome do cliente...">
                </div>
                <div class="form-group">
                    <label for="searchEmail">Email</label>
                    <input type="email" id="searchEmail" placeholder="contato@empresa.com">
                </div>
                <button class="btn-search" onclick="searchClients()">Pesquisar</button>
            </div>
            <div id="feedback-message" style="display: none;">Preencha pelo menos um campo para realizar a pesquisa.</div>
        </section>

        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>CNPJ</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Data de Cadastro</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                
                	<c:forEach items="${listaClientes}" var="cliente">
                		<tr>
                			<td><a href="DetalharCliente?cnpj=${cliente.cnpj}" class="cnpj-link">${cliente.cnpj}</a></td>
                			<td>${cliente.nomeEmpresa}</td>
                			<td>${cliente.emailContato}</td>
                			<td>${cliente.dtCadastro}</td>
                		</tr>
                	</c:forEach>

                </tbody>
            </table>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-cliente.js"></script>

</body>
</html>