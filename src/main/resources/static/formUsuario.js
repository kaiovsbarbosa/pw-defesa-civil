document.addEventListener('DOMContentLoaded', function() {
    const api_url = 'http://localhost:8080/api/usuarios';
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const email = document.getElementById('email').value;
        const senha = document.getElementById('senha').value;
        const perfilId = document.getElementById('perfil').value;

        const novoUsuario = {
            nome,
            email,
            senha,
            perfilId: parseInt(perfilId)
        };

        async function salvarUsuario() {
            try {
                const response = await fetch(api_url, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(novoUsuario)
                });

                if (!response.ok) {
                    const errorData = await response.json();
                    const errorMessage = errorData.message || `Erro ao salvar Usuário: ${response.statusText}`;
                    throw new Error(errorMessage);
                }

                alert('Usuário salvo com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
                alert(`Erro ao salvar Usuário: ${error.message}`);
            }
        }

        salvarUsuario();

        console.log("Enviando para API:", novoUsuario);
    });
});