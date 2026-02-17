<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.gestaocooperativareciclagem.model.PrecoMaterial" %>
<%@ page import="com.gestaocooperativareciclagem.model.TipoMaterial" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDate" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Preços de Materiais</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/_css/styles.css">
    
</head>
<body>

    <!-- Navegação -->
    <nav class="main-nav">
        <div class="brand">ERP System</div>
        <div>
            <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
        </div>
    </nav>

    <main class="container">
        
        <!-- Cabeçalho -->
        <div class="page-header">
            <h1>Gestão de Preços de Materiais</h1>
            <button class="btn-new" onclick="openPriceModal('new')">
                + Novo Preço
            </button>
        </div>

        <!-- Filtros -->
        <section class="search-card">
            <div class="search-form">
                <!-- Data -->
                <div class="filter-group">
                    <label>Intervalo de Datas (Vigência)</label>
                    <div class="inputs-row">
                        <input type="date" id="searchDateStart">
                        <span class="separator">até</span>
                        <input type="date" id="searchDateEnd">
                    </div>
                </div>
                <!-- Valor -->
                <div class="filter-group">
                    <label>Intervalo de Valores (R$)</label>
                    <div class="inputs-row">
                        <input type="number" id="searchValMin" placeholder="Mín" step="0.01">
                        <span class="separator">até</span>
                        <input type="number" id="searchValMax" placeholder="Máx" step="0.01">
                    </div>
                </div>
                <!-- Material -->
                <div class="filter-group">
                    <label>Tipo de Material</label>
                    <div class="inputs-row">
                        <select id="searchMaterial" name="searchMaterial">
                            <option value="">Todos</option>
                            <!-- Preenchido via JS -->
                            <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                            	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                            </c:forEach>
                            
                        </select>
                    </div>
                </div>

                <button class="btn-search" onclick="handleSearch()">Pesquisar</button>
            </div>
        </section>

        <!-- Tabela -->
        <section class="table-container">
            <table>
                <thead>
                    <tr>
                        <th style="width: 30%;">Preço (R$/Kg)</th>
                        <th style="width: 30%;">Data de Vigência</th>
                        <th style="width: 40%;">Tipo de Material</th>
                    </tr>
                </thead>
                <tbody id="tableBody">

					<c:forEach items="${listaPrecosMateriais}" var="precoMaterial">
						<tr>
							<td>
								<span class="price-link" onclick="openPriceModal('edit', {id: ${precoMaterial.id}, price: ${precoMaterial.precoCompra}, date: '${precoMaterial.dtVigencia}', materialId: ${precoMaterial.tipoMaterial.id}})">
									${String.format("R$ %.2f/Kg", precoMaterial.precoCompra)}
								</span>
							</td>
							<td>
								<fmt:formatDate value="${precoMaterial.dtVigencia}" pattern="dd/MM/yyyy" />
							</td>
							<td>
								<span class="material-link" onclick="openMaterialModal({id: ${precoMaterial.tipoMaterial.id}, name: '${precoMaterial.tipoMaterial.nome}', desc: '${precoMaterial.tipoMaterial.descricao}'})">
									${precoMaterial.tipoMaterial.nome}
								</span>
							</td>
						</tr>
					</c:forEach>      
					
					             

                </tbody>
            </table>
        </section>

    </main>

    <!-- MODAL 1: PREÇO (Edição/Criação) -->
    <div id="priceModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2 id="priceModalTitle">Preço de Material</h2>
                <span class="close-icon" onclick="closeModal('price')">&times;</span>
            </div>

            <!-- Aviso de data passada -->
            <div id="dateLockWarning" class="lock-warning">
                A data de vigência já ocorreu. A edição não é permitida.
            </div>

            <form id="priceForm" onsubmit="handlePriceSubmit(event)">
            	
            	<input type="text" id="modalId" name="modalId" style="display:none;">
            	
                <div class="form-group">
                    <label for="modalPrice">Preço (R$/Kg) *</label>
                    <input type="number" id="modalPrice" name="modalPrice" step="0.01" required>
                </div>
                <div class="form-group">
                    <label for="modalDate">Data de Vigência *</label>
                    <input type="date" id="modalDate" name="modalDate" required>
                </div>
                <div class="form-group">
                    <label for="modalMaterial">Tipo de Material *</label>
                    <select id="modalMaterial" name="modalMaterial" required>
                        <option value="">Selecione...</option>
                        <!-- Preenchido via JS -->
                        <c:forEach items="${listaTiposMateriais}" var="tipoMaterial">
                            	<option value="${tipoMaterial.id}">${tipoMaterial.nome}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="modal-footer">
                    <button type="button" id="btnDeletePrice" class="btn-modal-delete" onclick="deletePrice()">Excluir Preço</button>
                    <button type="submit" id="btnSavePrice" class="btn-modal-save">Salvar Alterações</button>
                    <button type="submit" id="btnCreatePrice" class="btn-modal-create">Cadastrar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- MODAL 2: TIPO DE MATERIAL (Leitura) -->
    <div id="materialInfoModal" class="modal-overlay">
        <div class="modal-content">
            <div class="modal-header">
                <h2>Detalhes do Material</h2>
                <span class="close-icon" onclick="closeModal('material')">&times;</span>
            </div>
            <div class="form-group">
                <label>Nome</label>
                <input type="text" id="infoMatName" disabled>
            </div>
            <div class="form-group">
                <label>Descrição</label>
                <textarea id="infoMatDesc" rows="4" disabled></textarea>
            </div>
            <div class="modal-footer" style="justify-content: flex-end;">
                <button type="button" class="btn-modal-save" style="background-color: #6c757d;" onclick="closeModal('material')">Fechar</button>
            </div>
        </div>
    </div>

    <script>
        /* --- Lógica da Aplicação --- */
        
        const ctx = "${pageContext.request.contextPath}";

        // Estado
        let currentEditPriceId = null;

        // Elementos Globais
        const tableBody = document.getElementById('tableBody');
        const form = document.getElementById('priceForm');
        
        // 2. Renderizar Tabela
        function renderTable(data) {
            tableBody.innerHTML = '';

            if (data.length === 0) {
                tableBody.innerHTML = '<tr><td colspan="3" style="text-align:center; padding: 2rem; color: #777;">Nenhum registo encontrado.</td></tr>';
                return;
            }

            data.forEach(item => {
                const material = materialsDB.find(m => m.id === item.materialId);
                const matName = material ? material.name : 'Desconhecido';
                
                // Formatações
                const fmtPrice = item.price.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + "/Kg";
                const fmtDate = new Date(item.date).toLocaleDateString('pt-BR', {timeZone: 'UTC'});

                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>
                        <span class="price-link" onclick="openPriceModal('edit', ${item.id})">
                            ${fmtPrice}
                        </span>
                    </td>
                    <td>${fmtDate}</td>
                    <td>
                        <span class="material-link" onclick="openMaterialModal(${item.materialId})">
                            ${matName}
                        </span>
                    </td>
                `;
                tableBody.appendChild(tr);
            });
        }

        // 3. Lógica Modal Preço
        function openPriceModal(mode, item = null) { // id = null
            const modal = document.getElementById('priceModal');
            const title = document.getElementById('priceModalTitle');
            const inpId = document.getElementById('modalId');
            const inpPrice = document.getElementById('modalPrice');
            const inpDate = document.getElementById('modalDate');
            const inpMat = document.getElementById('modalMaterial');
            const warning = document.getElementById('dateLockWarning');

            // Botões
            const btnDel = document.getElementById('btnDeletePrice');
            const btnSave = document.getElementById('btnSavePrice');
            const btnCreate = document.getElementById('btnCreatePrice');

            currentEditPriceId = !item ? null : item.id; // ---------------
            modal.style.display = 'flex';

            // Resetar estados
            inpPrice.disabled = false;
            inpDate.disabled = false;
            inpMat.disabled = false;
            warning.style.display = 'none';

            if (mode === 'new') {
                title.innerText = "Novo Preço";
                form.action = ctx + "/InserirPrecoMaterial";
                form.method = "POST";
                inpPrice.value = "";
                inpDate.value = "";
                inpMat.value = "";

                btnDel.style.display = 'none';
                btnSave.style.display = 'none';
                btnCreate.style.display = 'block';

            } else {
                title.innerText = "Editar Preço";
                
                if (!item) return;

                inpId.value = item.id;
                inpPrice.value = item.price;
                inpDate.value = item.date;
                inpMat.value = item.materialId;

                // Verificar Data de Vigência
                const today = new Date();
                today.setHours(0,0,0,0);
                const vigencia = new Date(item.date);
                vigencia.setHours(0,0,0,0); // Ignorar hora, comparar apenas data

                // Se vigência já ocorreu (< hoje), bloqueia edição
                if (vigencia < today) {
                    warning.style.display = 'block';
                    inpPrice.disabled = true;
                    inpDate.disabled = true;
                    inpMat.disabled = true;
                    btnSave.style.display = 'none'; // Não pode salvar
                    // Nota: O prompt diz "solicitará confirmação... para excluir", não diz que exclusão é bloqueada, mas edição sim. 
                    // Vou manter Excluir visível, mas edição bloqueada.
                } else {
                    btnSave.style.display = 'block';
                    form.action = ctx + "/AtualizarPrecoMaterial";
                    form.method = "POST";
                }

                btnDel.style.display = 'block';
                btnCreate.style.display = 'none';
            }
        }

        // 4. Lógica Modal Material (Read-only)
        function openMaterialModal(item = null) {
            const modal = document.getElementById('materialInfoModal');
            
            if (item) {
                document.getElementById('infoMatName').value = item.name;
                document.getElementById('infoMatDesc').value = item.desc;
                modal.style.display = 'flex';
            }
        }

        function closeModal(type) {
            if (type === 'price') document.getElementById('priceModal').style.display = 'none';
            if (type === 'material') document.getElementById('materialInfoModal').style.display = 'none';
            
            form.action = "";
        }

        // 5. CRUD Operations
        function handlePriceSubmit(event) {
            event.preventDefault();

            const priceVal = parseFloat(document.getElementById('modalPrice').value);
            const dateVal = document.getElementById('modalDate').value;
            const matId = parseInt(document.getElementById('modalMaterial').value);

            if (currentEditPriceId) {
                // Editar
                form.submit();
            } else {
                form.submit();
            }

        }

        function deletePrice() {
            
        	if (!currentEditPriceId) return;
            if (confirm("Tem a certeza que deseja excluir este registo de preço?")) {
                
                form.action = ctx + "/DeletarPrecoMaterial";
                form.method = "POST";
                form.submit();
                
            }
            
        }

        // 6. Pesquisa
        function handleSearch() {
            const dateStart = document.getElementById('searchDateStart').value;
            const dateEnd = document.getElementById('searchDateEnd').value;
            const valMin = document.getElementById('searchValMin').value;
            const valMax = document.getElementById('searchValMax').value;
            const matId = document.getElementById('searchMaterial').value;

            // Validação simples: exigir pelo menos 1
            if (!dateStart && !dateEnd && !valMin && !valMax && !matId) {
                alert("Preencha pelo menos um campo para pesquisar.");
                return;
            }

            const filtered = pricesDB.filter(item => {
                let validDate = true;
                let validVal = true;
                let validMat = true;

                if (dateStart) validDate = validDate && (new Date(item.date) >= new Date(dateStart));
                if (dateEnd) validDate = validDate && (new Date(item.date) <= new Date(dateEnd));

                if (valMin) validVal = validVal && (item.price >= parseFloat(valMin));
                if (valMax) validVal = validVal && (item.price <= parseFloat(valMax));

                if (matId) validMat = validMat && (item.materialId == matId);

                return validDate && validVal && validMat;
            });

            renderTable(filtered);
        }

        // Fechar modais ao clicar fora
        window.onclick = function(event) {
            if (event.target.classList.contains('modal-overlay')) {
                event.target.style.display = 'none';
            }
        }

    </script>
</body>
</html>