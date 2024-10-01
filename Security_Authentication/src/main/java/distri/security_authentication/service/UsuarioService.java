package distri.security_authentication.service;

import distri.beans.domain.Rol;
import distri.beans.domain.Usuario;
import distri.beans.dto.UsuarioDTO;
import distri.security_authentication.repository.RolRepository;
import distri.security_authentication.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;  // Acceso a la base de datos

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private ModelMapper modelMapper;  // Mapeo entre Entidades y DTOs

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Encriptación de contraseñas

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario por el nombre de usuario
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + email);
        }

        // Retornar un objeto UserDetails con los detalles del usuario
        return new org.springframework.security.core
                .userdetails
                .User(usuario.getEmail(), usuario.getPassword(), usuario.getAuthorities());
    }

    /**
     * Guardar un nuevo usuario con contraseña encriptada.
     *
     * @param usuarioDTO DTO del usuario a registrar.
     * @return UsuarioDTO con los datos del usuario registrado.
     */
    public UsuarioDTO saveUsuario(UsuarioDTO usuarioDTO) {
        // Verificar si el email ya está registrado
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()) != null) {
            throw new IllegalStateException("Email ya registrado");
        }

        // Convertir el DTO a una entidad Usuario
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));

        // Asignar el rol 'User' por defecto
        Rol rolUsuario = rolRepository.findByNombre("User");
        if (rolUsuario == null) {
            throw new IllegalStateException("El rol 'User' no está definido en la base de datos");
        }
        usuario.setRol(rolUsuario);  // Asignar el rol de usuario estándar


        // Guardar el usuario en la base de datos
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Convertir la entidad guardada de nuevo a DTO y devolverla
        return modelMapper.map(savedUsuario, UsuarioDTO.class);
    }

    /**
     * Buscar un usuario por su email.
     *
     * @param email Email del usuario a buscar.
     * @return UsuarioDTO con los datos del usuario si se encuentra.
     */
    public UsuarioDTO findByEmail(String email) {
        // Buscar el usuario en la base de datos usando su email
        Usuario usuario = usuarioRepository.findByEmail(email);

        // Convertir la entidad encontrada a DTO y devolverla
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
}

