package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detalle_ReservaDTO extends BaseDTO{

    private Integer id;
    private ReservaDTO reserva;
    private HabitacionDTO habitacion;
    private BigDecimal precio;
}

