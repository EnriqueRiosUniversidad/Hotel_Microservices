package distri.apigateway.filter;

import distri.apigateway.dto.VerifyResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

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
                                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                                return exchange.getResponse().setComplete();
                            }

                    );
        };
    }

    public static class Config {
    }
}
