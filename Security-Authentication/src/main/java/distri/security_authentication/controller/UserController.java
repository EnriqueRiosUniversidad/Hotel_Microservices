package distri.security_authentication.controller;

import distri.beans.dto.UsuarioDTO;
import distri.security_authentication.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    /*Punto numero 6  Obtener datos acorde al usuario autenticado
(objeto principal), filtrar datos por ID de usuario que inició sesión.

Utilizo el email como un id,
el cual saco de Authentication
y con el busco las credenciales del usuario
para filtrar los datos.
*/
    //Inserta pre feijo 'ROLE_'
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/profile")
    public ResponseEntity<UsuarioDTO> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        UsuarioDTO usuarioDTO = userService.findByEmail(email);
        return ResponseEntity.ok(usuarioDTO);
    }

    //@PreAuthorize("hasAuthority('ADMIN')")
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/getusers")
    public ResponseEntity<List<UsuarioDTO>> getUsers() {

        List<UsuarioDTO> usuarioDTOs = userService.getAllUsers();
        return ResponseEntity.ok(usuarioDTOs);
    }


}
