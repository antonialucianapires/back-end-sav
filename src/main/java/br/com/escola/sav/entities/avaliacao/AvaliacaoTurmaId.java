package br.com.escola.sav.entities.avaliacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AvaliacaoTurmaId implements Serializable {

    private static final long serialVersionUID = -5655501729867306961L;
    private Long idTurma;
    private Long idAvaliacao;
}
