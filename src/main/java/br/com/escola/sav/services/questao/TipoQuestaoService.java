package br.com.escola.sav.services.questao;

import br.com.escola.sav.entities.questao.TipoQuestao;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.questao.TipoQuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoQuestaoService implements ITipoQuestaoService{

    @Autowired
    private TipoQuestaoRepository repository;

    @Override
    public TipoQuestao buscarTipoQuestaoPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Tipo de questão não encontrada"));
    }

    @Override
    public void salvar(TipoQuestao tipoQuestao) {
        repository.save(tipoQuestao);
    }

    @Override
    public List<TipoQuestao> listarTipos() {
        return repository.findAll();
    }

    @Override
    public void deletar(TipoQuestao tipoQuestao) {
        repository.delete(tipoQuestao);
    }
}
