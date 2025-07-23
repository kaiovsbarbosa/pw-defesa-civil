const api_processos_url = 'http://localhost:8080/api/processos';
const api_usuarios_url = 'http://localhost:8080/api/usuarios';
const api_equipes_url = 'http://localhost:8080/api/equipes';
const api_relatorios_url = 'http://localhost:8080/relatorios';

const form = document.querySelector('form');
const selectCriador = document.getElementById('criador');
const selectEquipe = document.getElementById('equipe');


document.addEventListener('DOMContentLoaded', async () => {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    await carregarUsuarios();
    await carregarEquipes();
    await carregarDadosDoProcesso();
});


async function carregarUsuarios() {
    try {
        const resposta = await Auth.fetchWithAuth(api_usuarios_url);
        const usuarios = await resposta.json();

        usuarios.forEach(usuario => {
            const option = document.createElement('option');
            option.value = usuario.id;
            option.textContent = usuario.nome;
            selectCriador.appendChild(option);
        });
    } catch (erro) {
        console.error('Erro ao carregar usuários:', erro);
    }
}


async function carregarEquipes() {
    try {
        const resposta = await Auth.fetchWithAuth(api_equipes_url);
        const equipes = await resposta.json();

        equipes.forEach(equipe => {
            const option = document.createElement('option');
            option.value = equipe.id;
            option.textContent = equipe.nomeEquipe;
            selectEquipe.appendChild(option);
        });
    } catch (erro) {
        console.error('Erro ao carregar equipes:', erro);
    }
}


async function carregarDadosDoProcesso() {
    const urlParams = new URLSearchParams(window.location.search);
    const processoId = urlParams.get('id');

    if (!processoId) {
        alert('ID do processo não fornecido.');
        return;
    }

    try {
        const resposta = await Auth.fetchWithAuth(`${api_processos_url}/${processoId}`);
        if (!resposta.ok) throw new Error('Erro ao buscar dados do processo');
        const processo = await resposta.json();

        document.getElementById('tipo').value = processo.tipo || '';
        document.getElementById('data').value = processo.data ? processo.data.split('T')[0] : '';
        document.getElementById('status').value = processo.status || '';
        document.getElementById('localizacao').value = processo.localizacao || '';
        document.getElementById('equipamento').value = processo.equipamento || '';
        document.getElementById('descricao').value = processo.descricao || '';
        if (processo.criadoPorId) selectCriador.value = processo.criadoPorId;
        if (processo.equipeId) selectEquipe.value = processo.equipeId;

    } catch (erro) {
        console.error('Erro ao carregar processo:', erro);
        alert('Erro ao carregar dados para edição.');
    }
}


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
        criadoPorId: criadoPorId ? parseInt(criadoPorId) : null,
        equipeId: equipeId ? parseInt(equipeId) : null
    };

    try {
        const respostaProcesso = await Auth.fetchWithAuth(`${api_processos_url}/${processoId}`, {
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

            const respostaRelatorio = await Auth.fetchWithAuth(api_relatorios_url, {
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
