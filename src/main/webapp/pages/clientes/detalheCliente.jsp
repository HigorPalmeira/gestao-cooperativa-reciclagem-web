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
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP Reciclagem &rsaquo; Cliente</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarClientes">Voltar para Gestão</a>
        </div>
    </nav>

    <main class="container">
    
    	<c:if test="${not empty msgErro}">
    		<div style="background-color: #f8d7a; color: #721c24; margin-bottom: 15px; border-radius: 5px; border: 1px solid #f5c6cb;">
    			<strong>Erro:</strong> ${msgErro}
    		</div>
    	</c:if>
    	
    	<c:if test="${not empty sessionScope.msgSucesso}">
    		<div style="background-color: #d4edda; color: #155724; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #c3e6cb;">
    			${sessionScope.msgSucesso}
    		</div>
    		<% session.removeAttribute("msgSucesso"); %>
    	</c:if>
        
        <section class="card">
            <h2>Dados do Cliente</h2>
            <form id="editClientForm" action="${pageContext.request.contextPath}/AtualizarCliente" method="POST"> <!-- onsubmit="handleSave(event)" -->
                
                <input type="hidden" name="cnpj" id="cnpj" value="${not empty cliente ? cliente.cnpj : ''}">
                
                <div class="form-grid">
                    <div class="form-group">
                        <label for="cnpjEdit">CNPJ</label>
                        <input type="text" id="cnpjEdit" name="cnpjEdit" required 
                        	value="${not empty cliente ? cliente.cnpj : ''}">
                    </div>
                    <div class="form-group">
                        <label for="companyName">Nome da Empresa</label>
                        <input type="text" id="companyName" name="companyName" required
                        	value="${not empty cliente ? cliente.nomeEmpresa : ''}">
                    </div>
                    <div class="form-group">
                        <label for="contact">Contato Principal</label>
                        <input type="text" id="contact" name="contact" required
                        	value="${not empty cliente ? cliente.contatoPrincipal : ''}">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" required
                        	value="${not empty cliente ? cliente.emailContato : ''}">
                    </div>
                </div>

                <div style="display: flex; align-items: center;">
                    <button type=button id="btnSave" class="btn-save" disabled onclick="handleSave(event)">
                        Salvar Alterações
                    </button>
                    <!-- <span id="feedback-msg">Alterações salvas com sucesso!</span> -->
                </div>
            </form>
            <br>
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
                
                    <c:forEach items="${listaVendas}" var="venda">
                        <tr>
                            <td><a href="DetalharVenda?id=${venda.id}" class="sale-link">#VD-${String.format("%03d", venda.id)}</a></td>
                            <td>${venda.dtVenda}</td>
                            <td>${String.format("R$ %.2f", venda.valorTotal)}</td>
                        </tr>
                    </c:forEach>
                    
                    <!-- 
                    <tr>
                        <td><a href="DetalharVenda?id=1020" class="sale-link">#1020</a></td>
                        <td>10/01/2026</td>
                        <td>R$ 4.500,00</td>
                    </tr>
                    
                     -->
                     
                </tbody>
            </table>

        </section>


        <div class="danger-zone">
        	<form id="formDeletar" action="${pageContext.request.contextPath}/DeletarCliente" method="POST">
  
				<input type="hidden" name="cnpj" value="${not empty cliente ? cliente.cnpj : ''}">
      	
	            <span style="margin-right: 15px; color: #666; font-size: 0.9rem;">Deseja remover este registro permanentemente?</span>
	            <button type="button" class="btn-delete" onclick="deleteClient()">Excluir Cliente</button>
        	</form>
        </div>

    </main>

    <script>
        
        // Elementos do DOM
        const form = document.getElementById('editClientForm');
        const inputs = form.querySelectorAll('input');
        const btnSave = document.getElementById('btnSave');
        const feedbackMsg = document.getElementById('feedback-msg');

        
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

            form.submit();
            
        }

        // 5. Excluir Cliente
        function deleteClient() {
            //const companyName = document.getElementById('companyName').value;
            const confirmed = confirm(`ATENÇÃO:\n\nTem certeza que deseja excluir o cliente "\${document.getElementById('companyName').value}"?\nIsso apagará o histórico de vendas associado.`);

            if (confirmed) {
                document.getElementById('formDeletar').submit();
            }
        }

    </script>
</body>
</html>