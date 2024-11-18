import { createStore } from 'vuex';
import auth from './modules/auth';
import reservas from './modules/reservas';

const store = createStore({
  modules: {
    auth,
    reservas,
  },
});

export default store;






