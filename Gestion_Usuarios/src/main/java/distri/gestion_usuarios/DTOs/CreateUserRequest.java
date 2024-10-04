package distri.gestion_usuarios.DTOs;

import distri.beans.dto.RolDTO;
import lombok.Data;

@Data
public class CreateUserRequest
{
    private Long id;
    private String nombre;
    private String email;
    private RolDTO rol;
    private String password;

}
