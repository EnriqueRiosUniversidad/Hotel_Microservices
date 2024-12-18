
import http from '../../http';
import { jwtDecode } from 'jwt-decode';

const state = {
  token: localStorage.getItem('token') || '',
  email: localStorage.getItem('email') || '',
  userRole: localStorage.getItem('userRole') || '',
};

const mutations = {
  SET_TOKEN(state, token) {
    state.token = token;
  },
  SET_EMAIL(state, email) {
    state.email = email;
  },
  SET_USER_ROLE(state, role) {
    state.userRole = role;
  },
  SET_ERROR(state, error) {
    state.error = error;
  },
  CLEAR_ERROR(state) {
    state.error = null;
  },
};

const actions = {
  async login({ commit }, credentials) {
    try {
      const response = await http.post('/auth/login', {
        email: credentials.email,
        password: credentials.password,
      });

     
      const token = response.data.token;
      const email = response.data.email;
      const decodedToken = jwtDecode(token);
      const userRole = decodedToken.role; // 'ROLE_ADMIN' o 'ROLE_USER'

    
      console.log(userRole);
      commit('SET_TOKEN', token);
      commit('SET_EMAIL', email);
      commit('SET_USER_ROLE', userRole);

      localStorage.setItem('token', token);
      localStorage.setItem('email', email);
      commit('CLEAR_ERROR');
    } catch (error) {
      console.error('Error decodificando el token:', error);
      throw error.response.data || 'Error al iniciar sesión';
    }
  },
  async register(_, userData) {
    try {
      await http.post('/auth/register', {
        nombre: userData.nombre,
        email: userData.email,
        password: userData.password,
      });
    } catch (error) {
      throw error.response.data || 'Error al registrar usuario';
    }
  },
  logout({ commit }) {
    commit('SET_TOKEN', '');
    commit('SET_EMAIL', '');
    commit('SET_USER_ROLE', '');
    localStorage.removeItem('token');
    localStorage.removeItem('email');
    localStorage.removeItem('userRole');
  },
  
};

const getters = {
  isAuthenticated: (state) => !!state.token,
  email: (state) => state.email,
  userRole: (state) => state.userRole,
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters,
};
