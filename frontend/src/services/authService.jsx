import axios from 'axios';

const API_URL = 'http://localhost:8080/auth';

// Axios instance oluştur
const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Login fonksiyonu
export const login = async (username, password) => {
    try {
        const response = await api.post('/login', { username, password });

        // Token'ı localStorage'a kaydet
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);
            // Backend'den gelen tüm veriyi kaydet (username, role vb.)
            localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Giriş başarısız' };
    }
};

// Register fonksiyonu
export const register = async (userData) => {
    try {
        const response = await api.post('/register', userData);

        // Otomatik login yap
        if (response.data.token) {
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
    } catch (error) {
        throw error.response?.data || { message: 'Kayıt başarısız' };
    }
};

// Logout fonksiyonu
export const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
};

// Token kontrolü
export const getCurrentUser = () => {
    try {
        const user = localStorage.getItem('user');
        if (!user || user === 'undefined' || user === 'null') {
            return null;
        }
        return JSON.parse(user);
    } catch (error) {
        console.error('getCurrentUser error:', error);
        return null;
    }
};

// Token'ı header'a ekle
export const getAuthHeader = () => {
    const token = localStorage.getItem('token');
    return token ? { Authorization: `Bearer ${token}` } : {};
};

export default {
    login,
    register,
    logout,
    getCurrentUser,
    getAuthHeader,
};