package br.com.escola.sav.services.questao.resposta;

import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;
import br.com.escola.sav.repositories.questao.resposta.QuestaoRespostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestaoRespostaService implements IQuestaoRespostaService{

    private final QuestaoRespostaRepository respostaRepository;
    @Override
    public void salvarRespostas(List<RespostaQuestaoApurada> respostas) {
        respostaRepository.saveAll(respostas);
    }
}
