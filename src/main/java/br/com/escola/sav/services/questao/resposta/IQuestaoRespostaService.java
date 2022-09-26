package br.com.escola.sav.services.questao.resposta;

import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;

import java.util.List;


public interface IQuestaoRespostaService {

    void salvarRespostas(List<RespostaQuestaoApurada> respostas);
}
