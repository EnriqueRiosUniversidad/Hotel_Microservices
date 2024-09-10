package distri.beans.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
* No extiende de BaseDir, debido a
* 1- el Primary key es el rol y permiso id
* 2- No necesito fechas de creacion o actualizacion esto sera HardCode
* */

@Entity
@Table(name = "rol_permiso")
// Indica que la clave primaria está definida en RolPermisoId
@IdClass(Rol_Permiso.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol_Permiso {

    @Id
    @ManyToOne
    @JoinColumn(name = "rol_id")
    @NotNull(message = "El rol no puede ser nulo.")
    private Rol rol;

    @Id
    @ManyToOne
    @JoinColumn(name = "permiso_id")
    @NotNull(message = "El permiso no puede ser nulo.")
    private Permiso permiso;

}
