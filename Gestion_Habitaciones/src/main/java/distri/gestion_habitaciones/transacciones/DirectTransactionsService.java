package distri.gestion_habitaciones.transacciones;

import distri.beans.domain.Habitacion;
import distri.gestion_habitaciones.repository.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DirectTransactionsService {
    @Autowired
    HabitacionRepository habitacionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Value("${lanzar.excepcion}")
    private Boolean lanzar_exepcion;



    @Transactional(timeout = 1,propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
    public Habitacion byNumero_TIMEOUT(int numero) {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);



        try {
            log.info("Simulando una espera larga para provocar un timeout...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error("Error al intentar simular la espera: ", e);
        }


        return habitacion;
    }


    /* LAS TRANSACCIONES*/
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Habitacion byNumero_REQUIRED(int numero) throws Exception {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);


        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS numero: {}", numero);
            throw new Exception(String.format("Error en REQUIRED Directo parametro numero: %d", numero));
        }

        return habitacion;
    }


    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public Habitacion byNumero_SUPPORTS(int numero) throws Exception {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);

        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS numero: {}", numero);
            throw new Exception(String.format("Error en REQUIRED Directo parametro numero: %d", numero));
        }

        return habitacion;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, rollbackFor = Exception.class)
    public Habitacion byNumero_NOT_SUPPORTED(int numero) throws Exception {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);

        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS numero: {}", numero);
            throw new Exception(String.format("Error en REQUIRED Directo parametro numero: %d", numero));
        }
        return habitacion;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Habitacion byNumero_REQUIRES_NEW(int numero) throws Exception {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);

        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS numero: {}", numero);
            throw new Exception(String.format("Error en REQUIRED Directo parametro numero: %d", numero));
        }
        return habitacion;
    }

    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public Habitacion byNumero_NEVER(int numero) throws Exception {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);
        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS numero: {}", numero);
            throw new Exception(String.format("Error en REQUIRED Directo parametro numero: %d", numero));
        }
        return habitacion;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Exception.class)
    public Habitacion byNumero_MANDATORY(int numero) throws Exception {
        Habitacion habitacion = habitacionRepository.findByNumero(numero).orElse(null);

        if (lanzar_exepcion) {
            log.error("-- Error en SUPPORTS numero: {}", numero);
            throw new Exception(String.format("Error en REQUIRED Directo parametro numero: %d", numero));
        }
        return habitacion;
    }


}
