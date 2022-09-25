package br.com.escola.sav.entities.periodo.subperiodo;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.periodo.Periodo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "subperiodos")
@Getter
@Setter
public class SubPeriodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "periodo_id", nullable = false)
    private Periodo periodo;
    @Column(name = "nome_subperiodo", nullable = false, length = 50)
    private String nome;
    @Column(name = "data_inicio_subperiodo", nullable = false)
    private Date dataInicio;
    @Column(name = "data_fim_subperiodo", nullable = false)
    private Date dataFim;
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @OneToMany(mappedBy = "subPeriodo")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Avaliacao> avaliacoes;

    @Deprecated
    public SubPeriodo() {
    }

    public SubPeriodo(String nomeSubperiodo, Periodo periodo, Date dataInicio, Date dataFim) {
        this.periodo = periodo;
        this.nome = nomeSubperiodo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.dataCriacao = new Date();
    }
}
