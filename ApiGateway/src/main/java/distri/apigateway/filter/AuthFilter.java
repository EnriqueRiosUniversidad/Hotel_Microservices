package distri.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    private final RestTemplate restTemplate;

    public AuthFilter(RestTemplate restTemplate) {
        super(Config.class);
        this.restTemplate = restTemplate;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            logger.debug("AuthFilter has been invoked for path: {}", exchange.getRequest().getPath());

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.warn("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);

            logger.debug("Token extracted: {}", token);

            try {
                // Llamada al microservicio de seguridad para validar el token
                ResponseEntity<Void> response = restTemplate.getForEntity(
                        "http://localhost:8090/auth/verify?token=" + token, Void.class);

                logger.debug("Security service response: {}", response.getStatusCode());

                if (response.getStatusCode() != HttpStatus.OK) {
                    logger.warn("Token verification failed, received status: {}", response.getStatusCode());
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
            } catch (Exception e) {
                logger.error("Error during token validation", e);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        // Custom configuration class if needed
    }
}

