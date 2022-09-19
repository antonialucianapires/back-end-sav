package br.com.escola.sav.repositories.questao;

import br.com.escola.sav.entities.questao.Questao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {
}
