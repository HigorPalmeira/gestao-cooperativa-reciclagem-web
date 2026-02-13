<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Fornecedor</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
     
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System &rsaquo; Fornecedor</div>
        <div>
            <a href="${pageContext.request.contextPath}/ListarFornecedores">Voltar para Gestão</a>
            <a href="${pageContext.request.contextPath}/ListarLotesBruto">Lotes Brutos</a>
            <a href="${pageContext.request.contextPath}/ListarTransacoesCompra">Transações de Compra</a>
        </div>
    </nav>

    <main class="container">
        
        <section class="card">
            <h1 style="margin-top:0; font-size: 1.5rem;">Editar Fornecedor</h1>
            
            <div class="edit-form">
                <div class="form-group">
                    <label for="doc">Documento</label>
                    <input type="text" id="doc">
                </div>
                <div class="form-group">
                    <label for="name">Nome</label>
                    <input type="text" id="name">
                </div>
                <div class="form-group">
                    <label for="type">Tipo</label>
                    <select id="type">
                        <option value="Coletor">Coletor</option>
                        <option value="Empresa">Empresa</option>
                        <option value="Municipio">Municipio</option>
                    </select>
                </div>
            </div>
            <div style="margin-top: 1rem; display: flex; justify-content: space-between; align-items: center;">
                <button class="btn-save" onclick="saveChanges()">Salvar Alterações</button>
                <div>
                    <span class="error-msg">Erro: Se alterar o Tipo, você deve alterar o Documento.</span>
                    <span class="success-msg">Alterações salvas com sucesso!</span>
                </div>
            </div>
        </section>

        <h2>Lotes Brutos Recentes</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data de Entrada</th>
                    <th>Peso de Entrada (Kg)</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
            
	            <c:forEach items="${listaLotesBrutos}" var="loteBruto">
	            
	            	<tr>
	            		<td>
	            			<a href="${pageContext.request.contextPath}/DetalharLoteBruto?id=${loteBruto.id}" class="id-link">
	            				#LB-${String.format("%03d", loteBruto.id)}
	            			</a>
	            		</td>
	            		<td>${loteBruto.dtEntrada}</td>
	            		<td>${loteBruto.pesoEntradaKg}</td>
	            		
	            		<td>
	            			<c:choose>
	            				<c:when test="${loteBruto.status == 'RECEBIDO'}">
	            					<span class="status-badge status-recebido">${loteBruto.status}</span>
	            				</c:when>
	            				<c:when test="${loteBruto.status == 'EM_TRIAGEM'}">
	            					<span class="status-badge status-processamento">${loteBruto.status}</span>
	            				</c:when>
	            				<c:otherwise>
	            					<span class="status-badge status-concluido">${loteBruto.status}</span>
	            				</c:otherwise>
	            			</c:choose>
	            		</td>
	            		
	            	</tr>
	            
	            </c:forEach>
	            
            </tbody>
        </table>

        <h2>Transações de Compra</h2>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data de Pagamento</th>
                    <th>Valor Total (R$)</th>
                    <th>Status de Pagamento</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><a href="${pageContext.request.contextPath}/DetalharTransacaoCompra?id=555" class="id-link">#TR-555</a></td>
                    <td>15/01/2026</td>
                    <td>R$ 15.000,00</td>
                    <td><span class="status-badge status-pend">Pendente</span></td>
                </tr>
                <tr>
                    <td><a href="${pageContext.request.contextPath}/DetalharTransacaoCompra?id=432" class="id-link">#TR-432</a></td>
                    <td>10/12/2025</td>
                    <td>R$ 8.500,00</td>
                    <td><span class="status-badge status-ok">Pago</span></td>
                </tr>
            </tbody>
        </table>

        <div class="danger-zone">
            <button class="btn-delete" onclick="deleteSupplier()">Excluir Fornecedor</button>
        </div>

    </main>

    <script>
        /* --- JavaScript: Lógica de Negócio --- */

        // 1. Simulação de Dados Iniciais (Vindo do Banco de Dados)
        // Guardamos os valores originais para validar a regra de mudança de Tipo/Documento
        
        const originalData = {
            doc: '<c:out value="${fornecedor.documento}" />', //"55.123.456/0001-00",
            name: '<c:out value="${fornecedor.nome}" />', //"Indústrias Metalurgicas Aço",
            type: '<c:out value="${fornecedor.tipo}" />' // "Matéria-prima"
        };

        // Preencher o formulário ao carregar a página
        // EDITEI AQUIIIIII
        window.onload = function() {
            document.getElementById('doc').value = originalData.doc;
            document.getElementById('name').value = originalData.name;
            document.getElementById('type').value = originalData.type.charAt(0).toUpperCase() + originalData.type.slice(1).toLowerCase() ;
        };

        // 2. Função de Salvar (Com Validação Obrigatória)
        function saveChanges() {
            const currentDoc = document.getElementById('doc').value;
            const currentType = document.getElementById('type').value;
            const currentName = document.getElementById('name').value;
            
            const errorMsg = document.getElementById('error-msg');
            const successMsg = document.getElementById('success-msg');

            // Reset mensagens
            errorMsg.style.display = 'none';
            successMsg.style.display = 'none';

            // REGRA DE NEGÓCIO: Se editar o ‘Tipo’, é obrigatório alterar o ‘Documento’
            const typeChanged = currentType !== "${fornecedor.tipo}";//originalData.type;
            const docChanged = currentDoc !== "${fornecedor.documento}";//originalData.doc;

            if (typeChanged && !docChanged) {
                // Se mudou o tipo, mas manteve o mesmo documento -> ERRO
                errorMsg.style.display = 'block';
                // Feedback visual no input
                document.getElementById('doc').style.borderColor = 'var(--danger-color)';
                return;
            }

            // Se passou na validação
            document.getElementById('doc').style.borderColor = 'var(--border-color)';
            
            // Simula salvamento no banco de dados e atualiza "originais"
            originalData.doc = currentDoc;
            originalData.type = currentType;
            originalData.name = currentName;

            successMsg.style.display = 'block';
            
            // Oculta mensagem de sucesso após 3 segundos
            setTimeout(() => {
                successMsg.style.display = 'none';
            }, 3000);
        }

        // 3. Função de Exclusão
        function deleteSupplier() {
            // "abre uma caixa de confirmação"
            const confirmed = confirm(`ATENÇÃO:\n\nTem certeza que deseja excluir o fornecedor "${document.getElementById('name').value}"?\n\nEsta ação removerá o histórico e não pode ser desfeita.`);

            if (confirmed) {
                alert("Registro excluído com sucesso.");
                // Redireciona para a listagem principal
                window.location.href = '${pageContext.request.contextPath}/ListarFornecedores';
            }
        }
    </script>
</body>
</html>