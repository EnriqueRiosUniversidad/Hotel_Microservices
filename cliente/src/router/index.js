  // src/router/index.js
  import { createRouter, createWebHistory } from 'vue-router';
  import store from '../store';
  import Home from '../views/HomeView.vue';
  import UserLogin from '../views/UserLogin.vue';
  import UserRegister from '../views/UserRegister.vue';
  import MisReservas from '../views/MisReservas.vue';
  import ReservaDetalle from '../views/ReservaDetalle.vue';
  import CrearReserva from '../views/CrearReserva.vue';
  import UserPerfil from '../views/UserPerfil.vue'
  import TodasLasReservas from '@/views/TodasLasReservas.vue';
  const routes = [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/login',
      name: 'UserLogin',
      component: UserLogin,
      meta: { guest: true },
    },
    {
      path: '/register',
      name: 'Register',
      component: UserRegister,
      meta: { guest: true },
    },
    {
      path: '/mis-reservas',
      name: 'MisReservas',
      component: MisReservas,
      meta: { requiresAuth: true  },
    },
    {
      path: '/reservas/:id',
      name: 'ReservaDetalle',
      component: ReservaDetalle,
      meta: { requiresAuth: true },
    },
    {
      path: '/reservas/nueva',
      name: 'CrearReserva',
      component: CrearReserva,
      meta: { requiresAuth: true },
    },
    {
      path: '/perfil',
      name: 'UserPerfil',
      component: UserPerfil,
      meta: { requiresAuth: true },
    },
    //Parte admin
    {
      path: '/ReservasAdmin',
      name: 'ReservasAdmin',
      component: TodasLasReservas,
      meta: { requiresAuth: true },
    },
  ];

  const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes,
  });

  // Protección de rutas
  router.beforeEach((to, from, next) => {
    const isAuthenticated = store.getters['auth/isAuthenticated'];

    if (to.matched.some((record) => record.meta.requiresAuth)) {
      if (!isAuthenticated) {
        next({ name: 'UserLogin' });
      } else {
        next();
      }
    } else if (to.matched.some((record) => record.meta.guest)) {
      if (isAuthenticated) {
        next({ name: 'MisReservas' });
      } else {
        next();
      }
    } else {
      next();
    }
  });

  export default router;
