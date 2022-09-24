package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAvaliacaoService {
    void criarAvaliacao(Avaliacao avaliacao);

    Avaliacao buscarPorId(Long id);

    Page<Avaliacao> buscarAvaliacoes(SpecificationTemplate.AvaliacaoSpec spec, Pageable pageable);
}
