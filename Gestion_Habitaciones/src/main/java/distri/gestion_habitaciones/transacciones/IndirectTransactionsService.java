package distri.gestion_habitaciones.transacciones;

import distri.beans.domain.Habitacion;
import distri.gestion_habitaciones.repository.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class IndirectTransactionsService {
    @Autowired
    HabitacionRepository habitacionRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private DirectTransactionsService directTransactionsService;


    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarDisponibilidad_REQUIRED(int numero, Boolean disponibilidad) throws Exception {
        Habitacion habitacion = directTransactionsService.byNumero_REQUIRED(numero);
        habitacion.setDisponibilidad(disponibilidad);
        return "Éxito REQUIRED";
    }

    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarDisponibilidad_SUPPORTS(int numero, Boolean disponibilidad) throws Exception {
        Habitacion habitacion = directTransactionsService.byNumero_SUPPORTS(numero);
        habitacion.setDisponibilidad(disponibilidad);
        return "Éxito SUPPORTS";
    }

    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarDisponibilidad_NOT_SUPPORTED(int numero, Boolean disponibilidad) throws Exception {
        Habitacion habitacion = directTransactionsService.byNumero_NOT_SUPPORTED(numero);
        habitacion.setDisponibilidad(disponibilidad);
        return "Éxito NOT_SUPPORTED";
    }

    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarDisponibilidad_REQUIRES_NEW(int numero, Boolean disponibilidad) throws Exception {
        Habitacion habitacion = directTransactionsService.byNumero_REQUIRES_NEW(numero);
        habitacion.setDisponibilidad(disponibilidad);
        return "Éxito REQUIRES_NEW";
    }

    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarDisponibilidad_NEVER(int numero, Boolean disponibilidad) throws Exception {
        Habitacion habitacion = directTransactionsService.byNumero_NEVER(numero);
        habitacion.setDisponibilidad(disponibilidad);
        return "Éxito NEVER";
    }

   // @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public String cambiarDisponibilidad_MANDATORY(int numero, Boolean disponibilidad) throws Exception {

        Habitacion habitacion = directTransactionsService.byNumero_MANDATORY(numero);
        habitacion.setDisponibilidad(disponibilidad);
        return "Éxito MANDATORY";
    }


}
