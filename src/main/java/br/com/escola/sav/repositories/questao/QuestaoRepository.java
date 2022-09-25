package br.com.escola.sav.repositories.questao;

import br.com.escola.sav.entities.questao.Questao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuestaoRepository extends JpaRepository<Questao, Long> {

}
