<template>
  <div id="app">
    <!-- Mostrar el AppNavBar solo si el usuario está autenticado -->
    <AppNavBar v-if="isAuthenticated" />
    <router-view />
  </div>
</template>

<script>
import { mapGetters } from 'vuex';
import AppNavBar from './components/AppNavbar.vue'; // Asegúrate de usar el nuevo nombre

export default {
  components: {
    AppNavBar, // Registrar el componente con su nuevo nombre
  },
  computed: {
    ...mapGetters('auth', ['isAuthenticated']),
  },
};
</script>




<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
