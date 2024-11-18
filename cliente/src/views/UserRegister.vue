<!-- src/views/UserRegister.vue -->
<template>
  <div class="register">
    <h1>Registrarse</h1>
    <form @submit.prevent="handleRegister">
      <div>
        <label>Nombre:</label>
        <input v-model="nombre" type="text" required />
      </div>
      <div>
        <label>Email:</label>
        <input v-model="email" type="email" required />
      </div>
      <div>
        <label>Contraseña:</label>
        <input v-model="password" type="password" required />
      </div>
      <button type="submit">Registrarse</button>
    </form>
    <p>
      ¿Ya tienes una cuenta?
      <router-link to="/login">Inicia sesión aquí</router-link>
    </p>
  </div>
</template>

<script>
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      nombre: '',
      email: '',
      password: '',
    };
  },
  methods: {
    ...mapActions('auth', ['register']),
    async handleRegister() {
      try {
        await this.register({
          nombre: this.nombre,
          email: this.email,
          password: this.password,
        });
        alert('Registro exitoso. Ahora puedes iniciar sesión.');
        this.$router.push({ name: 'Login' });
      } catch (error) {
        alert(error.message || 'Error al registrarse');
      }
    },
  },
};
</script>

<style scoped>
.register {
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
