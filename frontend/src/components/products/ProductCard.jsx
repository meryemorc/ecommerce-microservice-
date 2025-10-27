import { addItemToBasket } from '../../services/basketService';
import { getCurrentUser } from '../../services/authService';
import { useNavigate } from 'react-router-dom';
import './ProductCard.css';

const ProductCard = ({ product }) => {
    const navigate = useNavigate();
    const currentUser = getCurrentUser();

    // Fiyat formatlama
    const formatPrice = (price) => {
        return new Intl.NumberFormat('tr-TR', {
            style: 'currency',
            currency: 'TRY',
        }).format(price);
    };

    // Placeholder renkleri (her ürün için farklı)
    const colors = ['#667eea', '#764ba2', '#f093fb', '#4facfe', '#43e97b', '#fa709a'];
    const randomColor = colors[Math.floor(Math.random() * colors.length)];

    const handleAddToCart = async () => {
        // Login kontrolü
        if (!currentUser) {
            alert('Sepete eklemek için giriş yapmalısınız!');
            navigate('/login');
            return;
        }

        try {
            await addItemToBasket(product.id, 1, product.price, product.name);
            alert(`✅ ${product.name} sepete eklendi!`);
        } catch (error) {
            console.error('Sepete ekleme hatası:', error);
            alert('❌ Sepete eklenirken bir hata oluştu!');
        }
    };

    return (
        <div className="product-card">
            {/* Placeholder Görsel */}
            <div
                className="product-image"
                style={{ background: `linear-gradient(135deg, ${randomColor} 0%, ${randomColor}dd 100%)` }}
            >
                <div className="product-badge">
                    {product.stock > 0 ? 'Stokta' : 'Tükendi'}
                </div>
                <div className="product-icon">📦</div>
            </div>

            {/* Ürün Bilgileri */}
            <div className="product-info">
                <div className="product-brand">{product.brand}</div>
                <h3 className="product-name">{product.name}</h3>
                <p className="product-description">
                    {product.description?.substring(0, 60)}
                    {product.description?.length > 60 && '...'}
                </p>

                {/* Özellikler */}
                <div className="product-details">
                    {product.color && (
                        <span className="product-tag">🎨 {product.color}</span>
                    )}
                    {product.size && (
                        <span className="product-tag">📏 {product.size}</span>
                    )}
                </div>

                {/* Fiyat ve Buton */}
                <div className="product-footer">
                    <div className="product-price-section">
                        <span className="product-price">{formatPrice(product.price)}</span>
                        <span className="product-stock">Stok: {product.stock}</span>
                    </div>
                    <button
                        className="btn-add-cart"
                        onClick={handleAddToCart}
                        disabled={product.stock === 0}
                    >
                        {product.stock > 0 ? '🛒 Sepete Ekle' : 'Stokta Yok'}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ProductCard;