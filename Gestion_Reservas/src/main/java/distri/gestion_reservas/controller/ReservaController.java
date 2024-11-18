package distri.gestion_reservas.controller;


import distri.beans.domain.Reserva;
import distri.beans.dto.EstadoReserva;
import distri.beans.dto.ReservaDTO;
import distri.gestion_reservas.service.ReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
@Slf4j
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Value("${pagination.default-page-number}")
    private int defaultPageNumber;

    @Value("${pagination.default-page-size}")
    private int defaultPageSize;

    // 1. Crear una nueva reserva (ROLE_USER, ROLE_ADMIN)
    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO, Authentication authentication) {
        try {

            String emailUsuario = authentication.getName();

            ReservaDTO nuevaReserva = reservaService.crearReserva(reservaDTO, emailUsuario);

            log.info("Reserva creada exitosamente: {}", nuevaReserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
        } catch (Exception e) {
            log.error("Error al crear la reserva: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // 2. Obtener reservas del usuario actual (ROLE_USER, ROLE_ADMIN)
    @GetMapping("/mis-reservas")
    public ResponseEntity<Page<ReservaDTO>> obtenerReservasDelUsuario(
            Authentication authentication,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
        // Si no se proporcionan parámetros en la solicitud HTTP, usar los valores por defecto
        int pageNumber = (page != null) ? page : defaultPageNumber;
        int pageSize = (size != null) ? size : defaultPageSize;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        try {
            String emailUsuario = authentication.getName();
            Page<ReservaDTO> reservas = reservaService.obtenerReservasDelUsuario(pageable, emailUsuario);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            log.error("Error al obtener reservas del usuario: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


//----------------------IMPLEMENTAR CACHEABLE-------------------------

    // 3. Obtener una reserva específica del usuario actual (ROLE_USER, ROLE_ADMIN)
    //Si es un ROLE_USER solo ve sus reservas
    //Si es un ROLE_ADMIN ve cualquier reserva a traves del id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReservaPorId(@PathVariable Long id, Authentication authentication) {
        try {
            String emailUsuario = authentication.getName();
            String rolUsuario = authentication.getAuthorities().stream().findFirst().orElseThrow(null).getAuthority();
            ReservaDTO reserva = reservaService.obtenerReservaPorIdYUsuario(id, emailUsuario, rolUsuario);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            log.error("Error al obtener la reserva con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //----------------------IMPLEMENTAR CACHEABLE-------------------------
    // 4. Actualizar una reserva --ANTES DE SU FECHA de inicio--
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarReserva(@PathVariable Long id, @RequestBody ReservaDTO reservaDTO, Authentication authentication) {
        try {
            String emailUsuario = authentication.getName();
            ReservaDTO reservaActualizada = reservaService.actualizarReserva(id, reservaDTO, emailUsuario);
            return ResponseEntity.ok(reservaActualizada);
        } catch (Exception e) {
            log.error("Error al actualizar la reserva con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // ---------------- Eliinar de cache -------------------
    // 5. Cancelar una reserva (ROLE_USER, ROLE_ADMIN)
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id, Authentication authentication) {
        try {
            String emailUsuario = authentication.getName();
            String rolUsuario = authentication.getAuthorities().stream().findFirst().orElseThrow(null).getAuthority();
            reservaService.cancelarReserva(id, emailUsuario, rolUsuario);
            return ResponseEntity.ok("Reserva cancelada exitosamente");
        } catch (Exception e) {
            log.error("Error al cancelar la reserva con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // -------- Endpoints para administradores (ROLE_ADMIN) --------4
    //6  Obtener todas las reservas
    //
    @GetMapping
    public ResponseEntity<Page<ReservaDTO>> obtenerReservas(Pageable pageable) {
        try {
            Page<ReservaDTO> reservaDTOPage = reservaService.findAll(pageable);
            log.info(" Obteniendo optener todas las reservas ");
            return ResponseEntity.ok(reservaDTOPage);
        } catch (Exception e) {
            log.error(" No se pudieron obtener las reservas ");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // 7. Obtener reservas por usuario (ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Page<ReservaDTO>> obtenerReservasPorUsuario(@PathVariable Long usuarioId,
                                                                      @RequestParam(value = "page", required = false) Integer page,
                                                                      @RequestParam(value = "size", required = false) Integer size) {

        int pageNumber = (page != null) ? page : defaultPageNumber;
        int pageSize = (size != null) ? size : defaultPageSize;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {
            Page<ReservaDTO> reservas = reservaService.obtenerReservasPorUsuario(usuarioId, pageable);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            log.error("Error al obtener reservas para el usuario {}: {}", usuarioId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 8. Obtener reservas por habitación (ROLE_ADMIN)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<Page<ReservaDTO>> obtenerReservasPorHabitacion(
            @PathVariable Long habitacionId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value= "size", required = false) Integer size) {
        int pageNumber = (page != null) ? page : defaultPageNumber;
        int pageSize = (size != null) ? size : defaultPageSize;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        try {
            Page<ReservaDTO> reservas = reservaService.obtenerReservasPorHabitacion(habitacionId, pageable);
            return ResponseEntity.ok(   reservas);
        } catch (Exception e) {
            log.error("Error al obtener reservas para la habitación {}: {}", habitacionId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 9. Actualizar el ESTADO de una reserva (ROLE_ADMIN)
    //PENDIENTE
    //CONFIRMADA
    //CANCELADAa
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoReserva(@PathVariable Long id, @RequestParam EstadoReserva estado) {
        try {
            //ReservaDTO reservaActualizada = reservaService.actualizarEstadoReserva(id, estado);
            reservaService.actualizarEstadoReserva(id, estado);
            return ResponseEntity.ok(estado);
        } catch (Exception e) {
            log.error("Error al actualizar el estado de la reserva con ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

