package br.com.escola.sav.entities.questao;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "itens_questao")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    private Questao questao;

    @Column
    private Character indicadorGabarito;

    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao;
}
