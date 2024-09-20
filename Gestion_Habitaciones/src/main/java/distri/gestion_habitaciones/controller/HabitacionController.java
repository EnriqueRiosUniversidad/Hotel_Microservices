package distri.gestion_habitaciones.controller;

import distri.beans.dto.HabitacionDTO;
import distri.gestion_habitaciones.service.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    /* 1. Crear una nueva habitación */
    @PostMapping
    public ResponseEntity<HabitacionDTO> crearHabitacion(@RequestBody HabitacionDTO habitacionDTO) {
        HabitacionDTO nuevaHabitacion = habitacionService.crearHabitacion(habitacionDTO);
        return new ResponseEntity<>(nuevaHabitacion, HttpStatus.CREATED);
    }

    /* 2. Obtener habitaciones por disponibilidad (no eliminadas) */
    @GetMapping("/disponibles")
    public ResponseEntity<Page<HabitacionDTO>> obtenerHabitacionesPorDisponibilidad(
            @RequestParam Boolean disponibilidad, Pageable pageable) {
        Page<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorDisponibilidad(disponibilidad, pageable);
        return new ResponseEntity<>(habitaciones, HttpStatus.OK);
    }

    /* 3. Obtener habitación por ID o Número */
    @GetMapping
    public ResponseEntity<HabitacionDTO> obtenerHabitacionPorIdONumero(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long numero) {
        Optional<HabitacionDTO> habitacion = habitacionService.obtenerHabitacionPorIdONumero(id, numero);
        return habitacion.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* 4. Obtener habitaciones por rango de precios */
    @GetMapping("/precio")
    public ResponseEntity<Page<HabitacionDTO>> obtenerHabitacionesPorRangoDePrecios(
            @RequestParam BigDecimal precioMin, @RequestParam BigDecimal precioMax, Pageable pageable) {
        Page<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorRangoDePrecios(precioMin, precioMax, pageable);
        return new ResponseEntity<>(habitaciones, HttpStatus.OK);
    }

    /* 5. Obtener habitaciones por tipo */
    @GetMapping("/tipo")
    public ResponseEntity<Page<HabitacionDTO>> obtenerHabitacionesPorTipo(
            @RequestParam String tipo, Pageable pageable) {
        Page<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorTipo(tipo, pageable);
        return new ResponseEntity<>(habitaciones, HttpStatus.OK);
    }

    /* 6. Actualizar una habitación */
    @PatchMapping("/{id}")
    public ResponseEntity<HabitacionDTO> actualizarHabitacion(
            @PathVariable Long id, @RequestBody HabitacionDTO habitacionDTO) {
        HabitacionDTO habitacionActualizada = habitacionService.actualizarHabitacion(id, habitacionDTO);
        return ResponseEntity.ok(habitacionActualizada);
    }

    /* 7. Eliminar una habitación (eliminación suave) */
    @DeleteMapping("/{id}")
    public ResponseEntity<HabitacionDTO> eliminarHabitacion(@PathVariable Long id) {
        Optional<HabitacionDTO> habitacionEliminada = habitacionService.eliminarHabitacion(id);
        return habitacionEliminada.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /* 8. Restaurar una habitación eliminada */
    @PutMapping("/restaurar/{id}")
    public ResponseEntity<HabitacionDTO> restaurarHabitacion(@PathVariable Long id) {
        Optional<HabitacionDTO> habitacionRestaurada = habitacionService.restaurarHabitacion(id);
        return habitacionRestaurada.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
