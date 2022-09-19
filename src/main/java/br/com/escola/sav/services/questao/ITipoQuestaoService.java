package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.TipoQuestao;

import java.util.List;

public interface ITipoQuestaoService {

    TipoQuestao buscarTipoQuestaoPorId(Long id);

    void salvar(TipoQuestao tipoQuestao);

    List<TipoQuestao> listarTipos();

    void deletar(TipoQuestao tipoQuestao);
}
