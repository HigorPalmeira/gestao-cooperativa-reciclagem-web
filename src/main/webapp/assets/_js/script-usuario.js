
const tableBody = document.getElementById('resultsTableBody');

function renderTable(data) {

    tableBody.innerHTML = '';

    if (data.length === 0) {

        tableBody.innerHTML = '<tr><td colspan="2" style="text-align: center; padding: 2rem; color: #777;">Nenhum usuário encontrado.</td></tr>';

        return;

    }

    data.forEach(usuario => {

        let badgeClass = 'role-user';
        if (usuario.papel.toLowerCase() === 'administrador') badgeClass = 'role-admin';
        if (usuario.papel.toLowerCase() === 'gerente') badgeClass = 'role-manager';
        if (usuario.papel.toLowerCase() === 'operador') badgeClass = 'role-operator';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <a href="${ctx}/DetalharUsuario?id${usuario.id}" class="user-link">
                    ${usuario.nome}
                </a>
            </td>
            <td>${usuario.email}</td>
            <td><span class="role-badge ${badgeClass}">${usuario.papel}</span></td>
        `;
        tableBody.appendChild(tr);

    });

}

let countNullSearch = 0;
function searchUsers() {
    const nameInput = document.getElementById('searchName').value.trim().toLowerCase();
    const roleInput = document.getElementById('searchRole').value;
    const feedbackMsg = document.getElementById('feedback-message');

    // 1. Regra de Validação: Pesquisa só executa se houver input
    if (nameInput === "" && roleInput === "" && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return;
    } else {

        if (nameInput === "" && roleInput === "") countNullSearch++;
        
        feedbackMsg.style.display = 'none';
    }

    const parametros = `nome=${nameInput}&papel=${roleInput}`;

    fetch((ctx + "/ListagemUsuarios?" + parametros))
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.error);
                });
            }

            return response.json();
        })
        .then(data => {
            renderTable(data);
        })
        .catch(err => {
            console.error('Erro no fetch: ', err.message);
            alert('Erro: ' + err.message);
        });

}