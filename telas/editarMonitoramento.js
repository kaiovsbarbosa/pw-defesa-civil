document.addEventListener('DOMContentLoaded', function() {
    const urlParams = new URLSearchParams(window.location.search);
    const monitoramentoId = urlParams.get('id');

    // Dados mocados para exemplo
    const monitoramentoExistente = {
        id: monitoramentoId,
        dataInicio: '2023-11-20',
        dataFim: '',
        status: 'em-andamento',
        descricao: 'Descrição do monitoramento para edição'
    };

    if (monitoramentoId) {
        document.getElementById('dataInicio').value = monitoramentoExistente.dataInicio;
        document.getElementById('dataFim').value = monitoramentoExistente.dataFim;
        document.getElementById('status').value = monitoramentoExistente.status;
        document.getElementById('descricaoLocalizacao').value = monitoramentoExistente.descricao;
    }

    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const dadosAtualizados = {
            id: monitoramentoId,
            dataInicio: document.getElementById('dataInicio').value,
            dataFim: document.getElementById('dataFim').value,
            status: document.getElementById('status').value,
            descricao: document.getElementById('descricaoLocalizacao').value
        };

        console.log('Monitoramento Atualizado:', dadosAtualizados);
        alert('Monitoramento atualizado com sucesso!');
    });
});