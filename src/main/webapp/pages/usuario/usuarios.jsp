<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.Usuario" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Usuários</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
    <script>const ctx = "${pageContext.request.contextPath}";</script>

</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP Reciclagem</div>
        <div>
            <a href="${pageContext.request.contextPath}/Home">Início</a>
            <a href="#">Relatórios</a>
        </div>
    </nav>

    <main class="container">
        <div class="page-header">
            <h1>Gestão de Usuários</h1>
            <button class="btn-new" onclick="window.location.href='pages/usuario/novoUsuario.jsp'">
                + Novo Usuário
            </button>
        </div>

        <section class="search-card">
            <div class="search-form">
                <div class="form-group">
                    <label for="searchName">Nome</label>
                    <input type="text" id="searchName" placeholder="Ex: Maria Silva">
                </div>
                <div class="form-group">
                    <label for="searchRole">Papel</label>
                    <select id="searchRole">
                        <option value="">Selecione...</option>
                        <option value="administrador">Administrador</option>
                        <option value="gerente">Gerente</option>
                        <option value="operador">Operador</option>
                    </select>
                </div>

                <div class="form-group">
                    <button class="btn-search" onclick="searchUsers()">Pesquisar</button>
                </div>
            </div>
            <div id="feedback-message" style="display: none;">Por favor, preencha pelo menos um campo para realizar a pesquisa.</div>
        </section>

        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Papel</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                
                	<c:forEach items="${listaUsuarios}" var="usuario">
                		
                		<tr>
                			<td><a href="DetalharUsuario?userId=${usuario.id}">${usuario.nome}</a></td>
                			<td>${usuario.email}</td>
                			<td>

                                <c:choose>

                                    <c:when test="${usuario.papel == 'Administrador'}">
                                        <span class="role-badge role-admin">${usuario.papel}</span>
                                    </c:when>

                                    <c:when test="${usuario.papel == 'Gerente'}">
                                        <span class="role-badge role-manager">${usuario.papel}</span>
                                    </c:when>

                                    <c:when test="${usuario.papel == 'Operador'}">
                                        <span class="role-badge role-operator">${usuario.papel}</span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="role-badge role-user">${usuario.papel}</span>
                                    </c:otherwise>

                                </c:choose>

                                <!--
                                <span class="role-badge">${usuario.papel}</span>
                                -->
                            </td>
                		</tr>
                		
                	</c:forEach>
                	
                </tbody>
            </table>
        </section>

    </main>

    <script src="${pageContext.request.contextPath}/assets/_js/script-usuario.js"></script>

</body>
</html>