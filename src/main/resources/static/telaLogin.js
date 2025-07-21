document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    loginForm.addEventListener('submit', async function (event) {
        event.preventDefault();
        loginError.classList.add('d-none');

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const formData = new URLSearchParams();
        formData.append('username', email);
        formData.append('password', password);

        try {
            const response = await fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: formData.toString()
            });

            if (response.ok) {
                const data = await response.json();

                if (data.token) {
                    localStorage.setItem('token', data.token);
                    window.location.href = 'processo.html';
                } else {
                    throw new Error("Token não retornado");
                }
            } else {
                loginError.textContent = 'Credenciais inválidas.';
                loginError.classList.remove('d-none');
            }
        } catch (error) {
            console.error('Erro ao tentar fazer login:', error);
            loginError.textContent = 'Erro inesperado. Tente novamente.';
            loginError.classList.remove('d-none');
        }
    });
});
