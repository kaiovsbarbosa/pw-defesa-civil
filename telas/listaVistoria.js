let vistorias = [
    { id: 1, status: 'Em Andamento', descricao: 'Descrição relacionada a vistoria', dataInicio: '20/11/2023', dataFim: '' }];

let vistoriaSelecionado = null;


function renderizarTabela() {
    const corpoTabela = document.getElementById('tabela-clientes-corpo');
    corpoTabela.innerHTML = '';

    if (vistorias.length === 0) {
        corpoTabela.innerHTML = '<tr><td colspan="5" class="text-center">Nenhuma Vistoria encontrada.</td></tr>';
        return;
    }

    vistorias.forEach(vistoria => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
                    <td>${vistoria.status}</td>
                    <td>${vistoria.descricao}</td>
                    <td>${vistoria.dataInicio}</td>
                    <td>${vistoria.dataFim}</td>
                    <td class="text-center">
                        <a href="editarVistoria.html?id=${vistoria.id}" class="btn btn-primary btn-sm" title="Editar">
                            <i class="fa fa-pencil"></i>
                        </a>
                        <button class="btn btn-danger btn-sm ms-2" title="Deletar" onclick="preparaDelecao(${vistoria.id})">
                            <i class="fa fa-trash"></i>
                        </button>
                    </td>
                `;
        corpoTabela.appendChild(tr);
    });
}


function preparaDelecao(vistoriaId) {
    const confirmou = window.confirm("Tem certeza que deseja excluir esta vistoria?");
    if (confirmou) {
        const indexParaDeletar = vistorias.findIndex(v => v.id === vistoriaId);
        if (indexParaDeletar > -1) {
            vistorias.splice(indexParaDeletar, 1);
        }
        renderizarTabela();
        alert("Vistoria excluída com sucesso!");
    }
}


document.addEventListener('DOMContentLoaded', () => {
    renderizarTabela();
});