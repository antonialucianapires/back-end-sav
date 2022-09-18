package br.com.escola.sav.services.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;

import java.util.List;

public interface IDisciplinaService {
    boolean existeNome(String nome);

    void cadastrar(Disciplina disciplina);

    List<Disciplina> listarDisciplinasAtivas();

    List<Disciplina> listarDisciplinas();

    void associarDocenteDisciplina(Long idDocente, Long idDisciplina);
}
