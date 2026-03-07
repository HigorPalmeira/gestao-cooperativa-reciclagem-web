
const tableBody = document.getElementById('resultsTableBody');

function renderTable(data) {

    tableBody.innerHTML = '';

    if (data.length === 0){

        tableBody.innerHTML = `
            <tr>
                <td colspan="2" style="text-align:center; padding: 2rem; color: #777;">
                    Nenhum tipo de material encontrado.
                </td>
            </tr>`;

        return;

    }

    data.forEach(lote => {

        const fmtDate = new Date(lote.dtEntrada).toLocaleDateString('pt-BR');
        const fmtWeight = lote.pesoEntradaKg.toLocaleString('pt-BR', {minimumFractionDigits: 2});

        let statusClass = 'status-recebido';
        let statusText = 'Recebido';
        
        if (lote.status === 'PROCESSADO') {
            statusClass = 'status-concluido';
            statusText = 'Processado';
        }

        if (lote.status === 'EM_TRIAGEM') {
            statusClass = 'status-processamento';
            statusText = 'Em Triagem';
        }

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <a href="${ctx}/DetalharLoteBruto?id=${lote.id}" class="id-link">
                    #LB-${String(lote.id).padStart(3, '0')}
                </a>
            </td>
            <td>${fmtWeight}</td>
            <td>${fmtDate}</td>
            <td><span class="status-badge ${statusClass}">${statusText}</span></td>
        `;
        tableBody.appendChild(tr);

    });

}

let countNullSearch = 0;
function searchBatches() {
    // 1. Obter inputs
    const dateStart = document.getElementById('dateStart').value;
    const dateEnd = document.getElementById('dateEnd').value;
    const weightMin = document.getElementById('weightMin').value;
    const weightMax = document.getElementById('weightMax').value;

    const feedbackMsg = document.getElementById('feedback-message');

    // 2. Validação: A pesquisa só é executada quando pelo menos um campo for preenchido
    const isDateEmpty = !dateStart && !dateEnd;
    const isWeightEmpty = !weightMin && !weightMax;

    if (isDateEmpty && isWeightEmpty && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return; // Interrompe a execução
    } else {

        if (isDateEmpty && isWeightEmpty) countNullSearch++;

        feedbackMsg.style.display = 'none';
    }

    const parametros = `dataEntrada=${dateStart}&pesoEntrada=${weightMin}`;

    fetch((ctx + '/ListagemLotesBruto?' + parametros))
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