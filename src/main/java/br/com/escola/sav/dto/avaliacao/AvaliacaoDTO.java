package br.com.escola.sav.dto.avaliacao;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.util.validador.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvaliacaoDTO {

    public interface AvaliacaoView {
        interface CriarAvaliacao {}
        interface AtualizarAvaliacao {}
    }

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    private Long id;

    @NotBlank(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @UniqueValue(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class},domainClass = Avaliacao.class,fieldName = "titulo",message = "Já existe uma avaliação com este título. Tente seguir um padrão: [periodo] - [turma] - [disciplina] - Avaliação.")
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    private String titulo;

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    private Double notaObjetivo;

    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    private Integer idSubperiodo;

    //TODO: ajustar formato da hora
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    private LocalDateTime dataHoraInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    @NotNull(groups = {AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    @JsonView({AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class, AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class})
    private LocalDateTime dataHoraFim;

}
