package br.com.escola.sav.entities.avaliacao;

import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private Double notaObjetivo;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataHoraInicio;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataHoraFim;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "sub_periodo_id")
    private SubPeriodo subPeriodo;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioCriacao;

    @Column
    @JsonIgnore
    private LocalDateTime dataHoraCriacao;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "avaliacao_questao", joinColumns = @JoinColumn(name = "avaliacao_id"), inverseJoinColumns = @JoinColumn(name = "questao_id"))
    @Fetch(FetchMode.JOIN)
    private Set<Questao> questoes = new HashSet<>();
}
