package br.com.escola.sav.dto.request.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioRequestDTO {

    public interface UsuarioView {
        interface RegistrarUsuarioPost {}
        interface AtualizarUsuarioPut {}
        interface AtualizarSenhaPut {}
        interface AtualizarImagemPut {}
        interface AtualizarStatusUsuario {}

        interface VisualizarUsuario {}
    }

    @NotNull(groups = UsuarioView.AtualizarStatusUsuario.class)
    @JsonView(UsuarioView.AtualizarStatusUsuario.class)
    private Long id;

    @NotBlank(groups = UsuarioView.RegistrarUsuarioPost.class)
    @JsonView(UsuarioView.RegistrarUsuarioPost.class)
    @Size(min = 4,max = 50, groups = UsuarioView.RegistrarUsuarioPost.class)
    private String matricula;

    @NotBlank(groups = UsuarioView.RegistrarUsuarioPost.class)
    @Email(groups = UsuarioView.RegistrarUsuarioPost.class)
    @Size(min = 4,max = 50, groups = UsuarioView.RegistrarUsuarioPost.class)
    @JsonView(UsuarioView.RegistrarUsuarioPost.class)
    private String email;

    @Size(min = 6,max = 20, groups = {UsuarioView.RegistrarUsuarioPost.class, UsuarioView.AtualizarSenhaPut.class})
    @NotBlank(groups = {UsuarioView.RegistrarUsuarioPost.class, UsuarioView.AtualizarSenhaPut.class})
    @JsonView({UsuarioView.RegistrarUsuarioPost.class, UsuarioView.AtualizarSenhaPut.class})
    private String senha;

    @NotBlank(groups = UsuarioView.AtualizarSenhaPut.class)
    @JsonView(UsuarioView.AtualizarSenhaPut.class)
    @Size(min = 6,max = 20, groups = UsuarioView.AtualizarSenhaPut.class)
    private String senhaAnterior;

    @JsonView({UsuarioView.RegistrarUsuarioPost.class, UsuarioView.AtualizarUsuarioPut.class})
    private String nome;

    @NotBlank(groups = UsuarioView.AtualizarImagemPut.class)
    @JsonView({UsuarioView.AtualizarImagemPut.class, UsuarioView.VisualizarUsuario.class})
    private String urlImagem;

    @NotBlank(groups = UsuarioView.AtualizarStatusUsuario.class)
    @JsonView(UsuarioView.AtualizarStatusUsuario.class)
    private String status;

    @NotBlank(groups = UsuarioView.RegistrarUsuarioPost.class)
    @JsonView(UsuarioView.RegistrarUsuarioPost.class)
    private String tipo;

    @JsonProperty("id_disciplina")
    @JsonView(UsuarioView.RegistrarUsuarioPost.class)
    private Long idDisciplina;

    public static UsuarioRequestDTO create(Usuario usuario) {
        var usuarioDTO = new UsuarioRequestDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setMatricula(usuarioDTO.getMatricula());
        usuarioDTO.setTipo(usuario.getTipoUsuario().name());
        usuarioDTO.setStatus(usuario.getStatusUsuario().name());
        usuarioDTO.setUrlImagem(usuario.getUrlImagem());
        return usuarioDTO;
    }

}
