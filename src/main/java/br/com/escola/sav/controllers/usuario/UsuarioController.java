package br.com.escola.sav.controllers.usuario;

import br.com.escola.sav.dto.request.periodo.ResultView;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.services.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<ResponsePattern> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Usuario> userModelPage = usuarioService.listarTodosUsuarios(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value()).payload(userModelPage).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarUsuarioPorId(@PathVariable(value = "id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        return usuarioOptional.
                <ResponseEntity<Object>>map(usuarioEntity -> ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value()).payload(usuarioEntity).build()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponsePattern.builder().httpCode(HttpStatus.NOT_FOUND.value()).message("Usuário com identificador ["+ id+ "] não foi encontrado").build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> inativarUsuario(@PathVariable(value = "id") Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);

        if(usuarioOptional.isPresent()) {
            usuarioService.inativarUsuario(usuarioOptional.get());

            return ResponseEntity.status(HttpStatus.OK).body(ResultView.builder()
                            .status(HttpStatus.OK.value())
                            .message("Usuário inativado com sucesso.")
                    .build());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponsePattern.builder().httpCode(HttpStatus.NOT_FOUND.value()).message("Usuário com identificador ["+ id+ "] não foi encontrado").build());
    }

}
