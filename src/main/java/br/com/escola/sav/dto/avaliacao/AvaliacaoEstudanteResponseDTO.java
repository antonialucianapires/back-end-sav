package br.com.escola.sav.dto.avaliacao;

import br.com.escola.sav.dto.questao.QuestaoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoEstudanteResponseDTO {

    @JsonProperty(value = "titulo_avaliacao",index = 0)
    private String tituloAvaliacao;
    @JsonProperty(value = "nome_estudante", index = 1)
    private String nomeEstudante;
    @JsonProperty(value = "matricula_estudante", index = 2)
    private String matriculaEstudante;
    @JsonProperty(value = "nome_turma", index = 3)
    private String nomeTurma;
    @JsonProperty(value = "nome_docente", index = 4)
    private String nomeDocente;
    @JsonProperty(value = "questoes", index = 5)
    List<QuestaoDTO> questoes;
}
