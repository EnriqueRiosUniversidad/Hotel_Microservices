<template>
  <div class="home">
    <h1>Bienvenido a la Página Principal</h1>
    <!-- Mostrar los botones de Login y Register solo si el usuario no está autenticado -->
    <div v-if="!isAuthenticated">
      <button @click="goToLogin">Login</button>
      <button @click="goToRegister">Register</button>
    </div>
    <!-- Mostrar un mensaje de bienvenida si el usuario está autenticado -->
    <div v-else>
      <p>Hola, {{ userEmail }}. Bienvenido de nuevo.</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'HomeView',
  computed: {
    ...mapGetters('auth', ['isAuthenticated', 'email']),
    userEmail() {
      return this.email;
    },
  },
  methods: {
    goToLogin() {
      this.$router.push({ name: 'UserLogin' });
    },
    goToRegister() {
      this.$router.push({ name: 'Register' });
    },
  },
};
</script>

<style scoped>
.home {
  text-align: center;
  margin-top: 50px;
}
button {
  margin: 5px;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}
</style>
