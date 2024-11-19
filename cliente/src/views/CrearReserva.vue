<!-- 
<template>
    <div class="crear-reserva">
      <h1>Registrar Nueva Reserva</h1>
      <form @submit.prevent="enviarReserva">
        <div class="form-group">
          <label for="fechaInicio">Fecha de Inicio:</label>
          <input
            type="date"
            id="fechaInicio"
            v-model="reserva.fechaInicio"
            required
          />
        </div>
        <div class="form-group">
          <label for="fechaFin">Fecha de Fin:</label>
          <input
            type="date"
            id="fechaFin"
            v-model="reserva.fechaFin"
            required
          />
        </div>
  
        <div class="habitaciones">
          <h3>Habitaciones</h3>
          <div
            v-for="(habitacion, index) in reserva.detalles"
            :key="index"
            class="form-group"
          >
            <label>Habitación ID:</label>
            <input
              type="number"
              v-model="habitacion.habitacionId"
              required
              placeholder="Ingrese el ID de la habitación"
            />
            <button type="button" @click="eliminarHabitacion(index)">Eliminar</button>
          </div>
          <button type="button" @click="agregarHabitacion">Agregar Habitación</button>
        </div>
  
        <button type="submit">Crear Reserva</button>
      </form>
    </div>
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  
  export default {
    data() {
      return {
        reserva: {
          fechaInicio: '',
          fechaFin: '',
          detalles: [
            { habitacionId: null },
          ],
        },
      };
    },
    methods: {
      ...mapActions('reservas', ['crearReserva']),
      agregarHabitacion() {
        this.reserva.detalles.push({ habitacionId: null });
      },
      eliminarHabitacion(index) {
        this.reserva.detalles.splice(index, 1);
      },
      async enviarReserva() {
        try {
          await this.crearReserva(this.reserva);
          alert('Reserva creada exitosamente');
          this.$router.push({ name: 'MisReservas' });
        } catch (error) {
          alert(error || 'Error al crear la reserva');
        }
      },
    },
  };
  </script>
  
-->

<template>
  <div class="crear-reserva">
    <h1>Registrar Nueva Reserva</h1>
    <form @submit.prevent="enviarReserva">
      <div class="form-group">
        <label for="fechaInicio">Fecha de Inicio:</label>
        <input type="date" id="fechaInicio" v-model="reserva.fechaInicio" required />
      </div>
      <div class="form-group">
        <label for="fechaFin">Fecha de Fin:</label>
        <input type="date" id="fechaFin" v-model="reserva.fechaFin" required />
      </div>

      <div class="habitaciones">
        <h3>Habitaciones Disponibles</h3>
        <div v-for="habitacion in habitacionesDisponibles" :key="habitacion.id" class="form-group">
          <label>{{ habitacion.numero }} - ${{ habitacion.precio }} por noche</label>
          <input type="checkbox" :id="'habitacion-' + habitacion.id" v-model="habitacionesSeleccionadas"
            :value="habitacion" @change="calcularTotalReserva" />
        </div>
      </div>

      <div>
        <label>Total Reserva: ${{ totalReserva }}</label>
      </div>

      <button type="submit">Crear Reserva</button>
    </form>
  </div>
</template>

<script>
import { mapActions } from 'vuex';
import http from '../http';

export default {
  data() {
    return {
      reserva: {
        fechaInicio: '',
        fechaFin: '',
        detalles: [],
      },
      habitacionesDisponibles: [], // Lista de habitaciones disponibles
      habitacionesSeleccionadas: [], // Habitaciones seleccionadas
      totalReserva: 0,
    };
  },
  methods: {
    ...mapActions('reservas', ['crearReserva']),

    async obtenerHabitacionesDisponibles() {
  if (this.reserva.fechaInicio && this.reserva.fechaFin) {
    try {
      const response = await http.get('/habitaciones/disponibles', {
        params: {
          fechaInicio: this.reserva.fechaInicio,
          fechaFin: this.reserva.fechaFin,
        },
      });
      this.habitacionesDisponibles = response.data.filter(
        (habitacion) => habitacion.disponibilidad
      );
    } catch (error) {
      console.error('Error al obtener habitaciones disponibles:', error);
    }
  } else {
    this.habitacionesDisponibles = [];
  }
},

    calcularTotalReserva() {
      const noches = this.calcularNoches();
      this.totalReserva = this.habitacionesSeleccionadas.reduce((total, habitacion) => {
        return total + (habitacion.precio * noches);
      }, 0);
    },

    calcularNoches() {
      const fechaInicio = new Date(this.reserva.fechaInicio);
      const fechaFin = new Date(this.reserva.fechaFin);
      const diffTime = Math.abs(fechaFin - fechaInicio);
      return Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    },

    async enviarReserva() {
      try {
        this.reserva.detalles = this.habitacionesSeleccionadas.map(habitacion => ({
          habitacionId: habitacion.id,
          precio: habitacion.precio,
        }));
        await this.crearReserva(this.reserva);
        alert('Reserva creada exitosamente');
        this.$router.push({ name: 'MisReservas' });
      } catch (error) {
        alert(error || 'Error al crear la reserva');
      }
    },
  },
  watch: {
    'reserva.fechaInicio': 'obtenerHabitacionesDisponibles',
    'reserva.fechaFin': 'obtenerHabitacionesDisponibles',
  },
};
</script>



<style scoped>
.crear-reserva {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: var(--color-secondary);
  color: #fff;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 15px;
}

button {
  background-color: var(--color-primary);
  border: none;
  color: #fff;
  padding: 10px;
  cursor: pointer;
  margin-top: 10px;
}

button:hover {
  background-color: var(--color-tertiary);
}
</style>