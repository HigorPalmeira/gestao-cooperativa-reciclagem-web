
const tableBody = document.getElementById('resultsTableBody');

function renderTable(data) {

    tableBody.innerHTML = '';

    if (data.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="2" style="text-align:center; padding: 2rem; color: #777;">
                    Nenhuma transação de compra encontrado.
                </td>
            </tr>`;

        return;

    }

    data.forEach(transacao => {

        const fmtDate = transacao.dtPagamento ? new Date(transacao.dtPagamento).toLocaleDateString('pt-BR', {timeZone: 'UTC'}) : '---';
        const fmtValue = transacao.valorTotalCalculado.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'});

        let statusClass = 'status-pendente';
        if (transacao.status === 'PAGO') statusClass = 'status-pago';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <a href="${ctx}/DetalharTransacaoCompra?id=${transacao.id}" class="id-link">
                    #TR-${String(transacao.id).padStart(3, '0')}
                </a>
            </td>
            <td>${fmtDate}</td>
            <td class="text-right">${fmtValue}</td>
            <td>
                <span class="status-badge ${statusClass}">
                    ${transacao.status}
                </span>
            </td>
        `;
        tableBody.appendChild(tr);
        
    });

}


let countNullSearch = 0;
function searchTransactions() {
    // 1. Obter inputs
    const dateStart = document.getElementById('dateStart').value;
    const dateEnd = document.getElementById('dateEnd').value;
    const valMin = document.getElementById('valMin').value;
    const valMax = document.getElementById('valMax').value;
    const status = document.getElementById('statusSelect').value;

    const feedbackMsg = document.getElementById('feedback-message');

    // 2. Validação: Pelo menos um campo deve estar preenchido
    const isDateEmpty = !dateStart && !dateEnd;
    const isValEmpty = !valMin && !valMax;
    const isStatusEmpty = !status;

    if (isDateEmpty && isValEmpty && isStatusEmpty && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return;
    } else {

        if (isDateEmpty && isValEmpty && isStatusEmpty) countNullSearch++;

        feedbackMsg.style.display = 'none';
    }

    const parametros = `dataInicio=${dateStart}&dataFinal=${dateEnd}&valor=${valMin}&valorMax=${valMax}&status=${status}`;
    
    fetch((ctx + '/ListagemTransacoesCompra?' + parametros))
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