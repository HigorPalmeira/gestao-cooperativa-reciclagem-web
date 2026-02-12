<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Fornecedor</title>
    
    <link rel="stylesheet" href="../../assets/_css/styles.css">
     
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

            <form id="createSupplierForm" onsubmit="handleRegister(event)"> <!--  action="../../InserirFornecedor" method="POST" -->
                
                <div class="form-group">
                    <label for="supplierName">Nome do Fornecedor <span class="required">*</span></label>
                    <input type="text" id="supplierName" placeholder="Ex: Indústria XYZ Ltda" required>
                </div>

                <div class="form-group">
                    <label for="supplierDoc">Documento (CPF/CNPJ) <span class="required">*</span></label>
                    <input type="text" id="supplierDoc" placeholder="Ex: 00.000.000/0001-00" required>
                </div>

                <div class="form-group">
                    <label for="supplierType">Tipo de Fornecedor <span class="required">*</span></label>
                    <select id="supplierType" required>
                        <option value="">Selecione uma opção...</option>
                        <option value="Coletor">Coletor</option>
                        <option value="Empresa">Empresa</option>
                        <option value="Municipio">Município</option>
                        <!-- <option value="Outros">Outros</option> -->
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    
                    <a href="../../ListarFornecedores" class="btn-back">Cancelar e voltar para Gestão de Fornecedores</a>
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
            
            const fornecedor = {
            	nome: inputName.value.trim(),
            	documento: inputDoc.value.trim(),
            	tipo: selectType.value
            };
            
            fetch('http://localhost:8080/gestao-cooperativa-reciclagem-web/InserirFornecedor', {
            	method: 'POST',
            	headers: {
            		'Content-Type': 'application/json'
            	},
            	body: JSON.stringify(fornecedor)
            })
            .then(response => response.json())
            .then(fornec => alert("Sucesso: ", fornec))
            .catch(error => alert("Erro: ", error));
            
            // Aqui entraria a lógica de salvar no Backend
            // alert(`Sucesso!\nFornecedor "${inputName.value}" cadastrado.`);
            
            // Opcional: Redirecionar de volta para a listagem
            window.location.href = '../../ListarFornecedores';
        }

    </script>
</body>
</html>