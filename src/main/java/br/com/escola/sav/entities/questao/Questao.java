package br.com.escola.sav.entities.questao;

import br.com.escola.sav.enums.questao.NivelQuestao;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "questoes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Questao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String enunciado;

    @ManyToOne(optional = false)
    private TipoQuestao tipoQuestao;

    @Column(nullable = false, name = "nivel")
    @Enumerated(EnumType.STRING)
    private NivelQuestao nivelQuestao;

    @Column
    private LocalDateTime dataHoraCriacao;

    @OneToMany(mappedBy = "questao")
    private List<ItemQuestao> itens;
}
