package distri.gestion_reservas.service;


import distri.beans.domain.Detalle_Reserva;
import distri.beans.domain.Habitacion;
import distri.beans.domain.Reserva;
import distri.beans.domain.Usuario;
import distri.beans.dto.EstadoReserva;
import distri.beans.dto.HabitacionDTO;
import distri.beans.dto.ReservaDTO;
import distri.beans.AppConfig;

import distri.gestion_reservas.repository.HabitacionRepository;
import distri.gestion_reservas.repository.ReservaRepository;
import distri.gestion_reservas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {
    @Autowired
    private final ReservaRepository reservaRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final HabitacionRepository habitacionRepository;
    @Autowired
    private ModelMapper modelMapper;


    public ReservaDTO crearReserva(ReservaDTO reservaDTO, String emailUsuario) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            // Create the Reserva instance and set basic properties
            Reserva reserva = new Reserva();
            reserva.setUsuario(usuario);
            reserva.setFechaCreacion(LocalDateTime.now());
            reserva.setFechaInicio(reservaDTO.getFechaInicio());
            reserva.setFechaFin(reservaDTO.getFechaFin());
            reserva.setEstado(EstadoReserva.PENDIENTE);
            reserva.setDeleted(false);
            reserva.setTotal(BigDecimal.ZERO);
            // Save reserva to ensure it has an ID for setting in Detalle_Reserva instances
            Reserva nuevaReserva = reservaRepository.save(reserva);

            final Long reservaID = nuevaReserva.getId();

            // Map details and link them to the newly created reserva
            List<Detalle_Reserva> detalles = reservaDTO.getDetalles().stream().map(detalleDTO -> {
                Habitacion habitacion = habitacionRepository.findById(detalleDTO.getHabitacionId())
                        .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

                Detalle_Reserva detalleReserva = new Detalle_Reserva();
                detalleReserva.setReservaId(reservaID);
                detalleReserva.setHabitacionId(habitacion.getId());
                detalleReserva.setPrecio(habitacion.getPrecio());
                detalleReserva.setDeleted(false);
                return detalleReserva;
            }).collect(Collectors.toList());

            // Calculate the total price and set the details
            BigDecimal total = detalles.stream()
                    .map(Detalle_Reserva::getPrecio)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            nuevaReserva.setTotal(total);
            nuevaReserva.setDetalles(detalles);

            // Save the updated reserva with details
            nuevaReserva = reservaRepository.save(nuevaReserva);

            // Map to DTO and return
            return modelMapper.map(nuevaReserva, ReservaDTO.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



/*
RESERVA
    private Usuario usuario;
    private LocalDateTime fechaCreacion;
    private BigDecimal total;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoReserva estado;
    private List<Detalle_Reserva> detalles;*
DETALLES
    private Long reservaId;
    private Long habitacionId;
    private BigDecimal precio;
*/


    public Page<ReservaDTO> findAll(Pageable pageable) {

        try {
            Page<Reserva> reservas = reservaRepository.findAll(pageable);
            log.info("Cantidad de reservas obtenidas: {}", reservas.getTotalElements());

            Page<Reserva> reservasPage = reservaRepository.findAll(pageable);
            return reservasPage.map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }






    /*------------------------------------------------------------*/

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id)
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
    }

    public ReservaDTO create(ReservaDTO reservaDTO) {
        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        Reserva savedReserva = reservaRepository.save(reserva);
        return modelMapper.map(savedReserva, ReservaDTO.class);
    }

    public Optional<ReservaDTO> update(Long id, ReservaDTO reservaDTO) {
        return reservaRepository.findById(id)
                .map(existingReserva -> {
                    Reserva updatedReserva = modelMapper.map(reservaDTO, Reserva.class);
                    updatedReserva.setId(existingReserva.getId());
                    return modelMapper.map(reservaRepository.save(updatedReserva), ReservaDTO.class);
                });
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }
}
