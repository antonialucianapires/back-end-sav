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
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
    @Column(name = "avaliacao_id", nullable = false)
    private Long avaliacaoId;
    @Column(name = "questao_id", nullable = false)
    private Long questaoId;
    @Column(name = "item_id", nullable = false)
    private Long itemQuestaoId;
    @Column
    private String textoRespostaLivre;
    @Column
    private Character indicadorCorrecao;
    @Column
    private Character indicadorAcerto;
    @Column
    private LocalDateTime dataCriacao;

}
