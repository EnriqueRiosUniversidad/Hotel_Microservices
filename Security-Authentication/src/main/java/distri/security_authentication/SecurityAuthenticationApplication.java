package distri.security_authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
@EntityScan(basePackages = {"distri.beans.domain"})
@EnableJpaRepositories(basePackages = {"distri.security_authentication.repository"})
@ComponentScan(basePackages =
        {"distri.beans", "distri.security_authentication"})
@EnableWebSecurity
public class SecurityAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityAuthenticationApplication.class, args);
    }

}
