package br.com.escola.sav.services.periodo.subperiodo;

import br.com.escola.sav.dto.response.periodo.subperiodo.SubperiodoResponseDTO;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ISubperiodoService {
    void criarSubperiodo(String nomeSubperiodo, int codigoPeriodo, Date dataFim, Date dataFim1);

    SubperiodoResponseDTO atualizarSubperiodo(int idSubperiodo, String nomeSubperiodo, int codigoPeriodo, Date dataInicio, Date dataFim);

    List<SubperiodoResponseDTO> listarSubperiodosPorPeriodo(int idPeriodo);

    void excluirSubperiodo(int idSubperiodo);

    void excluirSubperiodos(Set<SubPeriodo> subperiodos);

    void criarSubperiodos(ArrayList<SubPeriodo> subperiodosEntities);

    SubPeriodo buscarSubperiodoPorId(Integer idSubperiodo);
}
