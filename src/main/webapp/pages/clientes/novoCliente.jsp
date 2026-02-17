<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Cliente</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
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

            <form id="createClientForm" action="${pageContext.request.contextPath}/InserirCliente" method="POST">
                
                <div class="form-group">
                    <label for="clientCnpj">CNPJ <span class="required">*</span></label>
                    <input type="text" id="clientCnpj" name="clientCnpj" placeholder="00.000.000/0001-00" required>
                </div>

                <div class="form-group">
                    <label for="clientName">Nome da Empresa <span class="required">*</span></label>
                    <input type="text" id="clientName" name="clientName" placeholder="Ex: Soluções Tecnológicas Ltda" required>
                </div>

                <div class="form-group">
                    <label for="clientContact">Contato Principal <span class="required">*</span></label>
                    <input type="text" id="clientContact" name="clientContact" placeholder="Ex: João da Silva (Gerente)" required>
                </div>

                <div class="form-group">
                    <label for="clientEmail">Email Corporativo <span class="required">*</span></label>
                    <input type="email" id="clientEmail" name="clientEmail" placeholder="comercial@empresa.com" required>
                </div>

                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="${pageContext.request.contextPath}/ListarClientes" class="btn-cancel">Cancelar e voltar para Gestão de Clientes</a>
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

    </script>
</body>
</html>