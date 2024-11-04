package distri.beans.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_reserva")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Detalle_Reserva extends BaseEntity {

    @Column(name = "reserva_id", nullable = false)
    private Long reservaId;

    @Column(name = "habitacion_id", nullable = false)
    private Long habitacionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}



