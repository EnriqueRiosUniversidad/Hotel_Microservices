<template>
  <div class="admin-reservas">
    <h1>Administración de Reservas</h1>

    <div class="reservas-lista">
      <ReservaListaAdmin :reservas="reservas" :totalPages="totalPages" :paginaActual="currentPage"
        @cambiarEstado="actualizarReservaEstado" @paginaCambiada="cargarReservas" @ordenar="cambiarOrden" />
    </div>

    <div class="paginacion">
      <label for="pageSize">Tamaño de página:</label>
      <select id="pageSize" v-model="pageSize" @change="cargarReservas(0)">
        <option :value="5">5</option>
        <option :value="10">10</option>
        <option :value="20">20</option>
      </select>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import ReservaListaAdmin from '../components/ReservaListaAdmin.vue';

export default {
  components: {
    ReservaListaAdmin,
  },
  data() {
    return {
      currentPage: 0, // Página actual inicial es 0
      pageSize: 10,    // Numero de paginas que devuelve mi microservicio
      orden: {
        campo: 'fechaInicio',
        direccion: 'asc',
      },
    };
  },
  computed: {
    ...mapState('reservas', {
      reservas: (state) => state.reservas,
      totalPages: (state) => state.totalPages,
    }),
  },
  methods: {
    ...mapActions('reservas', ['obtenerTodasLasReservas', 'actualizarEstadoReserva']),

    cargarReservas(page = 0) {
      this.currentPage = page;
      this.obtenerTodasLasReservas({
        page: page,
        size: this.pageSize, // Utiliza el tamaño de página seleccionado
        sort: `${this.orden.campo},${this.orden.direccion}`,
      });
    },
    actualizarReservaEstado(id, estado) {
      this.actualizarEstadoReserva({ id, estado }).then(() => {
        this.cargarReservas(this.currentPage);
      });
    },
    cambiarOrden(campo) {
      // Si el campo de orden es el mismo, cambia la dirección; de lo contrario, establece ascendente
      if (this.orden.campo === campo) {
        this.orden.direccion = this.orden.direccion === 'asc' ? 'desc' : 'asc';
      } else {
        this.orden.campo = campo;
        this.orden.direccion = 'asc';
      }
      // Recargar las reservas con el nuevo orden
      this.cargarReservas(this.currentPage);
    },
  },
  created() {
    this.cargarReservas();
  },
};
</script>



<style scoped>
.admin-reservas {
  max-width: 90%; /* Cambia de 800px a 100% */
  margin: 0 auto;
  padding: 20px;
  background-color: #FFCF9D;
  color: #333;
  border-radius: 10px;
  font-family: Arial, sans-serif;
  overflow-x: auto; /* Para permitir scroll horizontal si es necesario */
}

h1 {
  text-align: center;
  font-size: 2rem;
  color: #DE8F5F;
  /* Título con un tono cálido */
  margin-bottom: 20px;
}

.reservas-lista {
  margin-bottom: 20px;  
}

.paginacion {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px;
  margin-top: 20px;
}

label {
  font-weight: bold;
}

select {
  padding: 5px 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
  background-color: #fff;
  color: #333;
}

select:focus {
  outline: none;
  border-color: #FFB38E;
  /* Resalta el selector al enfocarlo */
}

button {
  background-color: #28a745;
  /* Botón verde */
  color: white;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
}

button:hover {
  background-color: #218838;
  /* Verde más oscuro */
}
</style>