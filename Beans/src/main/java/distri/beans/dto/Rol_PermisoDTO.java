package distri.beans.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol_PermisoDTO extends BaseDTO {

    private RolDTO rol;
    private PermisoDTO permiso;
}
