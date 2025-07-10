document.addEventListener('DOMContentLoaded', function () {
    const api_url = '';
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        event.preventDefault();

        const dataInicio = document.getElementById('dataInicio').value;
        const dataFim = document.getElementById('dataFim').value;
        const status = document.getElementById('status').value;
        const descricao = document.getElementById('descricaoLocalizacao').value;

        const novoMonitoramento = {
            dataInicio,
            dataFim,
            status,
            descricao,
            tipo: 'Monitoramento'
        };

        async function salvarMonitoramento() {
            try {
                const response = await fetch(api_url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(novoMonitoramento)
                });

                if (!response.ok) {
                    throw new Error(`Erro ao salvar monitoramento: ${response.statusText}`);
                }

                const resultado = await response.json();
                alert('Monitoramento salvo com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
            }
        }

        salvarMonitoramento();
    });
});