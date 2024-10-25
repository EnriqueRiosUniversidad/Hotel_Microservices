package distri.gestion_habitaciones.controller;

import distri.beans.dto.HabitacionDTO;
import distri.gestion_habitaciones.service.HabitacionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
@Slf4j


@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    /* 1. Crear una nueva habitación */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<HabitacionDTO> crearHabitacion(@RequestBody HabitacionDTO habitacionDTO) {
        try {
            log.info("Creando nueva habitacion: {}", habitacionDTO);
            HabitacionDTO nuevaHabitacion = habitacionService.crearHabitacion(habitacionDTO);
            log.info("Habitacion creada exitosamente: {}", nuevaHabitacion);
            return new ResponseEntity<>(nuevaHabitacion, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear habitacion: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 2. Obtener habitaciones por disponibilidad (no eliminadas) */
    @GetMapping("/disponibles")
    public ResponseEntity<Page<HabitacionDTO>> obtenerHabitacionesPorDisponibilidad(
            @RequestParam Boolean disponibilidad, Pageable pageable) {
        try {
            log.info("Obteniendo habitaciones con disponibilidad: {}", disponibilidad);
            Page<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorDisponibilidad(disponibilidad, pageable);
            return new ResponseEntity<>(habitaciones, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones por disponibilidad: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 3. Obtener habitación por ID */
    @GetMapping
    public ResponseEntity<HabitacionDTO> obtenerHabitacionPorId(@RequestParam Long id) {
        try {
            log.info("Obteniendo habitacion con ID: {}", id);
            Optional<HabitacionDTO> habitacion = habitacionService.obtenerHabitacionPorId(id);
            return habitacion.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error al obtener habitacion con ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 4. Obtener habitaciones por rango de precios */
    @GetMapping("/precio")
    public ResponseEntity<Page<HabitacionDTO>> obtenerHabitacionesPorRangoDePrecios(
            @RequestParam BigDecimal precioMin, @RequestParam BigDecimal precioMax, Pageable pageable) {
        try {
            log.info("Obteniendo habitaciones entre precios {} y {}", precioMin, precioMax);
            Page<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorRangoDePrecios(precioMin, precioMax, pageable);
            return new ResponseEntity<>(habitaciones, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones por rango de precios: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 5. Obtener habitaciones por tipo */
    @GetMapping("/tipo")
    public ResponseEntity<Page<HabitacionDTO>> obtenerHabitacionesPorTipo(
            @RequestParam String tipo, Pageable pageable) {
        try {
            log.info("Obteniendo habitaciones por tipo: {}", tipo);
            Page<HabitacionDTO> habitaciones = habitacionService.obtenerHabitacionesPorTipo(tipo, pageable);
            return new ResponseEntity<>(habitaciones, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error al obtener habitaciones por tipo: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 6. Actualizar una habitación */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HabitacionDTO> actualizarHabitacion(
            @PathVariable Long id, @RequestBody HabitacionDTO habitacionDTO) {
        try {
            log.info("Actualizando habitacion con ID: {}", id);
            HabitacionDTO habitacionActualizada = habitacionService.actualizarHabitacion(id, habitacionDTO);
            log.info("Habitacion actualizada exitosamente: {}", habitacionActualizada);
            return ResponseEntity.ok(habitacionActualizada);
        } catch (Exception e) {
            log.error("Error al actualizar habitacion con ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 7. Eliminar una habitación (eliminación suave) */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HabitacionDTO> eliminarHabitacion(@PathVariable Long id) {
        try {
            log.info("Eliminando habitacion con ID: {}", id);
            Optional<HabitacionDTO> habitacionEliminada = habitacionService.eliminarHabitacion(id);
            return habitacionEliminada.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error al eliminar habitacion con ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 8. Restaurar una habitación eliminada */
    @PutMapping("/restaurar/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HabitacionDTO> restaurarHabitacion(@PathVariable Long id) {
        try {
            log.info("Restaurando habitacion con ID: {}", id);
            Optional<HabitacionDTO> habitacionRestaurada = habitacionService.restaurarHabitacion(id);
            return habitacionRestaurada.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            log.error("Error al restaurar habitacion con ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
