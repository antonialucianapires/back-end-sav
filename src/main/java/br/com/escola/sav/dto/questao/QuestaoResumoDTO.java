package br.com.escola.sav.dto.questao;

import br.com.escola.sav.entities.questao.Questao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestaoResumoDTO {

    private Long id;
    private String titulo;
    private String enunciado;
    private String tipo;
    private String nivel;

    public QuestaoResumoDTO(Questao questao) {
        var questaoResumo = new QuestaoResumoDTO();
        this.id = questao.getId();
        this.titulo = questao.getTitulo();
        this.enunciado = questao.getEnunciado();
        this.tipo = questao.getTipoQuestao().getNome();
        this.nivel = questao.getNivelQuestao().name();
    }

}
