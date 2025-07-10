const api_url = '';

document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const monitoramentoId = urlParams.get('id');

    if (!monitoramentoId) {
        alert('ID do monitoramento não fornecido.');
        window.location.href = 'listaMonitoramento.html';
        return;
    }

    try {
        const resposta = await fetch(`${api_url}/${monitoramentoId}`);
        if (!resposta.ok) throw new Error('Erro ao carregar o monitoramento');
        const monitoramento = await resposta.json();


        document.getElementById('dataInicio').value = formatarDataInput(monitoramento.dataInicio);
        document.getElementById('dataFim').value = formatarDataInput(monitoramento.dataFim);
        document.getElementById('status').value = monitoramento.status.toLowerCase();
        document.getElementById('descricaoLocalizacao').value = monitoramento.descricao;

    } catch (erro) {
        console.error('Erro ao carregar o monitoramento:', erro);
        alert('Erro ao carregar dados do monitoramento.');
        window.location.href = 'listaMonitoramento.html';
    }

    const form = document.querySelector('form');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const dadosAtualizados = {
            dataInicio: document.getElementById('dataInicio').value,
            dataFim: document.getElementById('dataFim').value,
            status: document.getElementById('status').value,
            descricao: document.getElementById('descricaoLocalizacao').value
        };

        try {
            const resposta = await fetch(`${api_url}/${monitoramentoId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!resposta.ok) throw new Error('Erro ao atualizar o monitoramento');
            alert('Monitoramento atualizado com sucesso!');
            window.location.href = 'listaMonitoramento.html';
        } catch (erro) {
            console.error('Erro ao atualizar o monitoramento:', erro);
            alert('Erro ao salvar alterações. Tente novamente.');
        }
    });
});

function formatarDataInput(dataString) {
    if (!dataString) return '';
    const data = new Date(dataString);
    return data.toISOString().split('T')[0];
}
