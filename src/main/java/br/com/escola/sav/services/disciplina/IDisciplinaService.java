package br.com.escola.sav.services.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;

public interface IDisciplinaService {
    boolean existeNome(String nome);

    void cadastrar(Disciplina disciplina);
}
