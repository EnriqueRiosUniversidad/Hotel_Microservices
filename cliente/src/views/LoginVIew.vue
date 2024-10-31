<template>
  <div class="login">
    <h1>Login page</h1>
    <br>
    <form id="login_form" @submit.prevent="handleLogin">
      <label>Email
        <input v-model="email" id="login_email" type="email" placeholder="Email" required>
      </label>
      <br>
      <label>Password
        <input v-model="password" id="login_password" type="password" placeholder="Password" required>
      </label>
      <br>
      <button id="login_submit" type="submit">Submit</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

export default {
  setup() {
    const email = ref('');
    const password = ref('');
    const router = useRouter();

    const handleLogin = async () => {
      try {
        const response = await axios.post('http://localhost:8090/auth/login', {
          email: email.value, // Acceder a los valores de ref
          password: password.value
        });

        console.log('Response data:', response.data);

        const { token } = response.data;
        
        localStorage.setItem('token', token); // Guardar el token para usarlo en /profile

        alert('Inicio de sesión exitoso');
        router.push('/profile'); // Redirigir al perfil del usuario
      } catch (error) {
        console.error('Error details:', error);

        if (error.response) {
          alert(`Error al iniciar sesión: ${error.response.data.message || 'Verifica tus credenciales.'}`);
        } else if (error.request) {
          alert('No se pudo conectar al servidor. Verifica si el servidor está funcionando.');
        } else {
          alert(`Error en la solicitud: ${error.message}`);
        }
      }
    };

    return { email, password, handleLogin }; // Retorna las variables y la función para ser usadas en el template
  }
};
</script>

 
<style>
@media (min-width: 1024px) {
  .login {
    min-height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
  }
}
</style>
