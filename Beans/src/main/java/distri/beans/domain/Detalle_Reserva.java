package distri.beans.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalle_reserva")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor

public class Detalle_Reserva extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "reserva_id" , nullable = false)
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal precio;

}


