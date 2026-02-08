<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Usuários</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Padrão do Sistema) --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
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
        nav.main-nav a:hover { text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        /* Cabeçalho: Título e Botão Novo */
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
            height: 42px;
        }
        .btn-search:hover { background-color: #004494; }

        /* Mensagem de erro/validação */
        #feedback-message {
            margin-top: 10px;
            color: #dc3545;
            font-size: 0.9rem;
            display: none; /* Oculto por padrão */
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
            min-width: 600px;
        }

        th, td {
            text-align: left;
            padding: 12px 15px;
            border-bottom: 1px solid var(--border-color);
        }

        th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
        tr:hover { background-color: #f1f1f1; }

        /* Links da Tabela */
        .user-link {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
        }
        .user-link:hover { text-decoration: underline; }

        /* Badges de Papel (Role) */
        .role-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 0.85rem;
            font-weight: bold;
        }
        .role-admin { background-color: #e2e3e5; color: #383d41; border: 1px solid #d6d8db; }
        .role-manager { background-color: #d1ecf1; color: #0c5460; border: 1px solid #bee5eb; }
        .role-user { background-color: #fff3cd; color: #856404; border: 1px solid #ffeeba; }

    </style>
     -->
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System</div>
        <div>
            <a href="index.html">Início</a>
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
                    <tr>
                        <td><a href="DetalharUsuario?id=1" class="user-link">Carlos Eduardo</a></td>
                        <td>carlos.edu@empresa.com</td>
                        <td><span class="role-badge role-admin">Administrador</span></td>
                    </tr>
                    <tr>
                        <td><a href="DetalharUsuario?id=2" class="user-link">Ana Souza</a></td>
                        <td>ana.souza@empresa.com</td>
                        <td><span class="role-badge role-manager">Gerente</span></td>
                    </tr>
                    <tr>
                        <td><a href="DetalharUsuario?id=3" class="user-link">Roberto Dias</a></td>
                        <td>roberto.dias@empresa.com</td>
                        <td><span class="role-badge role-user">Operador</span></td>
                    </tr>
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