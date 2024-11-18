<template>
  <div>
    <h1>Administración de Reservas</h1>
    <ReservaListaAdmin :reservas="reservas" :totalPages="totalPages" :paginaActual="currentPage"
      @cambiarEstado="actualizarReservaEstado" @paginaCambiada="cargarReservas" @ordenar="cambiarOrden" />
      <div>
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