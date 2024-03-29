package br.com.escola.sav.services.periodo;

import br.com.escola.sav.dto.request.periodo.subperiodo.SubperiodoRequestDTO;
import br.com.escola.sav.dto.response.periodo.PeriodoResponseDTO;
import br.com.escola.sav.entities.periodo.Periodo;

import java.util.Date;
import java.util.List;

public interface IPeriodoService {
    void criarPeriodo(String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo, List<SubperiodoRequestDTO> subperiodos);

    List<PeriodoResponseDTO> consultarPeriodos(boolean comSubperiodos);

    PeriodoResponseDTO atualizarPeriodo(int idPeriodo, String nomePeriodo, Date dataInicio, Date dataFim, int tipoPeriodo, List<SubperiodoRequestDTO> subperiodos);

    void excluirPeriodo(int idPeriodo);

    PeriodoResponseDTO recuperarPeriodoPorId(int idPeriodo, boolean comSubperiodos);

    public Periodo recuperarPeriodoPorId(int idPeriodo);
}
