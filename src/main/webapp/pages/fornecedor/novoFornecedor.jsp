<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Fornecedor</title>
    <style>
        /* --- CSS: Estilização Visual (Consistente com a página anterior) --- */
        :root {
            --primary-color: #0056b3;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
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
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; opacity: 0.8; }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Centralizado */
        .container {
            max-width: 600px; /* Mais estreito para focar no formulário */
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

        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            box-sizing: border-box; /* Garante que o padding não estoure a largura */
            transition: border-color 0.2s;
        }

        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: var(--primary-color);
        }

        /* Botão Cadastrar */
        .form-actions {
            margin-top: 2rem;
            display: flex;
            flex-direction: column;
            gap: 10px;
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

        .btn-submit:hover:not(:disabled) {
            background-color: #218838;
        }

        /* Estado Inativo (Disabled) */
        .btn-submit:disabled {
            background-color: var(--disabled-color);
            cursor: not-allowed;
            opacity: 0.7;
        }

        .btn-back {
            text-align: center;
            color: #666;
            text-decoration: none;
            font-size: 0.9rem;
            margin-top: 10px;
            display: block;
        }
        .btn-back:hover { text-decoration: underline; color: var(--primary-color); }

    </style>
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Novo Fornecedor</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Novo Fornecedor</h1>
                <p class="subtitle">Preencha os dados abaixo para adicionar um registro.</p>
            </div>

            <form id="createSupplierForm" onsubmit="handleRegister(event)">
                
                <div class="form-group">
                    <label for="supplierName">Nome do Fornecedor <span class="required">*</span></label>
                    <input type="text" id="supplierName" placeholder="Ex: Indústria XYZ Ltda" required>
                </div>

                <div class="form-group">
                    <label for="supplierDoc">Documento (CNPJ/NIF) <span class="required">*</span></label>
                    <input type="text" id="supplierDoc" placeholder="Ex: 00.000.000/0001-00" required>
                </div>

                <div class="form-group">
                    <label for="supplierType">Tipo de Fornecedor <span class="required">*</span></label>
                    <select id="supplierType" required>
                        <option value="">Selecione uma opção...</option>
                        <option value="Matéria-prima">Matéria-prima</option>
                        <option value="Serviços">Serviços</option>
                        <option value="Logística">Logística</option>
                        <option value="Outros">Outros</option>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="./fornecedores.jsp" class="btn-back">Cancelar e voltar para Gestão de Fornecedores</a>
                </div>
            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Lógica de Validação --- */

        // 1. Selecionar os elementos do DOM
        const form = document.getElementById('createSupplierForm');
        const inputName = document.getElementById('supplierName');
        const inputDoc = document.getElementById('supplierDoc');
        const selectType = document.getElementById('supplierType');
        const btnSubmit = document.getElementById('btnSubmit');

        // 2. Função que verifica se os campos estão preenchidos
        function checkFormValidity() {
            const isNameFilled = inputName.value.trim().length > 0;
            const isDocFilled = inputDoc.value.trim().length > 0;
            const isTypeSelected = selectType.value !== "";

            // Se todos forem verdadeiros, habilita o botão. Se não, desabilita.
            if (isNameFilled && isDocFilled && isTypeSelected) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // 3. Adicionar "ouvintes" (listeners) para rodar a validação sempre que o usuário digitar ou mudar algo
        inputName.addEventListener('input', checkFormValidity);
        inputDoc.addEventListener('input', checkFormValidity);
        selectType.addEventListener('change', checkFormValidity);

        // 4. Simulação do envio do formulário
        function handleRegister(event) {
            event.preventDefault(); // Evita recarregar a página real
            
            // Aqui entraria a lógica de salvar no Backend
            alert(`Sucesso!\nFornecedor "${inputName.value}" cadastrado.`);
            
            // Opcional: Redirecionar de volta para a listagem
            window.location.href = './fornecedores.jsp';
        }

    </script>
</body>
</html>