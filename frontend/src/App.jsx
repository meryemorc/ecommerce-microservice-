import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import ProductList from './pages/products/ProductList';
import Basket from './pages/basket/Basket';
import './App.css';

function App() {
    return (
        <Router>
            <div className="App">
                <Navbar />
                <Routes>
                    {/* Ana sayfa - ürün listesi */}
                    <Route path="/" element={<ProductList />} />

                    {/* Auth routes */}
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />

                    {/* Products */}
                    <Route path="/products" element={<ProductList />} />

                    {/* Basket */}
                    <Route path="/basket" element={<Basket key="basket" />} />

                    {/* 404 - sayfa bulunamadı */}
                    <Route path="*" element={<Navigate to="/" />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;