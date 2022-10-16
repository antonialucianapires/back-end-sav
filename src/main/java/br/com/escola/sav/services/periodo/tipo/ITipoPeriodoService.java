package br.com.escola.sav.services.periodo.tipo;

import br.com.escola.sav.dto.response.periodo.tipo.TipoPeriodoResponseDTO;

import java.util.List;

public interface ITipoPeriodoService {
    void criarTipoPeriodo(String nome);

    List<TipoPeriodoResponseDTO> listarTipoPeriodos();

    void atualizar(String nomeTipoPeriodo, Integer tipoId);
}
