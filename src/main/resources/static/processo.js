document.addEventListener('DOMContentLoaded', function() {

    function atualizarTotalProcessos() {
        fetch('http://localhost:8080/api/processos/total')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao buscar os dados: ' + response.statusText);
                }
                return response.json();
            })
            .then(total => {
                document.getElementById('total_dispensas').textContent = total;
            })
            .catch(error => {
                console.error('Falha ao buscar o total de processos:', error);
                document.getElementById('total_dispensas').textContent = 'X';
            });
    }

    function atualizarTotalProcessosAbertos() {
        fetch('http://localhost:8080/api/processos/total/abertos')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erro ao buscar os dados: ' + response.statusText);
                }
                return response.json();
            })
            .then(totalAbertos => {
                document.getElementById('total_pacientes').textContent = totalAbertos;
            })
            .catch(error => {
                console.error('Falha ao buscar o total de processos abertos:', error);
                document.getElementById('total_pacientes').textContent = 'X';
            });
    }
    atualizarTotalProcessos();
    atualizarTotalProcessosAbertos();
});