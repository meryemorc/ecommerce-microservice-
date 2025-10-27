import { useState, useEffect } from 'react';
import { getAllCategories, getAllBrands, getAllColors } from '../../services/productService';
import './FilterPanel.css';

const FilterPanel = ({ onFilterChange, onSearchChange }) => {
    const [categories, setCategories] = useState([]);
    const [brands, setBrands] = useState([]);
    const [colors, setColors] = useState([]);
    const [loading, setLoading] = useState(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedCategory, setSelectedCategory] = useState('');
    const [selectedBrand, setSelectedBrand] = useState('');
    const [selectedColor, setSelectedColor] = useState('');
    const [priceRange, setPriceRange] = useState({ min: '', max: '' });
    const [expandedCategories, setExpandedCategories] = useState([]); // Açık kategoriler

    useEffect(() => {
        fetchFilterData();
    }, []);

    const fetchFilterData = async () => {
        try {
            setLoading(true);
            // Paralel olarak hepsini getir
            const [categoriesData, brandsData, colorsData] = await Promise.all([
                getAllCategories(),
                getAllBrands(),
                getAllColors()
            ]);

            setCategories(categoriesData);
            setBrands(brandsData);
            setColors(colorsData);
        } catch (error) {
            console.error('Filtre verileri yüklenirken hata:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleSearchChange = (e) => {
        const value = e.target.value;
        setSearchTerm(value);
        onSearchChange(value);
    };

    const handleCategoryChange = (categoryId) => {
        setSelectedCategory(categoryId);
        applyFilters({ category: categoryId, brand: selectedBrand, color: selectedColor, priceRange });
    };

    const toggleCategory = (categoryId) => {
        setExpandedCategories(prev =>
            prev.includes(categoryId)
                ? prev.filter(id => id !== categoryId)
                : [...prev, categoryId]
        );
    };

    // Ana kategorileri ve alt kategorileri ayır
    const mainCategories = categories.filter(cat => cat.level === 1);
    const getSubCategories = (parentId) => {
        return categories.filter(cat => cat.parentId === parentId);
    };

    const handleBrandChange = (brand) => {
        setSelectedBrand(brand);
        applyFilters({ category: selectedCategory, brand, color: selectedColor, priceRange });
    };

    const handleColorChange = (color) => {
        setSelectedColor(color);
        applyFilters({ category: selectedCategory, brand: selectedBrand, color, priceRange });
    };

    const handlePriceChange = (type, value) => {
        const newPriceRange = { ...priceRange, [type]: value };
        setPriceRange(newPriceRange);
        applyFilters({ category: selectedCategory, brand: selectedBrand, color: selectedColor, priceRange: newPriceRange });
    };

    const applyFilters = (filters) => {
        onFilterChange(filters);
    };

    const clearFilters = () => {
        setSelectedCategory('');
        setSelectedBrand('');
        setSelectedColor('');
        setPriceRange({ min: '', max: '' });
        setSearchTerm('');
        onFilterChange({ category: '', brand: '', color: '', priceRange: { min: '', max: '' } });
        onSearchChange('');
    };

    return (
        <div className="filter-panel">
            {loading ? (
                <div className="filter-loading">
                    <div className="loading-spinner-small"></div>
                    <p>Filtreler yükleniyor...</p>
                </div>
            ) : (
                <>
                    {/* Arama Çubuğu */}
                    <div className="filter-section">
                        <h3>🔍 Ürün Ara</h3>
                        <input
                            type="text"
                            className="search-input"
                            placeholder="Ürün adı ile ara..."
                            value={searchTerm}
                            onChange={handleSearchChange}
                        />
                    </div>

                    {/* Kategori Filtresi */}
                    <div className="filter-section">
                        <h3>📁 Kategori</h3>
                        <div className="filter-options">
                            <label className="filter-option">
                                <input
                                    type="radio"
                                    name="category"
                                    checked={selectedCategory === ''}
                                    onChange={() => handleCategoryChange('')}
                                />
                                <span>Tümü</span>
                            </label>

                            {mainCategories.map((mainCat) => {
                                const subCategories = getSubCategories(mainCat.id);
                                const isExpanded = expandedCategories.includes(mainCat.id);

                                return (
                                    <div key={mainCat.id} className="category-group">
                                        {/* Ana Kategori */}
                                        <div
                                            className="main-category"
                                            onClick={() => toggleCategory(mainCat.id)}
                                        >
                  <span className="category-icon">
                    {isExpanded ? '📂' : '📁'}
                  </span>
                                            <span className="category-name">{mainCat.categoryName}</span>
                                            <span className="expand-icon">
                    {subCategories.length > 0 && (isExpanded ? '▼' : '▶')}
                  </span>
                                        </div>

                                        {/* Alt Kategoriler */}
                                        {isExpanded && subCategories.length > 0 && (
                                            <div className="sub-categories">
                                                {subCategories.map((subCat) => (
                                                    <label key={subCat.id} className="filter-option sub-category">
                                                        <input
                                                            type="radio"
                                                            name="category"
                                                            checked={selectedCategory === subCat.id}
                                                            onChange={() => handleCategoryChange(subCat.id)}
                                                        />
                                                        <span>{subCat.categoryName}</span>
                                                    </label>
                                                ))}
                                            </div>
                                        )}
                                    </div>
                                );
                            })}
                        </div>
                    </div>

                    {/* Marka Filtresi */}
                    <div className="filter-section">
                        <h3>🏷️ Marka</h3>
                        <div className="filter-options">
                            <label className="filter-option">
                                <input
                                    type="radio"
                                    name="brand"
                                    checked={selectedBrand === ''}
                                    onChange={() => handleBrandChange('')}
                                />
                                <span>Tümü</span>
                            </label>
                            {brands.map((brand) => (
                                <label key={brand} className="filter-option">
                                    <input
                                        type="radio"
                                        name="brand"
                                        checked={selectedBrand === brand}
                                        onChange={() => handleBrandChange(brand)}
                                    />
                                    <span>{brand}</span>
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Renk Filtresi */}
                    <div className="filter-section">
                        <h3>🎨 Renk</h3>
                        <div className="filter-options">
                            <label className="filter-option">
                                <input
                                    type="radio"
                                    name="color"
                                    checked={selectedColor === ''}
                                    onChange={() => handleColorChange('')}
                                />
                                <span>Tümü</span>
                            </label>
                            {colors.map((color) => (
                                <label key={color} className="filter-option">
                                    <input
                                        type="radio"
                                        name="color"
                                        checked={selectedColor === color}
                                        onChange={() => handleColorChange(color)}
                                    />
                                    <span>{color}</span>
                                </label>
                            ))}
                        </div>
                    </div>

                    {/* Fiyat Aralığı */}
                    <div className="filter-section">
                        <h3>💰 Fiyat Aralığı</h3>
                        <div className="price-inputs">
                            <input
                                type="number"
                                placeholder="Min"
                                value={priceRange.min}
                                onChange={(e) => handlePriceChange('min', e.target.value)}
                                className="price-input"
                            />
                            <span className="price-separator">-</span>
                            <input
                                type="number"
                                placeholder="Max"
                                value={priceRange.max}
                                onChange={(e) => handlePriceChange('max', e.target.value)}
                                className="price-input"
                            />
                        </div>
                    </div>

                    {/* Filtreleri Temizle */}
                    <button className="clear-filters-btn" onClick={clearFilters}>
                        🗑️ Filtreleri Temizle
                    </button>
                </>
            )}
        </div>
    );
};

export default FilterPanel;