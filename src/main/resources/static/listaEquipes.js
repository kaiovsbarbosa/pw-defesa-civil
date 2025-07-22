const api_url = 'http://localhost:8080/api/equipes';

async function carregarEquipes() {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    try {
        const resposta = await Auth.fetchWithAuth(api_url);
        if (!resposta.ok) throw new Error('Erro ao buscar equipes');
        const equipes = await resposta.json();
        renderizarTabela(equipes);
    } catch (erro) {
        console.error('Erro ao carregar equipes:', erro);
        alert('Erro ao carregar equipes. Tente novamente mais tarde.');
    }
}

function renderizarTabela(equipes) {
    const corpoTabela = document.getElementById('tabelaEquipes');
    corpoTabela.innerHTML = '';

    if (!equipes || equipes.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="3" class="text-center">Nenhuma equipe encontrada.</td></tr>';
        return;
    }

    equipes.forEach(equipe => {
        const tr = document.createElement('tr');

        const membros = equipe.membros && equipe.membros.length > 0
            ? equipe.membros.map(m => m.nome).join(', ')
            : 'Sem membros';

        tr.innerHTML = `
            <td>${equipe.nomeEquipe}</td>
            <td>${membros}</td>
            <td class="text-center">
                <button class="btn btn-primary btn-sm" onclick="editarEquipe(${equipe.id})" title="Editar">
                    <i class="fa fa-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm ms-2" onclick="excluirEquipe(${equipe.id})" title="Deletar">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;

        corpoTabela.appendChild(tr);
    });
}

function editarEquipe(id) {
    window.location.href = `editarEquipe.html?id=${id}`;
}

async function excluirEquipe(id) {
    const confirmou = confirm("Tem certeza que deseja excluir esta equipe?");
    if (!confirmou) return;

    try {
        const resposta = await Auth.fetchWithAuth(`${api_url}/${id}`, {
            method: 'DELETE'
        });
        if (!resposta.ok) throw new Error('Erro ao excluir equipe');
        alert('Equipe excluída com sucesso!');
        carregarEquipes();
    } catch (erro) {
        console.error('Erro ao excluir equipe:', erro);
        alert('Erro ao excluir equipe. Tente novamente.');
    }
}

document.addEventListener('DOMContentLoaded', carregarEquipes);
