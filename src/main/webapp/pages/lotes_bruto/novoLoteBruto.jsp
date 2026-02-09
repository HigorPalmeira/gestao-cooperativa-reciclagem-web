<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Lote Bruto</title>
    
    <link rel="stylesheet" href="../../assets/_css/styles.css">
    
    <!-- 
    <style>
        /* --- CSS: Estilização Visual (Consistente com o Sistema ERP) --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --success-color: #28a745;
            --danger-color: #dc3545;
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
        nav.main-nav a { 
            color: #fff; 
            text-decoration: none; 
            font-size: 0.9rem; 
            opacity: 0.8; 
            cursor: pointer;
        }
        nav.main-nav a:hover { opacity: 1; text-decoration: underline; }

        /* Container Centralizado */
        .container {
            max-width: 600px;
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
        
        .form-group label span.required { color: #e74c3c; }

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
        }

        /* Campos Readonly (Preenchimento Automático) */
        .form-group input[readonly] {
            background-color: #e9ecef;
            color: #6c757d;
            cursor: not-allowed;
            border-color: #ced4da;
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
            display: block;
            cursor: pointer;
        }
        .btn-back:hover { text-decoration: underline; color: var(--primary-color); }

        /* Mensagens de Erro */
        .error-msg {
            color: var(--danger-color);
            font-size: 0.85rem;
            margin-top: 5px;
            display: none;
        }

    </style>
     -->
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Novo Lote Bruto</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Entrada de Lote Bruto</h1>
                <p class="subtitle">Registe a entrada de materiais de fornecedores.</p>
            </div>

            <!-- Formulário -->
            <form id="createBatchForm" onsubmit="handleRegister(event)">
                
                <!-- Seção Fornecedor -->
                <div class="form-group">
                    <label for="supplierDoc">Documento do Fornecedor (CPF/CNPJ) <span class="required">*</span></label>
                    <input type="text" id="supplierDoc" placeholder="Digite apenas números" onblur="fetchSupplierData()">
                    <span id="docError" class="error-msg">Erro: Fornecedor não encontrado ou documento inválido.</span>
                </div>

                <div class="form-group">
                    <label for="supplierName">Nome do Fornecedor</label>
                    <input type="text" id="supplierName" readonly tabindex="-1" placeholder="Preenchimento automático">
                </div>

                <div class="form-group">
                    <label for="supplierType">Tipo de Fornecedor</label>
                    <input type="text" id="supplierType" readonly tabindex="-1" placeholder="Preenchimento automático">
                </div>

                <hr style="border: 0; border-top: 1px solid #eee; margin: 2rem 0;">

                <!-- Seção Detalhes do Lote -->
                <div class="form-group">
                    <label for="entryWeight">Peso de Entrada (Kg) <span class="required">*</span></label>
                    <input type="number" id="entryWeight" placeholder="0.00" step="0.01" min="0.1">
                </div>

                <!-- Botão de Ação -->
                <div class="form-actions">
                    <!-- O botão começa desativado -->
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="../../ListarLotesBruto" class="btn-back">
                        Cancelar e voltar
                    </a>
                </div>
            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Lógica de Validação e Registo --- */

        // 1. Base de Dados Simulada (Fornecedores)
        const suppliersDB = {
            "55123456000100": { name: "Indústrias Metalurgicas Aço", type: "Matéria-prima" },
            "99888777000122": { name: "Transportadora Rápida", type: "Logística" },
            "12345678909": { name: "João Coletor Autônomo", type: "Fornecedor Pessoa Física" }
        };

        // Elementos DOM
        const docInput = document.getElementById('supplierDoc');
        const nameInput = document.getElementById('supplierName');
        const typeInput = document.getElementById('supplierType');
        const weightInput = document.getElementById('entryWeight');
        const docError = document.getElementById('docError');
        const btnSubmit = document.getElementById('btnSubmit');

        let isSupplierValid = false;

        // 2. Função: Buscar Fornecedor (Blur Event)
        function fetchSupplierData() {
            // Remove caracteres não numéricos
            const rawDoc = docInput.value.replace(/\D/g, "");
            
            // Validação simples de tamanho (CPF=11 ou CNPJ=14)
            const isValidLength = rawDoc.length === 11 || rawDoc.length === 14;

            if (isValidLength && suppliersDB[rawDoc]) {
                // SUCESSO: Encontrou
                nameInput.value = suppliersDB[rawDoc].name;
                typeInput.value = suppliersDB[rawDoc].type;
                
                docError.style.display = 'none';
                docInput.style.borderColor = 'var(--success-color)';
                isSupplierValid = true;
            } else {
                // ERRO: Não encontrou ou formato inválido
                nameInput.value = "";
                typeInput.value = "";
                
                // Só mostra erro se o campo não estiver vazio
                if (rawDoc.length > 0) {
                    docError.style.display = 'block';
                    docInput.style.borderColor = 'var(--danger-color)';
                } else {
                    docError.style.display = 'none';
                    docInput.style.borderColor = 'var(--border-color)';
                }
                isSupplierValid = false;
            }

            checkFormValidity();
        }

        // 3. Função: Validar Formulário Completo
        function checkFormValidity() {
            const weightValue = parseFloat(weightInput.value);
            const isWeightValid = !isNaN(weightValue) && weightValue > 0;

            // Ativa botão se Fornecedor for válido E Peso for válido
            if (isSupplierValid && isWeightValid) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // Listener para o campo de peso
        weightInput.addEventListener('input', checkFormValidity);

        // 4. Função: Simular Cadastro
        function handleRegister(event) {
            event.preventDefault();

            // Dados automáticos
            const currentDate = new Date().toLocaleDateString('pt-BR');
            const defaultStatus = "Recebido";

            const supplierName = nameInput.value;
            const weight = weightInput.value;

            // Feedback visual
            alert(`Lote Cadastrado com Sucesso!\n\nFornecedor: ${supplierName}\nPeso: ${weight} Kg\nData de Entrada: ${currentDate}\nStatus: ${defaultStatus}`);

            // Opcional: Redirecionar
            window.location.href = '../../ListarLotesBruto';
        }

    </script>
</body>
</html>