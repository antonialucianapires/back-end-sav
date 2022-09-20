package br.com.escola.sav.entities.avaliacao;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.questao.Questao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    private Double notaObjetivo;

    @Column
    private LocalDateTime dataHoraInicio;

    @Column
    private LocalDateTime dataHoraFim;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sub_periodo_id")
    private SubPeriodo subPeriodo;

    @Column
    private LocalDateTime dataHoraCriacao;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "avaliacao_questao", joinColumns = @JoinColumn(name = "avaliacao_id"), inverseJoinColumns = @JoinColumn(name = "questao_id"))
    private List<Questao> questoes = new ArrayList<>();
}
