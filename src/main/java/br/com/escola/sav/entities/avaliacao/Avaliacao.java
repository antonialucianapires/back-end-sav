package br.com.escola.sav.entities.avaliacao;

import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String titulo;

    @Column
    @JsonProperty("nota_objetivo")
    private Double notaObjetivo;

    @Column
    @JsonProperty("data_hora_inicio")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="GMT-3")
    private LocalDateTime dataHoraInicio;

    @Column
    @JsonProperty("data_hora_fim")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone="GMT-3")
    private LocalDateTime dataHoraFim;

    @Transient
    private String status;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "sub_periodo_id")
    private SubPeriodo subPeriodo;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioCriacao;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;

    @Column
    @JsonIgnore
    private LocalDateTime dataHoraCriacao;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "avaliacao_questao", joinColumns = @JoinColumn(name = "avaliacao_id"), inverseJoinColumns = @JoinColumn(name = "questao_id"))
    @Fetch(FetchMode.JOIN)
    private Set<Questao> questoes = new HashSet<>();
}
