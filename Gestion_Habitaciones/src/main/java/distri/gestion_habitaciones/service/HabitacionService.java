package distri.gestion_habitaciones.service;

import distri.beans.domain.Habitacion;
import distri.beans.dto.HabitacionDTO;
import distri.gestion_habitaciones.repository.HabitacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    @Autowired
    private ModelMapper modelMapper;


    /* 1. Crear una nueva habitación */
    public HabitacionDTO crearHabitacion(HabitacionDTO habitacionDTO) {

        Habitacion habitacion = modelMapper.map(habitacionDTO, Habitacion.class);
        habitacion.setDeleted(false);

        // Establecer el ID igual al número de la habitación
        habitacion.setId(habitacionDTO.getNumero().longValue());

        Habitacion savedHabitacion = habitacionRepository.save(habitacion);
        return modelMapper.map(savedHabitacion, HabitacionDTO.class);
    }


    /* 2. Obtener habitaciones por disponibilidad (no eliminadas) */
    public Page<HabitacionDTO> obtenerHabitacionesPorDisponibilidad(Boolean disponibilidad, Pageable pageable) {
        Page<Habitacion> habitacionesPage = habitacionRepository.findByDeletedFalseAndDisponibilidad(disponibilidad, pageable);
        return habitacionesPage.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
    }

    /* 3 Obtener habitación por ID o Número */
    public Optional<HabitacionDTO> obtenerHabitacionPorIdONumero(Long id, Long numero) {
        if (id != null) {
            // Buscar por ID si no es nulo
            Optional<Habitacion> habitacionOptional = habitacionRepository.findByIdAndDeletedFalse(id);
            return habitacionOptional.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
        } else if (numero != null) {
            // Buscar por número si ID es nulo
            Optional<Habitacion> habitacionOptional = habitacionRepository.findFirstByNumeroAndDeletedFalse(numero);
            return habitacionOptional.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
        }
        // Si ambos son nulos, retornar vacío
        return Optional.empty();
    }

    /* 4 Obtener habitaciones por rango de precios (no eliminadas) */
    public Page<HabitacionDTO> obtenerHabitacionesPorRangoDePrecios(BigDecimal precioMin, BigDecimal precioMax, Pageable pageable) {
        Page<Habitacion> habitacionesPage = habitacionRepository.findByDeletedFalseAndPrecioBetween(precioMin, precioMax, pageable);
        return habitacionesPage.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
    }

    /*  5 Obtener habitaciones por tipo (no eliminadas) */
    public Page<HabitacionDTO> obtenerHabitacionesPorTipo(String tipo, Pageable pageable) {
        Page<Habitacion> habitacionesPage = habitacionRepository.findByTipoContainingIgnoreCaseAndDeletedFalse(tipo, pageable);
        return habitacionesPage.map(habitacion -> modelMapper.map(habitacion, HabitacionDTO.class));
    }

    // Devolver la habitación actualizada como DTO si no encuentra retornar vacío
    public HabitacionDTO actualizarHabitacion(Long id, HabitacionDTO habitacionDTO) {


        Habitacion habitacionExistente = habitacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Habitacion no encontrado"));

        System.out.println(habitacionExistente);

            // Verificar cada campo del DTO y actualizar solo si no es null
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
            return modelMapper.map(habitacionGuardada, HabitacionDTO.class);

    }


    /* 7. Eliminar una habitación (eliminación suave) */
    public Optional<HabitacionDTO> eliminarHabitacion(Long id) {
        Optional<Habitacion> habitacionExistente = habitacionRepository.findByIdAndDeletedFalse(id);

        if (habitacionExistente.isPresent()) {
            Habitacion habitacion = habitacionExistente.get();
            habitacion.setDeleted(true);
            Habitacion updatedHabitacion = habitacionRepository.save(habitacion);
            return Optional.of(modelMapper.map(updatedHabitacion, HabitacionDTO.class));
        }

        return Optional.empty();
    }

    /* 8. Restaurar una habitación eliminada */
    public Optional<HabitacionDTO> restaurarHabitacion(Long id) {
        Optional<Habitacion> habitacionEliminada = habitacionRepository.findById(id);

        if (habitacionEliminada.isPresent()) {
            Habitacion habitacion = habitacionEliminada.get();
            habitacion.setDeleted(false);
            Habitacion restoredHabitacion = habitacionRepository.save(habitacion);
            return Optional.of(modelMapper.map(restoredHabitacion, HabitacionDTO.class));
        }

        return Optional.empty();
    }
}
