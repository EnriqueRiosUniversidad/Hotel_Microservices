package distri.gestion_usuarios.transacciones;

import distri.beans.domain.Usuario;
import distri.gestion_usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UsuarioIndirectTransactionsService {

    @Autowired
    private UsuarioDirectTransactionsService directTransactionsService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarMail_REQUIRED(String email, String nuevoEmail) throws Exception {
        Usuario usuario = directTransactionsService.requiredPorEmail(email);
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);
        return "Éxito REQUIRED";
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarMail_SUPPORTS(String email, String nuevoEmail) throws Exception {
        Usuario usuario = directTransactionsService.supportsPorEmail(email);
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);
        return "Éxito SUPPORTS";
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarMail_NOT_SUPPORTED(String email, String nuevoEmail) throws Exception {
        Usuario usuario = directTransactionsService.not_supportsPorEmail(email);
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);
        return "Éxito NOT_SUPPORTED";
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarMail_REQUIRES_NEW(String email, String nuevoEmail) throws Exception {
        Usuario usuario = directTransactionsService.requires_newPorEmail(email);
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);
        return "Éxito REQUIRES_NEW";
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarMail_NEVER(String email, String nuevoEmail) throws Exception {
        Usuario usuario = directTransactionsService.neverPorEmail(email);
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);
        return "Éxito NEVER";
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarMail_MANDATORY(String email, String nuevoEmail) throws Exception {
        Usuario usuario = directTransactionsService.mandatoryPorEmail(email);
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);
        return "Éxito MANDATORY";
    }
}
