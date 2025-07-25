document.addEventListener('DOMContentLoaded', async function () {
    const api_url = 'http://localhost:8081/api/usuarios';
    const urlParams = new URLSearchParams(window.location.search);
    const usuarioId = urlParams.get('id');

    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    if (!usuarioId) {
        alert('ID do usuário não fornecido.');
        window.location.href = 'listaUsuarios.html';
        return;
    }

    try {
        const resposta = await Auth.fetchWithAuth(`${api_url}/${usuarioId}`);
        if (!resposta.ok) throw new Error('Erro ao carregar usuário');
        const usuario = await resposta.json();

        document.getElementById('nome').value = usuario.nome;
        document.getElementById('email').value = usuario.email;
        if (usuario.perfilId) {
            document.getElementById('perfil').value = usuario.perfilId;
        }
    } catch (erro) {
        console.error('Erro ao carregar usuário:', erro);
        alert('Erro ao carregar dados do usuário.');
        window.location.href = 'listaUsuarios.html';
    }

    const form = document.querySelector('form');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const dadosAtualizados = {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            senha: document.getElementById('senha').value,
            perfilId: parseInt(document.getElementById('perfil').value)
        };

        if (!dadosAtualizados.senha) {
            delete dadosAtualizados.senha;
        }

        try {
            const resposta = await Auth.fetchWithAuth(`${api_url}/${usuarioId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!resposta.ok) {
                const errorData = await resposta.json();
                const errorMessage = errorData.message || `Erro ao atualizar Usuário: ${resposta.statusText}`;
                throw new Error(errorMessage);
            }

            alert('Usuário atualizado com sucesso!');
            window.location.href = 'listaUsuarios.html';
        } catch (erro) {
            console.error('Erro ao atualizar o usuário:', erro);
            alert(`Erro ao salvar alterações: ${erro.message}`);
        }
    });
});
