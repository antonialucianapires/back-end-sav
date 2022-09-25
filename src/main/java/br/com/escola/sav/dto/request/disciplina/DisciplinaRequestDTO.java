package br.com.escola.sav.dto.request.disciplina;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisciplinaRequestDTO {

    public interface DisciplinaView {
        interface CadastrarDisciplinaPost {}
        interface AssociarDocentePost {}
        interface AtualizarDisciplina {}
    }

    @NotNull(groups = DisciplinaView.AssociarDocentePost.class)
    @JsonView(DisciplinaView.AssociarDocentePost.class)
    private Long id;

    @JsonView({DisciplinaView.CadastrarDisciplinaPost.class, DisciplinaView.AtualizarDisciplina.class})
    @NotBlank(message = "nome da disciplina não pode ser vazio")
    private String nome;

    @NotNull(groups = DisciplinaView.AssociarDocentePost.class)
    @JsonView(DisciplinaView.AssociarDocentePost.class)
    @NotNull(message = "o identificador da pessoa docente não pode ser vazio")
    private Long idDocente;

    @JsonView({DisciplinaView.AtualizarDisciplina.class})
    @NotBlank(message = "o status da disciplina não pode ser vazio")
    private String status;
}
