document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    loginForm.addEventListener('submit', function (event) {
        event.preventDefault();

        loginError.classList.add('d-none');

        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const formData = new URLSearchParams();
        formData.append('username', email);
        formData.append('password', password);

        fetch('/login', {
            method: 'POST',
            body: formData,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => {
                if (response.ok && response.redirected) {
                    window.location.href = 'processo.html';
                } else {
                    loginError.classList.remove('d-none');
                }
            })
            .catch(error => {
                console.error('Erro ao tentar fazer login:', error);
                loginError.textContent = 'Ocorreu um erro inesperado. Tente novamente.';
                loginError.classList.remove('d-none');
            });
    });
});