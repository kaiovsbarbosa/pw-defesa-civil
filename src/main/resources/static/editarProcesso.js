const api_processos_url = 'http://localhost:8080/api/processos';
const api_usuarios_url = 'http://localhost:8080/api/usuarios';
const api_equipes_url = 'http://localhost:8080/api/equipes';
const api_relatorios_url = 'http://localhost:8080/relatorios';

const form = document.querySelector('form');
const selectCriador = document.querySelector('select[aria-label="select creator"]');
const selectEquipe = document.querySelector('select[aria-label="select team"]');

form.addEventListener('submit', async function (event) {
    event.preventDefault();

    const urlParams = new URLSearchParams(window.location.search);
    const processoId = urlParams.get('id');

    const formatDateToLocalDateTime = (dateStr) => {
        return dateStr ? `${dateStr}T00:00:00` : null;
    };

    const criadoPorId = selectCriador.value;
    const equipeId = selectEquipe.value;

    const dadosAtualizados = {
        tipo: document.getElementById('tipo').value,
        data: formatDateToLocalDateTime(document.getElementById('data').value),
        status: document.getElementById('status').value,
        localizacao: document.getElementById('localizacao').value,
        equipamento: document.getElementById('equipamento').value,
        descricao: document.getElementById('descricao').value,
        criadoPor: criadoPorId ? { id: parseInt(criadoPorId) } : null,
        equipe: equipeId ? { id: parseInt(equipeId) } : null
    };

    try {
        const respostaProcesso = await fetch(`${api_processos_url}/${processoId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosAtualizados)
        });

        if (!respostaProcesso.ok) {
            const erroData = await respostaProcesso.text();
            throw new Error(`Erro ao atualizar os dados do processo: ${erroData}`);
        }

        const relatorioFile = document.getElementById('relatorio').files[0];

        if (relatorioFile) {
            const formDataArquivo = new FormData();
            formDataArquivo.append('arquivo', relatorioFile);
            formDataArquivo.append('proprietarioId', processoId);
            formDataArquivo.append('tipo', 'PROCESSO');

            const respostaRelatorio = await fetch(api_relatorios_url, {
                method: 'POST',
                body: formDataArquivo
            });

            if (!respostaRelatorio.ok) {
                throw new Error('Processo atualizado, mas falha ao enviar o novo relatório.');
            }
        }

        alert('Processo atualizado com sucesso!');
        window.location.href = 'processo.html';

    } catch (erro) {
        console.error('Ocorreu um erro:', erro);
        alert(erro.message || 'Erro ao salvar alterações. Tente novamente.');
    }
});