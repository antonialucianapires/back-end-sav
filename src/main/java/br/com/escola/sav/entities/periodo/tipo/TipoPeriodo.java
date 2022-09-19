package br.com.escola.sav.entities.periodo.tipo;

import br.com.escola.sav.entities.periodo.Periodo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Entity
@Table(name = "tipo_periodo")
public class TipoPeriodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nome_periodo", nullable = false, length = 50)
    private String nome;

    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoPeriodo")
    private List<Periodo> periodos;

    @Deprecated
    public TipoPeriodo() {
    }

    public TipoPeriodo(String nome) {
        this.nome = nome;
        this.dataCriacao = new Date();
    }
}
