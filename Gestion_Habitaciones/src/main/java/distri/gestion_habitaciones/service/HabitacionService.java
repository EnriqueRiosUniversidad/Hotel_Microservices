package distri.gestion_habitaciones.service;

import distri.beans.domain.Habitacion;
import distri.beans.domain.Reserva;
import distri.beans.dto.HabitacionDTO;
import distri.gestion_habitaciones.repository.HabitacionRepository;
import distri.gestion_habitaciones.repository.ReservaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ModelMapper modelMapper;

    /* 1. Crear una nueva habitación */
    @CachePut(value = "usuarios", keyGenerator = "keyGenerator")
    public HabitacionDTO crearHabitacion(HabitacionDTO habitacionDTO) {
        log.info("Iniciando creación de una nueva habitación con número: {}", habitacionDTO.getNumero());
        try {
            Habitacion habitacion = modelMapper.map(habitacionDTO, Habitacion.class);
            habitacion.setDeleted(false);
            habitacion.setId(habitacionDTO.getNumero().longValue());

            Habitacion savedHabitacion = habitacionRepository.save(habitacion);
            log.info("Habitación creada exitosamente con ID: {}", savedHabitacion.getId());
            return modelMapper.map(savedHabitacion, HabitacionDTO.class);
        } catch (Exception e) {
            log.error("Error al crear la habitación: {}", e.getMessage(), e);
            throw new RuntimeException("Error al crear la habitación: " + e.getMessage());
        }
    }

    /* 2. Obtener habitaciones por disponibilidad (no eliminadas) */
    public Page<HabitacionDTO> obtenerHabitacionesPorDisponibilidad(Boolean disponibilidad, Pageable pageable) {
        log.info("Buscando habitaciones disponibles: {}", disponibilidad);
        try {
            Page<Habitacion> habitacionesPage = habitacionRepository.findByDeletedFalseAndDisponibilidad(disponibilidad, pageable);
            log.info("Se encontraron {} habitaciones disponibles", habitacionesPage.getTotalElements());
            return habitacionesPage.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
        } catch (Exception e) {
            log.error("Error al buscar habitaciones: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar habitaciones: " + e.getMessage());
        }
    }

    /* 3. Obtener habitación por ID */
    @Cacheable(value = "usuarios", keyGenerator = "keyGenerator")
    public Optional<HabitacionDTO> obtenerHabitacionPorId(Long id) {
        log.info("Buscando habitación con ID: {}", id);
        try {
            if (id != null) {
                Optional<Habitacion> habitacionOptional = habitacionRepository.findByIdAndDeletedFalse(id);
                if (habitacionOptional.isPresent()) {
                    log.info("Habitación encontrada con ID: {}", id);
                    return habitacionOptional.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
                } else {
                    log.warn("Habitación no encontrada con ID: {}", id);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al buscar habitación con ID: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar habitación: " + e.getMessage());
        }
    }

    /* 4. Obtener habitaciones por rango de precios (no eliminadas) */
    public Page<HabitacionDTO> obtenerHabitacionesPorRangoDePrecios(BigDecimal precioMin, BigDecimal precioMax, Pageable pageable) {
        log.info("Buscando habitaciones con precio entre {} y {}", precioMin, precioMax);
        try {
            Page<Habitacion> habitacionesPage = habitacionRepository.findByDeletedFalseAndPrecioBetween(precioMin, precioMax, pageable);
            log.info("Se encontraron {} habitaciones dentro del rango de precios", habitacionesPage.getTotalElements());
            return habitacionesPage.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
        } catch (Exception e) {
            log.error("Error al buscar habitaciones por rango de precios: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar habitaciones por rango de precios: " + e.getMessage());
        }
    }

    /* 5. Obtener habitaciones por tipo (no eliminadas) */
    public Page<HabitacionDTO> obtenerHabitacionesPorTipo(String tipo, Pageable pageable) {
        log.info("Buscando habitaciones del tipo: {}", tipo);
        try {
            Page<Habitacion> habitacionesPage = habitacionRepository.findByTipoContainingIgnoreCaseAndDeletedFalse(tipo, pageable);
            log.info("Se encontraron {} habitaciones del tipo {}", habitacionesPage.getTotalElements(), tipo);
            return habitacionesPage.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
        } catch (Exception e) {
            log.error("Error al buscar habitaciones por tipo: {}", e.getMessage(), e);
            throw new RuntimeException("Error al buscar habitaciones por tipo: " + e.getMessage());
        }
    }

    /* 6. Actualizar habitación */
    @CachePut(value = "usuarios", keyGenerator = "keyGenerator")
    public HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO habitacionDTO) {
        log.info("Iniciando actualización de la habitación con ID: {}", id);
        try {
            Habitacion habitacionExistente = habitacionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            if (habitacionDTO.getNumero() != null) {
                habitacionExistente.setNumero(habitacionDTO.getNumero());
            }
            if (habitacionDTO.getTipo() != null) {
                habitacionExistente.setTipo(habitacionDTO.getTipo());
            }
            if (habitacionDTO.getPrecio() != null) {
                habitacionExistente.setPrecio(habitacionDTO.getPrecio());
            }
            if (habitacionDTO.getDisponibilidad() != null) {
                habitacionExistente.setDisponibilidad(habitacionDTO.getDisponibilidad());
            }

            Habitacion habitacionGuardada = habitacionRepository.save(habitacionExistente);
            log.info("Habitación actualizada exitosamente con ID: {}", id);
            return modelMapper.map(habitacionGuardada, HabitacionDTO.class);
        } catch (Exception e) {
            log.error("Error al actualizar la habitación: {}", e.getMessage(), e);
            throw new RuntimeException("Error al actualizar la habitación: " + e.getMessage());
        }
    }

    /* 7. Eliminar una habitación (eliminación suave) */
    @CacheEvict(value = "usuarios", keyGenerator = "keyGenerator")
    public Optional<HabitacionDTO> eliminarHabitacion(Long id) {
        log.info("Iniciando eliminación de la habitación con ID: {}", id);
        try {
            Optional<Habitacion> habitacionExistente = habitacionRepository.findByIdAndDeletedFalse(id);
            if (habitacionExistente.isPresent()) {
                Habitacion habitacion = habitacionExistente.get();
                habitacion.setDeleted(true);
                Habitacion updatedHabitacion = habitacionRepository.save(habitacion);
                log.info("Habitación marcada como eliminada con ID: {}", id);
                return Optional.of(modelMapper.map(updatedHabitacion, HabitacionDTO.class));
            } else {
                log.warn("Habitación no encontrada con ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error al eliminar la habitación: {}", e.getMessage(), e);
            throw new RuntimeException("Error al eliminar la habitación: " + e.getMessage());
        }
    }

    /* 8. Restaurar una habitación eliminada */
    @CachePut(value = "usuarios", keyGenerator = "keyGenerator")
    public Optional<HabitacionDTO> restaurarHabitacion(Long id) {
        log.info("Iniciando restauración de la habitación con ID: {}", id);
        try {
            Optional<Habitacion> habitacionEliminada = habitacionRepository.findById(id);
            if (habitacionEliminada.isPresent()) {
                Habitacion habitacion = habitacionEliminada.get();
                habitacion.setDeleted(false);
                Habitacion restoredHabitacion = habitacionRepository.save(habitacion);
                log.info("Habitación restaurada exitosamente con ID: {}", id);
                return Optional.of(modelMapper.map(restoredHabitacion, HabitacionDTO.class));
            } else {
                log.warn("Habitación no encontrada con ID: {}", id);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error al restaurar la habitación: {}", e.getMessage(), e);
            throw new RuntimeException("Error al restaurar la habitación: " + e.getMessage());
        }
    }



/* 1- Buscar las habitaciones reservadas entre A y B fechas
    2- Optione todas las habitacines que NO estan en esa lista
    3- Retorna un conjunto de habitaciones
* */
    public List<HabitacionDTO> obtenerHabitacionesDisponibles(LocalDate fechaInicio, LocalDate fechaFin) {
        // Obtener IDs de habitaciones que están reservadas en el rango de fechas
        List<Long> habitacionesReservadasIds = reservaRepository.findHabitacionesReservadasEntreFechas(fechaInicio, fechaFin);

        // Obtener habitaciones que no están en la lista de habitaciones reservadas
        List<Habitacion> habitacionesDisponibles = habitacionRepository.findByIdNotIn(habitacionesReservadasIds);

        // Mapear a DTOs
        return habitacionesDisponibles.stream()
                .map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class))
                .collect(Collectors.toList());
    }


}
