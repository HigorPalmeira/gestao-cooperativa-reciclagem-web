<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Usuário</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Consistente com o Sistema) --- */
        :root {
            --primary-color: #0056b3;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --danger-color: #dc3545;
            --disabled-color: #95a5a6;
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
            align-items: center;
        }
        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; margin-right: 20px; }
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; opacity: 0.9; }
        nav.main-nav a:hover { text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 800px;
            margin: 3rem auto;
            padding: 0 1rem;
            padding-bottom: 5rem; /* Espaço extra para o rodapé */
        }

        /* Cartão de Edição */
        .card {
            background-color: var(--white);
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }

        .card h1 { margin-top: 0; color: #2c3e50; font-size: 1.5rem; border-bottom: 1px solid #eee; padding-bottom: 10px; margin-bottom: 20px;}

        /* Grid do Formulário */
        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr; /* Duas colunas */
            gap: 1.5rem;
        }

        .form-group { margin-bottom: 1rem; }
        .form-group.full-width { grid-column: span 2; } /* Ocupa linha inteira */

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #444;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            box-sizing: border-box;
        }

        /* Seção de Senha (Visualmente separada) */
        .password-section {
            grid-column: span 2;
            background-color: #f8f9fa;
            padding: 1.5rem;
            border-radius: 6px;
            border: 1px solid #e9ecef;
            margin-top: 10px;
        }
        .password-section h3 { margin-top: 0; font-size: 1rem; color: #666; margin-bottom: 1rem; }

        /* Botão Salvar */
        .btn-save {
            background-color: var(--primary-color);
            color: white;
            padding: 12px 30px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 1.5rem;
            transition: background-color 0.3s;
        }

        .btn-save:hover:not(:disabled) { background-color: #004494; }
        .btn-save:disabled {
            background-color: var(--disabled-color);
            cursor: default;
            opacity: 0.6;
        }

        /* Zona de Perigo (Exclusão) */
        .danger-zone {
            margin-top: 4rem;
            border-top: 1px solid #ddd;
            padding-top: 2rem;
            text-align: right; /* Botão alinhado à direita */
        }

        .btn-delete {
            background-color: transparent;
            color: var(--danger-color);
            border: 1px solid var(--danger-color);
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.2s;
        }

        .btn-delete:hover {
            background-color: var(--danger-color);
            color: white;
        }

        /* Mensagens de Feedback */
        #feedback-msg { margin-left: 15px; font-weight: 500; display: none; }
        .error { color: var(--danger-color); }
        .success { color: var(--success-color); }

    </style>
    
     -->
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="./usuarios.html">Voltar para Gestão de Usuários</a>
        </div>
    </nav>

    <main class="container">
        
        <section class="card">
            <h1>Editar Usuário</h1>
            
            <form id="editUserForm" onsubmit="handleSave(event)">
                <div class="form-grid">
                    
                    <div class="form-group">
                        <label for="userName">Nome Completo</label>
                        <input type="text" id="userName" required>
                    </div>

                    <div class="form-group">
                        <label for="userRole">Papel</label>
                        <select id="userRole">
                            <option value="Administrador">Administrador</option>
                            <option value="Gerente">Gerente</option>
                            <option value="Operador">Operador</option>
                        </select>
                    </div>

                    <div class="form-group full-width">
                        <label for="userEmail">Email</label>
                        <input type="email" id="userEmail" required>
                    </div>

                    <div class="password-section form-grid" style="grid-template-columns: 1fr 1fr; gap: 1.5rem; margin: 0;">
                        <div class="full-width">
                            <h3>Alterar Senha <small>(Deixe em branco para manter a atual)</small></h3>
                        </div>
                        <div class="form-group">
                            <label for="newPass">Nova Senha</label>
                            <input type="password" id="newPass" placeholder="******">
                        </div>
                        <div class="form-group">
                            <label for="confirmPass">Repetir Nova Senha</label>
                            <input type="password" id="confirmPass" placeholder="******">
                        </div>
                    </div>

                </div>

                <div style="display: flex; align-items: center;">
                    <button type="submit" id="btnSave" class="btn-save" disabled>
                        Salvar Alterações
                    </button>
                    <span id="feedback-msg"></span>
                </div>
            </form>
        </section>

        <div class="danger-zone">
            <p style="float: left; color: #666; margin: 5px 0 0 0; font-size: 0.9rem;">
                Atenção: Esta ação é irreversível.
            </p>
            <button class="btn-delete" onclick="deleteUser()">Excluir Usuário</button>
        </div>

    </main>

    <script>
        /* --- JavaScript: Lógica de Edição --- */

        // 1. Dados Simulados (Carregados do Banco)
        const userData = {
            id: 1,
            name: "Carlos Eduardo",
            email: "carlos.edu@empresa.com",
            role: "Administrador"
        };

        // Elementos DOM
        const form = document.getElementById('editUserForm');
        const nameInput = document.getElementById('userName');
        const emailInput = document.getElementById('userEmail');
        const roleInput = document.getElementById('userRole');
        const newPassInput = document.getElementById('newPass');
        const confirmPassInput = document.getElementById('confirmPass');
        const btnSave = document.getElementById('btnSave');
        const feedbackMsg = document.getElementById('feedback-msg');

        // 2. Inicialização: Preencher campos
        window.onload = function() {
            nameInput.value = userData.name;
            emailInput.value = userData.email;
            roleInput.value = userData.role;
        };

        // 3. Detecção de Alterações (Dirty State)
        // O botão 'Salvar' fica inativo até que algum campo seja alterado/clicado
        const allInputs = form.querySelectorAll('input, select');
        
        allInputs.forEach(input => {
            // Usamos 'input' para texto e 'change' para select
            input.addEventListener('input', enableSaveButton);
            input.addEventListener('change', enableSaveButton);
        });

        function enableSaveButton() {
            btnSave.disabled = false;
        }

        // 4. Lógica de Salvamento e Validação
        function handleSave(event) {
            event.preventDefault();

            // Validação de Senha (apenas se o campo de nova senha tiver conteúdo)
            if (newPassInput.value.length > 0) {
                if (newPassInput.value !== confirmPassInput.value) {
                    showFeedback("As senhas não coincidem.", "error");
                    confirmPassInput.style.borderColor = "var(--danger-color)";
                    return; // Para a execução
                }
            }

            // Se chegou aqui, está válido
            confirmPassInput.style.borderColor = "var(--border-color)";
            
            // Simula envio ao servidor
            showFeedback("Alterações salvas com sucesso!", "success");
            
            // Opcional: Desativar botão novamente até nova edição
            // btnSave.disabled = true; 
        }

        function showFeedback(text, type) {
            feedbackMsg.textContent = text;
            feedbackMsg.className = type; // Aplica classe css .error ou .success
            feedbackMsg.style.display = 'block';

            // Oculta após 3 segundos
            setTimeout(() => {
                feedbackMsg.style.display = 'none';
            }, 3000);
        }

        // 5. Lógica de Exclusão
        function deleteUser() {
            const confirmed = confirm(`Tem certeza que deseja remover o acesso de "${nameInput.value}"?\n\nEsta ação não pode ser desfeita.`);
            if (confirmed) {
                alert("Usuário excluído.");
                window.location.href = "./usuarios.html";
            }
        }

    </script>
</body>
</html>