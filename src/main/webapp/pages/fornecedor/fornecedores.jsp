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
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: #333;
        }

        /* Menu de Navegação (Contexto do Início) */
        nav.main-nav {
            background-color: var(--primary-color);
            color: var(--white);
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        nav.main-nav a {
            color: var(--white);
            text-decoration: none;
            margin-left: 20px;
            font-size: 0.9rem;
        }

        nav.main-nav a:hover {
            text-decoration: underline;
        }

        /* Container Principal */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        /* Cabeçalho da Página e Botão Novo */
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        h1 {
            margin: 0;
            font-size: 1.75rem;
            color: #2c3e50;
        }

        .btn-new {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.2s;
        }

        .btn-new:hover {
            background-color: #218838;
        }

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
            gap: 1rem;
            align-items: flex-end;
            flex-wrap: wrap;
        }

        .form-group {
            display: flex;
            flex-direction: column;
            flex: 1;
            min-width: 200px;
        }

        .form-group label {
            margin-bottom: 0.5rem;
            font-weight: 600;
            font-size: 0.9rem;
        }

        .form-group input, .form-group select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
        }

        .btn-search {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            height: 42px; /* Alinhar com inputs */
        }

        .btn-search:hover {
            background-color: #004494;
        }

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
            min-width: 800px;
        }

        th, td {
            text-align: left;
            padding: 12px 15px;
            border-bottom: 1px solid var(--border-color);
        }

        th {
            background-color: #f8f9fa;
            font-weight: 600;
            color: #495057;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        /* Link no nome do fornecedor */
        .supplier-link {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
        }

        .supplier-link:hover {
            text-decoration: underline;
        }

        /* Feedback de Erro/Aviso */
        #feedback-message {
            margin-top: 10px;
            color: #dc3545;
            font-size: 0.9rem;
            display: none;
        }
        
        /* Links rápidos de rodapé (Navegação contextual) */
        .quick-links {
            margin-top: 2rem;
            padding-top: 1rem;
            border-top: 1px solid #ddd;
            font-size: 0.9rem;
            color: #666;
        }
        .quick-links a { color: var(--secondary-color); margin-right: 15px; }

    </style>
    
    -->
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="index.jsp">Início</a>
            <a href="pages/lotes_bruto/lotesBrutos.jsp">Lotes Brutos</a>
            <a href="pages/transacoes_compra/transacoesCompra.jsp">Transações de Compra</a>
        </div>
    </nav>

    <main class="container">
        <div class="page-header">
            <h1>Gestão de Fornecedores</h1>
            <button class="btn-new" onclick="window.location.href='./novoFornecedor.jsp'"> <!-- '/criar-fornecedor' -->
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
                        <option value="Coletor">Coletor</option>
                        <option value="Empresa">Empresa</option>
                        <option value="Municipio">Municipio</option>
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
                        <th>Documento (CNPJ/NIF)</th>
                        <th>Nome do Fornecedor</th>
                        <th>Tipo de Fornecedor</th>
                        <th>Data de Cadastro</th>
                    </tr>
                </thead>
                <tbody id="resultsTableBody">
                	<c:forEach items="${listaFornecedores}" var="fornecedor">
                		<tr>
                			<td> <a href="DetalharFornecedor?documento=${fornecedor.documento}">${fornecedor.documento}</a></td>
                			<td>${fornecedor.nome}</td>
                			<td>${fornecedor.tipo}</td>
                			<td>${fornecedor.dtCadastro}</td>
                		</tr>
                    </c:forEach>
                    <tr>
                    	<!--
                        <td colspan="4" style="text-align: center; color: #777; padding: 2rem;">
                            Utilize os filtros acima para buscar fornecedores.
                        </td>
                        -->
                    </tr>
                </tbody>
            </table>
        </section>

        <div class="quick-links">
            Navegar para: 
            <a href="../lotes/lotesBrutos.jsp">Lotes Brutos</a> | 
            <a href="./transacoesCompra.jsp">Transações de Compra</a>
        </div>
    </main>

    <script>
        /* --- JavaScript: Lógica e Interação --- */

        // Dados simulados (Mock Data) para a base de dados
        /*const database = [
            { id: 1, doc: '55.123.456/0001-00', name: 'Indústrias Metalurgicas Aço', type: 'Matéria-prima', date: '10/01/2024' },
            { id: 2, doc: '99.888.777/0001-22', name: 'Transportadora Rápida', type: 'Logística', date: '15/02/2024' },
            { id: 3, doc: '12.345.678/0001-99', name: 'Tech Solutions Consultoria', type: 'Serviços', date: '20/03/2024' },
            { id: 4, doc: '44.555.666/0001-11', name: 'Plásticos do Sul', type: 'Matéria-prima', date: '05/04/2024' }
        ];*/

        function searchSuppliers() {
            const nameInput = document.getElementById('searchName').value.trim().toLowerCase();
            const typeInput = document.getElementById('searchType').value;
            const feedbackMsg = document.getElementById('feedback-message');
            const tbody = document.getElementById('resultsTableBody');

            // 1. Validação: A pesquisa só executa se pelo menos um campo for preenchido
            if (nameInput === "" && typeInput === "") {
                feedbackMsg.style.display = 'block';
                return; // Para a execução aqui
            } else {
                feedbackMsg.style.display = 'none';
            }
            
            // MÃOZINHA AQUI
            
            const listaFornecedores = [
            	<c:forEach var="fornecedor" items="${listaFornecedores}" varStatus="loop">
            		{
            			"doc": "${fornecedor.documento}",
            			"name": "${fornecedor.nome}",
            			"type": "${fornecedor.tipo}",
            			"date": "${fornecedor.dtCadastro}"
            		}
            	</c:forEach>
            ];
            
            // FIM DA MÃOZINHA

            // 2. Filtragem dos dados //database
            const filteredData = listaFornecedores.filter(supplier => {
                const matchName = nameInput ? supplier.name.toLowerCase().includes(nameInput) : true;
                const matchType = typeInput ? supplier.type === typeInput : true;
                return matchName && matchType;
            });

            // 3. Renderização da Tabela
            tbody.innerHTML = ''; // Limpa resultados anteriores

            if (filteredData.length === 0) {
                tbody.innerHTML = `<tr><td colspan="4" style="text-align: center; padding: 2rem;">Nenhum fornecedor encontrado.</td></tr>`;
                return;
            }

            filteredData.forEach(supplier => {
                const row = document.createElement('tr');
                
                row.innerHTML = `
                    <td>${supplier.doc}</td>
                    <td>
                        <a href="DetalharFornecedor?documento=${supplier.doc}" class="supplier-link">
                            ${supplier.name}
                        </a>
                    </td>
                    <td>${supplier.type}</td>
                    <td>${supplier.date}</td>
                `;
                
                tbody.appendChild(row);
            });
        }
    </script>
</body>
</html>