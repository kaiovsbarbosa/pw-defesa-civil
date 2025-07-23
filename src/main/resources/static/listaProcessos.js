const api_url = 'http://localhost:8080/api/processos';

document.addEventListener('DOMContentLoaded', () => {
    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    carregarProcessos();
});

async function carregarProcessos() {
    try {
        const resposta = await Auth.fetchWithAuth(api_url);
        if (!resposta.ok) throw new Error('Erro ao buscar processos');
        const processos = await resposta.json();
        renderizarTabela(processos);
    } catch (erro) {
        console.error('Erro ao carregar processos:', erro);
        alert('Erro ao carregar processos. Tente novamente mais tarde.');
    }
}

function renderizarTabela(processos) {
    const corpoTabela = document.getElementById('tabelaProcessos');
    corpoTabela.innerHTML = '';

    if (!processos || processos.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhum processo encontrado.</td></tr>';
        return;
    }

    processos.forEach(processo => {
        const tr = document.createElement('tr');

        const data = processo.data
            ? new Date(processo.data).toLocaleDateString('pt-BR') 
            : '';

        const responsavel = processo.criadoPorNome || 'Não atribuído';
        const tipo = capitalizarTexto(processo.tipo);

        tr.innerHTML = `
            <td>${responsavel}</td>
            <td>${tipo}</td>
            <td>${data}</td>
            <td class="text-center">
                <button class="btn btn-primary btn-sm" onclick="visuProcesso(${processo.id})" title="Visualizar">
                    <i class="fa fa-eye"></i>
                </button>
                <button class="btn btn-warning btn-sm ms-2" onclick="editarProcesso(${processo.id})" title="Editar">
                    <i class="fa fa-pencil"></i>
                </button>
                <button class="btn btn-danger btn-sm ms-2" onclick="excluirProcesso(${processo.id})" title="Deletar">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        corpoTabela.appendChild(tr);
    });
}

function visuProcesso(id) {
    window.location.href = `visuProcesso.html?id=${id}`;
}

function editarProcesso(id) {
    window.location.href = `editarProcesso.html?id=${id}`;
}

async function excluirProcesso(id) {
    const confirmou = confirm("Tem certeza que deseja excluir este processo?");
    if (!confirmou) return;

    try {
        const resposta = await Auth.fetchWithAuth(`${api_url}/${id}`, {
            method: 'DELETE'
        });
        if (!resposta.ok) throw new Error('Erro ao excluir processo');
        alert('Processo excluído com sucesso!');
        carregarProcessos();
    } catch (erro) {
        console.error('Erro ao excluir processo:', erro);
        alert('Erro ao excluir o processo. Tente novamente.');
    }
}

function capitalizarTexto(texto) {
    return texto
        .toLowerCase()
        .split(' ')
        .map(palavra => palavra.charAt(0).toUpperCase() + palavra.slice(1))
        .join(' ');
}
