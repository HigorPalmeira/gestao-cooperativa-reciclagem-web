<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Cliente</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Consistente com o ERP) --- */
        :root {
            --primary-color: #0056b3;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --disabled-color: #95a5a6;
            --text-color: #333;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: var(--text-color);
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
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; opacity: 0.8; }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Centralizado */
        .container {
            max-width: 600px; /* Largura ideal para formulários de cadastro único */
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
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #444;
        }
        
        .form-group label span.required { color: #e74c3c; } /* Asterisco vermelho */

        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            box-sizing: border-box; 
            transition: border-color 0.2s;
        }

        .form-group input:focus {
            outline: none;
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(0, 86, 179, 0.1);
        }

        /* Botão Cadastrar */
        .form-actions {
            margin-top: 2rem;
            display: flex;
            flex-direction: column;
            gap: 15px;
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
            transition: background-color 0.3s, opacity 0.3s;
        }

        .btn-submit:hover:not(:disabled) {
            background-color: #218838;
        }

        /* Estado Inativo (Disabled) */
        .btn-submit:disabled {
            background-color: var(--disabled-color);
            cursor: not-allowed;
            opacity: 0.7;
        }

        /* Botão Cancelar */
        .btn-cancel {
            text-align: center;
            color: #666;
            text-decoration: none;
            font-size: 0.9rem;
            display: block;
        }
        .btn-cancel:hover { text-decoration: underline; color: var(--primary-color); }

    </style>
     -->
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Novo Cliente</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Cadastrar Cliente</h1>
                <p class="subtitle">Insira as informações comerciais para adicionar um novo cliente.</p>
            </div>

            <form id="createClientForm" onsubmit="handleRegister(event)">
                
                <div class="form-group">
                    <label for="clientCnpj">CNPJ <span class="required">*</span></label>
                    <input type="text" id="clientCnpj" placeholder="00.000.000/0001-00" required>
                </div>

                <div class="form-group">
                    <label for="clientName">Nome da Empresa <span class="required">*</span></label>
                    <input type="text" id="clientName" placeholder="Ex: Soluções Tecnológicas Ltda" required>
                </div>

                <div class="form-group">
                    <label for="clientContact">Contato Principal <span class="required">*</span></label>
                    <input type="text" id="clientContact" placeholder="Ex: João da Silva (Gerente)" required>
                </div>

                <div class="form-group">
                    <label for="clientEmail">Email Corporativo <span class="required">*</span></label>
                    <input type="email" id="clientEmail" placeholder="comercial@empresa.com" required>
                </div>

                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="clientes.html" class="btn-cancel">Cancelar e voltar para Gestão de Clientes</a>
                </div>
            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Lógica de Validação --- */

        // 1. Selecionar Elementos
        const form = document.getElementById('createClientForm');
        const inputCnpj = document.getElementById('clientCnpj');
        const inputName = document.getElementById('clientName');
        const inputContact = document.getElementById('clientContact');
        const inputEmail = document.getElementById('clientEmail');
        const btnSubmit = document.getElementById('btnSubmit');

        // Array com todos os inputs para facilitar a adição de listeners
        const allInputs = [inputCnpj, inputName, inputContact, inputEmail];

        // 2. Função de Verificação
        function checkFormValidity() {
            // Verifica se todos os campos têm algum valor preenchido (trim remove espaços em branco)
            const isCnpjFilled = inputCnpj.value.trim().length > 0;
            const isNameFilled = inputName.value.trim().length > 0;
            const isContactFilled = inputContact.value.trim().length > 0;
            const isEmailFilled = inputEmail.value.trim().length > 0;
            
            // Validação simples de formato de email (verifica se tem @ e .)
            const isEmailValid = inputEmail.value.includes('@') && inputEmail.value.includes('.');

            // Lógica do botão: Ativo somente se TUDO for verdadeiro
            if (isCnpjFilled && isNameFilled && isContactFilled && isEmailFilled && isEmailValid) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // 3. Adicionar "Listeners" (Ouvintes de evento)
        // O evento 'input' dispara a cada tecla digitada ou colada
        allInputs.forEach(input => {
            input.addEventListener('input', checkFormValidity);
        });

        // 4. Simulação de Envio
        function handleRegister(event) {
            event.preventDefault(); // Impede o reload da página
            
            const clientName = inputName.value;
            
            // Feedback visual
            alert(`Sucesso!\n\nO cliente "${clientName}" foi cadastrado na base de dados.`);
            
            // Opcional: Limpar formulário após cadastro
            form.reset();
            btnSubmit.disabled = true;

            // Opcional: Redirecionar
            window.location.href = 'clientes.html';
        }

    </script>
</body>
</html>