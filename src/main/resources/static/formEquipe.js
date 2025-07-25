document.addEventListener('DOMContentLoaded', function () {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    const apiEquipes = 'http://localhost:8081/api/equipes';
    const apiUsuarios = 'http://localhost:8081/api/usuarios';

    const form = document.querySelector('form');
    const selectMembros = document.getElementById('membros');
    const inputNomeEquipe = document.getElementById('nomeEquipe');

    async function carregarMembros() {
        try {
            const res = await Auth.fetchWithAuth(apiUsuarios);
            if (!res.ok) throw new Error('Erro ao buscar usuários');
            const usuarios = await res.json();

            selectMembros.innerHTML = '';

            usuarios.forEach(usuario => {
                const option = document.createElement('option');
                option.value = usuario.id;
                option.textContent = `${usuario.nome} (${usuario.email})`;
                selectMembros.appendChild(option);
            });
        } catch (error) {
            console.error('Erro ao carregar membros:', error);
            alert('Erro ao carregar membros da equipe.');
        }
    }

    carregarMembros();

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const nomeEquipe = inputNomeEquipe.value.trim();

        if (!nomeEquipe) {
            alert('Por favor, insira o nome da equipe.');
            inputNomeEquipe.focus();
            return;
        }

        const membrosSelecionados = Array.from(selectMembros.selectedOptions)
            .map(option => ({ id: Number(option.value) }));

        if (membrosSelecionados.length === 0) {
            alert('Por favor, selecione pelo menos um membro para a equipe.');
            selectMembros.focus();
            return;
        }

        const novaEquipe = {
            nomeEquipe,
            membrosIds: Array.from(selectMembros.selectedOptions).map(option => Number(option.value))
        };

        async function salvarEquipe() {
            try {
                const response = await Auth.fetchWithAuth(apiEquipes, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(novaEquipe)
                });

                if (!response.ok) {
                    throw new Error(`Erro ao salvar Equipe: ${response.statusText}`);
                }

                alert('Equipe salva com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
                alert('Erro ao salvar Equipe. Tente novamente.');
            }
        }

        salvarEquipe();
    });
});
