package br.com.escola.sav.repositories.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long>, JpaSpecificationExecutor<Avaliacao> {
}
