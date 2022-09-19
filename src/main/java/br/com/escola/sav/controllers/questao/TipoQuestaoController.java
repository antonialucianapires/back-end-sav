package br.com.escola.sav.controllers.questao;

import br.com.escola.sav.dto.questao.TipoQuestaoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.questao.TipoQuestao;
import br.com.escola.sav.services.questao.ITipoQuestaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/questoes/tipos")
@RequiredArgsConstructor
public class TipoQuestaoController {

    private final ITipoQuestaoService tipoQuestaoService;

    @PostMapping
    public ResponseEntity<ResponsePattern> criarTipoQuestao(@RequestBody @Validated(TipoQuestaoDTO.TipoQuestaoView.CriarTipoQuestao.class) @JsonView(TipoQuestaoDTO.TipoQuestaoView.CriarTipoQuestao.class) TipoQuestaoDTO tipoQuestaoDTO) {
        var tipoQuestao = TipoQuestao.builder()
                .nome(tipoQuestaoDTO.getNome())
                .dataHoraCriacao(LocalDateTime.now())
                .build();

        tipoQuestaoService.salvar(tipoQuestao);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder().httpCode(HttpStatus.CREATED.value())
                .message("Tipo de questão criado com sucesso")
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponsePattern> listarTipos() {
        var tipos = tipoQuestaoService.listarTipos();
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                        .payload(tipos)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePattern> buscarTipoPorId(@PathVariable("id") Long id){
        var tipoQuestao = tipoQuestaoService.buscarTipoQuestaoPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                        .payload(tipoQuestao)
                .build());
    }

    @PutMapping
    public ResponseEntity<ResponsePattern> atualizarTipo(@RequestBody @Validated(TipoQuestaoDTO.TipoQuestaoView.AtualizarTipoQuestao.class) @JsonView(TipoQuestaoDTO.TipoQuestaoView.AtualizarTipoQuestao.class) TipoQuestaoDTO tipoQuestaoDTO) {
        var tipoQuestao = tipoQuestaoService.buscarTipoQuestaoPorId(tipoQuestaoDTO.getId());

        tipoQuestao.setNome(tipoQuestaoDTO.getNome());

        tipoQuestaoService.salvar(tipoQuestao);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .message("Tipo de questão atualizado com sucesso")
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePattern> excluirTipo(@PathVariable("id") Long id){
        var tipoQuestao = tipoQuestaoService.buscarTipoQuestaoPorId(id);

        if(!tipoQuestao.getQuestoes().isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResponsePattern.builder().httpCode(HttpStatus.CONFLICT.value())
                    .message("O tipo de questão não pode ser excluído porque está agrupando questões")
                    .build());
        }

        tipoQuestaoService.deletar(tipoQuestao);
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .message("O tipo de questão foi excluído com sucesso")
                .build());
    }
}
