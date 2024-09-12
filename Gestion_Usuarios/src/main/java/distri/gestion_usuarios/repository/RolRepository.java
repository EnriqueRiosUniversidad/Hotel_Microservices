package distri.gestion_usuarios.repository;

import distri.beans.domain.Rol;
import distri.beans.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
}

