let monitoramentos = [
    { id: 1, status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '20/11/2023', dataFim: '' }];

let monitoramentoSelecionado = null;


function renderizarTabela() {
    const corpoTabela = document.getElementById('tabela-clientes-corpo');
    corpoTabela.innerHTML = '';

    if (monitoramentos.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum Monitoramento encontrado.</td></tr>';
        return;
    }

    monitoramentos.forEach(monitoramento => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
                    <td>${monitoramento.status}</td>
                    <td>${monitoramento.descricao}</td>
                    <td>${monitoramento.dataInicio}</td>
                    <td>${monitoramento.dataFim}</td>
                    <td class="text-center">
                        <a href="editarMonitoramento.html?id=${monitoramento.id}" class="btn btn-primary btn-sm" title="Editar">
                            <i class="fa fa-pencil"></i>
                        </a>
                        <button class="btn btn-danger btn-sm ms-2" title="Deletar" onclick="preparaDelecao(${monitoramento.id})">
                            <i class="fa fa-trash"></i>
                        </button>
                    </td>
                `;
        corpoTabela.appendChild(tr);
    });
}

function preparaDelecao(monitoramentoId) {
    const confirmou = window.confirm("Tem certeza que deseja excluir este Monitoramento?");
    if (confirmou) {
        const indexParaDeletar = monitoramentos.findIndex(v => v.id === monitoramentoId);
        if (indexParaDeletar > -1) {
            monitoramentos.splice(indexParaDeletar, 1);
        }
        renderizarTabela();
        alert("Monitoramento excluído com sucesso!");
    }
}


document.addEventListener('DOMContentLoaded', () => {
    renderizarTabela();
});