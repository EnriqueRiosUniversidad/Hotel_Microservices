package distri.apigateway.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class VerifyResponseDTO implements Serializable
{
    String token;
    String email;
    String rol;

}
