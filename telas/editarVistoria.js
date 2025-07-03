document.addEventListener('DOMContentLoaded', function() {
    
    const urlParams = new URLSearchParams(window.location.search);
    const vistoriaId = urlParams.get('id');

    // dados mocados:
    const vistoriaExistente = {
        id: vistoriaId,
        dataInicio: '2023-11-20',
        dataFim: '',
        status: 'em-andamento',
        descricao: 'Descrição da vistoria para edição'
    };

    if (vistoriaId) {
        document.getElementById('dataInicio').value = vistoriaExistente.dataInicio;
        document.getElementById('dataFim').value = vistoriaExistente.dataFim;
        document.getElementById('status').value = vistoriaExistente.status;
        document.getElementById('descricaoLocalizacao').value = vistoriaExistente.descricao;
    }

    // Lógica para salvar os dados editados
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const dadosAtualizados = {
            id: vistoriaId,
            dataInicio: document.getElementById('dataInicio').value,
            dataFim: document.getElementById('dataFim').value,
            status: document.getElementById('status').value,
            descricao: document.getElementById('descricaoLocalizacao').value
        };

        console.log('Vistoria Atualizada:', dadosAtualizados);
        // Aqui você adicionaria a lógica para salvar as alterações

        alert('Vistoria atualizada com sucesso!');
    });
});