package br.com.escola.sav.repositories.avaliacao;

import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurmaId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoTurmaRepository extends JpaRepository<AvaliacaoTurma, AvaliacaoTurmaId> {
}
