package distri.beans.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Usuario extends BaseEntity {

    @NotNull(message = "nombre de usuario requerida. ")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotNull(message = "email de usuario requerida. ")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "password de usuario requerida. ")
    @Column(nullable = false, length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
}
