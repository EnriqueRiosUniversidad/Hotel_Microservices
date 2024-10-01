package distri.security_authentication.controller;

import distri.beans.dto.UsuarioDTO;
import distri.security_authentication.model.JwtResponse;
import distri.security_authentication.model.LoginRequest;
import distri.security_authentication.service.UsuarioService;
import distri.security_authentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Validar credenciales del usuario
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Generar JWT tras la autenticación exitosa
            String token = jwtUtil.generateToken(authentication);
            JwtResponse jwtResponse = new JwtResponse(token,loginRequest.getEmail());

            return ResponseEntity.ok(jwtResponse);
        } catch (AuthenticationException ex) {
            // Devolver error de autenticación
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsuarioDTO usuarioDTO) {
        // Lógica para registrar al usuario
        usuarioService.saveUsuario(usuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}


