document.addEventListener('DOMContentLoaded', function() {
    const api_url = 'http://localhost:8080/api/processos';
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const tipo = document.getElementById('tipo').value;
        const dataInicio = document.getElementById('dataInicio').value;
        const dataFim = document.getElementById('dataFim').value;
        const status = document.getElementById('status').value;
        const localizacaoDescricao = document.getElementById('descricaoLocalizacao').value;
        const descricao = document.getElementById('descricao').value;

        const novoProcesso = {
            tipo,
            dataInicio,
            dataFim,
            status,
            localizacaoDescricao,
            descricao
        };

         async function salvarProcesso() {
            try {
                const response = await fetch(api_url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(novoProcesso)
                });

                if (!response.ok) {
                    throw new Error(`Erro ao salvar Processo: ${response.statusText}`);
                }

                const resultado = await response.json();
                alert('Processo salvo com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
                alert('Erro ao salvar processo')
            }
        }

        salvarProcesso();
    });
});