package br.com.escola.sav.services.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUsuarioService {
    Page<Usuario> listarTodosUsuarios(Pageable pageable);

    Optional<Usuario> buscarUsuarioPorId(Long id);

    boolean existeMatricula(String matricula);

    boolean existeEmail(String email);

    void registrar(Usuario usuario);

    void inativarUsuario(Usuario usuario);
}
