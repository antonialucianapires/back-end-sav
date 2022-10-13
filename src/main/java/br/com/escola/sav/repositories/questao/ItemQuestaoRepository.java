package br.com.escola.sav.repositories.questao;

import br.com.escola.sav.entities.questao.ItemQuestao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemQuestaoRepository extends JpaRepository<ItemQuestao, Long> {

    @Modifying
    @Query(value = "delete from itens_questao where questao_id in (:ids)", nativeQuery = true)
    void deleteAllByQuestaoId(@Param("ids") Long id);
}
