package br.com.escola.sav.services.questao.resposta;

import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;

import java.util.List;
import java.util.Map;


public interface IQuestaoRespostaService {

    void salvarRespostas(List<RespostaQuestaoApurada> respostas);

    Map<Long, List<RespostaQuestaoApurada>> buscarMapaRespostasAvaliacao(Long id, Long id1, List<Long> idQuestoes);

    List<RespostaQuestaoApurada> buscarRespostasAvaliacao(Long idAvaliacao, Long idUsuario, List<Long> idQuestoes);
}
