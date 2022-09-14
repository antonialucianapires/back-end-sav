package br.com.escola.sav.services.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.repositories.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
}
