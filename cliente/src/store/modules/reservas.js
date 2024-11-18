  // src/store/modules/reservas.js
  import http from '../../http';

  const state = {
    reservas: [],
    reservaActual: null,
    totalPages: 0,
    totalElements: 0,
  };

  const mutations = {
    SET_RESERVAS(state, reservas) {
      state.reservas = reservas;
    },
    SET_RESERVA_ACTUAL(state, reserva) {
      state.reservaActual = reserva;
    },
    SET_TOTAL_PAGES(state, totalPages) {
      state.totalPages = totalPages;
    },

    SET_TOTAL_ELEMENTS(state, totalElements) {
      state.totalElements = totalElements;
    },
  };

  const actions = {
    // Acción para obtener todas las reservas (para ADMIN)
    async obtenerTodasLasReservas({ commit }, { page = 0, size = 10, sort }) {
      try {
        const response = await http.get('/reservas', {
          params: { page, size, sort },
        });
        commit('SET_RESERVAS', response.data.content);
        commit('SET_TOTAL_PAGES', response.data.page.totalPages);
        
        commit('SET_TOTAL_ELEMENTS', response.data.page.totalElements);
      } catch (error) {
        throw error.response.data || 'Error al obtener todas las reservas';
      }
    },

    // Acción para obtener reservas del usuario actual (USER o ADMIN)
    async obtenerMisReservas({ commit }, { page = 0, size = 10 }) {
      try {
        const response = await http.get('/reservas/mis-reservas', { params: { page, size } });
        commit('SET_RESERVAS', response.data.content);
      } catch (error) {
        throw error.response.data || 'Error al obtener reservas';
      }
    },

    // Acción para obtener una reserva por su ID
    async obtenerReservaPorId({ commit }, id) {
      try {
        const response = await http.get(`/reservas/${id}`);
        commit('SET_RESERVA_ACTUAL', response.data);
      } catch (error) {
        throw error.response.data || 'Error al obtener la reserva';
      }
    },

    // Acción para crear una reserva (solo USER)
    async crearReserva({ dispatch }, reservaData) {
      try {
        await http.post('/reservas', reservaData);
        dispatch('obtenerMisReservas', {});
      } catch (error) {
        throw error.response.data || 'Error al crear la reserva';
      }
    },

    // Acción para actualizar una reserva (solo antes de la fecha de inicio)
    async actualizarReserva({ dispatch }, { id, reservaData }) {
      try {
        await http.patch(`/reservas/${id}`, reservaData);
        dispatch('obtenerMisReservas', {});
      } catch (error) {
        throw error.response.data || 'Error al actualizar la reserva';
      }
    },

    // Acción para cancelar una reserva
    async cancelarReserva({ dispatch }, id) {
      try {
        await http.delete(`/reservas/cancelar/${id}`);
        dispatch('obtenerMisReservas', {});
      } catch (error) {
        throw error.response.data || 'Error al cancelar la reserva';
      }
    },

    // Acción para actualizar el estado de una reserva (ADMIN)
    async actualizarEstadoReserva(_, { id, estado }) {
      try {
        await http.put(`/reservas/${id}/estado`, null, { params: { estado } });
      } catch (error) {
        throw error.response.data || 'Error al actualizar el estado de la reserva';
      }
    },

    // Acción para obtener reservas por usuario (ADMIN)
    async obtenerReservaPorUsuario({ commit }, usuarioId) {
      try {
        const response = await http.get(`/reservas/usuario/${usuarioId}`);
        commit('SET_RESERVAS', response.data.content);
      } catch (error) {
        throw error.response.data || 'Error al obtener reservas del usuario';
      }
    },

    // Acción para enviar un email al usuario (ADMIN)
    async enviarEmailAlUsuario(_, { userId, emailContent }) {
      try {
        await http.post(`/reservas/email/${userId}`, emailContent);
      } catch (error) {
        throw error.response.data || 'Error al enviar el email';
      }
    },
  };

  export default {
    namespaced: true,
    state,
    mutations,
    actions,
  };
