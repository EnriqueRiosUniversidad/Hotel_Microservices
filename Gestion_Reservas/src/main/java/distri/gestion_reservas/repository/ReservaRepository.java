package distri.gestion_reservas.repository;

import distri.beans.domain.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Page<Reserva> findAll(Pageable pageable);

    Page<Reserva> findByUsuarioId(Pageable pageable, long id);

    // Buscar reservas que se superpongan en las fechas especificadas
    @Query("SELECT r FROM Reserva r JOIN r.detalles d WHERE d.habitacionId = :habitacionId " +
            "AND r.id <> :reservaId " +
            "AND ((:fechaInicio BETWEEN r.fechaInicio " +
            "AND r.fechaFin) " +
            "OR (:fechaFin BETWEEN r.fechaInicio AND r.fechaFin) " +
            "OR (r.fechaInicio BETWEEN :fechaInicio AND :fechaFin))")
    List<Reserva> findReservasEnConflicto(Long habitacionId,
                                          Long reservaId,
                                          LocalDate fechaInicio,
                                          LocalDate fechaFin);
}
