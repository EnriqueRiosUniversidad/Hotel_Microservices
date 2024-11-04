package distri.gestion_reservas.repository;

import distri.beans.domain.Detalle_Reserva;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleReservaRepository extends JpaRepository<Detalle_Reserva, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Detalle_Reserva d WHERE d.reservaId = :reservaId")
    void deleteByReservaId(Long reservaId);
}
