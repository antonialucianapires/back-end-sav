package br.com.escola.sav.controllers.usuario;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.services.usuario.IUsuarioService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<ResponsePattern> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "ATIVO", name = "status") StatusUsuario statusUsuario) {

        Page<Usuario> userModelPage = usuarioService.listarTodosUsuarios(pageable, statusUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value()).payload(userModelPage).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUsuarioPorId(@PathVariable(value = "id") Long id, @RequestParam(defaultValue = "ATIVO", name = "status") StatusUsuario statusUsuario) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorStatusEId(statusUsuario, id);
        return usuarioOptional.
                <ResponseEntity<Object>>map(usuarioEntity -> ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value()).payload(usuarioEntity).build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponsePattern.builder().httpCode(HttpStatus.NOT_FOUND.value()).message("Usuário com identificador ["+ id+ "] não foi encontrado").build()));
    }

    @PatchMapping("/status")
    public ResponseEntity<Object> alterarStatus(@Validated(UsuarioRequestDTO.UsuarioView.AtualizarStatusUsuario.class) @JsonView(UsuarioRequestDTO.UsuarioView.AtualizarStatusUsuario.class) @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(usuarioRequestDTO.getId());

        if(usuarioOptional.isPresent()) {

            if(usuarioOptional.get().getStatusUsuario().name().equals(usuarioRequestDTO.getStatus())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponsePattern.builder().httpCode(HttpStatus.CONFLICT.value()).message("O usuário informado já possui o status " + usuarioOptional.get().getStatusUsuario().name()).build());
            }

            usuarioService.alterarStatusUsuario(usuarioOptional.get(), StatusUsuario.valueOf(usuarioRequestDTO.getStatus()));

            return ResponseEntity.status(HttpStatus.OK).body(ResultView.builder()
                            .status(HttpStatus.OK.value())
                            .message("Status do usuário alterado para " + usuarioOptional.get().getStatusUsuario() + " com sucesso.")
                            .payload(usuarioOptional.get())
                    .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponsePattern.builder().httpCode(HttpStatus.NOT_FOUND.value()).message("Usuário com identificador ["+ usuarioRequestDTO.getId()+ "] não foi encontrado").build());
    }

}
