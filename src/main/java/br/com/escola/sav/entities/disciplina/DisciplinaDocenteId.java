package br.com.escola.sav.entities.disciplina;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DisciplinaDocenteId implements Serializable {

    private static final long serialVersionUID = -9003344019631831315L;

    private Long idDisciplina;
    private Long idDocente;
}
