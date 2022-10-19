package br.com.escola.sav.builders;

import br.com.escola.sav.entities.turma.Turma;

public class TurmaBuilder {

    private Turma turma;

    public TurmaBuilder () {
        this.turma = new Turma();
    }

    public static TurmaBuilder builder() {
        return new TurmaBuilder();
    }

    public TurmaBuilder comId() {
        this.turma.setId(1L);
        return this;
    }

    public TurmaBuilder comTitulo() {
        this.turma.setNome("Turma 1");
        return this;
    }

    public TurmaBuilder comPeriodo() {
        this.turma.setPeriodo(PeriodoBuilder.builder().comId().comTitulo().comDataInicio().comDataFim().build());
        return this;
    }

    public Turma build() {
        return this.turma;
    }




}
