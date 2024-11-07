package distri.apigateway.config;

import distri.apigateway.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {
    @Bean
    public AuthFilter authFilter(WebClient.Builder webClientBuilder) {
        return new AuthFilter(webClientBuilder);
    }
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}

