package distri.gestion_reservas.repository;

import distri.beans.domain.Detalle_Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleReservaRepository extends JpaRepository<Detalle_Reserva, Long> {
}
