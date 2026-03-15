<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recuperar Senha - ERP Reciclagem</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body class="auth-page">

    <div class="card">
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
        
        <c:if test="${not empty sessionScope.msgSucesso}">
    		<div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #c3e6cb;">
    			${sessionScope.msgSucesso}
    		</div>
    		<% session.removeAttribute("msgSucesso"); %>
    	</c:if>

        <!-- SEÇÃO A: Formulário -->
        <div id="formContainer">
            <h2>Recuperação de Senha</h2>
            <p style="font-size: 0.9rem; color: #666; margin-bottom: 1.5rem;">
                Insira o seu email corporativo. Enviaremos um link para definir uma nova senha.
            </p>

            <form class="recovery-form" action="${pageContext.request.contextPath}/RecuperarSenha" method="POST">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="nome@cooperativa.com" required>
                </div>

                <button type="submit" id="btnSubmit" class="btn-submit" disabled>
                    Enviar Instruções
                </button>
            </form>

            <div id="errorMsg" class="error-message">
                Email não encontrado na base de dados.
            </div>
        </div>

        <!-- SEÇÃO B: Mensagem de Sucesso (Oculta por padrão) -->
        <div id="successContainer" class="success-container" style="display: none;">
            <div class="success-icon">✓</div>
            <h3>Verifique o seu email</h3>
            <p class="success-text">
                Enviámos um link de recuperação para:<br>
                <strong id="sentEmailDisplay"></strong>
            </p>
            <p style="font-size: 0.9rem; color: #777;">
                Se não receber o email dentro de alguns minutos, verifique a pasta de spam.
            </p>
        </div>

        <!-- Link Voltar -->
        <div class="back-link">
            <a href="${pageContext.request.contextPath}/pages/login/login.jsp">&larr; Voltar ao Login</a>
        </div>
    </div>

    <script>
        /* --- JavaScript: Lógica de Recuperação --- */

        // 1. Elementos
        const emailInput = document.getElementById('email');
        const btnSubmit = document.getElementById('btnSubmit');
        const formContainer = document.getElementById('formContainer');
        const successContainer = document.getElementById('successContainer');
        const errorMsg = document.getElementById('errorMsg');
        const sentEmailDisplay = document.getElementById('sentEmailDisplay');

        emailInput.addEventListener('input', function() {
            if (this.value.trim().length > 0 && this.value.includes('@')) {
                btnSubmit.disabled = false;
            } else {
                btnSubmit.disabled = true;
            }
        });

    </script>
</body>
</html>