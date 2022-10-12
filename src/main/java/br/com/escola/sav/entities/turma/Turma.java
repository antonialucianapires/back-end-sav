package br.com.escola.sav.entities.turma;

import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "turmas")
@Getter
@Setter
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 36)
    private String nome;

    @Column(nullable = false, length = 255)
    private String descricao;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Periodo periodo;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @JsonProperty("data_criacao")
    private LocalDateTime dataHoraCriacao;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "usuario_turma", joinColumns = @JoinColumn(name = "turma_id"), inverseJoinColumns = @JoinColumn(name = "usuario_id"))
    @Fetch(FetchMode.JOIN)
    private List<Usuario> usuarios;

    @Transient
    @JsonProperty("total_estudantes")
    private int totalEstudantes;


}
