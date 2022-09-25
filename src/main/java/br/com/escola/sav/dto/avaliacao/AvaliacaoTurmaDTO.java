package br.com.escola.sav.dto.avaliacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoTurmaDTO {

    @JsonProperty("id_avaliacao")
    private Long idAvaliacao;
    @JsonProperty("turmas")
    private List<Long> turmas;
}
