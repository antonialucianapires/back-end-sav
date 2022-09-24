package br.com.escola.sav.specifications;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @Or({@Spec(path = "subPeriodo.periodo.id", spec = Equal.class),
            @Spec(path = "subPeriodo.id", spec = Equal.class),
            @Spec(path = "titulo", spec = Like.class)})
    public interface AvaliacaoSpec extends Specification<Avaliacao> {}
}
