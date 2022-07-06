package br.com.escola.sav.services.periodo.subperiodo;

import br.com.escola.sav.dto.response.periodo.subperiodo.SubperiodoResponseDTO;

import java.util.Date;

public interface ISubperiodoService {
    void criarSubperiodo(String nomeSubperiodo, int codigoPeriodo, Date dataFim, Date dataFim1);

    SubperiodoResponseDTO atualizarSubperiodo(int idSubperiodo, String nomeSubperiodo, int codigoPeriodo, Date dataInicio, Date dataFim);
}
