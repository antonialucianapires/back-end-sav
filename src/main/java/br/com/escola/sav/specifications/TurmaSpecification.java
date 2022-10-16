package br.com.escola.sav.specifications;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.entities.usuario.Usuario;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class TurmaSpecification {

    public static Specification<Turma> filtroUsuarioId(Long usuarioId) {
        return (root, query,cb) -> {
            query.distinct(true);
            Root<Usuario> usuarioRoot = query.from(Usuario.class);
            Expression<Collection<Turma>> turmas = usuarioRoot.get("turmas");
            return cb.and(cb.equal(usuarioRoot.get("id"), usuarioId), cb.isMember(root, turmas));
        };
    }

    public static Specification<Turma> filtroPeriodoId(int idPeriodo) {
        return (root, query,cb) -> {
            query.distinct(true);
            Root<Periodo> periodoRoot = query.from(Periodo.class);
            Expression<Collection<Turma>> turmas = periodoRoot.get("turmas");
            return cb.and(cb.equal(periodoRoot.get("id"), idPeriodo), cb.isMember(root, turmas));
        };
    }
}
