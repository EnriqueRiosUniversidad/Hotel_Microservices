import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import './assets/styles/colors.css';


// Configuración de VueToasted para Vue 3
import Toasted from '@meforma/vue-toaster';

const app = createApp(App);

app.use(router);
app.use(store);
app.use(Toasted, {
  duration: 5000,
});

app.mount('#app');