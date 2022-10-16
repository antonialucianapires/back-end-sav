package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.entities.turma.Turma;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IAvaliacaoService {
    Avaliacao criarAvaliacao(Avaliacao avaliacao);

    Avaliacao buscarPorId(Long id);

    Page<Avaliacao> buscarAvaliacoes(Specification<Avaliacao> spec, Pageable pageable);

    void distribuirAvaliacaoParaTurmas(List<AvaliacaoTurma> avaliacaoTurmas);

    List<Avaliacao> buscarAvaliacoesPorTurma(Turma turma);

    List<Avaliacao>  listarAvaliacoes();
}
