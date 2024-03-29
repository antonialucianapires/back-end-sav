package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurmaId;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoRepository;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoTurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Avaliacao> buscarAvaliacoesPorTurma(Turma turma) {
        var avaliacaoTurma= avaliacaoTurmaRepository.findByIdIdTurma(turma.getId());
        var ids = avaliacaoTurma.stream().map(AvaliacaoTurma::getId).map(AvaliacaoTurmaId::getIdAvaliacao).collect(Collectors.toUnmodifiableSet());
        return repository.findAllById(ids);
    }

    @Override
    public List<Avaliacao> listarAvaliacoes() {
        return repository.findAll();
    }
}
