package br.com.escola.sav.services.turma;

import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.entities.turma.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITurmaService {

    Turma criarTurma(Turma turma);

    void adicionarMatriculadoNaTurma(Long idTurma, Long idUsuario);

    Page<Turma> listarTurmas(Pageable pageable);

    void removerMatriculadoDaTurma(Long id, Long idUsuario);

    List<UsuarioRequestDTO> listarUsuarioPorTurma(Long idTurma);
}
