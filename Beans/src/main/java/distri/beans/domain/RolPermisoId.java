package distri.beans.domain;

import java.io.Serializable;
import java.util.Objects;

public class RolPermisoId implements Serializable {

    private Integer rol;  // Tipo y nombre deben coincidir con los campos en la entidad
    private Integer permiso;

    public RolPermisoId() {}

    public RolPermisoId(Integer rol, Integer permiso) {
        this.rol = rol;
        this.permiso = permiso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolPermisoId that = (RolPermisoId) o;
        return Objects.equals(rol, that.rol) && Objects.equals(permiso, that.permiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rol, permiso);
    }
}

