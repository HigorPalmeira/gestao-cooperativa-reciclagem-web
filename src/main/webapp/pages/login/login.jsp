<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - ERP Cooperativa</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual --- */
        :root {
            --primary-color: #0056b3;
            --primary-hover: #004494;
            --background-color: #e9ecef;
            --white: #ffffff;
            --text-color: #333;
            --border-color: #dee2e6;
            --danger-color: #dc3545;
            --disabled-color: #95a5a6;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: var(--text-color);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Container do Cartão de Login */
        .login-card {
            background-color: var(--white);
            padding: 2.5rem;
            border-radius: 8px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        /* Logótipo / Marca */
        .brand-logo {
            margin-bottom: 2rem;
            color: var(--primary-color);
        }
        .brand-icon {
            font-size: 3rem;
            display: block;
            margin-bottom: 10px;
        }
        .brand-text {
            font-size: 1.5rem;
            font-weight: bold;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        /* Formulário */
        .login-form {
            display: flex;
            flex-direction: column;
            gap: 1.2rem;
            text-align: left;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            margin-bottom: 0.5rem;
            font-weight: 600;
            font-size: 0.9rem;
            color: #555;
        }

        .form-group input {
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.2s, box-shadow 0.2s;
        }

        .form-group input:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(0, 86, 179, 0.1);
        }

        /* Botão Entrar */
        .btn-login {
            background-color: var(--primary-color);
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            font-size: 1.1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
            transition: background-color 0.3s;
        }

        .btn-login:hover:not(:disabled) {
            background-color: var(--primary-hover);
        }

        .btn-login:disabled {
            background-color: var(--disabled-color);
            cursor: not-allowed;
            opacity: 0.7;
        }

        /* Links Auxiliares */
        .forgot-password {
            margin-top: 1.5rem;
            font-size: 0.9rem;
        }
        .forgot-password a {
            color: #666;
            text-decoration: none;
        }
        .forgot-password a:hover {
            color: var(--primary-color);
            text-decoration: underline;
        }

        /* Mensagem de Erro */
        .error-message {
            color: var(--danger-color);
            font-size: 0.9rem;
            margin-top: 1rem;
            display: none; /* Oculto por padrão */
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #f5c6cb;
        }

    </style>
     -->
     
</head>
<body class="auth-page">

    <div class="login-card">
        <!-- Logótipo -->
        <div class="brand-logo">
            <span class="brand-icon">♻️</span>
            <div class="brand-text">ERP Reciclagem</div>
        </div>

        <!-- Formulário de Acesso -->
        <form id="loginForm" class="login-form" onsubmit="handleLogin(event)">
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" placeholder="nome@cooperativa.com" required>
            </div>

            <div class="form-group">
                <label for="password">Senha</label>
                <input type="password" id="password" placeholder="••••••••" required>
            </div>

            <button type="submit" id="btnLogin" class="btn-login" disabled>
                Entrar
            </button>
        </form>

        <!-- Mensagem de Erro -->
        <div id="errorMsg" class="error-message">
            Credenciais inválidas. Verifique o email e a senha.
        </div>

        <!-- Link Esqueci a Senha -->
        <div class="forgot-password">
            <a href="recuperacao-senha.html">Esqueci a minha senha</a>
        </div>
    </div>

    <script>
        /* --- JavaScript: Lógica de Autenticação --- */

        // 1. Elementos do DOM
        const emailInput = document.getElementById('email');
        const passInput = document.getElementById('password');
        const btnLogin = document.getElementById('btnLogin');
        const errorMsg = document.getElementById('errorMsg');

        // 2. Base de Dados de Utilizadores (Simulação)
        // Credenciais para teste
        const usersDB = [
            { email: "admin@coop.com", pass: "admin123", role: "Administrador" },
            { email: "gerente@coop.com", pass: "gerente123", role: "Gerente" },
            { email: "operador@coop.com", pass: "operador123", role: "Operador" }
        ];

        // 3. Validação de Preenchimento (Habilitar Botão)
        function checkInputs() {
            const email = emailInput.value.trim();
            const pass = passInput.value.trim();

            if (email.length > 0 && pass.length > 0) {
                btnLogin.disabled = false;
            } else {
                btnLogin.disabled = true;
            }
        }

        // Adicionar Listeners
        emailInput.addEventListener('input', checkInputs);
        passInput.addEventListener('input', checkInputs);

        // 4. Processar Login
        function handleLogin(event) {
            event.preventDefault(); // Impede recarregamento da página

            const email = emailInput.value.trim();
            const pass = passInput.value.trim();

            // Verificar credenciais na base de dados simulada
            const user = usersDB.find(u => u.email === email && u.pass === pass);

            if (user) {
                // SUCESSO
                errorMsg.style.display = 'none';
                
                // Simulação de armazenamento de sessão (opcional)
                sessionStorage.setItem('currentUser', JSON.stringify(user));

                // Redirecionamento
                // alert(`Bem-vindo, ${user.role}! Redirecionando...`);
                window.location.href = '../index.html'; // Redireciona para o Início (Dashboard)
            } else {
                // ERRO
                errorMsg.style.display = 'block';
                
                // Animação visual simples (shake) no botão ou input se desejar
                passInput.value = ""; // Limpa a senha
                btnLogin.disabled = true; // Desativa botão novamente
                passInput.focus();
            }
        }

    </script>
</body>
</html>