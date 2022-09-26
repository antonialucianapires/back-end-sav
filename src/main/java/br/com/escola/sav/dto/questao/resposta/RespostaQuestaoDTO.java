package br.com.escola.sav.dto.questao.resposta;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaQuestaoDTO {

    @JsonProperty("id_avaliacao")
    private Long idAvaliacao;
    @JsonProperty("id_usuario")
    private Long idUsuario;
    private List<RespostaQuestaoItemDTO> respostas;
}
