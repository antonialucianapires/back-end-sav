package br.com.escola.sav.repositories.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.enums.disciplina.StatusDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByNome(String nome);

    List<Disciplina> findAllByStatusDisciplina(StatusDisciplina statusDisciplina);
}
