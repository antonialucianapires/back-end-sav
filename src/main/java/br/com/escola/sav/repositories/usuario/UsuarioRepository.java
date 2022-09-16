package br.com.escola.sav.repositories.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByMatricula(String matricula);

    boolean existsByEmail(String email);

    Optional<Usuario> findByIdAndStatusUsuario(Long id, StatusUsuario statusUsuario);

    Page<Usuario> findAllByStatusUsuario(StatusUsuario statusUsuario, Pageable pageable);
}
