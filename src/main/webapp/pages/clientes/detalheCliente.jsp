<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.Cliente" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Cliente</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System &rsaquo; Cliente</div>
        <div>
            <a href="ListarClientes">Voltar para Gestão</a>
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
                    <td><a href="DetalharVenda?id=1020" class="sale-link">#1020</a></td>
                    <td>10/01/2026</td>
                    <td>R$ 4.500,00</td>
                </tr>
                <tr>
                    <td><a href="DetalharVenda?id=1015" class="sale-link">#1015</a></td>
                    <td>05/12/2025</td>
                    <td>R$ 1.250,50</td>
                </tr>
                <tr>
                    <td><a href="DetalharVenda?id=0998" class="sale-link">#0998</a></td>
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
                window.location.href = "ListarClientes"; // Redireciona para a lista
            }
        }

    </script>
</body>
</html>