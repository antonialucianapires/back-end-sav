package br.com.escola.sav.controllers.usuario.autenticacao;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.enums.usuario.TipoUsuario;
import br.com.escola.sav.services.usuario.IUsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios/autenticacao")
public class UsuarioAutenticacaoController {

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/cadastro")
    public ResponseEntity<Object> registrar(@RequestBody @Validated(UsuarioRequestDTO.UsuarioView.RegistrarUsuarioPost.class) @JsonView(UsuarioRequestDTO.UsuarioView.RegistrarUsuarioPost.class) UsuarioRequestDTO usuarioRequestDTO) {

        if(usuarioService.existeMatricula(usuarioRequestDTO.getMatricula())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResultView.builder()
                            .status(HttpStatus.CONFLICT.value())
                            .message("A matrícula informada já foi utilizada para cadastro. Consulte a direção escolar.")
                    .build());
        }

        if(usuarioService.existeEmail(usuarioRequestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResultView.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message("O e-mail informado já foi utilizado para cadastro. Tente um e-mail diferente ou consulte a direção escolar.")
                    .build());
        }

        var usuario = new Usuario();
        BeanUtils.copyProperties(usuarioRequestDTO, usuario);
        usuario.setTipoUsuario(TipoUsuario.valueOf(usuarioRequestDTO.getTipo()));
        usuario.setStatusUsuario(StatusUsuario.ATIVO);
        usuario.setDataHoraCriacao(LocalDateTime.now(ZoneId.of("UTC")));

        usuarioService.registrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultView.builder()
                .status(HttpStatus.OK.value())
                .message("Usuário registrado com sucesso!")
                .payload(usuario)
                .build());

    }

}
