document.addEventListener('DOMContentLoaded', function() {
    const api_url = 'http://localhost:8080/api/usuarios';
    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        event.preventDefault();

        const nome = document.getElementById('nome').value;
        const email = document.getElementById('email').value;
        const senha = document.getElementById('senha').value;
        const perfil = document.getElementById('perfil').value;

        const novoUsuario = {
            nome,
            email,
            senhaHash: senha,
            perfil: {
                id: parseInt(perfil)
            }
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
                    throw new Error(`Erro ao salvar Usuário: ${response.statusText}`);
                }

                alert('Usuário salvo com sucesso!');
                form.reset();
            } catch (error) {
                console.error('Erro na requisição:', error);
                alert('Erro ao salvar Usuário')
            }
        }

        salvarUsuario();

        console.log("Enviando para API:", novoUsuario);
    });
});

