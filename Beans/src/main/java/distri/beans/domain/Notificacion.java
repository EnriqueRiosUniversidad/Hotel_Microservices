package distri.beans.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notificacion")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notificacion extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String mensaje;

    @Column(nullable = false, columnDefinition = "timestamp(0) without time zone")
    private LocalDateTime fechaEnvio;
}

