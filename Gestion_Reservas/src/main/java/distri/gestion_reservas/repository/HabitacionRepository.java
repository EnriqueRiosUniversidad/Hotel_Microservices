package distri.gestion_reservas.repository;

import distri.beans.domain.Habitacion;
import distri.beans.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    @Override
    Optional<Habitacion> findById(Long aLong);
}
