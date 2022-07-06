package br.com.escola.sav.services.periodo.subperiodo;

import java.util.Date;

public interface ISubperiodoService {
    void criarSubperiodo(String nomeSubperiodo, int codigoPeriodo, Date dataFim, Date dataFim1);
}
