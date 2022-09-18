package br.com.escola.sav.services.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.repositories.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements IUsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Page<Usuario> listarTodosUsuarios(Pageable pageable, StatusUsuario statusUsuario) {
        return usuarioRepository.findAllByStatusUsuario(statusUsuario,pageable);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorStatusEId(StatusUsuario statusUsuario,Long id) {
        return usuarioRepository.findByIdAndStatusUsuario(id, statusUsuario);
    }

    @Override
    public boolean existeMatricula(String matricula) {
        return usuarioRepository.existsByMatricula(matricula);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void alterarStatusUsuario(Usuario usuario, StatusUsuario statusUsuario) {
        usuario.setStatusUsuario(statusUsuario);
        usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}
