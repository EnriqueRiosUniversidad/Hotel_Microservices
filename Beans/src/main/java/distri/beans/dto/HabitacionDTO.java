package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitacionDTO extends BaseDTO{

    private Long id;
    private Integer numero;
    private String tipo;
    private Boolean disponibilidad;
    private BigDecimal precio;
}
