package br.com.escola.sav.services.turma;

import br.com.escola.sav.entities.turma.Turma;

public interface ITurmaService {

    Turma criarTurma(Turma turma);

    void adicionarMatriculadoNaTurma(Long idTurma, Long idUsuario);
}
