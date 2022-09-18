package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.Questao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IQuestaoService {
    Questao criarQuestao(Questao questao);

    Page<Questao> listarQuestoes(Pageable pageable);

    Questao buscarPorId(Long idQuestao);
}
