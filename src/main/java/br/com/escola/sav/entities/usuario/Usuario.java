package br.com.escola.sav.entities.usuario;

import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.enums.usuario.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String matricula;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String senha;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Column
    private String urlImagem;

    @Column(nullable = false, name = "status")
    @Enumerated(EnumType.STRING)
    private StatusUsuario statusUsuario;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataHoraCriacao;

    @ManyToMany(mappedBy = "usuarios")
    private List<Turma> turmas;


}
