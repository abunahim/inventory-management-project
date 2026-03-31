import axios from 'axios';

const BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080';

const API_URL = `${BASE_URL}/products`;
const AUTH_URL = `${BASE_URL}/auth`;

export const getAllProducts = () => axios.get(API_URL);
export const getProductById = (id) => axios.get(`${API_URL}/${id}`);
export const createProduct = (product) => axios.post(API_URL, product);
export const updateProduct = (id, product) => axios.put(`${API_URL}/${id}`, product);
export const deleteProduct = (id) => axios.delete(`${API_URL}/${id}`);

export const register = (credentials) => axios.post(`${AUTH_URL}/register`, credentials);
export const login = (credentials) => axios.post(`${AUTH_URL}/login`, credentials);