import { useState, useEffect } from 'react';
import ProductForm from './components/ProductForm';
import ProductList from './components/ProductList';
import {
    getAllProducts, createProduct,
    updateProduct, deleteProduct
} from './services/productService';
import axios from 'axios';

const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

function App() {
    const [products, setProducts] = useState([]);
    const [editingProduct, setEditingProduct] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const [token, setToken] = useState(localStorage.getItem('token') || '');
    const [authForm, setAuthForm] = useState({ username: '', password: '' });
    const [isRegistering, setIsRegistering] = useState(false);

    useEffect(() => {
        if (token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
            fetchProducts();
        } else {
            setLoading(false);
        }
    }, [token]);

    const fetchProducts = async () => {
        try {
            setLoading(true);
            const response = await getAllProducts();
            setProducts(response.data);
            setError('');
        } catch (err) {
            setError('Failed to load products. Is the backend running?');
        } finally {
            setLoading(false);
        }
    };

    const handleAuth = async (e) => {
        e.preventDefault();
        try {
            const endpoint = isRegistering ? '/auth/register' : '/auth/login';
            const response = await axios.post(`${BASE_URL}${endpoint}`, authForm);
            const newToken = response.data.token;
            setToken(newToken);
            localStorage.setItem('token', newToken);
            axios.defaults.headers.common['Authorization'] = `Bearer ${newToken}`;
            setError('');
        } catch (err) {
            setError(isRegistering ? 'Registration failed' : 'Invalid credentials');
        }
    };

    const handleLogout = () => {
        setToken('');
        localStorage.removeItem('token');
        delete axios.defaults.headers.common['Authorization'];
        setProducts([]);
    };

    const handleCreate = async (product) => {
        try {
            await createProduct(product);
            fetchProducts();
            setError('');
        } catch (err) {
            setError('Failed to create product');
        }
    };

    const handleUpdate = async (product) => {
        try {
            await updateProduct(editingProduct.id, product);
            setEditingProduct(null);
            fetchProducts();
            setError('');
        } catch (err) {
            setError('Failed to update product');
        }
    };

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this product?')) return;
        try {
            await deleteProduct(id);
            fetchProducts();
        } catch (err) {
            setError('Failed to delete product');
        }
    };

    // Show login/register if not authenticated
    if (!token) {
        return (
            <div style={styles.app}>
                <header style={styles.header}>
                    <h1>📦 Inventory Management</h1>
                </header>
                <main style={styles.main}>
                    <div style={styles.authBox}>
                        <h2>{isRegistering ? 'Register' : 'Login'}</h2>
                        {error && <div style={styles.error}>{error}</div>}
                        <form onSubmit={handleAuth} style={styles.authForm}>
                            <input
                                style={styles.input}
                                type="text"
                                placeholder="Username"
                                value={authForm.username}
                                onChange={e => setAuthForm({...authForm, username: e.target.value})}
                                required
                            />
                            <input
                                style={styles.input}
                                type="password"
                                placeholder="Password"
                                value={authForm.password}
                                onChange={e => setAuthForm({...authForm, password: e.target.value})}
                                required
                            />
                            <button style={styles.submitBtn} type="submit">
                                {isRegistering ? 'Register' : 'Login'}
                            </button>
                        </form>
                        <p style={{marginTop: '16px', textAlign: 'center'}}>
                            {isRegistering ? 'Already have an account? ' : "Don't have an account? "}
                            <span
                                style={{color: '#2196F3', cursor: 'pointer'}}
                                onClick={() => { setIsRegistering(!isRegistering); setError(''); }}>
                                {isRegistering ? 'Login' : 'Register'}
                            </span>
                        </p>
                    </div>
                </main>
            </div>
        );
    }

    return (
        <div style={styles.app}>
            <header style={styles.header}>
                <h1>📦 Inventory Management</h1>
                <button style={styles.logoutBtn} onClick={handleLogout}>Logout</button>
            </header>
            <main style={styles.main}>
                {error && <div style={styles.error}>{error}</div>}
                <ProductForm
                    onSubmit={editingProduct ? handleUpdate : handleCreate}
                    editingProduct={editingProduct}
                    onCancel={() => setEditingProduct(null)}
                />
                {loading ? (
                    <div style={styles.loading}>Loading products...</div>
                ) : (
                    <ProductList
                        products={products}
                        onEdit={setEditingProduct}
                        onDelete={handleDelete}
                    />
                )}
            </main>
        </div>
    );
}

const styles = {
    app: { minHeight: '100vh', background: '#f5f5f5' },
    header: {
        background: '#1a1a2e', color: 'white',
        padding: '20px 40px', boxShadow: '0 2px 8px rgba(0,0,0,0.2)',
        display: 'flex', justifyContent: 'space-between', alignItems: 'center'
    },
    main: { maxWidth: '1000px', margin: '0 auto', padding: '32px 20px' },
    error: {
        background: '#ffebee', color: '#c62828',
        padding: '12px 16px', borderRadius: '6px',
        marginBottom: '20px', border: '1px solid #ef9a9a'
    },
    loading: { textAlign: 'center', padding: '40px', color: '#999' },
    authBox: {
        background: 'white', padding: '40px', borderRadius: '8px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        maxWidth: '400px', margin: '0 auto'
    },
    authForm: { display: 'flex', flexDirection: 'column', gap: '12px', marginTop: '16px' },
    input: {
        padding: '10px 14px', borderRadius: '6px',
        border: '1px solid #ddd', fontSize: '14px'
    },
    submitBtn: {
        padding: '10px 20px', background: '#4CAF50', color: 'white',
        border: 'none', borderRadius: '6px', cursor: 'pointer', fontSize: '14px'
    },
    logoutBtn: {
        padding: '8px 16px', background: '#f44336', color: 'white',
        border: 'none', borderRadius: '6px', cursor: 'pointer', fontSize: '14px'
    }
};

export default App;