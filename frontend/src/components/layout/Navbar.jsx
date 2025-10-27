import { Link, useNavigate } from 'react-router-dom';
import { getCurrentUser, logout } from '../../services/authService';
import './Navbar.css';

const Navbar = () => {
    const navigate = useNavigate();
    const currentUser = getCurrentUser();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <nav className="navbar">
            <div className="navbar-container">
                {/* Logo */}
                <Link to="/" className="navbar-logo">
                    🛍️ E-Commerce
                </Link>

                {/* Navigation Links */}
                <div className="navbar-links">
                    <Link to="/" className="nav-link">
                        🏠 Ana Sayfa
                    </Link>

                    {currentUser ? (
                        <>
                            <Link to="/basket" className="nav-link basket-link">
                                🛒 Sepetim
                            </Link>
                            <div className="navbar-user">
                                <span className="user-name">👤 {currentUser.name || currentUser.username}</span>
                                <button onClick={handleLogout} className="btn-logout">
                                    Çıkış Yap
                                </button>
                            </div>
                        </>
                    ) : (
                        <>
                            <Link to="/login" className="btn-login">
                                Giriş Yap
                            </Link>
                            <Link to="/register" className="btn-register">
                                Kayıt Ol
                            </Link>
                        </>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Navbar;