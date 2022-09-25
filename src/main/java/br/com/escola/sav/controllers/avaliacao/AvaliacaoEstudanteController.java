package br.com.escola.sav.controllers.avaliacao;

import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.turma.ITurmaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudante")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AvaliacaoEstudanteController {

    private final IAvaliacaoService avaliacaoService;

    private final ITurmaService turmaService;

    @GetMapping("/{id_estudante}/avaliacoes")
    public ResponseEntity<ResponsePattern> listarAvaliacoesEstudante(@PathVariable("id_estudante") Long idUsuarioEstudante, @RequestParam("id_periodo") Integer idPeriodo) {
        var turma = turmaService.buscarTurmaPorUsuarioEPeriodo(idUsuarioEstudante, idPeriodo);
        var avaliacoes = avaliacaoService.buscarAvaliacoesPorTurma(turma);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .payload(avaliacoes)
                .build());
    }
}
