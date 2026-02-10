<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Novo Usuário</title>
    
    <link rel="stylesheet" href="../../assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>&rsaquo; Novo Usuário</div>
    </nav>

    <main class="container">
        <section class="form-card">
            <div class="form-header">
                <h1>Criar Usuário</h1>
                <p class="subtitle">Adicione um novo operador ou administrador ao sistema.</p>
            </div>

            <form id="createUserForm" onsubmit="handleRegister(event)">
                
                <div class="form-group">
                    <label for="userName">Nome Completo *</label>
                    <input type="text" id="userName" placeholder="Ex: João Silva" required>
                </div>

                <div class="form-group">
                    <label for="userEmail">Email Corporativo *</label>
                    <input type="email" id="userEmail" placeholder="nome@empresa.com" required>
                </div>

                <div class="form-group">
                    <label for="userRole">Papel (Permissões) *</label>
                    <select id="userRole" required>
                        <option value="">Selecione...</option>
                        <option value="Administrador">Administrador</option>
                        <option value="Gerente">Gerente</option>
                        <option value="Operador">Operador</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="userPass">Senha *</label>
                    <input type="password" id="userPass" placeholder="******" required>
                </div>

                <div class="form-group">
                    <label for="userPassConfirm">Repetir Senha *</label>
                    <input type="password" id="userPassConfirm" placeholder="******" required>
                    <div id="passwordError" class="password-match-error">As senhas não coincidem.</div>
                </div>

                <div class="form-actions">
                    <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                        Cadastrar
                    </button>
                    <a href="../../ListarUsuarios" class="btn-cancel">Cancelar</a>
                </div>

            </form>
        </section>
    </main>

    <script>
        /* --- JavaScript: Validação Lógica --- */

        // 1. Elementos
        const nameInput = document.getElementById('userName');
        const emailInput = document.getElementById('userEmail');
        const roleInput = document.getElementById('userRole');
        const passInput = document.getElementById('userPass');
        const passConfirmInput = document.getElementById('userPassConfirm');
        const btnSubmit = document.getElementById('btnSubmit');
        const passwordErrorMsg = document.getElementById('passwordError');

        // 2. Função de Validação Central
        function checkFormValidity() {
            // Verifica preenchimento básico
            const isNameFilled = nameInput.value.trim().length > 0;
            const isEmailFilled = emailInput.value.trim().length > 0; // O type="email" já valida formato básico
            const isRoleSelected = roleInput.value !== "";
            const isPassFilled = passInput.value.length > 0;
            
            // Verifica correspondência de senhas
            const passwordsMatch = (passInput.value === passConfirmInput.value) && (passInput.value.length > 0);

            // Feedback visual de erro de senha
            if (passConfirmInput.value.length > 0 && !passwordsMatch) {
                passwordErrorMsg.style.display = 'block';
                passConfirmInput.style.borderColor = 'var(--danger-color)';
            } else {
                passwordErrorMsg.style.display = 'none';
                passConfirmInput.style.borderColor = (passConfirmInput.value.length > 0) ? 'var(--success-color)' : 'var(--border-color)';
            }

            // Habilitar ou desabilitar botão
            if (isNameFilled && isEmailFilled && isRoleSelected && isPassFilled && passwordsMatch) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        // 3. Adicionar Listeners em todos os campos
        const inputs = [nameInput, emailInput, roleInput, passInput, passConfirmInput];
        inputs.forEach(input => {
            input.addEventListener('input', checkFormValidity);
            // Para o select, usamos 'change' também
            if(input.tagName === 'SELECT') input.addEventListener('change', checkFormValidity);
        });

        // 4. Envio do Formulário
        function handleRegister(event) {
            event.preventDefault();
            alert(`Usuário ${nameInput.value} criado com sucesso!\nPapel: ${roleInput.value}`);
            window.location.href = '../../ListarUsuarios';
        }

    </script>
</body>
</html>