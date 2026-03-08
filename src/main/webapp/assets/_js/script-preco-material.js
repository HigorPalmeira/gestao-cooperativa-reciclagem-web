
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
        
        // Formatações
        const fmtPrice = item.precoCompra.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + "/Kg";
        const fmtDate = new Date(item.dtVigencia).toLocaleDateString('pt-BR', { timeZone: 'UTC' });

        const tr = document.createElement('tr');
        tr.innerHTML = `
                    <td>
                        <span class="price-link" onclick="openPriceModal('edit', {id: ${item.id}, price: ${item.precoCompra}, date: '${item.dtVigencia}', materialId: ${item.tipoMaterial.id}})">
                            ${fmtPrice}
                        </span>
                    </td>
                    <td>${fmtDate}</td>
                    <td>
                        <span class="material-link" onclick="openMaterialModal({id: ${item.tipoMaterial.id}, name: '${item.tipoMaterial.nome}', desc: '${item.tipoMaterial.descricao}'})">
                            ${item.tipoMaterial.nome}
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
        today.setHours(0, 0, 0, 0);
        const vigencia = new Date(item.date);
        vigencia.setHours(0, 0, 0, 0); // Ignorar hora, comparar apenas data

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

    const parametros = `precoCompra=${valMin}&dataVigencia=${dateStart}&idTipoMaterial=${matId}`;

    fetch((ctx + "/ListagemPrecosMaterial?" + parametros))
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.error);
                });
            }

            return response.json();

        })
        .then(data => {
            renderTable(data.precosMateriais);
        })
        .catch(error => {
            console.error("Erro no fetch:", error.message);
            alert("Erro: " + error.message);
        });

}

// Fechar modais ao clicar fora
window.onclick = function (event) {
    if (event.target.classList.contains('modal-overlay')) {
        event.target.style.display = 'none';
    }
}