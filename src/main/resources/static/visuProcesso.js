document.addEventListener('DOMContentLoaded', async function () {
    const api_processos_url = 'http://localhost:8081/api/processos';
    const bucket_url = 'https://73247bccb942.ngrok-free.app/view/';

    const selectCriador = document.getElementById('criador');
    const selectEquipe = document.getElementById('equipe');
    const btnEnviar = document.querySelector('button[type="submit"]');

    if (!Auth.getToken()) {
        window.location.href = 'telaLogin.html';
        return;
    }

    if (btnEnviar) {
        btnEnviar.style.display = 'none';
    }

    const urlParams = new URLSearchParams(window.location.search);
    const processoId = urlParams.get('id');

    if (!processoId) {
        alert('ID do processo não fornecido.');
        window.location.href = 'processo.html';
        return;
    }

    try {
        const resposta = await Auth.fetchWithAuth(`${api_processos_url}/${processoId}`);

        if (!resposta.ok) {
            throw new Error('Erro ao carregar os dados do processo. Verifique o ID e tente novamente.');
        }

        const processo = await resposta.json();

        document.getElementById('tipo').value = processo.tipo;
        document.getElementById('status').value = processo.status;
        document.getElementById('localizacao').value = processo.localizacao || '';
        document.getElementById('equipamento').value = processo.equipamento || '';
        document.getElementById('descricao').value = processo.descricao || '';

        if (processo.criadoPorNome) {
            selectCriador.innerHTML = `<option>${processo.criadoPorNome}</option>`;
        } else {
            selectCriador.innerHTML = `<option>Não definido</option>`;
        }

        if (processo.equipeNome) {
            selectEquipe.innerHTML = `<option>${processo.equipeNome}</option>`;
        } else {
            selectEquipe.innerHTML = `<option>Não definida</option>`;
        }

        if (processo.arquivo) {
            const link = `${bucket_url}${encodeURIComponent(processo.arquivo)}`;
            const linkRelatorio = document.getElementById('linkRelatorio');

            try {
                const respostaLink = await fetch(link, {
                    headers: {
                        'ngrok-skip-browser-warning': 'true'
                    }
                });
                if (!respostaLink.ok) throw new Error('Erro ao buscar o link do relatório');

                const { url } = await respostaLink.json();

                if (url && url.startsWith('http')) {
                    linkRelatorio.href = url;
                    linkRelatorio.target = '_blank';
                    linkRelatorio.rel = 'noopener noreferrer';
                    linkRelatorio.classList.remove('disabled');
                    linkRelatorio.innerText = 'Visualizar Relatório';
                } else {
                    throw new Error('URL inválida');
                }
            } catch (e) {
                console.error('Erro ao carregar o link do relatório:', e);
                linkRelatorio.href = '#';
                linkRelatorio.classList.add('disabled');
                linkRelatorio.innerText = 'Relatório indisponível';
            }
        } else {
            const linkRelatorio = document.getElementById('linkRelatorio');
            linkRelatorio.href = '#';
            linkRelatorio.classList.add('disabled');
            linkRelatorio.innerText = 'Relatório não disponível';
        }


        if (processo.data) {
            document.getElementById('data').value = processo.data.split('T')[0];
        }

    } catch (error) {
        console.error('Erro ao buscar processo:', error);
        alert(error.message);
        window.location.href = 'processo.html';
    }
});
