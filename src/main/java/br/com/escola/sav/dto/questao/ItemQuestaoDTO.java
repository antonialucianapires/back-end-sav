package br.com.escola.sav.dto.questao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemQuestaoDTO implements Serializable {

    private static final long serialVersionUID = 3172058538473135667L;
    @JsonView({QuestaoDTO.QuestaoView.AtualizarQuestao.class, QuestaoDTO.QuestaoView.CriarQuestao.class})
    private String descricao;
    @JsonProperty("indicador_gabarito")
    @JsonView({QuestaoDTO.QuestaoView.AtualizarQuestao.class, QuestaoDTO.QuestaoView.CriarQuestao.class})
    private Character indicadorGabarito;
}
