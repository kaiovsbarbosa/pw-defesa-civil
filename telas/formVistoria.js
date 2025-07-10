document.addEventListener('DOMContentLoaded', function() {
    const api_url = '';
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

         async function salvarVistoria() {
            try {
                const response = await fetch(api_url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(novaVistoria)
                });

                if (!response.ok) {
                    throw new Error(`Erro ao salvar vistoria: ${response.statusText}`);
                }

                const resultado = await response.json();
                alert('Vistoria salva com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
            }
        }

        salvarVistoria();
    });
});