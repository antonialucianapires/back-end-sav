package br.com.escola.sav.repositories.disciplina;

import br.com.escola.sav.entities.disciplina.DisciplinaDocente;
import br.com.escola.sav.entities.disciplina.DisciplinaDocenteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaDocenteRepository extends JpaRepository<DisciplinaDocente, DisciplinaDocenteId> {
}
