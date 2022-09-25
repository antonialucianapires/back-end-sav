package br.com.escola.sav.repositories.avaliacao;

import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurmaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoTurmaRepository extends JpaRepository<AvaliacaoTurma, AvaliacaoTurmaId> {
    List<AvaliacaoTurma> findByIdIdTurma(Long id);
}
