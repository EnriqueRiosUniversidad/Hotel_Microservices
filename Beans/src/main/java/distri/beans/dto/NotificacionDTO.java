package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {

    private Integer id;
    private UsuarioDTO usuario;
    private String mensaje;
    private LocalDateTime fechaEnvio;
}

