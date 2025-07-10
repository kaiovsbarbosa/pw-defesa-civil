const api_url = '';

async function carregarVistorias() {
    try {
        const resposta = await fetch(api_url);
        if (!resposta.ok) throw new Error('Erro ao buscar vistorias');
        const vistorias = await resposta.json();
        renderizarTabela(vistorias);
    } catch (erro) {
        console.error('Erro ao carregar vistorias:', erro);
        alert('Erro ao carregar vistorias. Tente novamente mais tarde.');
    }
}

function renderizarTabela(vistorias) {
    const corpoTabela = document.getElementById('tabela-clientes-corpo');
    corpoTabela.innerHTML = '';

    if (!vistorias || vistorias.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhuma Vistoria encontrada.</td></tr>';
        return;
    }

    vistorias.forEach(vistoria => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${vistoria.status}</td>
            <td>${vistoria.descricao}</td>
            <td>${vistoria.dataInicio}</td>
            <td>${vistoria.dataFim || ''}</td>
            <td class="text-center">
                <button class="btn btn-primary btn-sm" onclick="editarVistoria(${vistoria.id})" title="Editar">
                    <i class="fa fa-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm ms-2" onclick="excluirVistoria(${vistoria.id})" title="Deletar">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        corpoTabela.appendChild(tr);
    });
}


function editarVistoria(id) {
    window.location.href = `editarVistoria.html?id=${id}`;
}


async function excluirVistoria(id) {
    const confirmou = confirm("Tem certeza que deseja excluir esta vistoria?");
    if (!confirmou) return;

    try {
        const resposta = await fetch(`${api_url}/${id}`, {
            method: 'DELETE'
        });
        if (!resposta.ok) throw new Error('Erro ao excluir vistoria');
        alert('Vistoria excluída com sucesso!');
        carregarVistorias();
    } catch (erro) {
        console.error('Erro ao excluir vistoria:', erro);
        alert('Erro ao excluir a vistoria. Tente novamente.');
    }
}

document.addEventListener('DOMContentLoaded', carregarVistorias);
