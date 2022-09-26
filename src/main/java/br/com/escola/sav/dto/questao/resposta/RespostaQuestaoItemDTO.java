package br.com.escola.sav.dto.questao.resposta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaQuestaoItemDTO {

    @JsonProperty("id_questao")
    private Long idQuestao;
    @JsonProperty("id_item")
    private Long idItemSelecionado;
    @JsonProperty("resposta_texto")
    private String textoItem;
}
