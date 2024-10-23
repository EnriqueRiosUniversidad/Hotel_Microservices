package distri.gestion_habitaciones.transacciones;

import distri.beans.domain.Habitacion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habitaciones")
@Slf4j
public class transaccionesController {
    @Autowired
    DirectTransactionsService directTransactionsService;
    @Autowired
    IndirectTransactionsService indirectTransactionsService;


    @GetMapping("/timeout")
    public ResponseEntity<?> timeOut(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_TIMEOUT(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en REQUIRED", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct REQUIRED {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    // Llamada Direct REQUIRED
    @GetMapping("/direct/required")
    public ResponseEntity<?> getByNumero_REQUIRED(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_REQUIRED(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en REQUIRED", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct REQUIRED {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Indirect REQUIRED
    @PostMapping("/indirect/required")
    public ResponseEntity<?> cambiarDisponibilidad_REQUIRED(@RequestBody dispobilidadUpdateDTO request) {
        try {
            String response = indirectTransactionsService.cambiarDisponibilidad_REQUIRED(request.getNumero(), request.getDisponibilidad());
            log.info("Se cambió exitosamente la disponibilidad de la habitacion N: {} a la disponibilidad: {}", request.getNumero(), request.getDisponibilidad());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error dentro del controller indirect REQUIRED {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Direct SUPPORTS
    @GetMapping("/direct/supports")
    public ResponseEntity<?> getByNumero_SUPPORTS(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_SUPPORTS(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en SUPPORTS", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct SUPPORTS {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Indirect SUPPORTS
    @PostMapping("/indirect/supports")
    public ResponseEntity<?> cambiarDisponibilidad_SUPPORTS(@RequestBody dispobilidadUpdateDTO request) {
        try {
            String response = indirectTransactionsService.cambiarDisponibilidad_SUPPORTS(request.getNumero(), request.getDisponibilidad());
            log.info("Se cambió exitosamente la disponibilidad de la habitacion N: {} a la disponibilidad: {}", request.getNumero(), request.getDisponibilidad());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error dentro del controller indirect SUPPORTS {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Direct NOT_SUPPORTED
    @GetMapping("/direct/not-supported")
    public ResponseEntity<?> getByNumero_NOT_SUPPORTED(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_NOT_SUPPORTED(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en NOT_SUPPORTED", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct NOT_SUPPORTED {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Indirect NOT_SUPPORTED
    @PostMapping("/indirect/not-supported")
    public ResponseEntity<?> cambiarDisponibilidad_NOT_SUPPORTED(@RequestBody dispobilidadUpdateDTO request) {
        try {
            String response = indirectTransactionsService.cambiarDisponibilidad_NOT_SUPPORTED(request.getNumero(), request.getDisponibilidad());
            log.info("Se cambió exitosamente la disponibilidad de la habitacion N: {} a la disponibilidad: {}", request.getNumero(), request.getDisponibilidad());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error dentro del controller indirect NOT_SUPPORTED {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Direct REQUIRES_NEW
    @GetMapping("/direct/requires-new")
    public ResponseEntity<?> getByNumero_REQUIRES_NEW(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_REQUIRES_NEW(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en REQUIRES_NEW", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct REQUIRES_NEW {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Indirect REQUIRES_NEW
    @PostMapping("/indirect/requires-new")
    public ResponseEntity<?> cambiarDisponibilidad_REQUIRES_NEW(@RequestBody dispobilidadUpdateDTO request) {
        try {
            String response = indirectTransactionsService.cambiarDisponibilidad_REQUIRES_NEW(request.getNumero(), request.getDisponibilidad());
            log.info("Se cambió exitosamente la disponibilidad de la habitacion N: {} a la disponibilidad: {}", request.getNumero(), request.getDisponibilidad());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error dentro del controller indirect REQUIRES_NEW {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Direct NEVER
    @GetMapping("/direct/never")
    public ResponseEntity<?> getByNumero_NEVER(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_NEVER(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en NEVER", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct NEVER {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Indirect NEVER
    @PostMapping("/indirect/never")
    public ResponseEntity<?> cambiarDisponibilidad_NEVER(@RequestBody dispobilidadUpdateDTO request) {
        try {
            String response = indirectTransactionsService.cambiarDisponibilidad_NEVER(request.getNumero(), request.getDisponibilidad());
            log.info("Se cambió exitosamente la disponibilidad de la habitacion N: {} a la disponibilidad: {}", request.getNumero(), request.getDisponibilidad());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error dentro del controller indirect NEVER {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Direct MANDATORY
    @GetMapping("/direct/mandatory")
    public ResponseEntity<?> getByNumero_MANDATORY(@RequestBody dispobilidadUpdateDTO request) {
        try {
            Habitacion habitacion = directTransactionsService.byNumero_MANDATORY(request.getNumero());
            log.info("Se obtuvo la habitacion Numero {} en MANDATORY", habitacion.getNumero());
            return ResponseEntity.ok(habitacion);
        } catch (Exception e) {
            log.error("Error dentro del controller direct MANDATORY {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // Llamada Indirect MANDATORY
    @PostMapping("/indirect/mandatory")
    public ResponseEntity<?> cambiarDisponibilidad_MANDATORY(@RequestBody dispobilidadUpdateDTO request) {
        try {
            String response = indirectTransactionsService.cambiarDisponibilidad_MANDATORY(request.getNumero(), request.getDisponibilidad());
            log.info("Se cambió exitosamente la disponibilidad de la habitacion N: {} a la disponibilidad: {}", request.getNumero(), request.getDisponibilidad());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error dentro del controller indirect MANDATORY {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
