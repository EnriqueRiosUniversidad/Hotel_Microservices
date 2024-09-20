package distri.gestion_usuarios.service;

import distri.beans.domain.Rol;
import distri.beans.domain.Usuario;
import distri.beans.dto.RolDTO;
import distri.beans.dto.UsuarioDTO;
import distri.gestion_usuarios.repository.RolRepository;
import distri.gestion_usuarios.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    /*
        @Cacheable: Se usa para almacenar el resultado de métodos en el cache.
        @CacheEvict: Para eliminar un objeto del cache.
        @CachePut: Actualiza el valor en el cache.
    */
    @CachePut(value = "usuarios", keyGenerator = "keyGenerator")
    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioDOMAIN = usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if (usuarioDOMAIN.isPresent()) {
            throw new RuntimeException("Este email ya ha sido registrado, por favor introduzca otro");
        }

        Rol rol = rolRepository.findById(usuarioDTO.getRol().getId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuarioDTO.setRol(modelMapper.map(rol, RolDTO.class));
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        if (usuario.getDeleted() == null) {
            usuario.setDeleted(false);
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("//// Usuario guardado exitosamente: {} ////", usuarioGuardado);
        return modelMapper.map(usuarioGuardado, UsuarioDTO.class);
    }

    public Page<UsuarioDTO> obtenerUsuarios(Pageable pageable) {
        return usuarioRepository.findByDeletedFalse(pageable)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }


    @Cacheable(value = "usuarios", keyGenerator = "keyGenerator")
    //@Cacheable(value = "usuario", key = "#id")
    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        log.info("//// Buscando usuario con ID: {} En la base de datos. ////", id);
        Optional<Usuario> usuario = usuarioRepository.findByIdAndDeletedFalse(id);
        if (usuario.isPresent()) {
            return modelMapper.map(usuario.get(), UsuarioDTO.class);
        } else {
            log.warn(">>> Usuario con ID {} no encontrado o ha sido eliminado <<<", id);
            throw new RuntimeException("El usuario ha sido eliminado o no existe");
        }
    }

    public Page<UsuarioDTO> buscarUsuariosPorNombre(String nombre, Pageable pageable) {
        return usuarioRepository.findByNombreContainingIgnoreCaseAndDeletedFalse(nombre, pageable)
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class));
    }


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

        if (usuarioDTO.getPassword() != null) {
            usuarioExistente.setPassword(usuarioDTO.getPassword());
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
