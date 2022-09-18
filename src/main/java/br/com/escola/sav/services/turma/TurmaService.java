package br.com.escola.sav.services.turma;

import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.repositories.turma.TurmaRepository;
import br.com.escola.sav.repositories.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TurmaService implements ITurmaService {

    private final TurmaRepository repository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public Turma criarTurma(Turma turma) {
        return repository.saveAndFlush(turma);
    }

    @Override
    public void adicionarMatriculadoNaTurma(Long idTurma, Long idUsuario) {
        var turma = repository.findById(idTurma).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
        var usuario = usuarioRepository.findByIdAndStatusUsuario(idUsuario, StatusUsuario.ATIVO).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        turma.getUsuarios().add(usuario);
        repository.save(turma);
    }


}
