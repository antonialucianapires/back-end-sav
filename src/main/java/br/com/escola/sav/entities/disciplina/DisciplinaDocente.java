package br.com.escola.sav.entities.disciplina;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "disciplina_docente")
@Getter
@Setter
public class DisciplinaDocente {

    @EmbeddedId
    private DisciplinaDocenteId id;
    @Column(name = "data_criacao")
    private LocalDateTime dataHoraCriacao;
}
