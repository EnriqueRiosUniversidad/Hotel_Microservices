<template>
  <div class="user-profile">
    <h1>Perfil de Usuario</h1>
    <div v-if="profile">
      <p><strong>Nombre:</strong> {{ profile.nombre }}</p>
      <p><strong>Email:</strong> {{ profile.email }}</p>
      <p><strong>Rol:</strong> {{ profile.rol.nombre }}</p>
      <p><strong>Descripción del Rol:</strong> {{ profile.rol.descripcion }}</p>
    </div>
    <div v-else>
      <p>Cargando perfil...</p>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';

export default {
  name: 'UserPerfil',
  computed: {
    ...mapGetters('auth', ['userProfile']),
    profile() {
      return this.userProfile;
    },
  },
  methods: {
    ...mapActions('auth', ['fetchUserProfile']),
  },
  mounted() {
    this.fetchUserProfile();
  },
};
</script>

<style scoped>
.user-profile {
  padding: 20px;
  background-color: var(--color-secondary);
  border-radius: 8px;
}
</style>
