<template>
  <nav class="navbar">
    <ul class="navbar-links">
      <li><router-link to="/">Home</router-link></li>
      <li><router-link to="/profile">Profile</router-link></li>
      <li><router-link to="/reservas">Reservas</router-link></li>
      <li><router-link to="/buscar-habitacion">Buscar Habitación</router-link></li>
    </ul>
    <ul class="navbar-auth">
      <li><router-link to="/login">Log-In</router-link></li>
      <li><router-link to="/signup">Sign-Up</router-link></li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "Navbar",
};
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  padding: 1rem;
  background-color: #333;
  color: white;
}

.navbar-links,
.navbar-auth {
  display: flex;
  list-style: none;
  gap: 1rem;
}

.navbar a {
  color: white;
  text-decoration: none;
}

.navbar a:hover {
  text-decoration: underline;
}
</style>
