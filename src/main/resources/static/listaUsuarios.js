const api_url = 'http://localhost:8080/api/usuarios';

async function carregarUsuarios() {
    try {
        const resposta = await fetch(api_url);
        if (!resposta.ok) throw new Error('Erro ao buscar usuários');
        const usuarios = await resposta.json();
        renderizarTabela(usuarios);
    } catch (erro) {
        console.error('Erro ao carregar usuários:', erro);
        alert('Erro ao carregar usuários. Tente novamente mais tarde.');
    }
}

function renderizarTabela(usuarios) {
    const corpoTabela = document.getElementById('tabelaUsuarios');
    corpoTabela.innerHTML = '';

    if (!usuarios || usuarios.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum usuário encontrado.</td></tr>';
        return;
    }

    usuarios.forEach(usuario => {
        const tr = document.createElement('tr');

        tr.innerHTML = `
            <td>${usuario.nome}</td>
            <td>${usuario.email}</td>
            <td>${usuario.perfilTipo || 'Sem perfil'}</td>
            <td class="text-center">
                <button class="btn btn-primary btn-sm" onclick="editarUsuario(${usuario.id})" title="Editar">
                    <i class="fa fa-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm ms-2" onclick="excluirUsuario(${usuario.id})" title="Deletar">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        corpoTabela.appendChild(tr);
    });
}


function editarUsuario(id) {
    window.location.href = `editarUsuario.html?id=${id}`;
}


async function excluirUsuario(id) {
    const confirmou = confirm("Tem certeza que deseja excluir este usuário?");
    if (!confirmou) return;

    try {
        const resposta = await fetch(`${api_url}/${id}`, {
            method: 'DELETE'
        });
        if (!resposta.ok) throw new Error('Erro ao excluir usuário');
        alert('Usuário excluído com sucesso!');
        carregarUsuarios();
    } catch (erro) {
        console.error('Erro ao excluir usuário:', erro);
        alert('Erro ao excluir o . Tente novamente.');
    }
}

document.addEventListener('DOMContentLoaded', carregarUsuarios);