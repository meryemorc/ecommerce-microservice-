import axios from 'axios';

const API_URL = 'http://localhost:8083/api/basket';

// Axios instance oluştur
const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Her istekte token'ı header'a ekle
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Sepete ürün ekle
export const addItemToBasket = async (productId, quantity = 1, price, productName) => {
    try {
        const response = await api.post('/add-item', {
            productId,
            quantity,
            price,
            productName,
        });
        return response.data;
    } catch (error) {
        console.error('Sepete eklenirken hata:', error);
        throw error.response?.data || { message: 'Sepete eklenemedi' };
    }
};

// Sepeti getir
export const getBasket = async () => {
    try {
        const response = await api.get('/get-basket');
        return response.data;
    } catch (error) {
        console.error('Sepet yüklenirken hata:', error);
        throw error.response?.data || { message: 'Sepet yüklenemedi' };
    }
};

// Sepetten ürün çıkar
export const removeItemFromBasket = async (productId) => {
    console.log('📤 removeItemFromBasket çağrıldı, productId:', productId);
    try {
        const response = await api.post('/remove-item', {
            productId: productId,
        });
        console.log('📥 removeItemFromBasket response:', response.data);
        return response.data; // String veya Object olabilir
    } catch (error) {
        console.error('❌ removeItemFromBasket error:', error);
        // Hata olsa bile devam et
        return { success: true };
    }
};

// Sepeti temizle
export const deleteBasket = async (basketId) => {
    try {
        const response = await api.delete(`/delete-basket/${basketId}`);
        return response.data;
    } catch (error) {
        console.error('Sepet silinirken hata:', error);
        throw error.response?.data || { message: 'Sepet silinemedi' };
    }
};

// Siparişi tamamla
export const completeOrder = async () => {
    try {
        const response = await api.post('/complete-order'); // userId kaldırıldı
        return response.data;
    } catch (error) {
        console.error('Sipariş tamamlanırken hata:', error);
        throw error.response?.data || { message: 'Sipariş tamamlanamadı' };
    }
};

export default {
    addItemToBasket,
    getBasket,
    removeItemFromBasket,
    deleteBasket,
    completeOrder,
};