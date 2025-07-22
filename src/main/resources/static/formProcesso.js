document.addEventListener('DOMContentLoaded', function () {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    const api_url = 'http://localhost:8080/api/processos';
    const usuarios_url = 'http://localhost:8080/api/usuarios';
    const equipes_url = 'http://localhost:8080/api/equipes';
    const relatorios_url = 'http://localhost:8080/relatorios';

    const form = document.querySelector('form');
    const selectCriador = document.getElementById('criador');
    const selectEquipe = document.getElementById('equipe');
    const relatorioInput = document.getElementById('relatorio');

    const formatDateToLocalDateTime = (dateStr) => {
        return dateStr ? `${dateStr}T00:00:00` : null;
    };

    async function carregarUsuarios() {
        try {
            const resposta = await Auth.fetchWithAuth(usuarios_url);
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
            const resposta = await Auth.fetchWithAuth(equipes_url);
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

    carregarUsuarios();
    carregarEquipes();

    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const dataInput = document.getElementById('data').value;
        if (!dataInput) {
            alert('Por favor, selecione uma data.');
            return;
        }

        const novoProcesso = {
            tipo: document.getElementById('tipo').value,
            data: formatDateToLocalDateTime(dataInput),
            status: document.getElementById('status').value,
            localizacao: document.getElementById('localizacao').value,
            equipamento: document.getElementById('equipamento').value,
            descricao: document.getElementById('descricao').value,
            criadoPor: selectCriador.value ? { id: parseInt(selectCriador.value) } : null,
            equipe: selectEquipe.value ? { id: parseInt(selectEquipe.value) } : null
        };

        try {
            const responseProcesso = await Auth.fetchWithAuth(api_url, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(novoProcesso)
            });

            if (!responseProcesso.ok) {
                throw new Error(`Erro ao salvar Processo: ${responseProcesso.statusText}`);
            }

            const processoSalvo = await responseProcesso.json();
            const novoProcessoId = processoSalvo.id;
            const relatorioFile = relatorioInput.files[0];

            if (relatorioFile) {
                const formDataArquivo = new FormData();
                formDataArquivo.append('arquivo', relatorioFile);
                formDataArquivo.append('proprietarioId', novoProcessoId);
                formDataArquivo.append('tipo', 'PROCESSO');

                const responseRelatorio = await Auth.fetchWithAuth(relatorios_url, {
                    method: 'POST',
                    body: formDataArquivo
                });

                if (!responseRelatorio.ok) {
                    throw new Error('Processo salvo, mas falha ao enviar o relatório.');
                }
            }

            alert('Processo salvo com sucesso!');
            form.reset();

        } catch (error) {
            console.error('Erro na requisição:', error);
            alert(error.message || 'Erro ao salvar processo');
        }
    });
});
