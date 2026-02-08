<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Clientes</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Padrão ERP) --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --danger-color: #dc3545;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: #333;
        }

        /* Menu de Navegação */
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

        /* Cabeçalho e Botão Novo */
        .page-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 1.5rem;
        }

        h1 { margin: 0; font-size: 1.75rem; color: #2c3e50; }

        .btn-new {
            background-color: var(--success-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-weight: bold;
            transition: background-color 0.2s;
        }
        .btn-new:hover { background-color: #218838; }

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

        .form-group input {
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
            height: 42px; /* Alinhamento visual com inputs */
        }
        .btn-search:hover { background-color: #004494; }

        /* Mensagem de Erro */
        #feedback-message {
            margin-top: 10px;
            color: var(--danger-color);
            font-size: 0.9rem;
            display: none;
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

        th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
        tr:hover { background-color: #f1f1f1; }

        /* Link no CNPJ */
        .cnpj-link {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: bold;
            font-family: monospace; /* Fonte monoespaçada para números fica melhor */
            font-size: 1.05rem;
        }
        .cnpj-link:hover { text-decoration: underline; }

    </style>
    
     -->
     
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="../../index.html">Início</a>
            <a href="../vendas/vendas.html">Vendas</a>
            <a href="#">Relatórios</a>
        </div>
    </nav>

    <main class="container">
        <div class="page-header">
            <h1>Gestão de Clientes</h1>
            <button class="btn-new" onclick="window.location.href='./novo_cliente.html'">
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
                    <tr>
                        <td><a href="./detalhes_cliente.html?cnpj=12.345.678/0001-90" class="cnpj-link">12.345.678/0001-90</a></td>
                        <td>Supermercados Horizonte Ltda</td>
                        <td>compras@horizonte.com.br</td>
                        <td>10/01/2024</td>
                    </tr>
                    <tr>
                        <td><a href="./detalhes_cliente.html?cnpj=98.765.432/0001-15" class="cnpj-link">98.765.432/0001-15</a></td>
                        <td>Auto Peças Silva</td>
                        <td>financeiro@silvauto.com</td>
                        <td>15/02/2024</td>
                    </tr>
                    <tr>
                        <td><a href="./detalhes_cliente.html?cnpj=45.123.789/0001-22" class="cnpj-link">45.123.789/0001-22</a></td>
                        <td>Restaurante Sabor Caseiro</td>
                        <td>contato@saborcaseiro.com</td>
                        <td>22/03/2024</td>
                    </tr>
                </tbody>
            </table>
        </section>

    </main>

    <script>
        /* --- JavaScript: Lógica de Interação --- */

        // Banco de dados simulado
        const clientsDatabase = [
            { id: 1, cnpj: '12.345.678/0001-90', name: 'Supermercados Horizonte Ltda', email: 'compras@horizonte.com.br', date: '10/01/2024' },
            { id: 2, cnpj: '98.765.432/0001-15', name: 'Auto Peças Silva', email: 'financeiro@silvauto.com', date: '15/02/2024' },
            { id: 3, cnpj: '45.123.789/0001-22', name: 'Restaurante Sabor Caseiro', email: 'contato@saborcaseiro.com', date: '22/03/2024' },
            { id: 4, cnpj: '11.222.333/0001-44', name: 'Tech Inovação S.A.', email: 'admin@techinovacao.com', date: '05/04/2024' },
        ];

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
                        <a href="./detalhes_clientes.html?cnpj=${client.cnpj}" class="cnpj-link">
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