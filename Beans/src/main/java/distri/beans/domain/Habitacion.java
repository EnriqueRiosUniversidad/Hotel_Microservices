package distri.beans.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "habitacion")
@NoArgsConstructor
@AllArgsConstructor
public class Habitacion extends BaseEntity{

    @NotBlank(message = "Numero de habitacio requerido. ")
    @Column(name = "numero" , nullable= false)
    private int numero;

    @NotBlank(message = "Tipo de habitacio requerido. ")
    @Column(name="tipo", nullable= false)
    private String tipo;

    @NotNull(message = "Disponibilidad de habitacion requerida. ")
    @Column(name = "disponibilidad" , nullable= false)
    private boolean disponibilidad;

    @NotNull(message = "Precio de habitacion requerido. ")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}
