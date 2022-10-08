package br.com.escola.sav.dto.turma;

import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class TurmaInscritosDTO {

    @JsonProperty("id_turma")
    private Long idTurma;
    @JsonProperty("nome")
    private String nomeTurma;
    @JsonProperty("descricao")
    private String descricaoTurma;
    @JsonProperty("nome_periodo")
    private String nomePeriodo;
    @JsonProperty("imagem")
    private String imagemUrl;
    @JsonProperty("data_criacao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT-3")
    private LocalDateTime dataCriacao;
    @JsonProperty("inscritos")
    private List<UsuarioRequestDTO> inscritos;

}
