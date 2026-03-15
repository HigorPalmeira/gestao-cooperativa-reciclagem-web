
const tableBody = document.getElementById('resultsTableBody');

function renderTable(data) {

    tableBody.innerHTML = '';

    if (data.length === 0) {
        
        tableBody.innerHTML = `
            <tr>
                <td colspan="2" style="text-align:center; padding: 2rem; color: #777;">
                    Nenhum tipo de material encontrado.
                </td>
            </tr>`;
		
        return;

    }

    data.forEach(fornecedor => {

        const fmtDate = new Date(fornecedor.dtCadastro).toLocaleDateString('pt-BR', {timeZone: 'UTC'});

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${fornecedor.documento}</td>
            <td>
                <a href="${ctx}/DetalharFornecedor?doc=${fornecedor.documento}" class="supplier-link">
                    ${fornecedor.nome}
                </a>
            </td>
            <td>${fornecedor.tipo}</td>
            <td>${fmtDate}</td>
        `;
        tableBody.appendChild(tr);

    });

}

let countNullSearch = 0;
function searchSuppliers() {
    const nameInput = document.getElementById('searchName').value.trim();
    const typeInput = document.getElementById('searchType').value.trim();
    const feedbackMsg = document.getElementById('feedback-message');
    
    if (nameInput === "" && typeInput === "" && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return;
    } else {

        if (nameInput === "" && typeInput === "") countNullSearch++;

        feedbackMsg.style.display = 'none';
    }

    const parametros = `nome=${nameInput}&tipo=${typeInput}`;
    
    fetch((ctx + '/ListagemFornecedores?' + parametros))
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