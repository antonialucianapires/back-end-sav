package br.com.escola.sav.services.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.enums.disciplina.StatusDisciplina;
import br.com.escola.sav.repositories.disciplina.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Disciplina> listarDisciplinasAtivas() {
        return repository.findAllByStatusDisciplina(StatusDisciplina.ATIVA);
    }

    @Override
    public List<Disciplina> listarDisciplinas() {
        return repository.findAll();
    }
}
