
<template>
    <div class="reserva-detalle">
      <h1>Detalle de Reserva #{{ reserva.id }}</h1>
      <p>Fecha Inicio: {{ reserva.fechaInicio }}</p>
      <p>Fecha Fin: {{ reserva.fechaFin }}</p>
      <p>Total: {{ reserva.total }}</p>
      <p>Estado: {{ reserva.estado }}</p>
      <h2>Detalles</h2>
      <ul>
        <li v-for="detalle in reserva.detalles" :key="detalle.id">
          Habitación ID: {{ detalle.habitacionId }} - Precio: {{ detalle.precio }}
        </li>
      </ul>
      <button @click="cancelarReserva">Cancelar Reserva</button>
      <button @click="volver">Volver</button>
    </div>
  </template>
  
  <script>
  import { mapState, mapActions } from 'vuex';
  
  export default {
    data() {
      return {
        id: this.$route.params.id,
      };
    },
    computed: {
      ...mapState('reservas', ['reservaActual']),
      reserva() {
        return this.reservaActual || {};
      },
    },
    methods: {
      ...mapActions('reservas', ['obtenerReservaPorId', 'cancelarReserva']),
      async cancelarReserva() {
        if (confirm('¿Estás seguro de cancelar esta reserva?')) {
          try {
            await this.cancelarReserva(this.id);
            alert('Reserva cancelada exitosamente');
            this.$router.push({ name: 'MisReservas' });
          } catch (error) {
            alert(error);
          }
        }
      },
      volver() {
        this.$router.push({ name: 'MisReservas' });
      },
    },
    created() {
      this.obtenerReservaPorId(this.id);
    },
  };
  </script>
  
  <style scoped>
/* Enrique: Acuerdate agregar estilos

Menos latino
FFB38E
FFCF9D
FFB26F
DE8F5F
mas latino

28a745
*/
  </style>
  