package br.com.escola.sav.controllers.questao;

import br.com.escola.sav.dto.questao.QuestaoDTO;
import br.com.escola.sav.dto.questao.QuestaoResumoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.questao.ItemQuestao;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.TipoQuestao;
import br.com.escola.sav.enums.questao.NivelQuestao;
import br.com.escola.sav.services.questao.IItemQuestaoService;
import br.com.escola.sav.services.questao.IQuestaoService;
import br.com.escola.sav.services.questao.ITipoQuestaoService;
import br.com.escola.sav.specifications.QuestaoSpecification;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
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
    public ResponseEntity<ResponsePattern> listarQuestoes(QuestaoSpecification.QuestaoSpec questoaSpec, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable, @RequestParam(defaultValue = "false", required = false) Boolean semItens,
                                                          @RequestParam(required = false, defaultValue = "") List<String> tipos) {


        List<Long> listaTipos = tipoQuestaoService.listarTipos().stream().map(TipoQuestao::getId).collect(Collectors.toList());

        if(tipos != null && !tipos.isEmpty()) {
            listaTipos = tipos.stream().map(Long::valueOf).collect(Collectors.toList());
        }


        Page<Questao> questoes = questaoService.listarQuestoes(pageable, QuestaoSpecification.filtroPorTipoQuestao(listaTipos).and(questoaSpec), new ArrayList<>());

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

    @PutMapping
    public ResponseEntity<ResponsePattern> atualizarQuestao(@RequestBody @Validated(QuestaoDTO.QuestaoView.AtualizarQuestao.class) @JsonView(QuestaoDTO.QuestaoView.AtualizarQuestao.class) QuestaoDTO questaoDTO) {
        var questao = questaoService.buscarPorId(questaoDTO.getId());
        var tipoQuestao = tipoQuestaoService.buscarTipoQuestaoPorId(questaoDTO.getTipoQuestao());

        questao.setTitulo(questaoDTO.getTitulo());
        questao.setEnunciado(questaoDTO.getEnunciado());
        questao.setNivelQuestao(NivelQuestao.valueOf(questaoDTO.getNivel()));
        questao.setTipoQuestao(tipoQuestao);

        List<ItemQuestao> itensQuestao = new ArrayList<>();
        if(!questaoDTO.getItensQuestao().isEmpty()) {
            questaoDTO.getItensQuestao().forEach(itemDto -> {
                itensQuestao.add(ItemQuestao.builder()
                        .id(itemDto.getId())
                        .descricao(itemDto.getDescricao())
                        .questao(questao)
                        .indicadorGabarito(itemDto.getIndicadorGabarito())
                        .dataHoraCriacao(LocalDateTime.now())
                        .build());

            });

            questao.setItens(itensQuestao);
            itemQuestaoService.salvarItens(itensQuestao);
        }

        questaoService.criarQuestao(questao);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                        .message("Questão atualizada com sucesso")
                .payload(questao)
                .build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePattern> atualizarQuestao(@PathVariable("id") Long idQuestao) {

        var questao = questaoService.buscarPorId(idQuestao);

        questaoService.deletarQuestao(questao);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .message("Questão deletada com sucesso")
                .build());
    }
}
