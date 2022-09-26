package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.QuestaoValorObjetivo;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.questao.QuestaoRepository;
import br.com.escola.sav.repositories.questao.QuestaoValorObjetivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestaoService implements IQuestaoService{

    private final QuestaoRepository repository;
    private final QuestaoValorObjetivoRepository questaoValorObjetivoRepository;

    @Override
    public Questao criarQuestao(Questao questao) {
        return repository.saveAndFlush(questao);
    }

    @Override
    public Page<Questao> listarQuestoes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Questao buscarPorId(Long idQuestao) {
        return repository.findById(idQuestao).orElseThrow(() -> new ObjectNotFound("Questão não encontrada"));
    }

    @Override
    public void deletarQuestao(Questao questao) {
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
