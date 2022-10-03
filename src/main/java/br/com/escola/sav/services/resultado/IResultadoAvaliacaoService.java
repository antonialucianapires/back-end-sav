package br.com.escola.sav.services.resultado;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.resultado.ResultadoAvaliacao;
import br.com.escola.sav.entities.usuario.Usuario;

public interface IResultadoAvaliacaoService {
    ResultadoAvaliacao consultarResultadoAvaliacao(Avaliacao avaliacao, Usuario usuario);

    void cadastrarResultado(Avaliacao avaliacao, Usuario usuario);
}
