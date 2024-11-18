
<template>
  <div class="login">
    <h1>Iniciar Sesión</h1>
    <form @submit.prevent="handleLogin">
      <div>
        <label>Email:</label>
        <input v-model="email" type="email" required />
      </div>
      <div>
        <label>Contraseña:</label>
        <input v-model="password" type="password" required />
      </div>
      <button type="submit">Entrar</button>
    </form>
    <p>
      ¿No tienes una cuenta?
      <router-link to="/register">Regístrate aquí</router-link>
    </p>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    ...mapActions('auth', ['login']),
    async handleLogin() {
      try {
        await this.login({
          email: this.email,
          password: this.password,
        });
        this.$toast.success('Inicio de sesión exitoso');
        this.$router.push({ name: 'MisReservas' });
      } catch (error) {
        this.$toast.error(error.message || 'Error al iniciar sesión');
      }
    },
  },
  watch: {
    // Limpia el mensaje de error cuando se monta el componente para evitar mensajes residuales
    $route() {
      this.$store.commit('auth/CLEAR_ERROR');
    },
  },
  mounted() {
    // Limpiar el mensaje de error cuando se carga el componente
    this.$store.commit('auth/CLEAR_ERROR');
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
