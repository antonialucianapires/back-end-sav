package br.com.escola.sav.dto.turma;

import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.entities.turma.Turma;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TurmaDTO {

    public interface TurmaView {
        interface CriarTurma {}
        interface AtualizarTurma {}

        interface ExibirTurma {}
    }

    @NotNull(groups = {TurmaDTO.TurmaView.AtualizarTurma.class})
    @JsonView({TurmaDTO.TurmaView.AtualizarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    private Long id;
    @JsonView({TurmaDTO.TurmaView.AtualizarTurma.class, TurmaDTO.TurmaView.CriarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    private String nome;
    @JsonView({TurmaDTO.TurmaView.AtualizarTurma.class, TurmaDTO.TurmaView.CriarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    private String descricao;
    @NotNull(groups = {TurmaDTO.TurmaView.CriarTurma.class})
    @JsonView({TurmaDTO.TurmaView.CriarTurma.class,TurmaDTO.TurmaView.ExibirTurma.class})
    private Integer idPeriodo;

    public static TurmaDTO create(Turma turma) {
        var turmaDTO = new TurmaDTO();
        turmaDTO.setId(turma.getId());
        turmaDTO.setNome(turma.getNome());
        turmaDTO.setDescricao(turma.getDescricao());
        turmaDTO.setIdPeriodo(turma.getPeriodo().getId());
        return turmaDTO;
    }

}
