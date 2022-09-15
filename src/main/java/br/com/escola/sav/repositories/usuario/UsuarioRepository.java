package br.com.escola.sav.repositories.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByMatricula(String matricula);

    boolean existsByEmail(String email);
}
