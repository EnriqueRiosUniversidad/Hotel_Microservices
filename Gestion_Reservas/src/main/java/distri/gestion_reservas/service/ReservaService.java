package distri.gestion_reservas.service;


import distri.beans.domain.Detalle_Reserva;
import distri.beans.domain.Habitacion;
import distri.beans.domain.Reserva;
import distri.beans.domain.Usuario;
import distri.beans.dto.Detalle_ReservaDTO;
import distri.beans.dto.EstadoReserva;

import distri.beans.dto.ReservaDTO;


import distri.gestion_reservas.repository.DetalleReservaRepository;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final DetalleReservaRepository detalleReservaRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final HabitacionRepository habitacionRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Transactional(propagation = Propagation.REQUIRED
            , rollbackFor = Exception.class)
    public ReservaDTO crearReserva(ReservaDTO reservaDTO, String emailUsuario) {
        try {
            Usuario usuario = obtenerUsuarioPorEmail(emailUsuario);
            Reserva nuevaReserva = inicializarReserva(usuario, reservaDTO);

            List<Detalle_Reserva> detalles = crearDetallesReserva(reservaDTO.getDetalles(), nuevaReserva.getId());
            BigDecimal total = calcularTotalReserva(detalles);

            nuevaReserva.setDetalles(detalles);
            nuevaReserva.setTotal(total);

            reservaRepository.save(nuevaReserva); // Se guarda solo una vez

            return modelMapper.map(nuevaReserva, ReservaDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al crear la reserva", e);
        }
    }

    // Métodos privados para modularizar la lógica
    private Usuario obtenerUsuarioPorEmail(String emailUsuario) {
        return usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    private Reserva inicializarReserva(Usuario usuario, ReservaDTO reservaDTO) {
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setFechaCreacion(LocalDateTime.now());
        reserva.setFechaInicio(reservaDTO.getFechaInicio());
        reserva.setFechaFin(reservaDTO.getFechaFin());
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setDeleted(false);
        reserva.setTotal(BigDecimal.ZERO); // Inicialización del total
        return reservaRepository.save(reserva); // Se guarda una vez para obtener el ID
    }

    private List<Detalle_Reserva> crearDetallesReserva(List<Detalle_ReservaDTO> detallesDTO, Long reservaID) {
        return detallesDTO.stream().map(detalleDTO -> {
            Habitacion habitacion = habitacionRepository.findById(detalleDTO.getHabitacionId())
                    .orElseThrow(() -> new RuntimeException("Habitación no encontrada"));

            Detalle_Reserva detalleReserva = new Detalle_Reserva();
            detalleReserva.setReservaId(reservaID);
            detalleReserva.setHabitacionId(habitacion.getId());
            detalleReserva.setPrecio(habitacion.getPrecio());
            detalleReserva.setDeleted(false);
            return detalleReserva;
        }).collect(Collectors.toList());
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


    //--------2 /mis-reservas --------//
    // Obtener reservas del usuario actual

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Page<ReservaDTO> obtenerReservasDelUsuario(Pageable pageable, String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Page<Reserva> reservas = reservaRepository.findByUsuarioId(pageable, usuario.getId());

        return reservas.map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
    }


    //----------3 /reservas/{id} Obtiene por el usuario actual una reserva espesifica
    //Si es ADMIN puede ver igual la reserva
    // Obtener una reserva por ID y usuario

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public ReservaDTO obtenerReservaPorIdYUsuario(Long id, String emailUsuario, String rolUsuario) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!reserva.getUsuario().getEmail().equals(emailUsuario) && !rolUsuario.equals("ROLE_ADMIN")) {
            throw new RuntimeException("No tiene permiso para ver esta reserva");
        }

        return modelMapper.map(reserva, ReservaDTO.class);
    }

    //    ---------- 4   Actualizar una reserva

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ReservaDTO actualizarReserva(Long reserva_Id, ReservaDTO reservaDTO, String emailUsuario) {
        Reserva reserva = reservaRepository.findById(reserva_Id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Verificar que el usuario tenga permiso para actualizar esta reserva
        if (!reserva.getUsuario().getEmail().equals(emailUsuario)) {
            throw new RuntimeException("No tiene permiso para actualizar esta reserva");
        }

        // Verificar que la reserva aún no ha comenzado
        if (reserva.getFechaInicio().isBefore(LocalDate.now())) {
            throw new RuntimeException("No puede actualizar una reserva que ya ha comenzado");
        }

        // Usar las fechas proporcionadas o, si no están presentes, mantener las actuales
        LocalDate nuevaFechaInicio = reservaDTO.getFechaInicio() != null ? reservaDTO.getFechaInicio() : reserva.getFechaInicio();
        LocalDate nuevaFechaFin = reservaDTO.getFechaFin() != null ? reservaDTO.getFechaFin() : reserva.getFechaFin();

        // Verificar si los detalles están presentes en el JSON
        if (reservaDTO.getDetalles() != null && !reservaDTO.getDetalles().isEmpty()) {
            // Validar que cada habitación en los nuevos detalles existe
            for (Detalle_ReservaDTO detalleDTO : reservaDTO.getDetalles()) {
                Long habitacionId = detalleDTO.getHabitacionId();
                if (!habitacionRepository.existsById(habitacionId)) {
                    throw new RuntimeException("Habitación con ID " + habitacionId + " no encontrada");
                }
            }

            // Eliminar todos los detalles de la reserva en una sola consulta
            detalleReservaRepository.deleteByReservaId(reserva.getId());

            List<Detalle_Reserva> nuevosDetalles = crearDetallesReserva(reservaDTO.getDetalles(), reserva_Id);

            reserva.getDetalles().addAll(nuevosDetalles);
        }

        // Actualizar las fechas de inicio y fin de la reserva
        reserva.setFechaInicio(nuevaFechaInicio);
        reserva.setFechaFin(nuevaFechaFin);

        // Recalcular el total de la reserva con los nuevos detalles y fechas
        BigDecimal nuevoTotal = calcularTotalReserva(reserva.getDetalles(), nuevaFechaInicio, nuevaFechaFin);
        reserva.setTotal(nuevoTotal);

        Reserva reservaActualizada = reservaRepository.save(reserva);
        return modelMapper.map(reservaActualizada, ReservaDTO.class);
    }

    //    ------------5  Cancelar una reserva (antes de la fecha de inicio)
    // Cancelar una reserva (USER / ADMIN)

    @Transactional(propagation = Propagation.REQUIRED
            , rollbackFor = Exception.class)
    public void cancelarReserva(Long id, String emailUsuario, String rolUsuario) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        if (!reserva.getUsuario().getEmail().equals(emailUsuario) && !rolUsuario.equals("ROLE_ADMIN")) {
            throw new RuntimeException("No tiene permiso para cancelar esta reserva");
        }

        // Verificar que la reserva aún no ha comenzado
        if (reserva.getFechaInicio().isBefore(LocalDateTime.now().toLocalDate())) {
            throw new RuntimeException("No puede cancelar una reserva que ya ha comenzado");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        reservaRepository.save(reserva);// Cancelar una reserva


    }

    // -----------------METODOS ADMIN ---------------
    // 6 OBTENER TODAS LAS RESERVAS
    @Transactional(propagation = Propagation.SUPPORTS
            , rollbackFor = Exception.class)
    public Page<ReservaDTO> findAll(Pageable pageable) {

        try {
            //Page<Reserva> reservas = reservaRepository.findAll(pageable);
            Page<Reserva> reservasPage = reservaRepository.findAll(pageable);
            log.info("Cantidad de reservas obtenidas: {}", reservasPage.getTotalElements());

            return reservasPage.map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // 7  Obtener reservas por usuario (ROLE_ADMIN)

    public Page<ReservaDTO> obtenerReservasPorUsuario(Long usuarioId, Pageable pageable) {
        Page<Reserva> reservas = reservaRepository.findByUsuarioId(pageable, usuarioId);
        return reservas.map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
    }

    // 8 Obtener reservas por habitacion
    @Transactional(propagation = Propagation.SUPPORTS
            , rollbackFor = Exception.class)
    public Page<ReservaDTO> obtenerReservasPorHabitacion(Long habitacionId, Pageable pageable) {
        Page<Reserva> reservas = reservaRepository.findByHabitacionId(habitacionId, pageable);

        return reservas.map(reserva -> modelMapper.map(reserva, ReservaDTO.class));

    }


    // 9   Actualizar el estado de una reserva (ROLE_ADMIN)
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class)
    public void actualizarEstadoReserva(Long id, EstadoReserva estado) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setEstado(estado);
        reservaRepository.save(reserva);
        return ;
        //Reserva reservaActualizada =
        //return reservaActualizada;
       // return modelMapper.map(reservaActualizada, ReservaDTO.class);
    }


    /*---------Codigo Utilitario Begin*/
    private BigDecimal calcularTotalReserva(List<Detalle_Reserva> detalles) {
        return detalles.stream()
                .map(Detalle_Reserva::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /*  La politica del hotel es que, el dia donde el huesped se
        retira es dia de limpieza y ese dia no se puede asignar la habitacion.
    * */
    private BigDecimal calcularTotalReserva(List<Detalle_Reserva> detalles, LocalDate fechaInicio, LocalDate fechaFin) {
        long dias = java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        return detalles.stream()
                .map(detalle -> detalle.getPrecio().multiply(BigDecimal.valueOf(dias)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    //---------Codigo Utilitario End-------//


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
