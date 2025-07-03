document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const dataInicio = document.getElementById('dataInicio').value;
        const dataFim = document.getElementById('dataFim').value;
        const status = document.getElementById('status').value;
        const descricao = document.getElementById('descricaoLocalizacao').value;

        const novaVistoria = {
            dataInicio,
            dataFim,
            status,
            descricao,
            tipo: 'Vistoria'
        };

        console.log('Nova Vistoria:', novaVistoria);
        // adicionar lógica para salvar a vistoria
        
        alert('Vistoria salva com sucesso!');
        form.reset(); 
    });
});