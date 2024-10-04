package distri.gestion_usuarios.service;

import distri.beans.domain.Rol;
import distri.beans.domain.Usuario;
import distri.beans.dto.RolDTO;
import distri.beans.dto.UsuarioDTO;
import distri.gestion_usuarios.DTOs.CreateUserRequest;
import distri.gestion_usuarios.repository.RolRepository;
import distri.gestion_usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${lanzar.excepcion}")
    private Boolean lanzar_exepcion;

    /*
        @Cacheable: Se usa para almacenar el resultado de métodos en el cache.
        @CacheEvict: Para eliminar un objeto del cache.
        @CachePut: Actualiza el valor en el cache.
    */

    //Recibe un CreateRequest, guarda en BD un USUARIO y devuelve un USUARIODTO
    @Transactional(propagation = Propagation.REQUIRED
            , readOnly = false
            , rollbackFor = Exception.class)
    @CachePut(value = "usuarios", keyGenerator = "keyGenerator")
    public UsuarioDTO crearUsuario(CreateUserRequest createUserRequestDTO) throws Exception {
        Optional<Usuario> usuarioDOMAIN = usuarioRepository.findByEmail(createUserRequestDTO.getEmail());
        if (usuarioDOMAIN.isPresent()) {
            throw new RuntimeException("Este email ya ha sido registrado, por favor introduzca otro");
        }

        Rol rol = rolRepository.findById(createUserRequestDTO.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        createUserRequestDTO.setRol(modelMapper.map(rol, RolDTO.class));
        Usuario usuario = modelMapper.map(createUserRequestDTO, Usuario.class);

        if (usuario.getDeleted() == null) {
            usuario.setDeleted(false);
        }

        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error al crear el usuario con los datos: {}", createUserRequestDTO);
            throw new Exception("Error al crear usuario");
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("//// Usuario guardado exitosamente: {} ////", usuarioGuardado);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }
    
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UsuarioDTO> obtenerUsuarios(Pageable pageable) {
        return usuarioRepository.findByDeletedFalse(pageable)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    @Transactional(timeout = 1,propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    @Cacheable(value = "usuarios", keyGenerator = "keyGenerator")
    //@Cacheable(value = "usuario", key = "#id")
    public UsuarioDTO obtenerUsuarioPorId(Long id) throws Exception {
        // Simulación de espera para provocar el rollback por timeout
        try {
            log.info("Simulando una espera larga para provocar un timeout...");
            Thread.sleep(2000);  // Espera de 2 segundos (excede el timeout de 1 segundo)
        } catch (InterruptedException e) {
            log.error("Error al intentar simular la espera: ", e);
        }


        log.info("//// Buscando usuario con ID: {} En la base de datos. ////", id);
        Optional<Usuario> usuario = usuarioRepository.findByIdAndDeletedFalse(id);



        /*HACEMOS LA PRUEBA*/
        if (lanzar_exepcion) {
            log.error("-- Error al Buscar el usuario con ID: {}", id);
            throw new Exception("Error al buscar usuario");
        }

        if (usuario.isPresent()) {
            return modelMapper.map(usuario.get(), UsuarioDTO.class);
        } else {
            log.warn(">>> Usuario con ID {} no encontrado o ha sido eliminado <<<", id);
            throw new RuntimeException("El usuario ha sido eliminado o no existe");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<UsuarioDTO> buscarUsuariosPorNombre(String nombre, Pageable pageable) {
        return usuarioRepository.findByNombreContainingIgnoreCaseAndDeletedFalse(nombre, pageable)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @CachePut(value = "usuarios", keyGenerator = "keyGenerator")
    public UsuarioDTO actualizarUsuarioPorId(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuarioDTO.getNombre() != null) {
            usuarioExistente.setNombre(usuarioDTO.getNombre());
        }

        if (usuarioDTO.getEmail() != null) {
            usuarioExistente.setEmail(usuarioDTO.getEmail());
        }

        if (usuarioDTO.getRol() != null && usuarioDTO.getRol().getId() != null) {
            Rol nuevoRol = rolRepository.findById(usuarioDTO.getRol().getId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
            usuarioExistente.setRol(nuevoRol);
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuarioExistente);
        log.info("//// Usuario actualizado exitosamente con ID: {} ////", id);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @CacheEvict(value = "usuarios", keyGenerator = "keyGenerator")
    public String eliminarUsuario(Long id, String email) {
        try {
            Usuario usuarioEliminado = null;

            if (id != null) {
                usuarioEliminado = usuarioRepository.findById(id).orElse(null);
            } else if (email != null) {
                usuarioEliminado = usuarioRepository.findByEmail(email).orElse(null);
            }

            if (usuarioEliminado == null) {
                log.warn(">>> Usuario no encontrado con id: {} o email: {} <<<", id, email);
                return "Usuario no encontrado";
            }

            usuarioEliminado.setDeleted(true);
            usuarioRepository.save(usuarioEliminado);
            log.info("//// Usuario marcado como eliminado con id: {} o email: {} ////", id, email);
            return "Usuario eliminado";
        } catch (Exception e) {
            log.error("---- Error al intentar eliminar usuario: {} ----", e.getMessage(), e);
            throw new RuntimeException("Error al intentar eliminar el usuario: " + e.getMessage());
        }
    }


}
