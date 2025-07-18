const api_processos_url = 'http://localhost:8080/api/processos';
const api_usuarios_url = 'http://localhost:8080/api/usuarios';
const api_equipes_url = 'http://localhost:8080/api/equipes';

const form = document.querySelector('form');
const selectCriador = document.querySelector('select[aria-label="select creator"]');
const selectEquipe = document.querySelector('select[aria-label="select team"]');


async function carregarUsuarios() {
    try {
        const resposta = await fetch(api_usuarios_url);
        if (!resposta.ok) throw new Error('Erro ao buscar usuários');
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
        const resposta = await fetch(api_equipes_url);
        if (!resposta.ok) throw new Error('Erro ao buscar equipes');
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

document.addEventListener('DOMContentLoaded', async function () {
    await carregarUsuarios();
    await carregarEquipes();

    const urlParams = new URLSearchParams(window.location.search);
    const processoId = urlParams.get('id');

    if (!processoId) {
        alert('ID do processo não fornecido.');
        window.location.href = 'listaProcessos.html';
        return;
    }

    try {
        const resposta = await fetch(`${api_processos_url}/${processoId}`);
        if (!resposta.ok) throw new Error('Erro ao carregar o processo');
        const processo = await resposta.json();

        document.getElementById('tipo').value = processo.tipo;
        document.getElementById('dataInicio').value = processo.dataInicio?.split('T')[0];
        document.getElementById('dataFim').value = processo.dataFim?.split('T')[0];
        document.getElementById('status').value = processo.status;
        document.getElementById('localizacaoDescricao').value = processo.localizacaoDescricao;
        document.getElementById('descricao').value = processo.descricao ?? '';
        
        if (processo.criadoPor) {
            selectCriador.value = processo.criadoPor.id;
        }
        if (processo.equipe) {
            selectEquipe.value = processo.equipe.id;
        }

        console.log(processo)

    } catch (erro) {
        console.error('Erro ao carregar o processo:', erro);
        alert('Erro ao carregar dados do processo.');
        window.location.href = 'listaProcessos.html';
    }
});



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
        dataInicio: formatDateToLocalDateTime(document.getElementById('dataInicio').value),
        dataFim: formatDateToLocalDateTime(document.getElementById('dataFim').value),
        status: document.getElementById('status').value,
        localizacaoDescricao: document.getElementById('localizacaoDescricao').value,
        descricao: document.getElementById('descricao').value,
        criadoPor: criadoPorId ? { id: parseInt(criadoPorId) } : null,
        equipe: equipeId ? { id: parseInt(equipeId) } : null
    };

    try {
        const resposta = await fetch(`${api_processos_url}/${processoId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
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

