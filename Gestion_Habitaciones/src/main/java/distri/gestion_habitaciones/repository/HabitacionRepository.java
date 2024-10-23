package distri.gestion_habitaciones.repository;

import distri.beans.domain.Habitacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    /*          Buscar todas las habitaciones.          */
    //Las NO Eliminadas
    Page<Habitacion> findByDeletedFalse(Pageable pageable);

    // Las ELiminadas
    Page<Habitacion> findByDeletedTrue(Pageable pageable);

    // Habitaciones NO eliminadas y por disponibilidad (true o false)
    Page<Habitacion> findByDeletedFalseAndDisponibilidad(Boolean disponibilidad, Pageable pageable);


    //NO Eliminadas: Dentro de un precio
    Page<Habitacion> findByDeletedFalseAndPrecioBetween
            (BigDecimal precioMin, BigDecimal precioMax, Pageable pageable);

    //NO Eliminadas: Busqueda por tipo
    Page<Habitacion> findByTipoContainingIgnoreCaseAndDeletedFalse(String tipo, Pageable pageable);

    /*                 GET INDIVIDUAL               */
    // NO Eliminada: ID
    Optional<Habitacion> findByIdAndDeletedFalse(Long id);



    Optional<Habitacion> findByNumero(int numeroHabitacion);

}


