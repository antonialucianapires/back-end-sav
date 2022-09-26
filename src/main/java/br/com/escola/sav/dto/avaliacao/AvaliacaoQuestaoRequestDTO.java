package br.com.escola.sav.dto.avaliacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoQuestaoRequestDTO  implements Serializable {

    private static final long serialVersionUID = 4202945662488028053L;
    @NotNull
    @JsonProperty("id_avaliacao")
    private Long idAvaliacao;
    @NotNull
    private List<AvalicaoQuestaoValorObjetivoDTO> questoes;
}
