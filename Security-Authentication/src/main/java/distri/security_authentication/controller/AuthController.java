package distri.security_authentication.controller;


import distri.security_authentication.dto.LoginRequestDTO;
import distri.security_authentication.dto.LoginResponseDTO;
import distri.security_authentication.dto.RegisterRequestDTO;
import distri.security_authentication.dto.VerifyResponseDTO;
import distri.security_authentication.security.JwtTokenProvider;
import distri.security_authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class    AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("");

        String token = jwtTokenProvider.createToken(authentication.getName(), role);

       // String token = jwtTokenProvider.createToken(authentication.getName(),
          //      authentication.getAuthorities().toString());

        LoginResponseDTO response = new LoginResponseDTO();
        response.setEmail(authentication.getName());
        response.setToken(token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO registerRequest) {
        userService.registerUser(registerRequest);
        return ResponseEntity.ok("Usuario registrado exitosamente");
    }


    /*@GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok("se puedo lograr");
    }*/


    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Obtener email y rol del token
        String email = jwtTokenProvider.getEmail(token);
        String role = jwtTokenProvider.getRole(token);

        // Crear el DTO de respuesta
        VerifyResponseDTO response = new VerifyResponseDTO();
        response.setToken(token);
        response.setEmail(email);
        response.setRol(role);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/email")
    public ResponseEntity<String> getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(email);
    }


}

