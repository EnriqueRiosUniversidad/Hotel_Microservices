
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
      <button @click="handleCancelarReserva">Cancelar Reserva</button>
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
      async handleCancelarReserva() {
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
  .reserva-detalle {
    max-width: 600px;
    margin: 0 auto;
    padding: 20px;
    background-color: #FFCF9D; /* Fondo con un tono latino */
    color: #fff;
    border-radius: 8px;
    font-family: Arial, sans-serif;
  }
  
  h1 {
    font-size: 1.8rem;
    color: #DE8F5F; /* Título con un tono más oscuro */
    margin-bottom: 20px;
  }
  
  p {
    font-size: 1rem;
    margin: 10px 0;
    color: #333; /* Texto oscuro para buen contraste */
  }
  
  h2 {
    font-size: 1.4rem;
    margin-top: 20px;
    color: #FFB38E;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin: 5px 0;
    background-color: #FFB26F;
    padding: 10px;
    border-radius: 5px;
  }
  
  button {
    background-color: #28a745; /* Verde para acciones */
    color: white;
    border: none;
    padding: 10px 15px;
    margin-top: 10px;
    cursor: pointer;
    border-radius: 5px;
    font-size: 1rem;
  }
  
  button:hover {
    background-color: #218838; /* Verde más oscuro al pasar el cursor */
  }
  
  button:nth-child(2) {
    background-color: #FFB26F; /* Botón "Volver" con color diferente */
    margin-left: 10px;
  }
  
  button:nth-child(2):hover {
    background-color: #FFB38E;
  }
  </style>
  
  