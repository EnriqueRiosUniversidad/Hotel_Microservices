package distri.gestion_usuarios.repository;

import distri.beans.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {


    //@Transactional(propagation = Propagation.SUPPORTS)
    Optional<Usuario> findByEmail(String email);


    // Solo obtener usuarios que no estén eliminados
    List<Usuario> findByDeletedFalse();

    // Usar paginacion.
    Page<Usuario> findByDeletedFalse(Pageable pageable);

    // Get por ID que no esté eliminado
    //@Transactional(propagation = Propagation.SUPPORTS)
    Optional<Usuario> findByIdAndDeletedFalse(Long id);


     //@Transactional(propagation = Propagation.SUPPORTS)
    Usuario save(Usuario usuario);

    //              BUSQUEDAS
    // Buscar por nombre, ignorando mayúsculas/minúsculas, y devolver paginado
    Page<Usuario> findByNombreContainingIgnoreCaseAndDeletedFalse(String nombre, Pageable pageable);

}
