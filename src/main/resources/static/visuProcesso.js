document.addEventListener('DOMContentLoaded', async function () {
    const api_processos_url = 'http://localhost:8080/api/processos';

    const selectCriador = document.getElementById('criador');
    const selectEquipe = document.getElementById('equipe');
    const btnEnviar = document.querySelector('button[type="submit"]');

    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    if (btnEnviar) {
        btnEnviar.style.display = 'none';
    }

    const urlParams = new URLSearchParams(window.location.search);
    const processoId = urlParams.get('id');

    if (!processoId) {
        alert('ID do processo não fornecido.');
        window.location.href = 'processo.html';
        return;
    }

    try {
        const resposta = await Auth.fetchWithAuth(`${api_processos_url}/${processoId}`);

        if (!resposta.ok) {
            throw new Error('Erro ao carregar os dados do processo. Verifique o ID e tente novamente.');
        }

        const processo = await resposta.json();

        document.getElementById('tipo').value = processo.tipo;
        document.getElementById('status').value = processo.status;
        document.getElementById('localizacao').value = processo.localizacao || '';
        document.getElementById('equipamento').value = processo.equipamento || '';
        document.getElementById('descricao').value = processo.descricao || '';

        if (processo.data) {
            document.getElementById('data').value = processo.data.split('T')[0];
        }

        if (processo.criadoPor) {
            selectCriador.innerHTML = `<option>${processo.criadoPor.nome}</option>`;
        } else {
            selectCriador.innerHTML = `<option>Não definido</option>`;
        }

        if (processo.equipe) {
            selectEquipe.innerHTML = `<option>${processo.equipe.nomeEquipe}</option>`;
        } else {
            selectEquipe.innerHTML = `<option>Não definida</option>`;
        }

    } catch (error) {
        console.error('Erro ao buscar processo:', error);
        alert(error.message);
        window.location.href = 'processo.html';
    }
});
