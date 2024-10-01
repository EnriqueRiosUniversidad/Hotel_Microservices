package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO extends BaseDTO {

    private Long id;
    private String nombre;
    private String email;
    private RolDTO rol;
}