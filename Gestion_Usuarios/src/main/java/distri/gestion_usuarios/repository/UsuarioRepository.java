package distri.gestion_usuarios.repository;

import distri.beans.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    Optional<Usuario> findByEmail(String email);


    // Solo obtener usuarios que no estén eliminados
    List<Usuario> findByDeletedFalse();

    // Usar paginacion.
    Page<Usuario> findByDeletedFalse(Pageable pageable);

    // Get por ID que no esté eliminado
    Optional<Usuario> findByIdAndDeletedFalse(Long id);


    //              BUSQUEDAS
    // Buscar por nombre, ignorando mayúsculas/minúsculas, y devolver paginado
    Page<Usuario> findByNombreContainingIgnoreCaseAndDeletedFalse(String nombre, Pageable pageable);

}
