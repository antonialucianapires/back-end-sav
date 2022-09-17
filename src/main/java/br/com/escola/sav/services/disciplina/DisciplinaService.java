package br.com.escola.sav.services.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.entities.disciplina.DisciplinaDocente;
import br.com.escola.sav.entities.disciplina.DisciplinaDocenteId;
import br.com.escola.sav.enums.disciplina.StatusDisciplina;
import br.com.escola.sav.repositories.disciplina.DisciplinaDocenteRepository;
import br.com.escola.sav.repositories.disciplina.DisciplinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService implements IDisciplinaService{

    private final DisciplinaRepository repository;
    private final DisciplinaDocenteRepository disciplinaDocenteRepository;

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

    @Override
    public void associarDocenteDisciplina(Long idDocente, Long idDisciplina) {

        var associacao = new DisciplinaDocente();
        associacao.setId(new DisciplinaDocenteId(idDisciplina, idDocente));
        associacao.setDataHoraCriacao(LocalDateTime.now());

        disciplinaDocenteRepository.save(associacao);
    }
}
