package br.com.escola.sav.services.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuarioService {
    Page<Usuario> findAll(Pageable pageable);
}
