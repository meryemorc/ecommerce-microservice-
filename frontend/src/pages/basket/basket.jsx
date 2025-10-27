import { useState, useEffect, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { getBasket, removeItemFromBasket, completeOrder } from '../../services/basketService';
import { getCurrentUser } from '../../services/authService';
import './Basket.css';

const Basket = () => {
    const navigate = useNavigate();
    const [basket, setBasket] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [processingOrder, setProcessingOrder] = useState(false);
    const hasFetched = useRef(false); // Sadece 1 kere fetch için

    useEffect(() => {
        // Sadece 1 kere çalışsın
        if (hasFetched.current) return;
        hasFetched.current = true;

        const user = getCurrentUser();
        if (!user) {
            navigate('/login');
            return;
        }

        getBasket()
            .then(data => {
                setBasket(data);
                setLoading(false);
            })
            .catch(err => {
                setError(err.message || 'Sepet yüklenirken hata oluştu');
                setLoading(false);
            });
    }, []);

    const fetchBasket = async () => {
        try {
            setLoading(true);
            const data = await getBasket();
            setBasket(data);
            setError('');
        } catch (err) {
            setError(err.message || 'Sepet yüklenirken hata oluştu');
        } finally {
            setLoading(false);
        }
    };

    const handleRemoveItem = async (productId) => {
        console.log('🗑️ Kaldır butonu tıklandı, productId:', productId);
        try {
            await removeItemFromBasket(productId);
            console.log('✅ Ürün kaldırıldı');
            // Sayfayı yenile
            window.location.reload();
        } catch (err) {
            console.error('❌ Kaldırma hatası:', err);
            alert(err.message || 'Ürün çıkarılamadı');
        }
    };

    const handleCompleteOrder = async () => {
        const confirmOrder = window.confirm(
            'Siparişinizi tamamlamak istediğinize emin misiniz? Bu işlem geri alınamaz.'
        );

        if (!confirmOrder) return;

        try {
            setProcessingOrder(true);
            await completeOrder(); // basketId parametresi kaldırıldı
            alert('✅ Siparişiniz başarıyla tamamlandı! Email adresinize onay gönderildi.');
            navigate('/');
        } catch (err) {
            alert(err.message || 'Sipariş tamamlanamadı');
        } finally {
            setProcessingOrder(false);
        }
    };

    const calculateTotal = () => {
        if (!basket?.items) return 0;
        return basket.items.reduce((total, item) => {
            return total + (item.price * item.quantity);
        }, 0);
    };

    const formatPrice = (price) => {
        return new Intl.NumberFormat('tr-TR', {
            style: 'currency',
            currency: 'TRY',
        }).format(price);
    };

    if (loading) {
        return (
            <div className="basket-loading">
                <div className="loading-spinner"></div>
                <p>Sepetiniz yükleniyor...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="basket-error">
                <div className="error-icon">⚠️</div>
                <h2>Bir Hata Oluştu</h2>
                <p>{error}</p>
                <button onClick={fetchBasket} className="btn-retry">
                    Tekrar Dene
                </button>
            </div>
        );
    }

    if (!basket || !basket.items || basket.items.length === 0) {
        return (
            <div className="basket-empty">
                <div className="empty-icon">🛒</div>
                <h2>Sepetiniz Boş</h2>
                <p>Henüz sepetinize ürün eklemediniz</p>
                <button onClick={() => navigate('/')} className="btn-shopping">
                    Alışverişe Başla
                </button>
            </div>
        );
    }

    return (
        <div className="basket-container">
            <div className="basket-content">
                <div className="basket-header">
                    <h1>🛒 Sepetim</h1>
                    <p>{basket.items.length} ürün</p>
                </div>

                <div className="basket-items">
                    {basket.items.map((item) => (
                        <div key={item.productId} className="basket-item">
                            <div className="item-image">
                                <div className="placeholder-image">📦</div>
                            </div>

                            <div className="item-details">
                                <h3>{item.productName || 'Ürün'}</h3>
                                <p className="item-price">{formatPrice(item.price)}</p>
                                <p className="item-quantity">Adet: {item.quantity}</p>
                            </div>

                            <div className="item-actions">
                                <p className="item-total">
                                    {formatPrice(item.price * item.quantity)}
                                </p>
                                <button
                                    onClick={() => handleRemoveItem(item.productId)}
                                    className="btn-remove"
                                >
                                    🗑️ Kaldır
                                </button>
                            </div>
                        </div>
                    ))}
                </div>

                <div className="basket-summary">
                    <div className="summary-row">
                        <span>Ara Toplam:</span>
                        <span className="summary-value">{formatPrice(calculateTotal())}</span>
                    </div>
                    <div className="summary-row">
                        <span>Kargo:</span>
                        <span className="summary-value free">Ücretsiz</span>
                    </div>
                    <div className="summary-row total">
                        <span>Toplam:</span>
                        <span className="summary-value">{formatPrice(calculateTotal())}</span>
                    </div>

                    <button
                        onClick={handleCompleteOrder}
                        disabled={processingOrder}
                        className="btn-complete-order"
                    >
                        {processingOrder ? 'İşleniyor...' : '✓ Siparişi Tamamla'}
                    </button>

                    <button
                        onClick={() => navigate('/')}
                        className="btn-continue-shopping"
                    >
                        ← Alışverişe Devam Et
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Basket;