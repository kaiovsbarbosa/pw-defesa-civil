
const Auth = {
    login: async (email, password) => {
        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ usuario: email, senha: password })
            });

            if (!response.ok) {
                throw new Error('Credenciais inválidas');
            }

            const data = await response.json();
            if (data.tokenAcesso && data.refreshToken) {
                localStorage.setItem('token', data.tokenAcesso);
                localStorage.setItem('refreshToken', data.refreshToken);
                return true;
            } else {
                throw new Error('Tokens não retornados');
            }
        } catch (error) {
            console.error('Erro no login:', error);
            throw error;
        }
    },

    logout: async () => {
        const token = localStorage.getItem('token');
        if (token) {
            await fetch('/logout', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
        }
        localStorage.clear();
        window.location.href = 'login.html';
    },

    getToken: () => {
        return localStorage.getItem('token');
    },

    refreshToken: async () => {
        const refreshToken = localStorage.getItem('refreshToken');
        try {
            const response = await fetch('/refresh-token', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ refreshToken })
            });

            if (!response.ok) {
                throw new Error('Erro ao atualizar token');
            }

            const data = await response.json();
            localStorage.setItem('token', data.tokenAcesso);
            localStorage.setItem('refreshToken', data.refreshToken);
        } catch (error) {
            console.error('Erro no refresh token:', error);
            await Auth.logout();
        }
    },

    fetchWithAuth: async (url, options = {}) => {
        const token = Auth.getToken();
        const headers = options.headers || {};
        headers['Authorization'] = `Bearer ${token}`;
        options.headers = headers;

        let response = await fetch(url, options);
        if (response.status === 401) {
            await Auth.refreshToken();
            const newToken = Auth.getToken();
            options.headers['Authorization'] = `Bearer ${newToken}`;
            response = await fetch(url, options);
        }

        return response;
    }
};
