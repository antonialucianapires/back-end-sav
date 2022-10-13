package br.com.escola.sav.dto.turma;

import br.com.escola.sav.entities.turma.Turma;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaDTO {

    public interface TurmaView {
        interface CriarTurma {}
        interface AtualizarTurma {}

        interface ExibirTurma {}

        interface InscricaoUsuarioTurma {}
    }

    @NotNull(groups = {TurmaDTO.TurmaView.AtualizarTurma.class})
    @JsonView({TurmaDTO.TurmaView.AtualizarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class, TurmaView.InscricaoUsuarioTurma.class})
    private Long id;
    @NotBlank(groups = TurmaDTO.TurmaView.CriarTurma.class)
    @JsonView({TurmaDTO.TurmaView.AtualizarTurma.class, TurmaDTO.TurmaView.CriarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    private String nome;
    @JsonView({TurmaDTO.TurmaView.AtualizarTurma.class, TurmaDTO.TurmaView.CriarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    private String descricao;
    @NotNull(groups = {TurmaDTO.TurmaView.CriarTurma.class})
    @JsonView({TurmaDTO.TurmaView.CriarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    @JsonProperty("id_periodo")
    private Integer idPeriodo;
    @NotNull(groups = TurmaView.InscricaoUsuarioTurma.class)
    @JsonView(TurmaView.InscricaoUsuarioTurma.class)
    @JsonProperty("id_usuario")
    private Long idUsuario;

    public static TurmaDTO create(Turma turma) {
        var turmaDTO = new TurmaDTO();
        turmaDTO.setId(turma.getId());
        turmaDTO.setNome(turma.getNome());
        turmaDTO.setDescricao(turma.getDescricao());
        turmaDTO.setIdPeriodo(turma.getPeriodo().getId());
        return turmaDTO;
    }

}
