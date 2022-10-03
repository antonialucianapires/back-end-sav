package br.com.escola.sav.dto.avaliacao;

import br.com.escola.sav.dto.questao.QuestaoDTO;
import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoDTO {

    public interface AvaliacaoView {
        interface CriarAvaliacao {}
        interface AtualizarAvaliacao {}

        interface AvaliacaoResumo {}
    }

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AvaliacaoResumo.class})
    private Long id;

    @NotBlank(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @UniqueValue(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class},domainClass = Avaliacao.class,fieldName = "titulo",message = "Já existe uma avaliação com este título. Tente seguir um padrão: [periodo] - [turma] - [disciplina] - Avaliação.")
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class,AvaliacaoDTO.AvaliacaoView.AvaliacaoResumo.class})
    private String titulo;

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonProperty("nota_objetivo")
    private Double notaObjetivo;

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonProperty("id_subperiodo")
    private Integer idSubperiodo;

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonProperty("id_disciplina")
    private Long idDisciplina;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class,AvaliacaoDTO.AvaliacaoView.AvaliacaoResumo.class})
    @JsonProperty("data_hora_inicio")
    private LocalDateTime dataHoraInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class,AvaliacaoDTO.AvaliacaoView.AvaliacaoResumo.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonProperty("data_hora_fim")
    private LocalDateTime dataHoraFim;

    @JsonView({AvaliacaoDTO.AvaliacaoView.AvaliacaoResumo.class})
    private String status;

    @JsonView({AvaliacaoDTO.AvaliacaoView.AvaliacaoResumo.class})
    private List<QuestaoDTO> questoes;

    @NotNull(groups = AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class)
    @NotEmpty(groups = AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class)
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class})
    private List<Long> turmas;

    @NotNull(groups = AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class)
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class})
    @JsonProperty("usuario_criacao")
    private Long usuarioCriacao;


}
