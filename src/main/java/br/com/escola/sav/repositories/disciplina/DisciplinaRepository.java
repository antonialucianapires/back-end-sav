package br.com.escola.sav.repositories.disciplina;

import br.com.escola.sav.entities.disciplina.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
    boolean existsByNome(String nome);
}
