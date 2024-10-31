package distri.gestion_reservas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EntityScan(basePackages = "distri.beans.domain")
@EnableJpaRepositories(basePackages = "distri.gestion_reservas.repository")
@ComponentScan(basePackages = {"distri.beans",
        "distri.gestion_reservas"})
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO) //ESto es para el maper que devuuelve get all
public class GestionReservasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionReservasApplication.class, args);
    }

}
