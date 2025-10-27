import { useState, useEffect } from 'react';
import { getAllProducts, getProductsByCategory, getProductsByBrand, searchProductsByName } from '../../services/productService';
import ProductCard from '../../components/products/ProductCard';
import FilterPanel from '../../components/products/FilterPanel';
import './ProductList.css';

const ProductList = () => {
    const [allProducts, setAllProducts] = useState([]);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [activeFilters, setActiveFilters] = useState({
        category: '',
        brand: '',
        color: '',
        priceRange: { min: '', max: '' },
    });
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            setLoading(true);
            const data = await getAllProducts();
            setAllProducts(data);
            setFilteredProducts(data);
            setError('');
        } catch (err) {
            setError(err.message || 'Ürünler yüklenirken bir hata oluştu');
            console.error('Fetch error:', err);
        } finally {
            setLoading(false);
        }
    };

    // Filtreleme mantığı
    const applyFilters = (products, filters, search) => {
        let result = [...products];

        // İsim araması
        if (search && search.trim() !== '') {
            result = result.filter(product =>
                product.name.toLowerCase().includes(search.toLowerCase())
            );
        }

        // Kategori filtresi
        if (filters.category && filters.category !== '') {
            result = result.filter(product => product.categoryId === filters.category);
        }

        // Marka filtresi
        if (filters.brand && filters.brand !== '') {
            result = result.filter(product => product.brand === filters.brand);
        }

        // Renk filtresi
        if (filters.color && filters.color !== '') {
            result = result.filter(product => product.color === filters.color);
        }

        // Fiyat aralığı filtresi
        if (filters.priceRange.min !== '' || filters.priceRange.max !== '') {
            const min = parseFloat(filters.priceRange.min) || 0;
            const max = parseFloat(filters.priceRange.max) || Infinity;
            result = result.filter(product => product.price >= min && product.price <= max);
        }

        return result;
    };

    const handleFilterChange = (newFilters) => {
        setActiveFilters(newFilters);
        const filtered = applyFilters(allProducts, newFilters, searchTerm);
        setFilteredProducts(filtered);
    };

    const handleSearchChange = (search) => {
        setSearchTerm(search);
        const filtered = applyFilters(allProducts, activeFilters, search);
        setFilteredProducts(filtered);
    };

    if (loading) {
        return (
            <div className="loading-container">
                <div className="loading-spinner"></div>
                <p>Ürünler yükleniyor...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="error-container">
                <div className="error-icon">⚠️</div>
                <h2>Bir Hata Oluştu</h2>
                <p>{error}</p>
                <button onClick={fetchProducts} className="btn-retry">
                    Tekrar Dene
                </button>
            </div>
        );
    }

    return (
        <div className="product-list-page">
            <div className="product-list-container">
                {/* Sol Panel - Filtreler */}
                <aside className="sidebar">
                    <FilterPanel
                        onFilterChange={handleFilterChange}
                        onSearchChange={handleSearchChange}
                    />
                </aside>

                {/* Sağ Panel - Ürünler */}
                <main className="main-content">
                    {/* Header */}
                    <div className="product-list-header">
                        <h1>Ürünler</h1>
                        <p className="product-count">
                            {filteredProducts.length} ürün bulundu
                            {filteredProducts.length !== allProducts.length &&
                                ` (${allProducts.length} toplam)`
                            }
                        </p>
                    </div>

                    {/* Products Grid */}
                    {filteredProducts.length === 0 ? (
                        <div className="empty-state">
                            <div className="empty-icon">🔍</div>
                            <h2>Ürün Bulunamadı</h2>
                            <p>Arama kriterlerinize uygun ürün bulunamadı</p>
                        </div>
                    ) : (
                        <div className="products-grid">
                            {filteredProducts.map((product, index) => (
                                <ProductCard key={product.id || `product-${index}`} product={product} />
                            ))}
                        </div>
                    )}
                </main>
            </div>
        </div>
    );
};

export default ProductList;