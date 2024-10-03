package distri.security_authentication.dto;

import distri.beans.dto.RolDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterRequestDTO implements Serializable {
    private String nombre;
    private String email;
    private String password;
}