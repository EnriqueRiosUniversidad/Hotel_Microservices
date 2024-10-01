package distri.security_authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"distri.beans.domain"}) // Aquí escaneas el paquete de tus entidades
@EnableJpaRepositories(basePackages = {"distri.security_authentication.repository"})
@ComponentScan(basePackages =
        {"distri.security_authentication",
                "distri.beans",
                "distri.security_authentication.config",
                "distri.security_authentication.filter",
                "distri.security_authentication.util",
        "distri.security_authentication.service",
        "distri.security_authentication.controller"}) // Escanear beans y servicios
public class SecurityAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthenticationApplication.class, args);
    }

}
