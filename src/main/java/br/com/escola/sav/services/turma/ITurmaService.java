package br.com.escola.sav.services.turma;

import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.dto.turma.TurmaInscritosDTO;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.specifications.TurmaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ITurmaService {

    Turma criarTurma(Turma turma);

    void adicionarMatriculadoNaTurma(Long idTurma, Long idUsuario);

    Page<Turma> listarTurmas(Pageable pageable, Specification<Turma> specification);

    void removerMatriculadoDaTurma(Long id, Long idUsuario);

    Turma listarUsuarioPorTurma(Long idTurma);

    void removerTurmaPorId(Long idTurma);

    Turma buscarTurmaPorUsuarioEPeriodo(Long usuarioId, Integer periodo);

    Page<Turma> listarTurmas(Pageable pageable);
}
