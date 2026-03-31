import { useState, useEffect } from 'react';

function ProductForm({ onSubmit, editingProduct, onCancel }) {

    const [form, setForm] = useState({
        name: '',
        price: '',
        quantity: ''
    });

    // If editing, pre-fill the form
    useEffect(() => {
        if (editingProduct) {
            setForm({
                name: editingProduct.name,
                price: editingProduct.price,
                quantity: editingProduct.quantity
            });
        } else {
            setForm({ name: '', price: '', quantity: '' });
        }
    }, [editingProduct]);

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit({
            name: form.name,
            price: parseFloat(form.price),
            quantity: parseInt(form.quantity)
        });
        setForm({ name: '', price: '', quantity: '' });
    };

    return (
        <div style={styles.formContainer}>
            <h2>{editingProduct ? 'Edit Product' : 'Add New Product'}</h2>
            <form onSubmit={handleSubmit} style={styles.form}>
                <input
                    style={styles.input}
                    type="text"
                    name="name"
                    placeholder="Product name"
                    value={form.name}
                    onChange={handleChange}
                    required
                />
                <input
                    style={styles.input}
                    type="number"
                    name="price"
                    placeholder="Price"
                    value={form.price}
                    onChange={handleChange}
                    step="0.01"
                    required
                />
                <input
                    style={styles.input}
                    type="number"
                    name="quantity"
                    placeholder="Quantity"
                    value={form.quantity}
                    onChange={handleChange}
                    required
                />
                <div style={styles.buttons}>
                    <button style={styles.submitBtn} type="submit">
                        {editingProduct ? 'Update' : 'Add Product'}
                    </button>
                    {editingProduct && (
                        <button style={styles.cancelBtn} type="button" onClick={onCancel}>
                            Cancel
                        </button>
                    )}
                </div>
            </form>
        </div>
    );
}

const styles = {
    formContainer: {
        background: 'white',
        padding: '24px',
        borderRadius: '8px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)',
        marginBottom: '24px'
    },
    form: {
        display: 'flex',
        gap: '12px',
        marginTop: '16px',
        flexWrap: 'wrap'
    },
    input: {
        padding: '10px 14px',
        borderRadius: '6px',
        border: '1px solid #ddd',
        fontSize: '14px',
        flex: '1',
        minWidth: '150px'
    },
    buttons: {
        display: 'flex',
        gap: '8px'
    },
    submitBtn: {
        padding: '10px 20px',
        background: '#4CAF50',
        color: 'white',
        border: 'none',
        borderRadius: '6px',
        cursor: 'pointer',
        fontSize: '14px'
    },
    cancelBtn: {
        padding: '10px 20px',
        background: '#f44336',
        color: 'white',
        border: 'none',
        borderRadius: '6px',
        cursor: 'pointer',
        fontSize: '14px'
    }
};

export default ProductForm;