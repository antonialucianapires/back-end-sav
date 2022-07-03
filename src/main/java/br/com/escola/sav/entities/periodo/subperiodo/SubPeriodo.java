package br.com.escola.sav.entities.periodo.subperiodo;

import br.com.escola.sav.entities.periodo.Periodo;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "subperiodos")
@Getter
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

}
