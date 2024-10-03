package distri.gestion_usuarios.transacciones;

import distri.beans.domain.Usuario;
import distri.beans.dto.UsuarioDTO;
import distri.gestion_usuarios.repository.RolRepository;
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
public class UsuarioTransactionsService

{
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${lanzar.excepcion}")
    private Boolean lanzar_exepcion;


    /*PRUEBAS DE TRANSACCIONES*/
    // Directo e indirecto sin transaccion previa, crea transaccion
    // indirecto con transaccion previa, no crea y usa la existente
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Usuario requiredPorEmail(String email) throws Exception {
        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error en REQUIRED Email: {}", email);
            throw new Exception("Error en REQUIRED ");
        }

        Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);
        if (usuario == null) {return null;}
        //return modelMapper.map(usuario, UsuarioDTO.class);
        return  usuario;
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    public String cambiarMail_REQUIRED(String email, String nuevoEmail) throws Exception {
        //UsuarioDTO usuarioDTO = requiredPorEmail(email);
        Usuario usuario = requiredPorEmail(email);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado en REQUIRED");
        }

        usuario.setEmail(nuevoEmail);

        //Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        usuarioRepository.save(usuario);

        return "esxito REQUIRED";
    }


    // Directo e indirecto sin transaccion previa, no crea transaccion
    // indirecto con transaccion previa, no crea y usa la existente
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Usuario supportsPorEmail(String email) throws Exception {
        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error en REQUIRED Email: {}", email);
            throw new Exception("Error en SUPPORTS ");
        }

        return usuarioRepository.findByEmail(email).orElse(null);
    }


    //@Transactional(propagation = Propagation.REQUIRED)
    public String cambiarMail_SUPPORTS(String email, String nuevoEmail) throws Exception {
        Usuario usuario = supportsPorEmail(email);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado en SUPPORTS");
        }
        usuario.setEmail(nuevoEmail);

        usuarioRepository.save(usuario);

        return "esxito SUPPORTS";
    }


    // Directo e indirecto sin transaccion previa, no crea transaccion
    // indirecto con transaccion previa, no crea y se ejecuta fuera de la transaccion.
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Usuario not_supportsPorEmail(String email) throws Exception {
        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error en NOT_SUPPORTS Email: {}", email);
            throw new Exception("Error en SUPPORTS ");
        }

        return usuarioRepository.findByEmail(email).orElse(null);
    }

    //@Transactional(propagation = Propagation.REQUIRED)
    public String cambiarMail_NOT_SUPPORTED(String email, String nuevoEmail) throws Exception {
        Usuario usuario = not_supportsPorEmail(email);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado en NOT_SUPPORTED");
        }

        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);

        return "Éxito NOT_SUPPORTED";
    }




    // Directo e indirecto sin transaccion previa,  crea transaccion
    // indirecto con transaccion previa, Crea una nueva y propia transaccion.
    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
    public Usuario requires_newPorEmail(String email) throws Exception {
        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error en REQUIRES_NEW Email: {}", email);
            throw new Exception("Error en SUPPORTS ");
        }

        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public String cambiarMail_REQUIRES_NEW(String email, String nuevoEmail) throws Exception {
        Usuario usuario = requires_newPorEmail(email);

        if (usuario == null) {
            throw new Exception("Usuario no encontrado en REQUIRES_NE");
        }
        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);

        return "Éxito REQUIRES_NEW";
    }



    // Directo e indirecto sin transaccion previa, no crea transaccion
    // indirecto con transaccion previa, ERROR.
    @Transactional(propagation = Propagation.NEVER, readOnly = true)
    public Usuario neverPorEmail(String email) throws Exception {
        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error en NEVERS Email: {}", email);
            throw new Exception("Error en NEVER ");
        }

        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public String cambiarMail_NEVER(String email, String nuevoEmail) throws Exception {
        Usuario usuario = neverPorEmail(email);

        if (usuario == null) {
            throw new Exception("Usuario no encontrado en NEVER");
        }

        usuario.setEmail(nuevoEmail);
        usuarioRepository.save(usuario);

        return "Éxito NEVER";
    }


    // Directo e indirecto sin transaccion previa, ERROR
    // indirecto con transaccion previa, usa la existente.
    @Transactional(propagation = Propagation.MANDATORY, readOnly = false)
    public Usuario mandatoryPorEmail(String email) throws Exception {
        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error en MANDATORY Email: {}", email);
            throw new Exception("Error en NEVER ");
        }
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

        usuario.setEmail(email);
        return usuarioRepository.save(usuario);
        //return usuarioRepository.findByEmail(email).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String cambiarMail_MANDATORY(String email, String nuevoEmail) throws Exception {
        Usuario usuario = mandatoryPorEmail(email);

        if (usuario == null) {
            throw new Exception("Usuario no encontrado en MANDATORY");
        }

        //usuario.setEmail(nuevoEmail);
        //usuarioRepository.save(usuario);

        return "Éxito MANDATORY";
    }


}
