package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.QuestaoValorObjetivo;
import br.com.escola.sav.specifications.QuestaoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IQuestaoService {
    Questao criarQuestao(Questao questao);

    Page<Questao> listarQuestoes(Pageable pageable, Specification<Questao> questoaSpec, List<Long> tiposQuestao);

    Questao buscarPorId(Long idQuestao);

    void deletarQuestao(Questao questao);

    Set<Questao> listarQuestoesPorId(List<Long> questoes);

    void registrarValorDaQuestao(List<QuestaoValorObjetivo> valoresQuestoes);

    Map<Long, List<QuestaoValorObjetivo>> buscarValorQuestoes(Long idAvaliacao, List<Long> idsQuestoes);
}
