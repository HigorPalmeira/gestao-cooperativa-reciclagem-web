
const tableBody = document.getElementById('resultsTableBody');

function renderTable(data) {

    tableBody.innerHTML = '';
    
    if (data.length === 0) {
        
        tableBody.innerHTML = `
            <tr>
                <td colspan="2" style="text-align:center; padding: 2rem; color: #777;">
                    Nenhum tipo de material encontrado.
                </td>
            </tr>
        `;
		
        return;

    }

    data.forEach(cliente => {

        const fmtDate = new Date(cliente.dtCadastro).toLocaleDateString('pt-BR', {timeZone: 'UTC'});

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <a href="${ctx}/DetalharCliente?cnpj=${cliente.cnpj}" class="cnpj-link">
                    ${cliente.cnpj}
                </a>
            </td>
            <td>${cliente.nomeEmpresa}</td>
            <td>${cliente.emailContato}</td>
            <td>${fmtDate}</td>
        `;
        tableBody.appendChild(tr);

    });

}

let countNullSearch = 0;

function searchClients() {
    // 1. Obter valores
    const cnpjInput = document.getElementById('searchCnpj').value.trim();
    const nameInput = document.getElementById('searchName').value.trim().toLowerCase();
    const emailInput = document.getElementById('searchEmail').value.trim().toLowerCase();

    const feedbackMsg = document.getElementById('feedback-message');

    // 2. Validação: Pelo menos um campo preenchido
    if (cnpjInput === "" && nameInput === "" && emailInput === "" && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return; // Interrompe a função
    } else {

        if (cnpjInput === "" && nameInput === "" && emailInput === "") countNullSearch++;

        feedbackMsg.style.display = 'none';
    }

    const parametros = `cnpj=${cnpjInput}&nome=${nameInput}&email=${emailInput}`;

    fetch((ctx + '/ListagemClientes?' + parametros))
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
        .catch(err => {
            console.error('Erro no fetch: ', err.message);
            alert('Erro: ' + err.message);
        });

}