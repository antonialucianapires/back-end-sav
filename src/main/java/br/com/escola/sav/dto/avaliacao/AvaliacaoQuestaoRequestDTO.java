package br.com.escola.sav.dto.avaliacao;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoQuestaoRequestDTO {

    @NotNull
    private Long idAvaliacao;
    @NotNull
    private List<Long> questoes;
}
