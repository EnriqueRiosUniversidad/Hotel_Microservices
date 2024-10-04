package distri.gestion_usuarios.transacciones;

import distri.beans.domain.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Slf4j
public class TransaccionController {

    @Autowired
    private UsuarioDirectTransactionsService directTransactionsService;

    @Autowired
    private UsuarioIndirectTransactionsService indirectTransactionsService;

    // Llamada directa a requiredPorEmail
    @GetMapping("/direct/required")
    public ResponseEntity<Usuario> getUsuarioByEmailRequired(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = directTransactionsService.requiredPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a requiredPorEmail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_REQUIRED
    @PostMapping("/indirect/required")
    public ResponseEntity<String> cambiarEmailRequired(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = indirectTransactionsService.cambiarMail_REQUIRED(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_REQUIRED: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a supportsPorEmail
    @GetMapping("/direct/supports")
    public ResponseEntity<Usuario> getUsuarioByEmailSupports(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = directTransactionsService.supportsPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a supportsPorEmail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_SUPPORTS
    @PostMapping("/indirect/supports")
    public ResponseEntity<String> cambiarEmailSupports(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = indirectTransactionsService.cambiarMail_SUPPORTS(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_SUPPORTS: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a not_supportsPorEmail
    @GetMapping("/direct/not-supported")
    public ResponseEntity<Usuario> getUsuarioByEmailNotSupported(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = directTransactionsService.not_supportsPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a not_supportsPorEmail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_NOT_SUPPORTED
    @PostMapping("/indirect/not-supported")
    public ResponseEntity<String> cambiarEmailNotSupported(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = indirectTransactionsService.cambiarMail_NOT_SUPPORTED(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_NOT_SUPPORTED: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a requires_newPorEmail
    @GetMapping("/direct/requires-new")
    public ResponseEntity<Usuario> getUsuarioByEmailRequiresNew(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = directTransactionsService.requires_newPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a requires_newPorEmail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_REQUIRES_NEW
    @PostMapping("/indirect/requires-new")
    public ResponseEntity<String> cambiarEmailRequiresNew(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = indirectTransactionsService.cambiarMail_REQUIRES_NEW(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_REQUIRES_NEW: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a neverPorEmail
    @GetMapping("/direct/never")
    public ResponseEntity<Usuario> getUsuarioByEmailNever(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = directTransactionsService.neverPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a neverPorEmail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_NEVER
    @PostMapping("/indirect/never")
    public ResponseEntity<String> cambiarEmailNever(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = indirectTransactionsService.cambiarMail_NEVER(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_NEVER: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a mandatoryPorEmail
    @GetMapping("/direct/mandatory")
    public ResponseEntity<Usuario> getUsuarioByEmailMandatory(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = directTransactionsService.mandatoryPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a mandatoryPorEmail: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_MANDATORY
    @PostMapping("/indirect/mandatory")
    public ResponseEntity<String> cambiarEmailMandatory(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = indirectTransactionsService.cambiarMail_MANDATORY(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_MANDATORY: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }
}
