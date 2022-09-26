package br.com.escola.sav.repositories.questao;

import br.com.escola.sav.entities.questao.QuestaoValorObjetivo;
import br.com.escola.sav.entities.questao.QuestaoValorObjetivoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestaoValorObjetivoRepository extends JpaRepository<QuestaoValorObjetivo, QuestaoValorObjetivoId> {
    @Query(value = "select * from questao_valor_objetivo where id_avaliacao=?1 and id_questao in (?2);", nativeQuery = true)
    List<QuestaoValorObjetivo> findByIdAvaliacaoAndIdsQuestoes(Long idAvaliacao, List<Long> idsQuestoes);
}
