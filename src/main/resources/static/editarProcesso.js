const api_processos_url = 'http://localhost:8080/api/processos';
const api_usuarios_url = 'http://localhost:8080/api/usuarios';
const api_equipes_url = 'http://localhost:8080/api/equipes';
const bucket_url = 'https://8d6807fabce5.ngrok-free.app/upload';

const form = document.querySelector('form');
const selectCriador = document.getElementById('criador');
const selectEquipe = document.getElementById('equipe');
const inputRelatorio = document.getElementById('relatorio');
const nomeRelatorioAtual = document.getElementById('nome-relatorio-atual');

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

        if (processo.arquivo) {
            nomeRelatorioAtual.textContent = `Relatório atual: ${processo.arquivo}`;
        }

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

    const arquivoNome = relatorio.files[0].name;

    const dadosAtualizados = {
        tipo: document.getElementById('tipo').value,
        data: formatDateToLocalDateTime(document.getElementById('data').value),
        status: document.getElementById('status').value,
        localizacao: document.getElementById('localizacao').value,
        equipamento: document.getElementById('equipamento').value,
        descricao: document.getElementById('descricao').value,
        criadoPorId: criadoPorId ? parseInt(criadoPorId) : null,
        equipeId: equipeId ? parseInt(equipeId) : null,
        arquivo: arquivoNome       

    };

    try {
        const arquivo = inputRelatorio.files[0];
        if (arquivo) {
            const formData = new FormData();
            formData.append('file', arquivo, arquivo.name);

            const responseUpload = await fetch(bucket_url, {
                method: 'POST',
                body: formData
            });

            if (!responseUpload.ok) {
                throw new Error(`Erro ao enviar o relatório: ${await responseUpload.text()}`);
            }
        }

        const respostaProcesso = await Auth.fetchWithAuth(`${api_processos_url}/${processoId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(dadosAtualizados)
        });

        if (!respostaProcesso.ok) {
            const erroData = await respostaProcesso.text();
            throw new Error(`Erro ao atualizar os dados do processo: ${erroData}`);
        }

        alert('Processo atualizado com sucesso!');
        window.location.href = 'processo.html';

    } catch (erro) {
        console.error('Ocorreu um erro:', erro);
        alert(erro.message || 'Erro ao salvar alterações. Tente novamente.');
    }
});
