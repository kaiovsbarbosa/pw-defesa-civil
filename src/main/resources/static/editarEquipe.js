const api_url_equipes = 'http://localhost:8080/api/equipes';
const api_url_usuarios = 'http://localhost:8080/api/usuarios';

document.addEventListener('DOMContentLoaded', async function () {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const equipeId = urlParams.get('id');

    if (!equipeId) {
        alert('ID da equipe não fornecido.');
        window.location.href = 'listaEquipes.html';
        return;
    }

    const nomeInput = document.getElementById('nomeEquipe');
    const membrosSelect = document.getElementById('membros');

    let todosUsuarios = [];

    try {
        const respostaUsuarios = await Auth.fetchWithAuth(api_url_usuarios);
        if (!respostaUsuarios.ok) throw new Error('Erro ao buscar usuários');
        todosUsuarios = await respostaUsuarios.json();

        todosUsuarios.forEach(usuario => {
            const option = document.createElement('option');
            option.value = usuario.id;
            option.textContent = usuario.nome;
            membrosSelect.appendChild(option);
        });

        const respostaEquipe = await Auth.fetchWithAuth(`${api_url_equipes}/${equipeId}`);
        if (!respostaEquipe.ok) throw new Error('Erro ao buscar equipe');
        const equipe = await respostaEquipe.json();

        nomeInput.value = equipe.nomeEquipe;

        if (equipe.membrosIds) {
            equipe.membrosIds.forEach(id => {
                const option = [...membrosSelect.options].find(opt => Number(opt.value) === id);
                if (option) option.selected = true;
            });
        }

    } catch (erro) {
        console.error('Erro ao carregar dados:', erro);
        alert('Erro ao carregar dados da equipe.');
        window.location.href = 'listaEquipes.html';
    }

    const form = document.querySelector('form');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const nomeEquipe = nomeInput.value.trim();
        if (!nomeEquipe) {
            alert('Por favor, informe o nome da equipe.');
            return;
        }

        const membrosSelecionados = [...membrosSelect.selectedOptions].map(opt => Number(opt.value));

        const dadosAtualizados = {
            nomeEquipe,
            membrosIds: membrosSelecionados
        };

        try {
            const resposta = await Auth.fetchWithAuth(`${api_url_equipes}/${equipeId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!resposta.ok) throw new Error('Erro ao atualizar equipe');
            alert('Equipe atualizada com sucesso!');
            window.location.href = 'listaEquipes.html';
        } catch (erro) {
            console.error('Erro ao atualizar a equipe:', erro);
            alert('Erro ao salvar alterações. Tente novamente.');
        }
    });
});
