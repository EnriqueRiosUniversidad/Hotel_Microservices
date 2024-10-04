package distri.gestion_usuarios.transacciones;

import distri.beans.domain.Usuario;
import distri.gestion_usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UsuarioDirectTransactionsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ModelMapper      modelMapper;

    @Value("${lanzar.excepcion}")
    private Boolean lanzar_exepcion;

    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario requiredPorEmail(String email) throws Exception {
        if (lanzar_exepcion) {
            log.error("-- Error en REQUIRED Email: {}", email);
            throw new Exception("Error en REQUIRED ");
        }
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario supportsPorEmail(String email) throws Exception {
        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS Email: {}", email);
            throw new Exception("Error en SUPPORTS ");
        }
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Usuario not_supportsPorEmail(String email) throws Exception {
        if (lanzar_exepcion) {
            log.error("-- Error en NOT_SUPPORTS Email: {}", email);
            throw new Exception("Error en SUPPORTS ");
        }
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Usuario requires_newPorEmail(String email) throws Exception {
        if (lanzar_exepcion) {
            log.error("-- Error en REQUIRES_NEW Email: {}", email);
            throw new Exception("Error en SUPPORTS ");
        }
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    public Usuario neverPorEmail(String email) throws Exception {
        if (lanzar_exepcion) {
            log.error("-- Error en NEVER Email: {}", email);
            throw new Exception("Error en NEVER ");
        }
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(propagation = Propagation.MANDATORY, readOnly = false)
    public Usuario mandatoryPorEmail(String email) throws Exception {
        if (lanzar_exepcion) {
            log.error("-- Error en MANDATORY Email: {}", email);
            throw new Exception("Error en MANDATORY ");
        }
        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        usuario.setEmail(email);
        return usuarioRepository.save(usuario);
    }
}

