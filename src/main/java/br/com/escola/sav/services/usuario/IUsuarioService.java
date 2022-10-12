package br.com.escola.sav.services.usuario;

import br.com.escola.sav.dto.usuario.UsuarioResumoDTO;
import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.specifications.UsuarioSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IUsuarioService {
    Page<UsuarioResumoDTO> listarTodosUsuarios(UsuarioSpecification.UsuarioSpec usuarioSpec, Pageable pageable);

    Optional<Usuario> buscarUsuarioPorStatusEId(StatusUsuario statusUsuario,Long id);

    boolean existeMatricula(String matricula);

    boolean existeEmail(String email);

    Usuario registrar(Usuario usuario);

    void alterarStatusUsuario(Usuario usuario, StatusUsuario statusUsuario);

    Optional<Usuario> buscarUsuarioPorId(Long id);
}
