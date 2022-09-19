package br.com.escola.sav.entities.questao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "itens_questao")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Questao questao;

    @Column
    private Character indicadorGabarito;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao;
}
