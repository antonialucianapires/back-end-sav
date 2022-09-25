package br.com.escola.sav.repositories.turma;

import br.com.escola.sav.entities.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    @Query(value = "select * from turmas t\n" +
            "inner join periodos p on t.periodo_id = p.id\n" +
            "inner join usuario_turma u on t.id = u.turma_id\n" +
            "where t.periodo_id = ?2 and u.usuario_id = ?1", nativeQuery = true)
    Optional<Turma> findByPeriodoAndUsuarioId(Long usuario, Long periodo);

    Optional<Turma> findByPeriodoId(Integer periodo);
}
