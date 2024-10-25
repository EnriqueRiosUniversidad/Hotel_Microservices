    package distri.gestion_usuarios.controller;

    import distri.beans.dto.UsuarioDTO;
    import distri.gestion_usuarios.DTOs.CreateUserRequest;
    import distri.gestion_usuarios.service.UsuarioService;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;

    import org.springframework.security.core.Authentication;


    import java.util.Map;

    @Slf4j
    @RestController
    @RequestMapping("/usuarios")
    public class UsuarioController {

        @Autowired
        private UsuarioService usuarioService;

        // Inyectar valores por defecto desde application.properties
        @Value("${pagination.default-page-size}")
        private int defaultPageSize;

        @Value("${pagination.default-page-number}")
        private int defaultPageNumber;


        @PostMapping
        public ResponseEntity<?> crearUsuario(@RequestBody CreateUserRequest createUserRequestDTO) {
            try {
                UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(createUserRequestDTO);
                log.info("//// Usuario creado exitosamente: {} ////", nuevoUsuario);
                return ResponseEntity.ok(nuevoUsuario);
            } catch (Exception e) {
                log.error("==== Error al crear el usuario: {} ====", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
        }

        // Método para obtener usuarios paginados
        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @GetMapping
        public ResponseEntity<Page<UsuarioDTO>> obtenerUsuarios(
                @RequestParam(value = "page", required = false) Integer page,
                @RequestParam(value = "size", required = false) Integer size) {

            // Si no se proporcionan parámetros en la solicitud HTTP, usar los valores por defecto
            int pageNumber = (page != null) ? page : defaultPageNumber;
            int pageSize = (size != null) ? size : defaultPageSize;

            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            Page<UsuarioDTO> usuarios = usuarioService.obtenerUsuarios(pageable);

            return ResponseEntity.ok(usuarios);
        }

        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @GetMapping("/{id}")
        public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
            try {
                UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
                log.info("//// Usuario obtenido con ID {}: {} ////", id, usuario);
                return ResponseEntity.ok(usuario);
            } catch (Exception e) {
                log.error("---- Error al obtener usuario con ID {}: {} ----", id, e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
        }

        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @GetMapping("/buscar")
        public ResponseEntity<Page<UsuarioDTO>> buscarUsuariosPorNombre(
                @RequestParam("nombre") String nombre,
                @RequestParam(value = "page", required = false) Integer page,
                @RequestParam(value = "size", required = false) Integer size) {

            int pageNumber = (page != null) ? page : defaultPageNumber;
            int pageSize = (size != null) ? size : defaultPageSize;
            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            Page<UsuarioDTO> usuarios = usuarioService.buscarUsuariosPorNombre(nombre, pageable);
            return ResponseEntity.ok(usuarios);
        }



        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @PatchMapping("/{id}")
        public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
            UsuarioDTO usuario = usuarioService.actualizarUsuarioPorId(id, usuarioDTO);
            log.info("//// Usuario actualizado con ID {}: {} ////", id, usuario);
            return ResponseEntity.ok(usuario);
        }

        @PreAuthorize("hasAuthority('ROLE_ADMIN')")
        @DeleteMapping("/")
        public ResponseEntity<String> eliminarUsuarios(@RequestBody Map<String, Object> carga) {
            try {
                log.info("//// Intentando eliminar usuario ////");
                Long id = null;
                if (carga.containsKey("id")) {
                    Object idObj = carga.get("id");
                    if (!(idObj instanceof Integer || idObj instanceof Long)) {
                        log.error("---- El campo 'id' debe ser un número entero ----");
                        throw new IllegalArgumentException("El campo 'id' debe ser un número entero");
                    }
                    id = Long.valueOf(idObj.toString());
                }

                String email = null;
                if (carga.containsKey("email")) {
                    Object emailObj = carga.get("email");
                    if (!(emailObj instanceof String)) {
                        log.error("---- El email debe ser una cadena de texto ----");
                        throw new IllegalArgumentException("El email debe ser una cadena de texto");
                    }
                    email = emailObj.toString();
                }

                if (id == null && email == null) {
                    log.warn(">>> No se proporcionó 'id' ni 'email' <<<");
                    throw new IllegalArgumentException("Debe introducir un 'id' o un 'email'");
                }

                String mensaje = usuarioService.eliminarUsuario(id, email);
                if (mensaje.equals("Usuario no encontrado")) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
                }

                log.info("//// Usuario eliminado con ID: {} || email: {} ////", id, email);
                return ResponseEntity.ok(mensaje);
            } catch (IllegalArgumentException e) {
                log.error("---- Error al eliminar usuario: {} ----", e.getMessage(), e);
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (Exception e) {
                log.error("---- Error interno del servidor al eliminar usuario: {} ----", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error interno del servidor: " + e.getMessage());
            }
        }



            @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
            @GetMapping("/profile")
            public ResponseEntity<?> getUserProfile(Authentication authentication) {
            String email = authentication.getName();
                System.out.println("Email obtenido del Authentication: " + email);
                UsuarioDTO usuarioDTO = usuarioService.findByEmail(email);


                if (usuarioDTO != null) {
                    return ResponseEntity.ok(usuarioDTO);
                }else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Usuario no encontrado");
                }

            }

    }
