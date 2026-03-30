function ProductList({ products, onEdit, onDelete }) {
    if (products.length === 0) {
        return (
            <div style={styles.empty}>
                No products found. Add one above!
            </div>
        );
    }

    return (
        <div style={styles.container}>
            <h2>Products ({products.length})</h2>
            <table style={styles.table}>
                <thead>
                    <tr style={styles.headerRow}>
                        <th style={styles.th}>ID</th>
                        <th style={styles.th}>Name</th>
                        <th style={styles.th}>Price</th>
                        <th style={styles.th}>Quantity</th>
                        <th style={styles.th}>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {products.map(product => (
                        <tr key={product.id} style={styles.row}>
                            <td style={styles.td}>{product.id}</td>
                            <td style={styles.td}>{product.name}</td>
                            <td style={styles.td}>£{product.price.toFixed(2)}</td>
                            <td style={styles.td}>{product.quantity}</td>
                            <td style={styles.td}>
                                <button
                                    style={styles.editBtn}
                                    onClick={() => onEdit(product)}>
                                    Edit
                                </button>
                                <button
                                    style={styles.deleteBtn}
                                    onClick={() => onDelete(product.id)}>
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

const styles = {
    container: {
        background: 'white',
        padding: '24px',
        borderRadius: '8px',
        boxShadow: '0 2px 8px rgba(0,0,0,0.1)'
    },
    empty: {
        background: 'white',
        padding: '40px',
        borderRadius: '8px',
        textAlign: 'center',
        color: '#999'
    },
    table: {
        width: '100%',
        borderCollapse: 'collapse',
        marginTop: '16px'
    },
    headerRow: {
        background: '#f8f9fa'
    },
    th: {
        padding: '12px 16px',
        textAlign: 'left',
        fontWeight: '600',
        borderBottom: '2px solid #eee',
        fontSize: '14px'
    },
    row: {
        borderBottom: '1px solid #eee'
    },
    td: {
        padding: '12px 16px',
        fontSize: '14px'
    },
    editBtn: {
        padding: '6px 14px',
        background: '#2196F3',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        marginRight: '8px',
        fontSize: '13px'
    },
    deleteBtn: {
        padding: '6px 14px',
        background: '#f44336',
        color: 'white',
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
        fontSize: '13px'
    }
};

export default ProductList;