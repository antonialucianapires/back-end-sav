package br.com.escola.sav.repositories.periodo;

import br.com.escola.sav.entities.periodo.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodoRepository extends JpaRepository<Periodo, Integer> {
}
