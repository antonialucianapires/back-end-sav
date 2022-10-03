package br.com.escola.sav.services.questao.resposta;

import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;
import br.com.escola.sav.repositories.questao.resposta.QuestaoRespostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoRespostaService implements IQuestaoRespostaService{

    private final QuestaoRespostaRepository respostaRepository;
    @Override
    public void salvarRespostas(List<RespostaQuestaoApurada> respostas) {
        respostaRepository.saveAll(respostas);
    }

    @Override
    public Map<Long, List<RespostaQuestaoApurada>> buscarMapaRespostasAvaliacao(Long idAvaliacao, Long idUsuario, List<Long> idQuestoes) {
        return respostaRepository.findAllByAvaliacaoIdAndUsuarioIdAndQuestaoIdIn(idAvaliacao, idUsuario, idQuestoes).stream().collect(Collectors.groupingBy(r -> r.getQuestao().getId()));
    }

    @Override
    public List<RespostaQuestaoApurada> buscarRespostasAvaliacao(Long idAvaliacao, Long idUsuario, List<Long> idQuestoes) {
        return respostaRepository.findAllByAvaliacaoIdAndUsuarioIdAndQuestaoIdIn(idAvaliacao, idUsuario, idQuestoes);
    }
}
