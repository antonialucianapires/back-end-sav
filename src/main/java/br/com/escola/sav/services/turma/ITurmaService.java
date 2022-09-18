package br.com.escola.sav.services.turma;

import br.com.escola.sav.entities.turma.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITurmaService {

    Turma criarTurma(Turma turma);

    void adicionarMatriculadoNaTurma(Long idTurma, Long idUsuario);

    Page<Turma> listarTurmas(Pageable pageable);
}
