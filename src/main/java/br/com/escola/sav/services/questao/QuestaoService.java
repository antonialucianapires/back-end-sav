package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.repositories.questao.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestaoService implements IQuestaoService{

    @Autowired
    private QuestaoRepository repository;

    @Override
    public Questao criarQuestao(Questao questao) {
        return repository.saveAndFlush(questao);
    }

    @Override
    public Page<Questao> listarQuestoes(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
