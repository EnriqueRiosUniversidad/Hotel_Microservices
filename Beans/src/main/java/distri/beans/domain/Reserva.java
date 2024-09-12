package distri.beans.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "reserva")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Reserva extends BaseEntity{

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "timestamp(0) without time zone")
    private LocalDateTime fechaCreacion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;


}
