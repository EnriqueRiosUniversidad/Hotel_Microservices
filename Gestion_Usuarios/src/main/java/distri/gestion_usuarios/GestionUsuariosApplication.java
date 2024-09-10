package distri.gestion_usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"distri.beans.domain"}) // Aquí escaneas el paquete de tus entidades
@EnableJpaRepositories(basePackages = {"distri.gestion_usuarios.repository"})
@ComponentScan(basePackages = {"distri.gestion_usuarios", "distri.beans"}) // Escanear beans y servicios
public class GestionUsuariosApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionUsuariosApplication.class, args);
    }

}
