<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Usuário</title>
    
    <link rel="stylesheet" href="assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="ListarUsuarios">Voltar para Gestão de Usuários</a>
        </div>
    </nav>

    <main class="container">
        
        <section class="card">
            <h1>Editar Usuário</h1>
            
            <form id="editUserForm" onsubmit="handleSave(event)">
                <div class="form-grid">
                    
                    <div class="form-group">
                        <label for="userName">Nome Completo</label>
                        <input type="text" id="userName" required>
                    </div>

                    <div class="form-group">
                        <label for="userRole">Papel</label>
                        <select id="userRole">
                            <option value="Administrador">Administrador</option>
                            <option value="Gerente">Gerente</option>
                            <option value="Operador">Operador</option>
                        </select>
                    </div>

                    <div class="form-group full-width">
                        <label for="userEmail">Email</label>
                        <input type="email" id="userEmail" required>
                    </div>

                    <div class="password-section form-grid" style="grid-template-columns: 1fr 1fr; gap: 1.5rem; margin: 0;">
                        <div class="full-width">
                            <h3>Alterar Senha <small>(Deixe em branco para manter a atual)</small></h3>
                        </div>
                        <div class="form-group">
                            <label for="newPass">Nova Senha</label>
                            <input type="password" id="newPass" placeholder="******">
                        </div>
                        <div class="form-group">
                            <label for="confirmPass">Repetir Nova Senha</label>
                            <input type="password" id="confirmPass" placeholder="******">
                        </div>
                    </div>

                </div>

                <div style="display: flex; align-items: center;">
                    <button type="submit" id="btnSave" class="btn-save" disabled>
                        Salvar Alterações
                    </button>
                    <span id="feedback-msg"></span>
                </div>
            </form>
        </section>

        <div class="danger-zone">
            <p style="float: left; color: #666; margin: 5px 0 0 0; font-size: 0.9rem;">
                Atenção: Esta ação é irreversível.
            </p>
            <button class="btn-delete" onclick="deleteUser()">Excluir Usuário</button>
        </div>

    </main>

    <script>
        /* --- JavaScript: Lógica de Edição --- */

        // 1. Dados Simulados (Carregados do Banco)
        const userData = {
            id: 1,
            name: "Carlos Eduardo",
            email: "carlos.edu@empresa.com",
            role: "Administrador"
        };

        // Elementos DOM
        const form = document.getElementById('editUserForm');
        const nameInput = document.getElementById('userName');
        const emailInput = document.getElementById('userEmail');
        const roleInput = document.getElementById('userRole');
        const newPassInput = document.getElementById('newPass');
        const confirmPassInput = document.getElementById('confirmPass');
        const btnSave = document.getElementById('btnSave');
        const feedbackMsg = document.getElementById('feedback-msg');

        // 2. Inicialização: Preencher campos
        window.onload = function() {
            nameInput.value = userData.name;
            emailInput.value = userData.email;
            roleInput.value = userData.role;
        };

        // 3. Detecção de Alterações (Dirty State)
        // O botão 'Salvar' fica inativo até que algum campo seja alterado/clicado
        const allInputs = form.querySelectorAll('input, select');
        
        allInputs.forEach(input => {
            // Usamos 'input' para texto e 'change' para select
            input.addEventListener('input', enableSaveButton);
            input.addEventListener('change', enableSaveButton);
        });

        function enableSaveButton() {
            btnSave.disabled = false;
        }

        // 4. Lógica de Salvamento e Validação
        function handleSave(event) {
            event.preventDefault();

            // Validação de Senha (apenas se o campo de nova senha tiver conteúdo)
            if (newPassInput.value.length > 0) {
                if (newPassInput.value !== confirmPassInput.value) {
                    showFeedback("As senhas não coincidem.", "error");
                    confirmPassInput.style.borderColor = "var(--danger-color)";
                    return; // Para a execução
                }
            }

            // Se chegou aqui, está válido
            confirmPassInput.style.borderColor = "var(--border-color)";
            
            // Simula envio ao servidor
            showFeedback("Alterações salvas com sucesso!", "success");
            
            // Opcional: Desativar botão novamente até nova edição
            // btnSave.disabled = true; 
        }

        function showFeedback(text, type) {
            feedbackMsg.textContent = text;
            feedbackMsg.className = type; // Aplica classe css .error ou .success
            feedbackMsg.style.display = 'block';

            // Oculta após 3 segundos
            setTimeout(() => {
                feedbackMsg.style.display = 'none';
            }, 3000);
        }

        // 5. Lógica de Exclusão
        function deleteUser() {
            const confirmed = confirm(`Tem certeza que deseja remover o acesso de "${nameInput.value}"?\n\nEsta ação não pode ser desfeita.`);
            if (confirmed) {
                alert("Usuário excluído.");
                window.location.href = "ListarUsuarios";
            }
        }

    </script>
</body>
</html>