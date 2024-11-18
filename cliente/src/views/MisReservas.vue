<template>
  <div class="mis-reservas">
    <h1>Mis Reservas</h1>
    <div v-if="reservas.length">
      <ul>
        <li v-for="reserva in reservas" :key="reserva.id">
          <router-link :to="{ name: 'ReservaDetalle', params: { id: reserva.id } }">
            Reserva #{{ reserva.id }} - Estado: {{ reserva.estado }}
          </router-link>
        </li>
      </ul>
    </div>
    <div v-else>
      <p>No tienes reservas.</p>
    </div>
    <button @click="$router.push({ name: 'CrearReserva' })">Crear Nueva Reserva</button>
    <button @click="logout">Cerrar Sesión</button>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';

export default {
  computed: {
    ...mapState('reservas', ['reservas']),
  },
  methods: {
    ...mapActions('reservas', ['obtenerMisReservas']),
    ...mapActions('auth', ['logout']),
    async handleLogout() {
      try {
        await this.logout(); // Ejecuta la acción de logout
        this.$router.push({ name: 'Login' });
      } catch (error) {
        console.error('Error al cerrar sesión:', error);
      }
    },
    crearNuevaReserva() {

    },
  },
  created() {
    this.obtenerMisReservas({});
  },
};
</script>

<style scoped>
.login {
  background-color: var(--color-secondary);
  color: #fff;
  padding: 20px;
  border-radius: 8px;
}

button {
  background-color: var(--color-primary);
  border: none;
  color: #fff;
  padding: 10px 20px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: var(--color-tertiary);
}
</style>