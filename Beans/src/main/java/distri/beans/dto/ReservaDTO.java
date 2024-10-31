package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO extends BaseDTO{

    private Long id;
    private UsuarioDTO usuario;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal total;
    private EstadoReserva estado; //  (e.g., "PENDIENTE", "CONFIRMADA", "CANCELADA")
    private List<Detalle_ReservaDTO> detalles;

}
