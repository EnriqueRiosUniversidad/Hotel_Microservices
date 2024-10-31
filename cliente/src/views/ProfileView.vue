<template>
  <div class="profile">
    <h1>Perfil del Usuario</h1>
    <div v-if="user">
      <p>ID: {{ user.id }}</p>
      <p>Nombre: {{ user.nombre }}</p>
      <p>Email: {{ user.email }}</p>
      <p>Rol: {{ user.rol.nombre }} - {{ user.rol.descripcion }}</p>
    </div>
    <div v-else>
      <p>Cargando perfil...</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import axios from 'axios';

export default {
  setup() {
    const user = ref(null);

    const fetchUserProfile = async () => {
      const token = localStorage.getItem('token');
      try {
        const response = await axios.get('http://localhost:8090/user/profile', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        user.value = response.data;
      } catch (error) {
        alert('Error, por favor inicia sesión primero');
      }
    };

    onMounted(() => {
      fetchUserProfile();
    });

    return {
      user,
    };
  },
};
</script>

<style>
@media (min-width: 1024px) {
  .profile {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
}
</style>
