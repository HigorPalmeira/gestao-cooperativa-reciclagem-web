
const tableBody = document.getElementById('resultsTableBody');

function renderTable(lotes, etapas) {

    tableBody.innerHTML = '';

    if (lotes.length === 0) {

        tableBody.innerHTML = `
            <tr>
                <td colspan="2" style="text-align:center; padding: 2rem; color: #777;">
                    Nenhum tipo de material encontrado.
                </td>
            </tr>`;

        return;

    }

    lotes.forEach(lote => {

        const fmtDate = new Date(lote.dtCriacao).toLocaleDateString('pt-BR', {timeZone: 'UTC'});
        const fmtWeight = lote.pesoAtualKg.toLocaleString('pt-BR', {minimumFractionDigits: 2});

        let stageClass = '';
        const stage = etapas.find(etapa => etapa.loteProcessado.id === lote.id).categoriaProcessamento.nome;

        if (stage.toLowerCase().includes('triagem')) stageClass = 'stage-triagem';
        
        else if (stage.toLowerCase().includes('trituração')) stageClass = 'stage-trituracao';

        else if (stage.toLowerCase().includes('limpeza')) stageClass = 'stage-lavagem';

        else if (stage.toLowerCase().includes('extrusão')) stageClass = 'stage-extrusao';

        else if (stage.toLowerCase().includes('armazenado')) stageClass = 'stage-armazenado';

        else if (stage.toLowerCase().includes('descarte')) stageClass = 'stage-descarte';

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>
                <a href="${ctx}/DetalharLoteProcessado?id=${lote.id}" class="id-link">
                    #LP-${String(lote.id).padStart(3, '0')}
                </a>
            </td>
            <td>${fmtWeight}</td>
            <td>${fmtDate}</td>
            <td>${lote.tipoMaterial.nome}</td>
            <td>
                <span class="stage-badge ${stageClass}">
                    ${stage}
                </span>
            </td>
        `;
        tableBody.appendChild(tr);

    });

}


let countNullSearch = 0;
function searchProcessedBatches() {
    // 1. Obter inputs
    const dateStart = document.getElementById('dateStart').value;
    const dateEnd = document.getElementById('dateEnd').value;
    const weightMin = document.getElementById('weightMin').value;
    const weightMax = document.getElementById('weightMax').value;
    const stage = document.getElementById('stageSelect').value;

    const feedbackMsg = document.getElementById('feedback-message');

    
    const isDateEmpty = !dateStart && !dateEnd;
    const isWeightEmpty = !weightMin && !weightMax;
    const isStageEmpty = !stage;

    if (isDateEmpty && isWeightEmpty && isStageEmpty && countNullSearch >= 3) {
        countNullSearch = 0;
        feedbackMsg.style.display = 'block';
        return;
    } else {

        if (isDateEmpty && isWeightEmpty && isStageEmpty) countNullSearch++;

        feedbackMsg.style.display = 'none';
    }

    const parametros = `dataCriacao=${dateStart}&dataFinal=${dateEnd}&pesoAtual=${weightMin}&pesoMax=${weightMax}&etapa=${stage}`;

    fetch((ctx + '/ListagemLotesProcessado?' + parametros))
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => {
                    throw new Error(err.error);
                });
            }

            return response.json();

        })
        .then(data => {
            renderTable(data.lotesProcessados, data.etapasProcessamento);
        })
        .catch(err => {
            console.error('Erro no fetch:', err.message);
            alert('Erro: ' + err.message);
        });

}