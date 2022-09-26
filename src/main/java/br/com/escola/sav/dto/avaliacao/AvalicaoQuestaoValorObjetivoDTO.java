package br.com.escola.sav.dto.avaliacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AvalicaoQuestaoValorObjetivoDTO implements Serializable {

    private static final long serialVersionUID = -7975203801349510863L;
    @NotNull
    @JsonProperty("id_questao")
    private Long idQuestao;
    @NotNull
    @JsonProperty("valor_questao")
    private double valorQuestao;
}
