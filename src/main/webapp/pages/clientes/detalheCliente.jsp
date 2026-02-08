<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Cliente</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
    <!--
    <style>
        /* --- CSS: Estilização Visual (Padrão ERP) --- */
        :root {
            --primary-color: #0056b3;
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

        /* Menu Superior */
        nav.main-nav {
            background-color: var(--primary-color);
            color: var(--white);
            padding: 1rem 2rem;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        nav.main-nav a { 
	        color: #fff; 
	        text-decoration: none; 
	        font-size: 0.9rem; 
	        margin-left: 15px; 
	        opacity: 0.9; 
        }
        nav.main-nav a:hover { 
	        text-decoration: underline; 
	        opacity: 1; 
        }

        /* Container Principal */
        .container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 0 1rem;
            padding-bottom: 5rem;
        }

        /* --- SEÇÃO 1: Formulário de Edição --- */
        .card {
            background-color: var(--white);
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
            margin-bottom: 2.5rem;
        }

        .card h2 { margin-top: 0; color: #2c3e50; border-bottom: 1px solid #eee; padding-bottom: 15px; margin-bottom: 20px; font-size: 1.4rem; }

        .form-grid {
            display: grid;
            grid-template-columns: 1fr 1fr; /* 2 colunas */
            gap: 1.5rem;
        }

        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; color: #555; }
        .form-group input {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
            transition: border-color 0.2s;
        }
        .form-group input:focus { outline: none; border-color: var(--primary-color); }

        /* Botão Salvar */
        .btn-save {
            background-color: var(--primary-color);
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 4px;
            font-size: 1rem;
            font-weight: bold;
            cursor: pointer;
            margin-top: 20px;
            transition: background-color 0.3s;
        }
        .btn-save:hover:not(:disabled) { background-color: #004494; }
        .btn-save:disabled {
            background-color: var(--disabled-color);
            cursor: default;
            opacity: 0.7;
        }

        /* --- SEÇÃO 2: Tabela de Vendas --- */
        .section-title { font-size: 1.2rem; margin-bottom: 1rem; color: #444; }

        table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--white);
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
        }

        th, td { text-align: left; padding: 12px 15px; border-bottom: 1px solid var(--border-color); }
        th { background-color: #f8f9fa; font-weight: 600; color: #495057; }
        tr:hover { background-color: #f1f1f1; }

        /* Link ID da Venda */
        .sale-link { color: var(--primary-color); font-weight: bold; text-decoration: none; }
        .sale-link:hover { text-decoration: underline; }

        /* --- SEÇÃO 3: Botão Excluir (Rodapé) --- */
        .danger-zone {
            margin-top: 4rem;
            padding-top: 1.5rem;
            border-top: 1px solid #ddd;
            display: flex;
            justify-content: flex-end; /* Alinha à direita */
            align-items: center;
        }

        .btn-delete {
            background-color: transparent;
            color: var(--danger-color);
            border: 1px solid var(--danger-color);
            padding: 10px 20px;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.2s;
        }
        .btn-delete:hover { background-color: var(--danger-color); color: white; }

        /* Feedback Msg */
        #feedback-msg { margin-left: 15px; font-weight: 500; display: none; color: var(--success-color); }

    </style>
    
     -->
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System &rsaquo; Cliente</div>
        <div>
            <a href="clientes.html">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
        
        <section class="card">
            <h2>Dados do Cliente</h2>
            <form id="editClientForm" onsubmit="handleSave(event)">
                <div class="form-grid">
                    <div class="form-group">
                        <label for="cnpj">CNPJ</label>
                        <input type="text" id="cnpj" required>
                    </div>
                    <div class="form-group">
                        <label for="companyName">Nome da Empresa</label>
                        <input type="text" id="companyName" required>
                    </div>
                    <div class="form-group">
                        <label for="contact">Contato Principal</label>
                        <input type="text" id="contact" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" required>
                    </div>
                </div>

                <div style="display: flex; align-items: center;">
                    <button type="submit" id="btnSave" class="btn-save" disabled>
                        Salvar Alterações
                    </button>
                    <span id="feedback-msg">Alterações salvas com sucesso!</span>
                </div>
            </form>
        </section>

        <h3 class="section-title">Histórico de Vendas</h3>
        <table>
            <thead>
                <tr>
                    <th>ID da Venda</th>
                    <th>Data da Venda</th>
                    <th>Valor Total (R$)</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="../vendas/detalhes_venda.html?id=1020" class="sale-link">#1020</a></td>
                    <td>10/01/2026</td>
                    <td>R$ 4.500,00</td>
                </tr>
                <tr>
                    <td><a href="../vendas/detalhes_venda.html?id=1015" class="sale-link">#1015</a></td>
                    <td>05/12/2025</td>
                    <td>R$ 1.250,50</td>
                </tr>
                <tr>
                    <td><a href="../vendas/detalhes_venda.html?id=0998" class="sale-link">#0998</a></td>
                    <td>20/11/2025</td>
                    <td>R$ 10.000,00</td>
                </tr>
            </tbody>
        </table>

        <div class="danger-zone">
            <span style="margin-right: 15px; color: #666; font-size: 0.9rem;">Deseja remover este registro permanentemente?</span>
            <button class="btn-delete" onclick="deleteClient()">Excluir Cliente</button>
        </div>

    </main>

    <script>
        /* --- JavaScript: Lógica --- */

        // 1. Mock Data (Simulando dados vindos do Back-end)
        const clientData = {
            cnpj: "12.345.678/0001-90",
            name: "Supermercados Horizonte Ltda",
            contact: "Maria Oliveira",
            email: "compras@horizonte.com.br"
        };

        // Elementos do DOM
        const form = document.getElementById('editClientForm');
        const inputs = form.querySelectorAll('input');
        const btnSave = document.getElementById('btnSave');
        const feedbackMsg = document.getElementById('feedback-msg');

        // 2. Preencher formulário ao carregar
        window.onload = function() {
            document.getElementById('cnpj').value = clientData.cnpj;
            document.getElementById('companyName').value = clientData.name;
            document.getElementById('contact').value = clientData.contact;
            document.getElementById('email').value = clientData.email;
        };

        // 3. Lógica de Ativação do Botão
        // "O botão... fica inativo até que algum dos campos seja clicado."
        inputs.forEach(input => {
            // Usamos 'focus' para detectar o "clique" ou tabulação para dentro do campo
            input.addEventListener('focus', activateButton);
            // Usamos 'input' para garantir que se ele digitar, também ativa
            input.addEventListener('input', activateButton);
        });

        function activateButton() {
            if (btnSave.disabled) {
                btnSave.disabled = false;
            }
        }

        // 4. Salvar Alterações
        function handleSave(event) {
            event.preventDefault();

            // Validação simples (HTML5 'required' já faz o básico, aqui validamos lógica extra se necessário)
            let allValid = true;
            inputs.forEach(input => {
                if (!input.value.trim()) allValid = false;
            });

            if (!allValid) {
                alert("Por favor, preencha todos os campos corretamente.");
                return;
            }

            // Simula salvamento
            feedbackMsg.style.display = 'block';
            
            // Oculta msg após 3 seg
            setTimeout(() => {
                feedbackMsg.style.display = 'none';
                // Opcional: Desativar botão novamente até nova edição
                btnSave.disabled = true; 
            }, 3000);
        }

        // 5. Excluir Cliente
        function deleteClient() {
            const companyName = document.getElementById('companyName').value;
            const confirmed = confirm(`ATENÇÃO:\n\nTem certeza que deseja excluir o cliente "${companyName}"?\nIsso apagará o histórico de vendas associado.`);

            if (confirmed) {
                alert("Cliente excluído com sucesso.");
                window.location.href = "clientes.html"; // Redireciona para a lista
            }
        }

    </script>
</body>
</html>