package br.com.escola.sav.dto.questao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestaoDTO implements Serializable {

    private static final long serialVersionUID = 2430004649521782808L;

    public interface QuestaoView {
        interface CriarQuestao {}
        interface AtualizarQuestao {}
    }

    @NotNull(groups = QuestaoView.AtualizarQuestao.class)
    @JsonView(QuestaoView.AtualizarQuestao.class)
    private Long id;
    @NotBlank(groups = {QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    @JsonView({QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    private String titulo;
    @NotBlank(groups = {QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    @JsonView({QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    private String enunciado;
    @NotNull(groups = {QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    @JsonView({QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    private Long tipoQuestao;
    @NotBlank(groups = {QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    @JsonView({QuestaoView.AtualizarQuestao.class, QuestaoView.CriarQuestao.class})
    private String nivel;
    @JsonView({QuestaoView.CriarQuestao.class})
    List<ItemQuestaoDTO> itensQuestao = new ArrayList<>();
}
