package br.com.escola.sav.dto.resultado;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultadoAvaliacaoDTO {

    @JsonProperty("nota_final")
    private Double notaFinalAvaliacao;
    @JsonProperty("disciplina")
    private String nomeDisciplina;

}
