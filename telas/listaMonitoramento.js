let monitoramentos = [
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '20/11/2023', dataFim: '' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '15/04/2020', dataFim: '23/01/2023' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '03/02/2024', dataFim: '15/07/2024' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '18/03/2022', dataFim: '25/09/2023' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '12/02/2025', dataFim: '' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '03/06/2025', dataFim: '' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '01/08/2021', dataFim: '15/08/2021' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '30/11/2024', dataFim: '' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '22/05/2023', dataFim: '19/01/2024' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '15/04/2022', dataFim: '10/01/2023' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '05/04/2025', dataFim: '' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '14/07/2020', dataFim: '08/11/2022' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '05/02/2024', dataFim: '20/05/2024' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '02/12/2024', dataFim: '' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '11/09/2022', dataFim: '04/04/2023' },
    { status: 'Finalizado', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '07/01/2024', dataFim: '18/06/2024' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '19/03/2025', dataFim: '' },
    { status: 'Em Andamento', descricao: 'Descrição relacionada ao Monitoramento', dataInicio: '01/02/2025', dataFim: '' }];

let monitoramentoSelecionado = null;


function renderizarTabela() {
    const corpoTabela = document.getElementById('tabela-clientes-corpo');
    corpoTabela.innerHTML = '';

    if (monitoramentos.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum cliente encontrado.</td></tr>';
        return;
    }

    monitoramentos.forEach(monitoramentos => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
                    <td>${monitoramentos.status}</td>
                    <td>${monitoramentos.descricao}</td>
                    <td>${monitoramentos.dataInicio}</td>
                    <td>${monitoramentos.dataFim}</td>
                    <td class="text-center">
                        <a href="#" class="btn btn-primary btn-sm" title="Editar" onclick="editarMonitoramento(${monitoramentos.id})">
                            <i class="fa fa-pencil"></i>
                        </a>
                        <button class="btn btn-danger btn-sm ms-2" title="Deletar" onclick="preparaDelecao(${monitoramentos.id})">
                            <i class="fa fa-trash"></i>
                        </button>
                    </td>
                `;
        corpoTabela.appendChild(tr);
    });
}


document.addEventListener('DOMContentLoaded', () => {
    renderizarTabela();
});