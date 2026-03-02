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
     
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
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
            <div id="feedback-message">Preencha pelo menos um campo para realizar a pesquisa.</div>
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

    <script>
        /* --- JavaScript: Lógica de Interação --- */

        // Banco de dados simulado
        /*
        const clientsDatabase = [
            { id: 1, cnpj: '12.345.678/0001-90', name: 'Supermercados Horizonte Ltda', email: 'compras@horizonte.com.br', date: '10/01/2024' },
            { id: 2, cnpj: '98.765.432/0001-15', name: 'Auto Peças Silva', email: 'financeiro@silvauto.com', date: '15/02/2024' },
            { id: 3, cnpj: '45.123.789/0001-22', name: 'Restaurante Sabor Caseiro', email: 'contato@saborcaseiro.com', date: '22/03/2024' },
            { id: 4, cnpj: '11.222.333/0001-44', name: 'Tech Inovação S.A.', email: 'admin@techinovacao.com', date: '05/04/2024' },
        ];
		*/

        function searchClients() {
            // 1. Obter valores
            const cnpjInput = document.getElementById('searchCnpj').value.trim();
            const nameInput = document.getElementById('searchName').value.trim().toLowerCase();
            const emailInput = document.getElementById('searchEmail').value.trim().toLowerCase();
            
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

            // 2. Validação: Pelo menos um campo preenchido
            if (cnpjInput === "" && nameInput === "" && emailInput === "") {
                feedbackMsg.style.display = 'block';
                return; // Interrompe a função
            } else {
                feedbackMsg.style.display = 'none';
            }

            // 3. Filtragem
            const filteredData = clientsDatabase.filter(client => {
                const matchCnpj = cnpjInput ? client.cnpj.includes(cnpjInput) : true;
                const matchName = nameInput ? client.name.toLowerCase().includes(nameInput) : true;
                const matchEmail = emailInput ? client.email.toLowerCase().includes(emailInput) : true;
                
                return matchCnpj && matchName && matchEmail;
            });

            // 4. Renderização
            tbody.innerHTML = ''; // Limpar tabela

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="4" style="text-align: center; padding: 2rem; color: #777;">Nenhum cliente encontrado.</td></tr>`;
                return;
            }

            filteredData.forEach(client => {
                const row = document.createElement('tr');
                
                // Note que o Link está no CNPJ conforme o requisito
                row.innerHTML = `
                    <td>
                        <a href="DetalharCliente?cnpj=${client.cnpj}" class="cnpj-link">
                            ${client.cnpj}
                        </a>
                    </td>
                    <td>${client.name}</td>
                    <td>${client.email}</td>
                    <td>${client.date}</td>
                `;
                
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>