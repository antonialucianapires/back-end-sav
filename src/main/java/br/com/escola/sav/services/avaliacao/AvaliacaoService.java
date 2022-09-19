package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliacaoService implements IAvaliacaoService{

    private final AvaliacaoRepository repository;

    @Override
    public void criarAvaliacao(Avaliacao avaliacao) {
        repository.save(avaliacao);
    }

    @Override
    public Avaliacao buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Avaliação não encontrada"));
    }
}
