package distri.security_authentication.util;

import distri.beans.dto.UsuarioDTO;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${SECRET_KEY}")
    private String jwtSecret; // La clave secreta usada para firmar el token.

    @Value("${jwt.expiration-hours}")
    private int jwtExpirationHours;  // Cantidad de horas para que el token expire.

    @Value("${jwt.expiration-minutes}")
    private int jwtExpirationMinutes; // Cantidad de minutos adicionales antes de que el token expire.


    public String generateToken(Authentication authentication) {
        UsuarioDTO userPrincipal = (UsuarioDTO) authentication.getPrincipal();

        int expirationTimeInMs = (jwtExpirationHours * 3600000) + (jwtExpirationMinutes * 60000);

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())  // Se usa el email como "subject" del token
                .setIssuedAt(new Date())  // Fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeInMs))  // Fecha de expiración
                .signWith(SignatureAlgorithm.HS512, jwtSecret)  // Firmar el token con la clave secreta
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)  // Usar la clave secreta para validar el token
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // El "subject" es el email del usuario

    }

    public boolean validateToken(String authToken) {
        try {
            // Validar el token
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;  // Si no hay excepciones, el token es válido
        } catch (SignatureException e) {
            // Firma del token no coincide
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Firma del token no válida");
        } catch (MalformedJwtException e) {
            // El token está mal formado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token mal formado o inválido");
        } catch (ExpiredJwtException e) {
            // El token ha expirado
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El token ha vencido");
        } catch (IllegalArgumentException e) {
            // El token es nulo o está vacío
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El token es inválido");
        } catch (JwtException e) {
            // Cualquier otro error relacionado con JWT
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar el token");
        }
    }


    // Obtener la autenticación a partir del token
    public Authentication getAuthentication(String token) {
        String username = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
}

