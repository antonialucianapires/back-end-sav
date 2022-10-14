package br.com.escola.sav.repositories.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubperiodoRepository extends JpaRepository<SubPeriodo, Integer> {
    List<SubPeriodo> findByPeriodoId(int idPeriodo);

    @Modifying
    @Query(value = "delete from subperiodos where periodo_id = ?1",nativeQuery = true)
    void deleteAllByPeriodoId(int idPeriodo);
}
