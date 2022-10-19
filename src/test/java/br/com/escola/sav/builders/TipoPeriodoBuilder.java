package br.com.escola.sav.builders;

import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;

import java.util.Date;
import java.util.List;

public class TipoPeriodoBuilder {

    private TipoPeriodo tipoPeriodo;

    public TipoPeriodoBuilder() {
        this.tipoPeriodo = new TipoPeriodo();
        this.tipoPeriodo.setDataCriacao(new Date());
    }

    public static TipoPeriodoBuilder builder() {
        return new TipoPeriodoBuilder();
    }

    public TipoPeriodoBuilder comId() {
        this.tipoPeriodo.setId(1);
        return this;
    }

    public TipoPeriodoBuilder comNome() {
        this.tipoPeriodo.setNome("Semestral");
        return this;
    }

    public TipoPeriodoBuilder comPeriodos() {
        this.tipoPeriodo.setPeriodos(List.of(PeriodoBuilder.builder().comId().comTitulo().comDataFim().comDataInicio().build()));
        return this;
    }

    public TipoPeriodo build() {
        return this.tipoPeriodo;
    }

}
