<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-PT">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Definir Nova Senha - ERP Cooperativa</title>
    <!-- Link para a folha de estilos global -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    <style>
        .success-container {
            display: none;
            flex-direction: column;
            align-items: center;
            color: #2c3e50;
            text-align: center;
        }
        .success-icon {
            font-size: 3rem;
            color: var(--success-color);
            margin-bottom: 1rem;
        }
    </style>
</head>
<body class="auth-page">

    <div class="login-card">
        <!-- Logótipo -->
        <div class="brand-logo">
            <span class="brand-icon">♻️</span>
            <div class="brand-text">ERP Reciclagem</div>
        </div>

		<c:if test="${not empty sessionScope.msgErro}">
        	<div style="background-color: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
    			${sessionScope.msgErro}
    		</div>
    		<% session.removeAttribute("msgErro"); %>
        </c:if>

        <!-- SEÇÃO A: Formulário de Nova Senha -->
        <div id="formContainer">
            <h2 style="font-size: 1.2rem; border: none; padding: 0;">Criar Nova Senha</h2>
            <p style="font-size: 0.9rem; color: var(--text-muted); margin-bottom: 1.5rem;">
                Por favor, introduza a sua nova senha abaixo.
            </p>

            <form id="formSenha" class="login-form" onsubmit="handlePasswordReset(event)">
                
                <input type="hidden" name="userId" value="${userId}">
                
                <div class="form-group" style="text-align: left;">
                    <label for="newPassword">Nova Senha</label>
                    <input type="password" id="newPassword" name="newPassword" placeholder="Mínimo de 6 caracteres" required minlength="6">
                </div>

                <div class="form-group" style="text-align: left;">
                    <label for="confirmPassword">Repita a Nova Senha</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="••••••••" required>
                </div>

                <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                    Redefinir Senha
                </button>
            </form>

            <div id="errorMsg" class="error-msg" style="text-align: left; padding: 10px; background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 4px; margin-top: 15px;">
                As senhas não coincidem. Tente novamente.
            </div>
        </div>

        <!-- SEÇÃO B: Mensagem de Sucesso (Oculta por padrão) -->
        <div id="successContainer" class="success-container">
            <div class="success-icon">✓</div>
            <h3 style="margin-bottom: 10px;">Senha Alterada!</h3>
            <p style="font-size: 0.95rem; color: var(--text-muted); margin-bottom: 2rem;">
                A sua senha foi atualizada com sucesso. Agora já pode aceder ao sistema utilizando a nova credencial.
            </p>
            <a href="login.html" class="btn-submit" style="text-decoration: none; display: block; width: 100%; box-sizing: border-box;">
                Ir para o Login
            </a>
        </div>

    </div>

    <script>
        /* --- JavaScript: Lógica de Validação e Submissão --- */

        const ctx = "${pageContext.request.contextPath}";
        
        // 1. Elementos do DOM
        const passInput = document.getElementById('newPassword');
        const confirmInput = document.getElementById('confirmPassword');
        const btnSubmit = document.getElementById('btnSubmit');
        const formContainer = document.getElementById('formContainer');
        const formSenha = document.getElementById('formSenha');
        const successContainer = document.getElementById('successContainer');
        const errorMsg = document.getElementById('errorMsg');

        // 2. Validação para habilitar o botão
        function checkInputs() {
            // Habilita o botão apenas se os dois campos tiverem sido preenchidos
            if (passInput.value.length > 0 && confirmInput.value.length > 0) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        }

        passInput.addEventListener('input', checkInputs);
        confirmInput.addEventListener('input', checkInputs);

        // 3. Processar Formulário
        function handlePasswordReset(event) {
            event.preventDefault();

            const pass1 = passInput.value;
            const pass2 = confirmInput.value;

            // Validação de Igualdade
            if (pass1 !== pass2) {
                // Senhas diferentes
                errorMsg.style.display = 'block';
                confirmInput.style.borderColor = 'var(--danger-color)';
                passInput.style.borderColor = 'var(--danger-color)';
            } else {
                // Senhas iguais - SUCESSO
                errorMsg.style.display = 'none';
                
                // Simula um tempo de carregamento (Request para API)
                btnSubmit.textContent = "A guardar...";
                btnSubmit.disabled = true;
                
                
                formSenha.action = ctx + "/AlterarSenha";
                formSenha.method = "POST";
                formSenha.submit();

            }
        }
    </script>
</body>
</html>