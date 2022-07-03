package br.com.escola.sav.entities.periodo;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import br.com.escola.sav.enums.periodo.TipoPeriodoEnum;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "periodos")
@Getter
public class Periodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nome_periodo", nullable = false, length = 50)
    private String nome;
    @Column(name = "data_inicio_periodo", nullable = false)
    private Date dataInicio;
    @Column(name = "data_fim_periodo", nullable = false)
    private Date dataFim;
    @ManyToOne
    @JoinColumn(name = "tipoPeriodo_id", nullable = false)
    private TipoPeriodo tipoPeriodo;
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;
    @OneToMany(mappedBy = "periodo")
    private List<SubPeriodo> subperiodos;

    @Deprecated
    public Periodo() {}

    public Periodo (String nome, Date dataInicio, Date dataFim, TipoPeriodo tipoPeriodo) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.tipoPeriodo = tipoPeriodo;
        this.subperiodos = new ArrayList<>();
        this.dataCriacao = new Date();
    }

}
