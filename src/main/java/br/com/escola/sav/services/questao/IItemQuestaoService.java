package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.ItemQuestao;

import java.util.List;

public interface IItemQuestaoService {

    void salvarItens(List<ItemQuestao> itensQuestao);

    void deletarItens(List<ItemQuestao> itens);
}
