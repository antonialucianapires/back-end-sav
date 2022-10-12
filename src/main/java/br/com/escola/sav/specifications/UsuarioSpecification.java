package br.com.escola.sav.specifications;

import br.com.escola.sav.entities.usuario.Usuario;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.domain.NotEqual;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class UsuarioSpecification {

    @And({@Spec(path = "matricula", spec = Like.class),
            @Spec(path = "nome", spec = Like.class),
            @Spec(path = "statusUsuario", spec = Equal.class),
            @Spec(path = "tipoUsuario", spec = NotEqual.class)})
    public interface UsuarioSpec extends Specification<Usuario> {}
}