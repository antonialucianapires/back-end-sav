package br.com.escola.sav.builders;

import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class PeriodoBuilder {

    private Periodo periodo;

    public PeriodoBuilder() {
        this.periodo = new Periodo();
    }

    public static PeriodoBuilder builder() {
        return new PeriodoBuilder();
    }

    public PeriodoBuilder comId() {
        this.periodo.setId(1);
        return this;
    }

    public PeriodoBuilder comTitulo() {
        this.periodo.setNome("Periodo 2020");
        return this;
    }

    public PeriodoBuilder comDataInicio() {
        this.periodo.setDataInicio(Date.valueOf("2022-01-11"));
        return this;
    }

    public PeriodoBuilder comDataFim() {
        this.periodo.setDataFim(Date.valueOf("2022-12-11"));
        return this;
    }

    public PeriodoBuilder comTipoPeriodo() {
        this.periodo.setTipoPeriodo(TipoPeriodoBuilder.builder().comId().comNome().build());
        return this;
    }

    public PeriodoBuilder semSubperiodos(){
        this.periodo.setSubperiodos(new HashSet<>());
        return this;
    }

    public PeriodoBuilder comSubperiodos(){
        var subperiodos = new HashSet<SubPeriodo>();
        subperiodos.add(SubperiodoBuilder.builder()
                .comId().comNome().comDataFim().comDataInicio().comPeriodo()
                .build());

        this.periodo.setSubperiodos(subperiodos);
        return this;
    }

    public Periodo  build() {
        return this.periodo;
    }

}
