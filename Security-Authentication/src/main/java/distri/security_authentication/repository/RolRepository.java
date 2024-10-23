package distri.security_authentication.repository;

import distri.beans.domain.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);

}
