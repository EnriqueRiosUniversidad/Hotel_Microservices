package distri.gestion_habitaciones.transacciones;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class dispobilidadUpdateDTO {
    int numero;
    Boolean disponibilidad;

}
