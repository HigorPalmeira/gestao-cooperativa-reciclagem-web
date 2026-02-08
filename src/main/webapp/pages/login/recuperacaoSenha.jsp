<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recuperar Senha - ERP Cooperativa</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Consistente com Login) --- */
        :root {
            --primary-color: #0056b3;
            --primary-hover: #004494;
            --background-color: #e9ecef;
            --white: #ffffff;
            --text-color: #333;
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
            color: var(--text-color);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Container do Cartão */
        .card {
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
        .brand-icon { font-size: 3rem; display: block; margin-bottom: 10px; }
        .brand-text { font-size: 1.5rem; font-weight: bold; text-transform: uppercase; letter-spacing: 1px; }

        h2 { font-size: 1.2rem; color: #555; margin-bottom: 1.5rem; margin-top: 0; }

        /* Formulário */
        .recovery-form {
            display: flex;
            flex-direction: column;
            gap: 1.2rem;
            text-align: left;
        }

        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; font-size: 0.9rem; color: #555; }
        
        .form-group input {
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.2s;
        }
        .form-group input:focus { outline: none; border-color: var(--primary-color); }

        /* Botão Enviar */
        .btn-submit {
            background-color: var(--primary-color);
            color: white;
            padding: 12px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 10px;
            transition: background-color 0.3s;
        }

        .btn-submit:hover:not(:disabled) { background-color: var(--primary-hover); }
        .btn-submit:disabled { background-color: var(--disabled-color); cursor: not-allowed; opacity: 0.7; }

        /* Links Auxiliares */
        .back-link { margin-top: 1.5rem; font-size: 0.9rem; }
        .back-link a { color: #666; text-decoration: none; }
        .back-link a:hover { color: var(--primary-color); text-decoration: underline; }

        /* Mensagens de Feedback */
        .error-message {
            color: var(--danger-color);
            font-size: 0.9rem;
            margin-top: 1rem;
            display: none;
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #f5c6cb;
        }

        /* Estado de Sucesso */
        .success-container {
            display: none; /* Oculto inicialmente */
            flex-direction: column;
            align-items: center;
            color: #2c3e50;
        }
        .success-icon {
            font-size: 3rem;
            color: var(--success-color);
            margin-bottom: 1rem;
        }
        .success-text { font-size: 1rem; margin-bottom: 1.5rem; line-height: 1.5; }

    </style>
     -->
</head>
<body class="auth-page">

    <div class="card">
        <!-- Logótipo -->
        <div class="brand-logo">
            <span class="brand-icon">♻️</span>
            <div class="brand-text">ERP Reciclagem</div>
        </div>

        <!-- SEÇÃO A: Formulário -->
        <div id="formContainer">
            <h2>Recuperação de Senha</h2>
            <p style="font-size: 0.9rem; color: #666; margin-bottom: 1.5rem;">
                Insira o seu email corporativo. Enviaremos um link para definir uma nova senha.
            </p>

            <form class="recovery-form" onsubmit="handleRecovery(event)">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" placeholder="nome@cooperativa.com" required>
                </div>

                <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                    Enviar Instruções
                </button>
            </form>

            <div id="errorMsg" class="error-message">
                Email não encontrado na base de dados.
            </div>
        </div>

        <!-- SEÇÃO B: Mensagem de Sucesso (Oculta por padrão) -->
        <div id="successContainer" class="success-container">
            <div class="success-icon">✓</div>
            <h3>Verifique o seu email</h3>
            <p class="success-text">
                Enviámos um link de recuperação para:<br>
                <strong id="sentEmailDisplay"></strong>
            </p>
            <p style="font-size: 0.9rem; color: #777;">
                Se não receber o email dentro de alguns minutos, verifique a pasta de spam.
            </p>
        </div>

        <!-- Link Voltar -->
        <div class="back-link">
            <a href="login.html">&larr; Voltar ao Login</a>
        </div>
    </div>

    <script>
        /* --- JavaScript: Lógica de Recuperação --- */

        // 1. Elementos
        const emailInput = document.getElementById('email');
        const btnSubmit = document.getElementById('btnSubmit');
        const formContainer = document.getElementById('formContainer');
        const successContainer = document.getElementById('successContainer');
        const errorMsg = document.getElementById('errorMsg');
        const sentEmailDisplay = document.getElementById('sentEmailDisplay');

        // 2. Base de Dados Simulada (Mesmos emails do Login)
        const usersDB = [
            "admin@coop.com",
            "gerente@coop.com",
            "operador@coop.com"
        ];

        // 3. Habilitar Botão
        emailInput.addEventListener('input', function() {
            if (this.value.trim().length > 0 && this.value.includes('@')) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        });

        // 4. Processar Envio
        function handleRecovery(event) {
            event.preventDefault();
            const email = emailInput.value.trim();

            // Simula tempo de processamento
            btnSubmit.textContent = "A verificar...";
            btnSubmit.disabled = true;

            setTimeout(() => {
                // Verifica se o email existe na DB
                if (usersDB.includes(email)) {
                    // SUCESSO
                    showSuccess(email);
                } else {
                    // ERRO
                    errorMsg.style.display = 'block';
                    btnSubmit.textContent = "Enviar Instruções";
                    btnSubmit.disabled = false;
                }
            }, 1000); // Delay de 1 segundo para realismo
        }

        // 5. Exibir Ecrã de Sucesso
        function showSuccess(email) {
            formContainer.style.display = 'none'; // Oculta formulário
            successContainer.style.display = 'flex'; // Mostra sucesso
            sentEmailDisplay.textContent = email; // Exibe o email para confirmação
        }

    </script>
</body>
</html>