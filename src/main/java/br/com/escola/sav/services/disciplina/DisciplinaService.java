package br.com.escola.sav.services.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.repositories.disciplina.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService implements IDisciplinaService{

    @Autowired
    private DisciplinaRepository repository;

    @Override
    public boolean existeNome(String nome) {
        return repository.existsByNome(nome);
    }

    @Override
    public void cadastrar(Disciplina disciplina) {
        repository.save(disciplina);
    }
}
