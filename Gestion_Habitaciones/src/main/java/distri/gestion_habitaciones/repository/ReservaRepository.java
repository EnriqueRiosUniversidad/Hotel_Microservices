package distri.gestion_habitaciones.repository;

import distri.beans.domain.Reserva;
import distri.beans.domain.Detalle_Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("SELECT DISTINCT d.habitacion.id FROM Reserva r JOIN r.detalles d "
            + "WHERE r.estado != 'CANCELADA' "
            + "AND ((r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio))")
    List<Long> findHabitacionesReservadasEntreFechas(@Param("fechaInicio") LocalDate fechaInicio,
                                                     @Param("fechaFin") LocalDate fechaFin);


}
