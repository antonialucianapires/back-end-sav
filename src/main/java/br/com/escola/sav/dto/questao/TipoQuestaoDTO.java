package br.com.escola.sav.dto.questao;

import br.com.escola.sav.entities.questao.TipoQuestao;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TipoQuestaoDTO implements Serializable {

    public interface TipoQuestaoView {
        interface CriarTipoQuestao {}
        interface AtualizarTipoQuestao {}
    }

    private static final long serialVersionUID = -9066728052457495916L;

    @NotNull(groups = TipoQuestaoView.AtualizarTipoQuestao.class)
    @JsonView(TipoQuestaoView.AtualizarTipoQuestao.class)
    private Long id;

    @NotBlank(groups = {TipoQuestaoView.AtualizarTipoQuestao.class, TipoQuestaoView.CriarTipoQuestao.class})
    @JsonView({TipoQuestaoView.AtualizarTipoQuestao.class, TipoQuestaoView.CriarTipoQuestao.class})
    @UniqueValue(groups = {TipoQuestaoView.AtualizarTipoQuestao.class, TipoQuestaoView.CriarTipoQuestao.class},domainClass = TipoQuestao.class,fieldName = "nome",message = "Já existe um tipo questão com este nome")
    private String nome;
}
