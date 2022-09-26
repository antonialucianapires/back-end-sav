package br.com.escola.sav.entities.questao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "questao_valor_objetivo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestaoValorObjetivo {

    @EmbeddedId
    private QuestaoValorObjetivoId id;

    @Column(nullable = false)
    private BigDecimal valorObjetivo;

    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao;
}
