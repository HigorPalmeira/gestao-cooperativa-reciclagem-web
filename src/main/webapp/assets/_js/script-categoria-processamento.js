/**
 * 
 * Script de interações para a página categoriaProcessamento.jsp.
 * 
 */


// Estado Atual
let currentEditId = null;

// Elementos DOM
const tableBody = document.getElementById('tableBody');
const modal = document.getElementById('categoryModal');
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
		tableBody.innerHTML = '<tr><td colspan="2" style="text-align:center; padding: 2rem; color: #777;">Nenhuma categoria encontrada.</td></tr>';
		return;
	}

	data.forEach(cat => {
		const tr = document.createElement('tr');
		tr.innerHTML = `
                    <td>
                        <span class="name-link" onclick="openModal('edit', ${cat.id})">
                            ${cat.nome}
                        </span>
                    </td>
                    <td>${cat.descricao}</td>
                `;
		tableBody.appendChild(tr);
	});
}

// 3. Abrir Modal (Lógica Central)
function openModal(mode, cat = null) { // id = null
	modal.style.display = 'flex';
	currentEditId = !cat ? null : cat.id;

	if (mode === 'new') {
		// MODO: NOVA CATEGORIA
		modalTitle.innerText = "Nova Categoria";
		modalForm.action = ctx + "/InserirCategoriaProcessamento";
		modalForm.method = "POST";
		inpName.value = "";
		inpDesc.value = "";

		// Configuração de Botões
		btnDelete.style.display = 'none'; // Esconde Excluir
		btnSave.style.display = 'none';   // Esconde Salvar
		btnCreate.style.display = 'block'; // Mostra Cadastrar

	} else if (mode === 'edit') {
		// MODO: EDITAR CATEGORIA
		//const cat = categoriesDB.find(c => c.id === id);
		if (!cat) return;

		modalTitle.innerText = "Editar Categoria";
		modalForm.action = "";
		modalForm.method = "POST";
		inpId.value = cat.id;
		inpName.value = cat.name;
		inpDesc.value = cat.desc;

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

		modalForm.action = ctx + "/AtualizarCategoriaProcessamento";
		modalForm.submit();

	} else {

		modalForm.submit();

	}

	closeModal();
}

// 6. Excluir Categoria
function deleteCategory() {
	if (!currentEditId) return;

	const confirmDelete = confirm("Tem a certeza que deseja excluir esta categoria?\nEsta ação não pode ser desfeita.");

	if (confirmDelete) {

		modalForm.action = ctx + "/DeletarCategoriaProcessamento";
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

	fetch((ctx + '/ListagemCategoriasProcessamento?' + parametros))
		.then(response => {

			if (!response.ok) {
				return response.json().then(err => {
					throw new Error(err.error);
				});
			}

			return response.json();

		})
		.then(data => {
			console.log('Categorias: ', data);
			renderTable(data);
		})
		.catch(error => {
			console.error('Erro no fetch:', error.message);
			alert('Erro: ' + error.message);
		})

}