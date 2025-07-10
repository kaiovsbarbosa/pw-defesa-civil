const api_url = '';

async function carregarMonitoramentos() {
    try {
        const resposta = await fetch(api_url);
        if (!resposta.ok) throw new Error('Erro ao buscar monitoramento');
        const monitoramentos = await resposta.json();
        renderizarTabela(monitoramentos);
    } catch (erro) {
        console.error('Erro ao carregar monitoramentos:', erro);
        alert('Erro ao carregar monitoramentos. Tente novamente mais tarde.');
    }
}

function renderizarTabela(monitoramentos) {
    const corpoTabela = document.getElementById('tabela-clientes-corpo');
    corpoTabela.innerHTML = '';

    if (!monitoramentos || monitoramentos.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum Monitoramento encontrado.</td></tr>';
        return;
    }

    vistorias.forEach(monitoramento => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${monitoramento.status}</td>
            <td>${monitoramento.descricao}</td>
            <td>${monitoramento.dataInicio}</td>
            <td>${monitoramento.dataFim || ''}</td>
            <td class="text-center">
                <button class="btn btn-primary btn-sm" onclick="editarVistoria(${monitoramento.id})" title="Editar">
                    <i class="fa fa-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm ms-2" onclick="excluirVistoria(${monitoramento.id})" title="Deletar">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        corpoTabela.appendChild(tr);
    });
}


function editarMonitoramento(id) {
    window.location.href = `editarMonitoramento.html?id=${id}`;
}


async function excluirMonitoramento(id) {
    const confirmou = confirm("Tem certeza que deseja excluir este monitoramento?");
    if (!confirmou) return;

    try {
        const resposta = await fetch(`${api_url}/${id}`, {
            method: 'DELETE'
        });
        if (!resposta.ok) throw new Error('Erro ao excluir monitoramento');
        alert('Monitoramento excluído com sucesso!');
        carregarMonitoramentos();
    } catch (erro) {
        console.error('Erro ao excluir monitoramento:', erro);
        alert('Erro ao excluir o monitoramento. Tente novamente.');
    }
}

document.addEventListener('DOMContentLoaded', carregarMonitoramentos);
