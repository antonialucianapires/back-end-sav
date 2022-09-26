package br.com.escola.sav.repositories.questao.resposta;

import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestaoRespostaRepository extends JpaRepository<RespostaQuestaoApurada, Long> {
}
