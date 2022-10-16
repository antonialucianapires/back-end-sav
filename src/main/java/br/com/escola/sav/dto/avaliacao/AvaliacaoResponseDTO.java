package br.com.escola.sav.dto.avaliacao;

import br.com.escola.sav.dto.questao.QuestaoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoResponseDTO implements Serializable {

    private static final long serialVersionUID = 7521910966379466085L;

    @JsonProperty("id")
    private Long idAvaliacao;
    private String titulo;
    private String periodo;
    private String subperiodo;
    @JsonProperty("data_hora_inicio")
    private String dataHoraInicio;
    @JsonProperty("data_hora_fim")
    private String dataHoraFim;
    @JsonProperty("nota_objetivo")
    private Double notaObjetivo;
    private List<String> turmas;
    private List<QuestaoDTO> questoes;
}
