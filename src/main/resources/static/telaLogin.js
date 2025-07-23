document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    loginForm.addEventListener('submit', async function (event) {
        event.preventDefault();
        loginError.classList.add('d-none');

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        try {
            const success = await Auth.login(email, password);
            if (success) {
                window.location.href = 'processo.html';
            }
        } catch (error) {
            loginError.textContent = error.message || 'Erro inesperado. Tente novamente.';
            loginError.classList.remove('d-none');
        }
    });
});