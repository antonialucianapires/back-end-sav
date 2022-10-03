package br.com.escola.sav.repositories.resultado;

import br.com.escola.sav.entities.resultado.ResultadoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResultadoAvaliacaoRepository extends JpaRepository<ResultadoAvaliacao, Long> {
    Optional<ResultadoAvaliacao> findByAvaliacaoIdAndUsuarioId(Long idAvaliacao, Long idUsuario);
}
