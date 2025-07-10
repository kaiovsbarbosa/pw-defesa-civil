const api_url = '';

document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const vistoriaId = urlParams.get('id');

    if (!vistoriaId) {
        alert('ID da vistoria não fornecido.');
        window.location.href = 'listaVistoria.html';
        return;
    }

    try {
        const resposta = await fetch(`${api_url}/${vistoriaId}`);
        if (!resposta.ok) throw new Error('Erro ao carregar a vistoria');
        const vistoria = await resposta.json();


        document.getElementById('dataInicio').value = formatarDataInput(vistoria.dataInicio);
        document.getElementById('dataFim').value = formatarDataInput(vistoria.dataFim);
        document.getElementById('status').value = vistoria.status.toLowerCase();
        document.getElementById('descricaoLocalizacao').value = vistoria.descricao;

    } catch (erro) {
        console.error('Erro ao carregar a vistoria:', erro);
        alert('Erro ao carregar dados da vistoria.');
        window.location.href = 'listaVistoria.html';
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
            const resposta = await fetch(`${api_url}/${vistoriaId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!resposta.ok) throw new Error('Erro ao atualizar a vistoria');
            alert('Vistoria atualizada com sucesso!');
            window.location.href = 'listaVistoria.html';
        } catch (erro) {
            console.error('Erro ao atualizar a vistoria:', erro);
            alert('Erro ao salvar alterações. Tente novamente.');
        }
    });
});

function formatarDataInput(dataString) {
    if (!dataString) return '';
    const data = new Date(dataString);
    return data.toISOString().split('T')[0];
}
