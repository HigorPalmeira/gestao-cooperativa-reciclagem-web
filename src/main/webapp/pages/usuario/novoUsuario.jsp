<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Usuário</title>
    
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
            --disabled-color: #95a5a6;
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
            align-items: center;
        }
        
        nav.main-nav .brand { font-weight: bold; font-size: 1.2rem; margin-right: 20px; }
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; opacity: 0.9; }
        nav.main-nav a:hover { text-decoration: underline; }

        /* Container Centralizado */
        .container {
            max-width: 500px; /* Estreito para focar na criação de conta */
            margin: 3rem auto;
            padding: 0 1rem;
        }

        /* Cartão do Formulário */
        .form-card {
            background-color: var(--white);
            padding: 2.5rem;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }

        .form-header {
            margin-bottom: 2rem;
            text-align: center;
            border-bottom: 1px solid #eee;
            padding-bottom: 1rem;
        }

        h1 { margin: 0; font-size: 1.5rem; color: #2c3e50; }
        p.subtitle { margin: 5px 0 0; color: #666; font-size: 0.9rem; }

        /* Estilos dos Campos */
        .form-group {
            margin-bottom: 1.2rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.4rem;
            font-weight: 600;
            color: #444;
            font-size: 0.95rem;
        }

        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            box-sizing: border-box;
            transition: border-color 0.2s;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: var(--primary-color);
        }

        /* Feedback de Senha */
        .password-match-error {
            color: var(--danger-color);
            font-size: 0.85rem;
            margin-top: 5px;
            display: none; /* Oculto por padrão */
        }

        /* Botão Cadastrar */
        .form-actions {
            margin-top: 2rem;
        }

        .btn-submit {
            background-color: var(--success-color);
            color: white;
            padding: 15px;
            border: none;
            border-radius: 4px;
            font-size: 1.1rem;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: background-color 0.3s;
        }

        .btn-submit:hover:not(:disabled) { background-color: #218838; }

        /* Estado Inativo (Disabled) */
        .btn-submit:disabled {
            background-color: var(--disabled-color);
            cursor: not-allowed;
            opacity: 0.7;
        }

        .btn-cancel {
            display: block;
            text-align: center;
            margin-top: 15px;
            color: #666;
            text-decoration: none;
            font-size: 0.9rem;
        }
        .btn-cancel:hover { color: var(--danger-color); }

    </style>
     -->
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Novo Usuário</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Criar Usuário</h1>
                <p class="subtitle">Adicione um novo operador ou administrador ao sistema.</p>
            </div>

            <form id="createUserForm" onsubmit="handleRegister(event)">
                
                <div class="form-group">
                    <label for="userName">Nome Completo *</label>
                    <input type="text" id="userName" placeholder="Ex: João Silva" required>
                </div>

                <div class="form-group">
                    <label for="userEmail">Email Corporativo *</label>
                    <input type="email" id="userEmail" placeholder="nome@empresa.com" required>
                </div>

                <div class="form-group">
                    <label for="userRole">Papel (Permissões) *</label>
                    <select id="userRole" required>
                        <option value="">Selecione...</option>
                        <option value="Administrador">Administrador</option>
                        <option value="Gerente">Gerente</option>
                        <option value="Operador">Operador</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="userPass">Senha *</label>
                    <input type="password" id="userPass" placeholder="******" required>
                </div>

                <div class="form-group">
                    <label for="userPassConfirm">Repetir Senha *</label>
                    <input type="password" id="userPassConfirm" placeholder="******" required>
                    <div id="passwordError" class="password-match-error">As senhas não coincidem.</div>
                </div>

                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    <a href="./usuarios.html" class="btn-cancel">Cancelar</a>
                </div>

            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Validação Lógica --- */

        // 1. Elementos
        const nameInput = document.getElementById('userName');
        const emailInput = document.getElementById('userEmail');
        const roleInput = document.getElementById('userRole');
        const passInput = document.getElementById('userPass');
        const passConfirmInput = document.getElementById('userPassConfirm');
        const btnSubmit = document.getElementById('btnSubmit');
        const passwordErrorMsg = document.getElementById('passwordError');

        // 2. Função de Validação Central
        function checkFormValidity() {
            // Verifica preenchimento básico
            const isNameFilled = nameInput.value.trim().length > 0;
            const isEmailFilled = emailInput.value.trim().length > 0; // O type="email" já valida formato básico
            const isRoleSelected = roleInput.value !== "";
            const isPassFilled = passInput.value.length > 0;
            
            // Verifica correspondência de senhas
            const passwordsMatch = (passInput.value === passConfirmInput.value) && (passInput.value.length > 0);

            // Feedback visual de erro de senha
            if (passConfirmInput.value.length > 0 && !passwordsMatch) {
                passwordErrorMsg.style.display = 'block';
                passConfirmInput.style.borderColor = 'var(--danger-color)';
            } else {
                passwordErrorMsg.style.display = 'none';
                passConfirmInput.style.borderColor = (passConfirmInput.value.length > 0) ? 'var(--success-color)' : 'var(--border-color)';
            }

            // Habilitar ou desabilitar botão
            if (isNameFilled && isEmailFilled && isRoleSelected && isPassFilled && passwordsMatch) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // 3. Adicionar Listeners em todos os campos
        const inputs = [nameInput, emailInput, roleInput, passInput, passConfirmInput];
        inputs.forEach(input => {
            input.addEventListener('input', checkFormValidity);
            // Para o select, usamos 'change' também
            if(input.tagName === 'SELECT') input.addEventListener('change', checkFormValidity);
        });

        // 4. Envio do Formulário
        function handleRegister(event) {
            event.preventDefault();
            alert(`Usuário ${nameInput.value} criado com sucesso!\nPapel: ${roleInput.value}`);
            window.location.href = './usuarios.html';
        }

    </script>
</body>
</html>