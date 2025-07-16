document.addEventListener('DOMContentLoaded', function () {
    const api_url = 'http://localhost:8080/api/processos';
    const usuarios_url = 'http://localhost:8080/api/usuarios';
    const equipes_url = 'http://localhost:8080/api/equipes';

    const form = document.querySelector('form');
    const selectCriador = document.querySelector('select[aria-label="select creator"]');
    const selectEquipe = document.querySelector('select[aria-label="select team"]');


    const formatDateToLocalDateTime = (dateStr) => {
        return dateStr ? `${dateStr}T00:00:00` : null;
    };

    async function carregarUsuarios() {
        try {
            const resposta = await fetch(usuarios_url);
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
            const resposta = await fetch(equipes_url);
            if (!resposta.ok) throw new Error('Erro ao buscar equipes');
            const equipes = await resposta.json();

            equipes.forEach(equipe => {
                const option = document.createElement('option');
                option.value = equipe.id;
                option.textContent = equipe.nome;
                selectEquipe.appendChild(option);
            });
        } catch (erro) {
            console.error('Erro ao carregar equipes:', erro);
        }
    }

    carregarUsuarios();
    carregarEquipes();

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const tipo = document.getElementById('tipo').value;
        const dataInicio = formatDateToLocalDateTime(document.getElementById('dataInicio').value);
        const dataFim = formatDateToLocalDateTime(document.getElementById('dataFim').value);
        const status = document.getElementById('status').value;
        const localizacaoDescricao = document.getElementById('localizacaoDescricao').value;
        const descricao = document.getElementById('descricao').value;

        const criadoPorId = selectCriador.value;
        const equipeId = selectEquipe.value;

        const novoProcesso = {
            tipo,
            dataInicio,
            dataFim,
            status,
            localizacaoDescricao,
            descricao,
            criadoPor: criadoPorId ? { id: parseInt(criadoPorId) } : null,
            equipe: equipeId ? { id: parseInt(equipeId) } : null
        };

        async function salvarProcesso() {
            try {
                const response = await fetch(api_url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(novoProcesso)
                });

                if (!response.ok) {
                    throw new Error(`Erro ao salvar Processo: ${response.statusText}`);
                }

                await response.json();
                alert('Processo salvo com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
                alert('Erro ao salvar processo');
            }
        }

        salvarProcesso();
    });
});
