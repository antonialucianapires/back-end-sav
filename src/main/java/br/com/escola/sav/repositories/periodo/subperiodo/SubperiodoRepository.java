package br.com.escola.sav.repositories.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubperiodoRepository extends JpaRepository<SubPeriodo, Integer> {
    List<SubPeriodo> findByPeriodoId(int idPeriodo);
}
