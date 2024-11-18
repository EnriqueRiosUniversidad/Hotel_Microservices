// src/http.js
import axios from 'axios';
import store from './store';
import router from './router';

const http = axios.create({
  baseURL: 'http://localhost:8080', // Base URL del API Gateway
});

// Interceptor para añadir el token a cada solicitud
http.interceptors.request.use(
  (config) => {
    const token = store.state.auth.token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);


http.interceptors.response.use(
  response => response,
  error => {
    if (error.response && error.response.status === 401) {
      // Token expirado o no válido
      store.dispatch('auth/logout'); // Cerrar sesión
      store.commit('auth/SET_ERROR', 'Su sesión ha caducado. Por favor, inicie sesión de nuevo.'); // Configura el mensaje de error
      router.push({ name: 'login' }); // Redirige a la página de login
    }
    return Promise.reject(error);
  }
);

export default http;
