package distri.beans.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.ColumnDefault;
import java.time.LocalDateTime;

@MappedSuperclass // Anotación que indica que esta clase no se mapeará a una tabla, pero sus campos serán heredados.
@Data
@NoArgsConstructor
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "timestamp(0) without time zone")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt", nullable = false, columnDefinition = "timestamp(0) without time zone")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    @Column(name = "deleted", nullable = false)
    @ColumnDefault("false")
    private Boolean deleted;
}
