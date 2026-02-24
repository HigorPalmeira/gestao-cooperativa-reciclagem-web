<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - ERP Cooperativa</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
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

        <!-- Formulário de Acesso -->
        <form id="loginForm" class="login-form" action="${pageContext.request.contextPath}/Login" method="POST"> <!-- onsubmit="handleLogin(event)" -->
            
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" placeholder="nome@cooperativa.com" required
                	value="${not empty sessionScope.email ? sessionScope.email : ''}">
                
                <c:if test="${not empty sessionScope.email}">
                	<% session.removeAttribute("email"); %>
                </c:if>
                
            </div>

            <div class="form-group">
                <label for="password">Senha</label>
                <input type="password" id="password" name="password" placeholder="••••••••" required>
            </div>

            <button type="submit" id="btnLogin" class="btn-login" disabled>
                Entrar
            </button>
        </form>

        <!-- Mensagem de Erro -->
        <div id="errorMsg" class="error-message">
            Credenciais inválidas. Verifique o email e a senha.
        </div>

        <!-- Link Esqueci a Senha -->
        <div class="forgot-password">
            <a href="${pageContext.request.contextPath}/pages/login/recuperacaoSenha.jsp">Esqueci a minha senha</a>
        </div>
    </div>

    <script>
        /* --- JavaScript: Lógica de Autenticação --- */

        // 1. Elementos do DOM
        const emailInput = document.getElementById('email');
        const passInput = document.getElementById('password');
        const btnLogin = document.getElementById('btnLogin');
        const errorMsg = document.getElementById('errorMsg');

        // 2. Base de Dados de Utilizadores (Simulação)
        // Credenciais para teste
        const usersDB = [
            { email: "admin@coop.com", pass: "admin123", role: "Administrador" },
            { email: "gerente@coop.com", pass: "gerente123", role: "Gerente" },
            { email: "operador@coop.com", pass: "operador123", role: "Operador" }
        ];

        // 3. Validação de Preenchimento (Habilitar Botão)
        function checkInputs() {
            const email = emailInput.value.trim();
            const pass = passInput.value.trim();

            if (email.length > 0 && pass.length > 0) {
                btnLogin.disabled = false;
            } else {
                btnLogin.disabled = true;
            }
        }

        // Adicionar Listeners
        emailInput.addEventListener('input', checkInputs);
        passInput.addEventListener('input', checkInputs);

        // 4. Processar Login
        function handleLogin(event) {
            event.preventDefault(); // Impede recarregamento da página

            const email = emailInput.value.trim();
            const pass = passInput.value.trim();

            // Verificar credenciais na base de dados simulada
            const user = usersDB.find(u => u.email === email && u.pass === pass);

            if (user) {
                // SUCESSO
                errorMsg.style.display = 'none';
                
                // Simulação de armazenamento de sessão (opcional)
                sessionStorage.setItem('currentUser', JSON.stringify(user));

                // Redirecionamento
                // alert(`Bem-vindo, ${user.role}! Redirecionando...`);
                window.location.href = '../index.html'; // Redireciona para o Início (Dashboard)
            } else {
                // ERRO
                errorMsg.style.display = 'block';
                
                // Animação visual simples (shake) no botão ou input se desejar
                passInput.value = ""; // Limpa a senha
                btnLogin.disabled = true; // Desativa botão novamente
                passInput.focus();
            }
        }

    </script>
</body>
</html>