package br.com.escola.sav.specifications;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;

public class SpecificationTemplate {

    @Or({@Spec(path = "titulo", spec = Like.class)})
    public interface AvaliacaoSpec extends Specification<Avaliacao> {}

    public static Specification<Avaliacao> avaliacoesPeriodoId(Integer periodoId) {
    return (root, query,cb) -> {
        query.distinct(true);
        Root<SubPeriodo> subPeriodoRoot = query.from(SubPeriodo.class);
        Expression<Collection<Avaliacao>> avaliacoesSubperiodos = subPeriodoRoot.get("avaliacoes");
        return cb.and(cb.equal(subPeriodoRoot.get("periodo").get("id"), periodoId), cb.isMember(root, avaliacoesSubperiodos));
    };
    }
}
