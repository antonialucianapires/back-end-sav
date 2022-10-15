package br.com.escola.sav.entities.questao;

import br.com.escola.sav.dto.questao.ItemQuestaoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "itens_questao")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @JsonProperty("indicador_gabarito")
    private Character indicadorGabarito;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao;

}
