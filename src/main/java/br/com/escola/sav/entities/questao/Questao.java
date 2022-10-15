package br.com.escola.sav.entities.questao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.enums.questao.NivelQuestao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @JsonProperty("tipo_questao")
    private TipoQuestao tipoQuestao;

    @Column(nullable = false, name = "nivel")
    @Enumerated(EnumType.STRING)
    @JsonProperty("nivel_questao")
    private NivelQuestao nivelQuestao;

    @JsonIgnore
    @Column
    private LocalDateTime dataHoraCriacao;

    @JsonProperty("itens_questao")
    @OneToMany(mappedBy = "questao", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Fetch(FetchMode.JOIN)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private List<ItemQuestao> itens;

    @JsonIgnore
    @ManyToMany(mappedBy = "questoes")
    private List<Avaliacao> avaliacoes;
}
