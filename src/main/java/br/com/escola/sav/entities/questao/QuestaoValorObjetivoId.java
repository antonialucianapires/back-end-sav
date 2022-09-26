package br.com.escola.sav.entities.questao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestaoValorObjetivoId implements Serializable {

    private static final long serialVersionUID = -8351167668159183841L;
    private Long idAvaliacao;
    private Long idQuestao;
}
