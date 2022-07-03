package br.com.escola.sav.services.periodo;

import java.util.Date;

public interface IPeriodoService {
    void criarPeriodo(String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo);
}
