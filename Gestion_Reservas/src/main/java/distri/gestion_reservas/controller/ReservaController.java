package distri.gestion_reservas.controller;



import distri.beans.domain.Reserva;
import distri.beans.dto.ReservaDTO;
import distri.gestion_reservas.service.ReservaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
@Slf4j
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;


    @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO nuevaReserva = reservaService.crearReserva(reservaDTO);
            log.info("Reserva creada exitosamente: {}", nuevaReserva);
            return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error al crear la reserva: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Page<ReservaDTO>> obtenerReservas( Pageable pageable) {
        try {
            Page<ReservaDTO> reservaDTOPage =  reservaService.findAll(pageable);
            log.info(" Obteniendo optener todas las reservas ");
            return ResponseEntity.ok(reservaDTOPage);
        }catch (Exception e) {
            log.error(" No se pudieron obtener las reservas ");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}

