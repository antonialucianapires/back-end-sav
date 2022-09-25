package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoRepository;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoTurmaRepository;
import br.com.escola.sav.specifications.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService implements IAvaliacaoService{

    private final AvaliacaoRepository repository;
    private final AvaliacaoTurmaRepository avaliacaoTurmaRepository;

    @Override
    public Avaliacao criarAvaliacao(Avaliacao avaliacao) {
        return repository.saveAndFlush(avaliacao);
    }

    @Override
    public Avaliacao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Avaliação não encontrada"));
    }

    @Override
    public Page<Avaliacao> buscarAvaliacoes(Specification<Avaliacao> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public void distribuirAvaliacaoParaTurmas(List<AvaliacaoTurma> avaliacaoTurmas) {
        avaliacaoTurmaRepository.saveAll(avaliacaoTurmas);
    }
}
