package distri.gestion_usuarios.repository;

import distri.beans.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findByEmail(String email);


    // Solo obtener usuarios que no estén eliminados
    List<Usuario> findByDeletedFalse();

    // Buscar usuario por ID que no esté eliminado
    Optional<Usuario> findByIdAndDeletedFalse(Long id);
}
