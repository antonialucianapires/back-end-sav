package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.ItemQuestao;

import java.util.List;
import java.util.Set;

public interface IItemQuestaoService {

    void salvarItens(List<ItemQuestao> itensQuestao);

    void deletarItens(List<ItemQuestao> itens);
}
