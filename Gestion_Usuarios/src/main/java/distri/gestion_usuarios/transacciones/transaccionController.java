package distri.gestion_usuarios.transacciones;

import distri.beans.domain.Usuario;
import distri.beans.dto.UsuarioDTO;
import distri.gestion_usuarios.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Slf4j // Anotación de Lombok para logging
public class transaccionController {

    @Autowired
    private UsuarioTransactionsService usuarioTransactionsService;



    @GetMapping("/direct/required")
    public ResponseEntity<Usuario> getUsuarioByEmailRequired(@RequestBody Usuario request) {
        try {
            Usuario usuario = usuarioTransactionsService.requiredPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a requiredPorEmail: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_REQUIRED (que invoca requiredPorEmail)
    @PostMapping("/indirect/required")
    public ResponseEntity<String> cambiarEmailRequired(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = usuarioTransactionsService.cambiarMail_REQUIRED(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_REQUIRED: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a supportsPorEmail (propagación SUPPORTS)
    @GetMapping("/direct/supports")
    public ResponseEntity<Usuario> getUsuarioByEmailSupports(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = usuarioTransactionsService.supportsPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a supportsPorEmail: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_SUPPORTS (que invoca supportsPorEmail)
    @PostMapping("/indirect/supports")
    public ResponseEntity<String> cambiarEmailSupports(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = usuarioTransactionsService.cambiarMail_SUPPORTS(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_SUPPORTS: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a not_supportsPorEmail (propagación NOT_SUPPORTED)
    @GetMapping("/direct/not-supported")
    public ResponseEntity<Usuario> getUsuarioByEmailNotSupported(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = usuarioTransactionsService.not_supportsPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a not_supportsPorEmail: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_NOT_SUPPORTED (que invoca not_supportsPorEmail)
    @PostMapping("/indirect/not-supported")
    public ResponseEntity<String> cambiarEmailNotSupported(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = usuarioTransactionsService.cambiarMail_NOT_SUPPORTED(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_NOT_SUPPORTED: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a requires_newPorEmail (propagación REQUIRES_NEW)
    @GetMapping("/direct/requires-new")
    public ResponseEntity<Usuario> getUsuarioByEmailRequiresNew(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = usuarioTransactionsService.requires_newPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a requires_newPorEmail: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_REQUIRES_NEW (que invoca requires_newPorEmail)
    @PostMapping("/indirect/requires-new")
    public ResponseEntity<String> cambiarEmailRequiresNew(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = usuarioTransactionsService.cambiarMail_REQUIRES_NEW(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_REQUIRES_NEW: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a neverPorEmail (propagación NEVER)
    @GetMapping("/direct/never")
    public ResponseEntity<Usuario> getUsuarioByEmailNever(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = usuarioTransactionsService.neverPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a neverPorEmail: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_NEVER (que invoca neverPorEmail)
    @PostMapping("/indirect/never")
    public ResponseEntity<String> cambiarEmailNever(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = usuarioTransactionsService.cambiarMail_NEVER(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_NEVER: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }

    // Llamada directa a mandatoryPorEmail (propagación MANDATORY)
    @GetMapping("/direct/mandatory")
    public ResponseEntity<Usuario> getUsuarioByEmailMandatory(@RequestBody EmailUpdateRequestDTO request) {
        try {
            Usuario usuario = usuarioTransactionsService.mandatoryPorEmail(request.getEmail());
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            log.error("Error en llamada directa a mandatoryPorEmail: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Llamada indirecta a cambiarMail_MANDATORY (que invoca mandatoryPorEmail)
    @PostMapping("/indirect/mandatory")
    public ResponseEntity<String> cambiarEmailMandatory(@RequestBody EmailUpdateRequestDTO request) {
        try {
            String resultado = usuarioTransactionsService.cambiarMail_MANDATORY(request.getEmail(), request.getEmail_nuevo());
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            log.error("Error en llamada indirecta a cambiarMail_MANDATORY: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falló el cambio de email");
        }
    }
}
