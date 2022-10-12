package br.com.escola.sav.dto.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioResumoDTO {
    private Long id;
    private String nome;

    private String matricula;
    private String imagemURL;
    private String tipo;

    public UsuarioResumoDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.tipo = usuario.getTipoUsuario().name();
        this.imagemURL = usuario.getUrlImagem();
        this.matricula = usuario.getMatricula();
    }
}
