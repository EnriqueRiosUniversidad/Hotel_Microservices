package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO extends BaseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
