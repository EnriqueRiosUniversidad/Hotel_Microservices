package distri.apigateway.filter;

import distri.apigateway.dto.VerifyResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;

/*@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.debug("AuthFilter invoked for path: {}", exchange.getRequest().getPath());

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            logger.debug("Token extracted: {}", token);

            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8090/auth/verify?token=" + token)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(response -> {
                        logger.debug("Token validation successful");
                        return chain.filter(exchange);
                    })
                    .onErrorResume(error -> {
                        logger.error("Error during token validation", error);
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    public static class Config {

    }
}*/
/*
@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {
    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config){
        return (exchange, chain) ->{
                log.warn("AuthFilter invocado para el PATH :> {}", exchange.getRequest().getPath());

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("Carece de Token o este es invalido. ");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            log.debug("Token extraido ;> {}",token);

            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8090/auth/verify?token=" + token)
                    .retrieve()
                    .bodyToMono( VerifyResponseDTO.class)
                    .flatMap( response ->{
                        log.debug("Token validado correctamente ");

                        //Vamos a agregar el email y ron en el header de mi respuesta

                        exchange.getRequest().mutate()
                                .header("X-User-Email", response.getEmail())
                                .header("X-User-Role", response.getRol())
                                .build();
                        return chain.filter(exchange);
                    })
                    .onErrorResume(
                            error ->{
                                log.error(" error durante la validacion de Token", error);
                                return buildErrorResponse(exchange, "Token de autorización inválido o expirado", HttpStatus.UNAUTHORIZED);
                            }

                    );
        };
    }
    // Método para construir la respuesta de error
    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        byte[] bytes = String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    public static class Config {
    }
}
*/


//@Component
@Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String USER_EMAIL_HEADER = "X-User-Email";
    private static final String USER_ROLE_HEADER = "X-User-Role";
    private static final String AUTH_SERVICE_URI = "http://localhost:8090/auth/verify?token=";

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.debug("AuthFilter invocado para el PATH: {}", exchange.getRequest().getPath());


           /* // Ignorar solicitudes OPTIONS
            if (HttpMethod.OPTIONS.equals(exchange.getRequest().getMethod())) {
                return chain.filter(exchange);
            }*/


            String authHeader = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_HEADER);

            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                log.warn("Falta el token de autorización.");
                return buildErrorResponse(exchange, "Falta el token de autorización", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(BEARER_PREFIX.length());
            log.debug("Token extraído: {}", token);

            // Utilizamos webClientBuilder para crear un WebClient en cada solicitud
            return webClientBuilder.build()
                    .get()
                    .uri(AUTH_SERVICE_URI + token)
                    .retrieve()
                    .onStatus(status -> status.isError(), clientResponse -> {
                        log.error("Error en la respuesta del servicio de autenticación: {}", clientResponse.statusCode());
                        return Mono.error(new RuntimeException("Error en la validación del token"));
                    })
                    .bodyToMono(VerifyResponseDTO.class)
                    .flatMap(response -> {
                        log.debug("Token validado correctamente.");

                        // Agregar email y rol en los headers de la solicitud
                        ServerWebExchange mutatedExchange = exchange.mutate()
                                .request(r -> r.headers(headers -> {
                                    headers.add(USER_EMAIL_HEADER, response.getEmail());
                                    headers.add(USER_ROLE_HEADER, response.getRol());
                                }))
                                .build();

                        return chain.filter(mutatedExchange);
                    })
                    .onErrorResume(error -> {
                        if (error instanceof ConnectException) {
                            log.error("El servicio de autenticación no está disponible", error);
                            return buildErrorResponse(exchange, "El servicio de autenticación no está disponible", HttpStatus.SERVICE_UNAVAILABLE);
                        } else {
                            log.error("Error durante la validación del token", error);
                            return buildErrorResponse(exchange, "Token de autorización inválido o expirado", HttpStatus.UNAUTHORIZED);
                        }
                    });
        };
    }

    private Mono<Void> buildErrorResponse(ServerWebExchange exchange, String message, HttpStatus status) {
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] bytes = String.format("{\"error\": \"%s\"}", message).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    public static class Config {

    }
}
