import axios from 'axios';

const API_URL = 'http://localhost:8081';

// Axios instance oluştur
const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Tüm ürünleri getir
export const getAllProducts = async () => {
    try {
        const response = await api.get('/products/all');
        return response.data;
    } catch (error) {
        console.error('Ürünler yüklenirken hata:', error);
        throw error.response?.data || { message: 'Ürünler yüklenemedi' };
    }
};

// Tek ürün getir
export const getProductById = async (id) => {
    try {
        const response = await api.get(`/products/${id}`);
        return response.data;
    } catch (error) {
        console.error('Ürün yüklenirken hata:', error);
        throw error.response?.data || { message: 'Ürün bulunamadı' };
    }
};

// Kategoriye göre ürünler
export const getProductsByCategory = async (categoryId) => {
    try {
        const response = await api.get(`/api/products/search/category/${categoryId}`);
        return response.data;
    } catch (error) {
        console.error('Kategori ürünleri yüklenirken hata:', error);
        throw error.response?.data || { message: 'Ürünler yüklenemedi' };
    }
};

// Markaya göre ürünler
export const getProductsByBrand = async (brand) => {
    try {
        const response = await api.get(`/products/brand?brand=${brand}`);
        return response.data;
    } catch (error) {
        console.error('Marka ürünleri yüklenirken hata:', error);
        throw error.response?.data || { message: 'Ürünler yüklenemedi' };
    }
};

// İsme göre arama
export const searchProductsByName = async (name) => {
    try {
        const response = await api.get(`/products/name?name=${name}`);
        return response.data;
    } catch (error) {
        console.error('Arama hatası:', error);
        throw error.response?.data || { message: 'Arama başarısız' };
    }
};

// Tüm kategorileri getir
export const getAllCategories = async () => {
    try {
        const response = await api.get('/category/all');
        return response.data;
    } catch (error) {
        console.error('Kategoriler yüklenirken hata:', error);
        throw error.response?.data || { message: 'Kategoriler yüklenemedi' };
    }
};

// Tüm markaları getir
export const getAllBrands = async () => {
    try {
        const response = await api.get('/products/filters/brands');
        return response.data;
    } catch (error) {
        console.error('Markalar yüklenirken hata:', error);
        throw error.response?.data || { message: 'Markalar yüklenemedi' };
    }
};

// Tüm renkleri getir
export const getAllColors = async () => {
    try {
        const response = await api.get('/products/filters/colors');
        return response.data;
    } catch (error) {
        console.error('Renkler yüklenirken hata:', error);
        throw error.response?.data || { message: 'Renkler yüklenemedi' };
    }
};

export default {
    getAllProducts,
    getProductById,
    getProductsByCategory,
    getProductsByBrand,
    searchProductsByName,
    getAllCategories,
    getAllBrands,
    getAllColors,
};