package br.com.escola.sav.repositories.questao.resposta;

import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestaoRespostaRepository extends JpaRepository<RespostaQuestaoApurada, Long> {
    List<RespostaQuestaoApurada> findAllByAvaliacaoIdAndUsuarioIdAndQuestaoIdIn(Long idAvaliacao, Long idUsuario, List<Long> idQuestoes);
}
