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
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="index.jsp">Início</a>
            <a href="#">Relatórios</a>
            <a href="#">Configurações</a>
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
                        <option value="Administrador">Administrador</option>
                        <option value="Gerente">Gerente</option>
                        <option value="Operador">Operador</option>
                    </select>
                </div>
                <button class="btn-search" onclick="searchUsers()">Pesquisar</button>
            </div>
            <div id="feedback-message">Por favor, preencha pelo menos um campo para realizar a pesquisa.</div>
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
                			<td><a href="DetalharUsuario?id=${usuario.id}">${usuario.nome}</a></td>
                			<td>${usuario.email}</td>
                			<td><span class="role-badge">${usuario.papel}</span></td>
                		</tr>
                		
                	</c:forEach>
                	
                </tbody>
            </table>
        </section>

    </main>

    <script>
        /* --- JavaScript: Lógica --- */

        // Dados simulados (Mock Data)
        const usersDatabase = [
            { id: 1, name: 'Carlos Eduardo', email: 'carlos.edu@empresa.com', role: 'Administrador' },
            { id: 2, name: 'Ana Souza', email: 'ana.souza@empresa.com', role: 'Gerente' },
            { id: 3, name: 'Roberto Dias', email: 'roberto.dias@empresa.com', role: 'Operador' },
            { id: 4, name: 'Fernanda Lima', email: 'fernanda.lima@empresa.com', role: 'Operador' },
            { id: 5, name: 'Marcos Paulo', email: 'marcos.paulo@empresa.com', role: 'Gerente' }
        ];

        function searchUsers() {
            const nameInput = document.getElementById('searchName').value.trim().toLowerCase();
            const roleInput = document.getElementById('searchRole').value;
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

            // 1. Regra de Validação: Pesquisa só executa se houver input
            if (nameInput === "" && roleInput === "") {
                feedbackMsg.style.display = 'block';
                return; 
            } else {
                feedbackMsg.style.display = 'none';
            }

            // 2. Filtragem
            const filteredData = usersDatabase.filter(user => {
                const matchName = nameInput ? user.name.toLowerCase().includes(nameInput) : true;
                const matchRole = roleInput ? user.role === roleInput : true;
                return matchName && matchRole;
            });

            // 3. Renderização
            tbody.innerHTML = ''; // Limpa tabela

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="3" style="text-align: center; padding: 2rem; color: #777;">Nenhum utilizador encontrado com estes critérios.</td></tr>`;
                return;
            }

            filteredData.forEach(user => {
                // Define classe do badge baseada no papel
                let badgeClass = 'role-user'; // padrão
                if (user.role === 'Administrador') badgeClass = 'role-admin';
                if (user.role === 'Gerente') badgeClass = 'role-manager';

                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>
                        <a href="DetalharUsuario?id=${user.id}" class="user-link">
                            ${user.name}
                        </a>
                    </td>
                    <td>${user.email}</td>
                    <td><span class="role-badge ${badgeClass}">${user.role}</span></td>
                `;
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>