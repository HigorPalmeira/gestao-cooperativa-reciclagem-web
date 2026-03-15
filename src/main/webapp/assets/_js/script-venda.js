

const tableBody = document.getElementById('resultsTableBody');

function renderTable(data) {

    tableBody.innerHTML = '';

    if (data.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="2" style="text-align:center; padding: 2rem; color: #777;">
                    Nenhuma venda encontrada.
                </td>
            </tr>`;

        return;

    }

    data.forEach(venda => {

        const fmtDate = new Date(venda.dtVenda).toLocaleDateString('pt-BR', { timeZone: 'UTC' });
        const fmtValue = venda.valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <a href="${ctx}/DetalharVenda?id=${venda.id}" class="id-link">
                    #VD-${String(venda.id).padStart(3, '0')}
                </a>
            </td>
            <td>${fmtDate}</td>
            <td class="text-right">${fmtValue}</td>
        `;
        tableBody.appendChild(tr);

    });

}


let countNullSearch = 0;
function searchSales() {
    // 1. Obter Valores dos Inputs
    const dateStart = document.getElementById('dateStart').value;
    const dateEnd = document.getElementById('dateEnd').value;
    const valMin = document.getElementById('valMin').value;
    const valMax = document.getElementById('valMax').value;

    const feedbackMsg = document.getElementById('feedback-message');

    // 2. Validação: Verifica se TODOS estão vazios
    const isDateEmpty = !dateStart && !dateEnd;
    const isValEmpty = !valMin && !valMax;

    if (isDateEmpty && isValEmpty && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return; // Para a execução
    } else {

        if (isDateEmpty && isValEmpty) countNullSearch++;

        feedbackMsg.style.display = 'none';
    }

    const parametros = `dataInicial=${dateStart}&dataFinal=${dateEnd}&valorMin=${valMin}&valorMax=${valMax}`;

    fetch((ctx + '/ListagemVendas?' + parametros))
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
            console.error('Erro no fetch:', err.message);
            alert('Erro: ' + err.message);
        });

}