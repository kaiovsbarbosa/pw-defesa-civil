const api_url = 'http://localhost:8080/api/processos';

document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const processoId = urlParams.get('id');

    if (!processoId) {
        alert('ID do processo não fornecido.');
        window.location.href = 'listaProcessos.html';
        return;
    }

    try {
        const resposta = await fetch(`${api_url}/${processoId}`);
        if (!resposta.ok) throw new Error('Erro ao carregar o processo');
        const processo = await resposta.json();

        document.getElementById('tipo').value = processo.tipo;
        document.getElementById('dataInicio').value = processo.dataInicio?.split('T')[0];
        document.getElementById('dataFim').value = processo.dataFim?.split('T')[0];
        document.getElementById('status').value = processo.status;
        document.getElementById('descricaoLocalizacao').value =processo.localizacaoDescricao;
        document.getElementById('descricao').value = processo.descricao ?? '';

        console.log('Processo retornado:', processo);
    } catch (erro) {
        console.error('Erro ao carregar o processo:', erro);
        alert('Erro ao carregar dados do processo.');
        window.location.href = 'listaProcessos.html';
    }

    const form = document.querySelector('form');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const formatDateToLocalDateTime = (dateStr) => {
            return dateStr ? `${dateStr}T00:00:00` : null;
        };

        const dadosAtualizados = {
            tipo: document.getElementById('tipo').value,
            dataInicio: formatDateToLocalDateTime(document.getElementById('dataInicio').value),
            dataFim: formatDateToLocalDateTime(document.getElementById('dataFim').value),
            status: document.getElementById('status').value,
            descricaoLocalizacao: document.getElementById('descricaoLocalizacao').value,
            descricao: document.getElementById('descricao').value,
            criadoPor: null,
            equipe: null
        };

        try {
            const resposta = await fetch(`${api_url}/${processoId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!resposta.ok) throw new Error('Erro ao atualizar o processo');
            alert('Processo atualizado com sucesso!');
            window.location.href = 'listaProcessos.html';
        } catch (erro) {
            console.error('Erro ao atualizar o processo:', erro);
            alert('Erro ao salvar alterações. Tente novamente.');
        }
    });
});


