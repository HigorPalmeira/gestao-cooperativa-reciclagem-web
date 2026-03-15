/**
 * 
 * Script de interações para a página tipoMaterial.jsp.
 * 
 */


// Estado Atual
let currentEditId = null;

// Elementos DOM
const tableBody = document.getElementById('tableBody');
const modal = document.getElementById('materialModal');
const modalTitle = document.getElementById('modalTitle');
const modalForm = document.getElementById('modalForm');
const inpId = document.getElementById('modalId');
const inpName = document.getElementById('modalName');
const inpDesc = document.getElementById('modalDesc');

// Botões do Modal
const btnDelete = document.getElementById('btnDelete');
const btnSave = document.getElementById('btnSave');
const btnCreate = document.getElementById('btnCreate');

// 2. Renderizar Tabela
function renderTable(data) {
	tableBody.innerHTML = '';

	if (data.length === 0) {
		tableBody.innerHTML = '<tr><td colspan="2" style="text-align:center; padding: 2rem; color: #777;">Nenhum tipo de material encontrado.</td></tr>';
		return;
	}

	data.forEach(item => {
		const tr = document.createElement('tr');
		tr.innerHTML = `
                    <td>
                        <span class="name-link" onclick="openModal('edit', ${item.id})">
                            ${item.nome}
                        </span>
                    </td>
                    <td>${item.descricao}</td>
                `;
		tableBody.appendChild(tr);
	});
}

// 3. Abrir Modal (Lógica Central)
function openModal(mode, item = null) {
	modal.style.display = 'flex';
	currentEditId = !item ? null : item.id;

	if (mode === 'new') {
		// MODO: NOVO TIPO
		modalTitle.innerText = "Novo Tipo";
		modalForm.action = ctx + "/InserirTipoMaterial";
		modalForm.method = "POST";
		inpName.value = "";
		inpDesc.value = "";

		// Configuração de Botões
		btnDelete.style.display = 'none'; // Esconde Excluir
		btnSave.style.display = 'none';   // Esconde Salvar
		btnCreate.style.display = 'block'; // Mostra Cadastrar

	} else if (mode === 'edit') {
		// MODO: EDITAR TIPO
		if (!item) return;

		modalTitle.innerText = "Editar Tipo";
		modalForm.action = "";
		modalForm.method = "POST";
		inpId.value = item.id;
		inpName.value = item.name;
		inpDesc.value = item.desc;

		// Configuração de Botões
		btnDelete.style.display = 'block'; // Mostra Excluir (Esquerda)
		btnSave.style.display = 'block';   // Mostra Salvar (Direita)
		btnCreate.style.display = 'none';  // Esconde Cadastrar
	}
}

// 4. Fechar Modal
function closeModal() {
	inpId.value = "";
	inpName.value = "";
	inpDesc.value = "";
	modal.style.display = 'none';
	modalForm.action = "";
	currentEditId = null;
}

// Fechar ao clicar fora
window.onclick = function(event) {
	if (event.target == modal) closeModal();
}

// 5. Submissão do Formulário (Salvar ou Cadastrar)
function handleFormSubmit(event) {
	event.preventDefault(); // Impede reload da página

	const newName = inpName.value.trim();
	const newDesc = inpDesc.value.trim();

	if (currentEditId) {

		modalForm.action = ctx + "/AtualizarTipoMaterial";
		modalForm.submit();

	} else {

		modalForm.submit();

	}

	closeModal();
}

// 6. Excluir Tipo
function deleteType() {
	if (!currentEditId) return;

	const confirmDelete = confirm("Tem a certeza que deseja excluir este tipo de material?\nEsta ação não pode ser desfeita.");

	if (confirmDelete) {

		modalForm.action = ctx + "/DeletarTipoMaterial";
		modalForm.submit();

	}
}

let countNullSearch = 0;
function handleSearch() {
	const sName = document.getElementById('searchName').value.toLowerCase();
	const sDesc = document.getElementById('searchDesc').value.toLowerCase();
	const feedback = document.getElementById('feedback-msg');

	// Validação mínima
	if (!sName && !sDesc && countNullSearch >= 3) {
		countNullSearch = 0;
		feedback.style.display = 'block';
		return;
	} else {

		if (!sName && !sDesc) countNullSearch++;
		
		feedback.style.display = 'none';
	}

	let parametros = `nome=${sName}&descricao=${sDesc}`;

	fetch((ctx + '/ListagemTiposMaterial?' + parametros))
		.then(response => {

			if (!response.ok) {
				return response.json().then(err => {
					throw new Error(err.error);
				});
			}

			return response.json();

		})
		.then(data => {
			renderTable(data);
		})
		.catch(error => {
			console.error('Erro no fetch:', error.message);
			alert('Erro: ' + error.message);
		})

}