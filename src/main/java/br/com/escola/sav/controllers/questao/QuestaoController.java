package br.com.escola.sav.controllers.questao;

import br.com.escola.sav.dto.questao.QuestaoDTO;
import br.com.escola.sav.dto.questao.QuestaoResumoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.questao.ItemQuestao;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.enums.questao.NivelQuestao;
import br.com.escola.sav.services.questao.IItemQuestaoService;
import br.com.escola.sav.services.questao.IQuestaoService;
import br.com.escola.sav.services.questao.ITipoQuestaoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/questoes")
@RequiredArgsConstructor
@Log4j2
public class QuestaoController {

    private final IQuestaoService questaoService;
    private final ITipoQuestaoService tipoQuestaoService;

    private final IItemQuestaoService itemQuestaoService;

    @PostMapping
    public ResponseEntity<ResponsePattern> criarQuestao(@RequestBody @Validated(QuestaoDTO.QuestaoView.CriarQuestao.class) @JsonView(QuestaoDTO.QuestaoView.CriarQuestao.class) QuestaoDTO questaoDTO) {

        var tipoQuestao = tipoQuestaoService.buscarTipoQuestaoPorId(questaoDTO.getTipoQuestao());

        var questao = Questao.builder()
                .titulo(questaoDTO.getTitulo())
                .enunciado(questaoDTO.getEnunciado())
                .nivelQuestao(NivelQuestao.valueOf(questaoDTO.getNivel()))
                .tipoQuestao(tipoQuestao)
                .dataHoraCriacao(LocalDateTime.now())
                        .build();

        Questao questaoSalva = questaoService.criarQuestao(questao);

        List<ItemQuestao> itensQuestao = new ArrayList<>();
        if(!questaoDTO.getItensQuestao().isEmpty()) {
            questaoDTO.getItensQuestao().forEach(itemDto -> {
                itensQuestao.add(ItemQuestao.builder()
                        .descricao(itemDto.getDescricao())
                        .questao(questaoSalva)
                        .indicadorGabarito(itemDto.getIndicadorGabarito())
                        .dataHoraCriacao(LocalDateTime.now())
                        .build());

                log.info(itemDto);

            });

            itemQuestaoService.salvarItens(itensQuestao);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder().httpCode(HttpStatus.CREATED.value())
                .message("Questão criada com sucesso")
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponsePattern> listarQuestoes(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "false", required = false) Boolean semItens) {

        Page<Questao> questoes = questaoService.listarQuestoes(pageable);

        if(semItens) {
            var questoesDTO = questoes.getContent().stream().map(QuestaoResumoDTO::new).collect(Collectors.toList());

            Page<QuestaoResumoDTO> page = new PageImpl<>(questoesDTO, pageable,questoesDTO.size());

            return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                    .payload(page)
                    .build());
        }

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                        .payload(questoes)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePattern> buscarQuestaoPorId(@PathVariable("id") Long idQuestao) {
        var questao = questaoService.buscarPorId(idQuestao);
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .payload(questao)
                .build());
    }

}
