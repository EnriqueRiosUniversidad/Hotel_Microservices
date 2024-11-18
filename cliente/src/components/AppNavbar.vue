<template>
  <nav class="navbar">
    <div class="navbar-links">
      <router-link class="nav-link" to="/">Home</router-link>
      <router-link v-if="isAuthenticated" class="nav-link" to="/contact">Contact</router-link>
      <router-link v-if="isAuthenticated" class="nav-link" to="/mis-reservas">Mis Reservas</router-link>
      <router-link v-if="isAdmin" class="nav-link" to="/ReservasAdmin">Todas las Reservas</router-link>

      <template v-if="isAuthenticated">
        <router-link class="nav-link" to="/perfil">Perfil</router-link>
        <button class="logout-button" @click="logout">Logout</button>
      </template>
      <template v-else>
        <router-link class="nav-link" to="/login">Login</router-link>
        <router-link class="nav-link" to="/register">Register</router-link>
      </template>
    </div>
  </nav>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'AppNavbar',
  computed: {
    ...mapGetters('auth', ['isAuthenticated', 'userRole']),
    isAdmin() {
      return this.userRole === 'ROLE_ADMIN';
    },
  },
  methods: {
    ...mapActions('auth', ['logout']),
  },
};
</script>

<style scoped>
@import "@/assets/styles/colors.css";

.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: var(--color-primary);
  border-bottom: 2px solid var(--color-quaternary);
}

.navbar-links {
  display: flex;
  gap: 1rem;
  margin-left: auto; /* enlaces a la derecha */
}

.nav-link {
  color: black; 
  text-decoration: none;
  font-weight: bold; 
  font-family: Arial, sans-serif; 
  padding: 0.5rem 1rem;
  border-radius: 5px;
  transition: background-color 0.3s;
}

.nav-link:hover {
  background-color: var(--color-tertiary);
  
}
    
.logout-button {
  background-color: var(--color-success);
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  font-weight: bold; 
  font-family: Arial, sans-serif; 
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #218838; 
}

</style>

