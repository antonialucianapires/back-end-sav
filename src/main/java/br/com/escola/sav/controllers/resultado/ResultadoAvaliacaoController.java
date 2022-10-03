package br.com.escola.sav.controllers.resultado;

import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.dto.resultado.ResultadoAvaliacaoDTO;
import br.com.escola.sav.entities.resultado.ResultadoAvaliacao;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.resultado.IResultadoAvaliacaoService;
import br.com.escola.sav.services.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class ResultadoAvaliacaoController {

    private final IResultadoAvaliacaoService resultadoAvaliacaoService;
    private final IAvaliacaoService avaliacaoService;
    private final IUsuarioService usuarioService;

    @GetMapping("/{id_avaliacao}/resultado")
    public ResponseEntity<ResponsePattern> consultarResultadoAvaliacao(@PathVariable("id_avaliacao") Long idAvaliacao, @RequestParam(name = "id_usuario") Long idUsuario) {

        var usuario = usuarioService.buscarUsuarioPorId(idUsuario).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        var avaliacao = avaliacaoService.buscarPorId(idAvaliacao);

        var  resultadoAvaliacao = resultadoAvaliacaoService.consultarResultadoAvaliacao(avaliacao, usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                        .payload(ResultadoAvaliacaoDTO.builder()
                                .notaFinalAvaliacao(resultadoAvaliacao.getNotaFinal())
                                .nomeDisciplina(avaliacao.getDisciplina().getNome())
                                .build())
                .build());
    }
}
