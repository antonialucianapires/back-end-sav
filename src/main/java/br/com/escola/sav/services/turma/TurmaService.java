package br.com.escola.sav.services.turma;

import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.dto.turma.TurmaInscritosDTO;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.repositories.turma.TurmaRepository;
import br.com.escola.sav.repositories.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        var turma = repository.findById(idTurma).orElseThrow(() -> new ObjectNotFound("Turma não encontrada"));
        var usuario = usuarioRepository.findByIdAndStatusUsuario(idUsuario, StatusUsuario.ATIVO).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        if(turma.getUsuarios().contains(usuario)) {
            throw new SavException("O usuário informado já está inscrito nesta turma");
        }
        
        turma.getUsuarios().add(usuario);
        repository.save(turma);
    }

    @Override
    public Page<Turma> listarTurmas(Pageable pageable, Specification<Turma> specification) {
        return repository.findAll(specification, pageable);

    }

    @Override
    public void removerMatriculadoDaTurma(Long idTurma, Long idUsuario) {
        var turma = repository.findById(idTurma).orElseThrow(() -> new ObjectNotFound("Turma não encontrada"));
        var usuario = usuarioRepository.findByIdAndStatusUsuario(idUsuario, StatusUsuario.ATIVO).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        if(!turma.getUsuarios().contains(usuario)) {
            throw new SavException("O usuário informado não foi encontrado nesta turma");
        }

        turma.getUsuarios().remove(usuario);
        repository.save(turma);
    }

    @Override
    public Turma listarUsuarioPorTurma(Long idTurma) {
        return repository.findById(idTurma).orElseThrow(() -> new ObjectNotFound("Turma não encontrada"));
    }

    @Override
    public void removerTurmaPorId(Long idTurma) {
        var turma = repository.findById(idTurma).orElseThrow(() -> new ObjectNotFound("Turma não encontrada"));

        if(!turma.getUsuarios().isEmpty()) {
            throw new SavException("A turma não pode ser removida por que possui inscritos. Remova os inscritos e tente novamente.");
        }

        repository.delete(turma);
    }

    @Override
    public Turma buscarTurmaPorUsuarioEPeriodo(Long usuarioId, Integer periodo) {
        var turmaPeriodo = repository.findByPeriodoId(periodo).orElseThrow(() -> new ObjectNotFound("Não encontramos a turma na qual a pessoa estudante está inscrita neste período."));

        var usuarioInscrito = turmaPeriodo.getUsuarios().stream().filter(usuario -> usuario.getId().equals(usuarioId)).findFirst();

        if(usuarioInscrito.isPresent()) {
            return turmaPeriodo;
        }

       throw new SavException("Usuário não está inscrito em uma turma.");
    }

    @Override
    public Page<Turma> listarTurmas(Pageable pageable) {
        return repository.findAll(pageable);
    }


}
