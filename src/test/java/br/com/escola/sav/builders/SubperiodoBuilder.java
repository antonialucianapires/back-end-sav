package br.com.escola.sav.builders;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.Month;

public class SubperiodoBuilder {

    private SubPeriodo subPeriodo;

    public SubperiodoBuilder() {
        this.subPeriodo = new SubPeriodo();
    }

    public static SubperiodoBuilder builder() {
        return new SubperiodoBuilder();
    }

    public SubperiodoBuilder comId() {
        this.subPeriodo.setId(1);
        return this;
    }

    public SubperiodoBuilder comNome() {
        this.subPeriodo.setNome("Subperiodo 1");
        return this;
    }

    public SubperiodoBuilder comDataInicio() {
        this.subPeriodo.setDataInicio(Date.valueOf("2022-01-11"));
        return this;
    }

    public SubperiodoBuilder comDataFim() {
        this.subPeriodo.setDataInicio(Date.valueOf("2022-08-11"));
        return this;
    }

    public SubperiodoBuilder comPeriodo() {
        this.subPeriodo.setPeriodo(PeriodoBuilder.builder()
                        .comId()
                        .comTitulo()
                        .comTipoPeriodo()
                        .comDataInicio()
                        .comDataFim()
                .build());
        return this;
    }

    public SubPeriodo build() {
        return this.subPeriodo;
    }
}
