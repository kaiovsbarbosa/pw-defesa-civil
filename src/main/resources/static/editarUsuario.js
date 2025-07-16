const api_url = 'http://localhost:8080/api/usuarios';

document.addEventListener('DOMContentLoaded', async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const usuarioId = urlParams.get('id');

    if (!usuarioId) {
        alert('ID do usuário não fornecido.');
        window.location.href = 'listaUsuarios.html';
        return;
    }

    try {
        const resposta = await fetch(`${api_url}/${usuarioId}`);
        if (!resposta.ok) throw new Error('Erro ao carregar usuário');
        const usuario = await resposta.json();

        document.getElementById('nome').value = usuario.nome;
        document.getElementById('email').value = usuario.email;
        document.getElementById('perfil').value = usuario.perfil?.tipo;
        

        console.log('Usuário retornado:', usuario);
    } catch (erro) {
        console.error('Erro ao carregar usuário:', erro);
        alert('Erro ao carregar dados do usuário.');
        window.location.href = 'listaUsuario.html';
    }

    const form = document.querySelector('form');
    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const dadosAtualizados = {
            nome: document.getElementById('nome').value,
            email: document.getElementById('email').value,
            perfil: {
                tipo: document.getElementById('perfil').value
            }
        };

        try {
            const resposta = await fetch(`${api_url}/${usuarioId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosAtualizados)
            });

            if (!resposta.ok) throw new Error('Erro ao atualizar usuário');
            alert('Usuário atualizado com sucesso!');
            window.location.href = 'listaUsuarios.html';
        } catch (erro) {
            console.error('Erro ao atualizar o usuário:', erro);
            alert('Erro ao salvar alterações. Tente novamente.');
        }
    });
});


