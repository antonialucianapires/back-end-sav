package br.com.escola.sav.services.periodo;

import br.com.escola.sav.dto.response.periodo.PeriodoResponseDTO;

import java.util.Date;
import java.util.List;

public interface IPeriodoService {
    void criarPeriodo(String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo);

    List<PeriodoResponseDTO> consultarPeriodos();

    PeriodoResponseDTO atualizarPeriodo(int idPeriodo, String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo);
}
