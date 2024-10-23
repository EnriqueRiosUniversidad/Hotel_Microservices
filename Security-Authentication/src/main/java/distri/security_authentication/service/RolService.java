package distri.security_authentication.service;



import distri.security_authentication.repository.RolRepository;
import distri.beans.domain.Rol;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    public Rol createRol(Rol rol) {
        return rolRepository.save(rol);
    }

}

