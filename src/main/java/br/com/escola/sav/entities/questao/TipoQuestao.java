package br.com.escola.sav.entities.questao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tipos_questao")
@Getter
@Setter
public class TipoQuestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoQuestao")
    private List<Questao> questoes;
}
