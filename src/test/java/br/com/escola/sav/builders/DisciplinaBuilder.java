package br.com.escola.sav.builders;

import br.com.escola.sav.entities.disciplina.Disciplina;

public class DisciplinaBuilder {

    private Disciplina disciplina;

    public DisciplinaBuilder() {
        this.disciplina = new Disciplina();
    }

    public static DisciplinaBuilder builder() {
        return new DisciplinaBuilder();
    }

    public DisciplinaBuilder comId() {
        this.disciplina.setId(1L);
        return this;
    }

    public DisciplinaBuilder comNome() {
        this.disciplina.setNome("Matemática");
        return this;
    }

    public Disciplina build() {
        return this.disciplina;
    }
}
