package br.com.escola.sav.entities.avaliacao;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacao_turma")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AvaliacaoTurma {

    @EmbeddedId
    private AvaliacaoTurmaId id;
    @Column(name = "data_criacao")
    private LocalDateTime dataHoraCriacao;
}
