document.addEventListener('DOMContentLoaded', function () {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    const api_url = 'http://localhost:8080/api/processos';
    const usuarios_url = 'http://localhost:8080/api/usuarios';
    const equipes_url = 'http://localhost:8080/api/equipes';
    const bucket_url = 'https://8d6807fabce5.ngrok-free.app/upload';

    const form = document.querySelector('form');
    const selectCriador = document.getElementById('criador');
    const selectEquipe = document.getElementById('equipe');
    const relatorio = document.getElementById('relatorio');

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

        const arquivoNome = relatorio.files[0].name;

        const novoProcesso = {
            tipo: document.getElementById('tipo').value,
            data: formatDateToLocalDateTime(dataInput),
            status: document.getElementById('status').value,
            localizacao: document.getElementById('localizacao').value,
            equipamento: document.getElementById('equipamento').value,
            descricao: document.getElementById('descricao').value,
            criadoPorId: selectCriador.value ? parseInt(selectCriador.value) : null,
            equipeId: selectEquipe.value ? parseInt(selectEquipe.value) : null,
            arquivo: arquivoNome
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

            const responseRelatorio = await fetch(bucket_url, {
                method: 'POST',
                body: (() => {
                    const formData = new FormData();
                    const arquivo = relatorio.files[0];
                    if (!arquivo) {
                        throw new Error('Selecione um arquivo para o relatório.');
                    }
                    formData.append('file', arquivo, arquivo.name);
                    return formData;
                })()
            });

            if (!responseRelatorio.ok) {
                throw new Error(`Erro ao enviar relatório: ${responseRelatorio.statusText}`);
            }

            alert('Processo salvo com sucesso!');
            form.reset();

        } catch (error) {
            console.error('Erro na requisição:', error);
            alert(error.message || 'Erro ao salvar processo');
        }

    });
});
