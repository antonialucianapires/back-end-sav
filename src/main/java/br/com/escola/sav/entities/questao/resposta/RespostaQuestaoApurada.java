package br.com.escola.sav.entities.questao.resposta;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.questao.ItemQuestao;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "resposta_questao")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespostaQuestaoApurada {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "usuario_id")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @ManyToOne(optional = false)
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;
    @ManyToOne(optional = false)
    @JoinColumn(name = "questao_id")
    private Questao questao;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemQuestao itemQuestao;
    @Column
    private String textoRespostaLivre;

    @Column
    private Character indicadorCorrecao;

    @Column
    private Character indicadorAcerto;
    @Column
    private LocalDateTime dataCriacao;

}
