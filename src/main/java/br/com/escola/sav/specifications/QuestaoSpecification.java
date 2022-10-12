package br.com.escola.sav.specifications;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.TipoQuestao;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

public class QuestaoSpecification {

    @And({@Spec(path = "titulo", spec = Like.class),
            @Spec(path = "enunciado", spec = Like.class)})
    public interface QuestaoSpec extends Specification<Questao> {}

    public static Specification<Questao> filtroPorTipoQuestao (List<Long> idsTipos){
        return (root, query,cb) -> {
            query.distinct(true);
            Root<TipoQuestao> tipoQuestaoRoot = query.from(TipoQuestao.class);
            Expression<Collection<Questao>> questoesRoot = tipoQuestaoRoot.get("questoes");
            return cb.and(tipoQuestaoRoot.get("id").in(idsTipos), cb.isMember(root, questoesRoot));
        };
    }
}
