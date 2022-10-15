package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.ItemQuestao;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.QuestaoValorObjetivo;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.repositories.questao.ItemQuestaoRepository;
import br.com.escola.sav.repositories.questao.QuestaoRepository;
import br.com.escola.sav.repositories.questao.QuestaoValorObjetivoRepository;
import br.com.escola.sav.specifications.QuestaoSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoService implements IQuestaoService{

    private final QuestaoRepository repository;
    private final QuestaoValorObjetivoRepository questaoValorObjetivoRepository;

    private final ItemQuestaoRepository itemQuestaoRepository;

    @Override
    public Questao criarQuestao(Questao questao) {
        return repository.saveAndFlush(questao);
    }

    @Override
    public Page<Questao> listarQuestoes(Pageable pageable, Specification<Questao> questoaSpec, List<Long> tiposQuestao) {

        if(tiposQuestao.isEmpty()) {
            return repository.findAll(questoaSpec,pageable);
        }
        var questoesPage =  repository.findAll(questoaSpec,pageable);

        var questoesFiltradas = questoesPage.getContent().stream().filter(questao -> tiposQuestao.contains(questao.getTipoQuestao().getId())).collect(Collectors.toList());

        return new PageImpl<>(questoesFiltradas, pageable,questoesPage.getTotalElements());
    }

    @Override
    public Questao buscarPorId(Long idQuestao) {
        return repository.findById(idQuestao).orElseThrow(() -> new ObjectNotFound("Questão não encontrada"));
    }

    @Override
    @Transactional
    public void deletarQuestao(Questao questao) {

        if(!questao.getAvaliacoes().isEmpty()) {
            throw new SavException("Esta questão não pode ser excluída porque faz parte de uma ou mais avaliações. Considere excluir a questão da avaliação e tente novamente.");
        }

        itemQuestaoRepository.deleteAllByQuestaoId(questao.getId());

        questao.setItens(new ArrayList<>());

        repository.delete(questao);
    }

    @Override
    public Set<Questao> listarQuestoesPorId(List<Long> questoes) {
        return repository.findAllById(questoes).stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public void registrarValorDaQuestao(List<QuestaoValorObjetivo> valoresQuestoes) {
        questaoValorObjetivoRepository.saveAll(valoresQuestoes);
    }

    @Override
    public Map<Long, List<QuestaoValorObjetivo>> buscarValorQuestoes(Long idAvaliacao, List<Long> idsQuestoes) {
        return questaoValorObjetivoRepository.findByIdAvaliacaoAndIdsQuestoes(idAvaliacao, idsQuestoes).stream().collect(Collectors.groupingBy(q -> q.getId().getIdQuestao()));
    }
}
