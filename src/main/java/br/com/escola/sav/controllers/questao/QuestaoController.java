package br.com.escola.sav.controllers.questao;

import br.com.escola.sav.dto.questao.QuestaoDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

}
