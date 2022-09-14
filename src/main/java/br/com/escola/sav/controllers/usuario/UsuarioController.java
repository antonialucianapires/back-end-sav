package br.com.escola.sav.controllers.usuario;

import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.services.usuario.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<Usuario>> getAllUsers(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<Usuario> userModelPage = usuarioService.findAll(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }
}
