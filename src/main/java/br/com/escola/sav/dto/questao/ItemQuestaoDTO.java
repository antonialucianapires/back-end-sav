package br.com.escola.sav.dto.questao;

import br.com.escola.sav.entities.questao.ItemQuestao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemQuestaoDTO implements Serializable {

    private static final long serialVersionUID = 3172058538473135667L;
    @JsonView({QuestaoDTO.QuestaoView.AtualizarQuestao.class, QuestaoDTO.QuestaoView.CriarQuestao.class,QuestaoDTO.QuestaoView.VisualizarQuestao.class})
    private String descricao;
    @JsonProperty("indicador_gabarito")
    @JsonView({QuestaoDTO.QuestaoView.AtualizarQuestao.class, QuestaoDTO.QuestaoView.CriarQuestao.class,QuestaoDTO.QuestaoView.VisualizarQuestao.class})
    private Character indicadorGabarito;

    public ItemQuestaoDTO(ItemQuestao itemQuestao) {
        this.descricao = itemQuestao.getDescricao();
        this.indicadorGabarito = itemQuestao.getIndicadorGabarito();
    }
}
