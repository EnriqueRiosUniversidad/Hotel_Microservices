package distri.security_authentication.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class LoginResponseDTO implements Serializable {
    private String email;
    private String token;
}
