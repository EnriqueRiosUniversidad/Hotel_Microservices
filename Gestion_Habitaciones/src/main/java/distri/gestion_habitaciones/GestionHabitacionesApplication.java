package distri.gestion_habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"distri.beans.domain"}) // Aquí escaneas el paquete de tus entidades
@EnableJpaRepositories(basePackages = {"distri.gestion_habitaciones.repository"})
@ComponentScan(basePackages = {"distri.gestion_habitaciones", "distri.beans"}) // Escanear beans y servicios
public class GestionHabitacionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionHabitacionesApplication.class, args);
    }

}
