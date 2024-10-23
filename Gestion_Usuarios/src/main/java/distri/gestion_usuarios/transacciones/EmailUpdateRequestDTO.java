package distri.gestion_usuarios.transacciones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailUpdateRequestDTO {
    private String email;
    private String email_nuevo;
}
