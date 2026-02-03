<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Fornecedor</title>
    <style>
        /* --- CSS: Estilização Visual --- */
        :root {
            --primary-color: #0056b3;
            --secondary-color: #6c757d;
            --background-color: #f4f6f9;
            --white: #ffffff;
            --border-color: #dee2e6;
            --danger-color: #dc3545;
            --success-color: #28a745;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background-color: var(--background-color);
            color: #333;
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
        nav.main-nav a { color: #fff; text-decoration: none; font-size: 0.9rem; margin-left: 15px;}
        nav.main-nav a:hover { text-decoration: underline; }

        /* Container Principal */
        .container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 0 1rem;
            padding-bottom: 4rem; /* Espaço para o botão de excluir no final */
        }

        h2 { border-bottom: 2px solid var(--border-color); padding-bottom: 10px; margin-top: 2rem; color: #495057; font-size: 1.2rem;}

        /* SEÇÃO 1: Formulário de Edição */
        .card {
            background-color: var(--white);
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            border: 1px solid var(--border-color);
            margin-bottom: 2rem;
        }

        .edit-form {
            display: grid;
            grid-template-columns: 1fr 1fr 1fr; /* 3 colunas */
            gap: 1.5rem;
            align-items: end;
        }

        .form-group { display: flex; flex-direction: column; }
        .form-group label { margin-bottom: 0.5rem; font-weight: 600; font-size: 0.9rem; }
        .form-group input, .form-group select {
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 4px;
            font-size: 1rem;
        }

        .btn-save {
            background-color: var(--primary-color);
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
            height: 42px;
        }
        .btn-save:hover { background-color: #004494; }

        /* SEÇÃO 2 e 3: Tabelas */
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: var(--white);
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 1rem;
        }

        th, td {
            text-align: left;
            padding: 12px 15px;
            border-bottom: 1px solid var(--border-color);
        }

        th { background-color: #e9ecef; font-weight: 600; color: #495057; }
        tr:last-child td { border-bottom: none; }
        
        /* Links de ID nas tabelas */
        .id-link { color: var(--primary-color); font-weight: bold; text-decoration: none; }
        .id-link:hover { text-decoration: underline; }

        /* Status Badges */
        .status { padding: 4px 8px; border-radius: 12px; font-size: 0.8rem; font-weight: bold; }
        .status-ok { background-color: #d4edda; color: #155724; }
        .status-pend { background-color: #fff3cd; color: #856404; }

        /* Botão de Exclusão (Final da página) */
        .danger-zone {
            margin-top: 3rem;
            text-align: right;
            border-top: 1px solid #ddd;
            padding-top: 1rem;
        }

        .btn-delete {
            background-color: var(--danger-color);
            color: white;
            padding: 12px 25px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
            font-weight: bold;
        }
        .btn-delete:hover { background-color: #c82333; }

        /* Mensagens de Feedback */
        #error-msg { color: var(--danger-color); font-size: 0.9rem; margin-top: 10px; display: none; }
        #success-msg { color: var(--success-color); font-size: 0.9rem; margin-top: 10px; display: none; }

    </style>
</head>
<body>

    <nav class="main-nav">
        <div style="font-weight: bold; font-size: 1.2rem;">ERP System &rsaquo; Fornecedor</div>
        <div>
            <a href="ListarFornecedores">Voltar para Gestão</a>
            <a href="../lotes_brutos/lotes_brutos.html">Lotes Brutos</a>
            <a href="../transacoes_compra/transacoes_compra.html">Transações de Compra</a>
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
                    <span id="error-msg">Erro: Se alterar o Tipo, você deve alterar o Documento.</span>
                    <span id="success-msg">Alterações salvas com sucesso!</span>
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
                <tr>
                    <td><a href="../lotes_brutos/lote_bruto.html?id=101" class="id-link">#LB-101</a></td>
                    <td>12/01/2026</td>
                    <td>500.00</td>
                    <td><span class="status status-pend">Em Análise</span></td>
                </tr>
                <tr>
                    <td><a href="../lotes_brutos/lote_bruto.html?id=098" class="id-link">#LB-098</a></td>
                    <td>05/01/2026</td>
                    <td>1,200.00</td>
                    <td><span class="status status-ok">Processado</span></td>
                </tr>
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
                    <td><a href="../transacoes_compra/transacao_compra.html?id=555" class="id-link">#TR-555</a></td>
                    <td>15/01/2026</td>
                    <td>R$ 15.000,00</td>
                    <td><span class="status status-pend">Pendente</span></td>
                </tr>
                <tr>
                    <td><a href="../transacoes_compra/transacao_compra.html?id=432" class="id-link">#TR-432</a></td>
                    <td>10/12/2025</td>
                    <td>R$ 8.500,00</td>
                    <td><span class="status status-ok">Pago</span></td>
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
                window.location.href = "ListarFornecedores";
            }
        }
    </script>
</body>
</html>