document.addEventListener('DOMContentLoaded', function () {
    const token = Auth.getToken();
    if (!token) {
        window.location.href = 'telaLogin.html';
        return;
    }

    async function atualizarTotalProcessos() {
        try {
            const response = await Auth.fetchWithAuth('http://localhost:8080/api/processos/total', {
                method: 'GET'
            });

            if (!response.ok) {
                throw new Error('Erro ao buscar os dados: ' + response.statusText);
            }

            const total = await response.json();
            document.getElementById('total_dispensas').textContent = total;
        } catch (error) {
            console.error('Falha ao buscar o total de processos:', error);
            document.getElementById('total_dispensas').textContent = 'X';
        }
    }

    async function atualizarTotalProcessosAbertos() {
        try {
            const response = await Auth.fetchWithAuth('http://localhost:8080/api/processos/total/abertos', {
                method: 'GET'
            });

            if (!response.ok) {
                throw new Error('Erro ao buscar os dados: ' + response.statusText);
            }

            const totalAbertos = await response.json();
            document.getElementById('total_pacientes').textContent = totalAbertos;
        } catch (error) {
            console.error('Falha ao buscar o total de processos abertos:', error);
            document.getElementById('total_pacientes').textContent = 'X';
        }
    }
    atualizarTotalProcessos();
    atualizarTotalProcessosAbertos();
});