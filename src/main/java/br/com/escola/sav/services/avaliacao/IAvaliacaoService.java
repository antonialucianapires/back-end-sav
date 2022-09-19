package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;

public interface IAvaliacaoService {
    void criarAvaliacao(Avaliacao avaliacao);

    Avaliacao buscarPorId(Long id);
}
