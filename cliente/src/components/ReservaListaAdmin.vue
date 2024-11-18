<template>
  <div class="tabla-centro">
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Usuario</th>
          <th @click="ordenarPor('fechaCreacion')">Fecha Creación</th>
          <th @click="ordenarPor('fechaInicio')">Fecha Inicio</th>
          <th @click="ordenarPor('fechaFin')">Fecha Fin</th>
          <th @click="ordenarPor('total')">Total</th>
          <th @click="ordenarPor('estado')">Estado</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="reserva in reservas" :key="reserva.id">
          <td>{{ reserva.id }}</td>
          <td>{{ reserva.usuario.nombre }} ({{ reserva.usuario.email }})</td>
          <td>{{ reserva.fechaCreacion }}</td>
          <td>{{ reserva.fechaInicio }}</td>
          <td>{{ reserva.fechaFin }}</td>
          <td>{{ reserva.total }}</td>
          <td>{{ reserva.estado }}</td>
          <td>
            <select v-model="reserva.estado" @change="cambiarEstado(reserva.id, reserva.estado)">
              <option value="PENDIENTE">PENDIENTE</option>
              <option value="CONFIRMADA">CONFIRMADA</option>
              <option value="CANCELADA">CANCELADA</option>
            </select>
          </td>
        </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button @click="cambiarPagina(0)" :disabled="paginaActual === 0">&laquo;</button>
      <button @click="cambiarPagina(paginaActual - 1)" :disabled="paginaActual === 0">&lt;</button>

      <span v-for="page in paginasMostradas" :key="page">
        <button @click="cambiarPagina(page)" :class="{ active: paginaActual === page }">{{ page + 1 }}</button>
      </span>

      <button @click="cambiarPagina(paginaActual + 1)" :disabled="paginaActual === totalPages - 1">&gt;</button>
      <button @click="cambiarPagina(totalPages - 1)" :disabled="paginaActual === totalPages - 1">&raquo;</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    reservas: Array,
    totalPages: Number,
    paginaActual: Number,
  },
  emits: ['cambiarEstado', 'paginaCambiada', 'ordenar'],
  computed: {
    paginasMostradas() {
      const maxPaginasVisibles = 5;
      let startPage = Math.max(0, this.paginaActual - Math.floor(maxPaginasVisibles / 2));
      let endPage = Math.min(this.totalPages - 1, startPage + maxPaginasVisibles - 1);

      if (endPage - startPage + 1 < maxPaginasVisibles) {
        startPage = Math.max(0, endPage - maxPaginasVisibles + 1);
      }

      const pages = [];
      for (let i = startPage; i <= endPage; i++) {
        pages.push(i);
      }
      return pages;
    }
  },
  methods: {


    cambiarEstado(id, estado) {
      this.$emit('cambiarEstado', id, estado);
    },
    cambiarPagina(page) {
      if (page >= 0 && page < this.totalPages) {
        this.$emit('paginaCambiada', page);
      }
    },
    ordenarPor(campo) {
      this.$emit('ordenar', campo);
    }
  },
};
</script>

<style scoped>
.tabla-centro {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

table {
  border-collapse: collapse;
  width: 80%;
  margin-bottom: 20px;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
  cursor: pointer;
}

th {
  background-color: #f2f2f2;
}

button {
  margin: 5px;
  padding: 8px 12px;
  /* Incrementar el tamaño de los botones */
  font-size: 16px;
}

button.active {
  font-weight: bold;
}

button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
</style>