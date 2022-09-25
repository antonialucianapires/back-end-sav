package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.ItemQuestao;
import br.com.escola.sav.repositories.questao.ItemQuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ItemQuestaoService implements IItemQuestaoService{

    @Autowired
    private ItemQuestaoRepository repository;

    @Override
    public void salvarItens(List<ItemQuestao> itensQuestao) {
        repository.saveAll(itensQuestao);
    }

    @Override
    public void deletarItens(List<ItemQuestao> itens) {
        repository.deleteAll(itens);
    }
}
