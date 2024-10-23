package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO extends BaseDTO{

    private Long id;
    private UsuarioDTO usuario;
    private LocalDateTime fechaCreacion;
    private BigDecimal total;
}
