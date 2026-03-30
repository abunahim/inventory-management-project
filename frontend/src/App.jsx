import { useState, useEffect } from 'react';
import ProductForm from './components/ProductForm';
import ProductList from './components/ProductList';
import {
    getAllProducts,
    createProduct,
    updateProduct,
    deleteProduct
} from './services/productService';

function App() {
    const [products, setProducts] = useState([]);
    const [editingProduct, setEditingProduct] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    // Load products on startup
    useEffect(() => {
        fetchProducts();
    }, []);

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

    const handleCreate = async (product) => {
        try {
            await createProduct(product);
            fetchProducts();
            setError('');
        } catch (err) {
            setError(err.response?.data?.name || 'Failed to create product');
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

    return (
        <div style={styles.app}>
            <header style={styles.header}>
                <h1>📦 Inventory Management</h1>
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
    app: {
        minHeight: '100vh',
        background: '#f5f5f5'
    },
    header: {
        background: '#1a1a2e',
        color: 'white',
        padding: '20px 40px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.2)'
    },
    main: {
        maxWidth: '1000px',
        margin: '0 auto',
        padding: '32px 20px'
    },
    error: {
        background: '#ffebee',
        color: '#c62828',
        padding: '12px 16px',
        borderRadius: '6px',
        marginBottom: '20px',
        border: '1px solid #ef9a9a'
    },
    loading: {
        textAlign: 'center',
        padding: '40px',
        color: '#999'
    }
};

export default App;