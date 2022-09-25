package br.com.escola.sav.controllers.avaliacao;

import br.com.escola.sav.dto.avaliacao.AvaliacaoDTO;
import br.com.escola.sav.dto.avaliacao.AvaliacaoQuestaoRequestDTO;
import br.com.escola.sav.dto.avaliacao.AvaliacaoResponseDTO;
import br.com.escola.sav.dto.questao.ItemQuestaoDTO;
import br.com.escola.sav.dto.questao.QuestaoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.periodo.subperiodo.ISubperiodoService;
import br.com.escola.sav.services.questao.IQuestaoService;
import br.com.escola.sav.specifications.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AvaliacaoController {

    private final IAvaliacaoService avaliacaoService;
    private final ISubperiodoService subperiodoService;

    private final IQuestaoService questaoService;

    @PostMapping
    public ResponseEntity<ResponsePattern> registrar(@RequestBody @Validated(AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class) @JsonView(AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class) AvaliacaoDTO avaliacaoDTO) {

        var subperiodo = subperiodoService.buscarSubperiodoPorId(avaliacaoDTO.getIdSubperiodo());

        var avaliacao = new Avaliacao();
        avaliacao.setTitulo(avaliacaoDTO.getTitulo());
        avaliacao.setSubPeriodo(subperiodo);
        avaliacao.setNotaObjetivo(avaliacaoDTO.getNotaObjetivo());
        avaliacao.setDataHoraInicio(avaliacaoDTO.getDataHoraInicio());
        avaliacao.setDataHoraFim(avaliacaoDTO.getDataHoraFim());
        avaliacao.setDataHoraCriacao(LocalDateTime.now());

        avaliacaoService.criarAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Avaliação criada com sucesso!")
                .build());

    }

    @PutMapping
    public ResponseEntity<ResponsePattern> atualizar(@RequestBody @Validated(AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class) @JsonView(AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class) AvaliacaoDTO avaliacaoDTO) {
        var subperiodo = subperiodoService.buscarSubperiodoPorId(avaliacaoDTO.getIdSubperiodo());
        var avaliacao = avaliacaoService.buscarPorId(avaliacaoDTO.getId());

        avaliacao.setTitulo(avaliacaoDTO.getTitulo());
        avaliacao.setSubPeriodo(subperiodo);
        avaliacao.setDataHoraInicio(avaliacaoDTO.getDataHoraInicio());
        avaliacao.setDataHoraFim(avaliacaoDTO.getDataHoraFim());
        avaliacao.setNotaObjetivo(avaliacaoDTO.getNotaObjetivo());
        avaliacao.setDataHoraCriacao(LocalDateTime.now(ZoneId.of("UTC")));

        avaliacaoService.criarAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Avaliação atualizada com sucesso!")
                .build());

    }

   @PutMapping("/questoes")
    public ResponseEntity<ResponsePattern> atacharQuestaoNaAvaliacao(@RequestBody @Validated AvaliacaoQuestaoRequestDTO questoesAvaliacao) {

        var avaliacao = avaliacaoService.buscarPorId(questoesAvaliacao.getIdAvaliacao());
        var questoes = questaoService.listarQuestoesPorId(questoesAvaliacao.getQuestoes());
        avaliacao.setQuestoes(questoes);
        avaliacaoService.criarAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Questões associadas com sucesso nesta avaliação")
                .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePattern> buscarAvaliacaoPorId(@PathVariable("id") Long id, @RequestParam(required = false, name = "com_questoes") Character comQuestoes) {

        var avaliacao = avaliacaoService.buscarPorId(id);

        if(comQuestoes.equals('S')) {

            List<QuestaoDTO> questoes = new ArrayList<>();

            avaliacao.getQuestoes().forEach(questao -> {
                questoes.add(QuestaoDTO.builder()
                        .id(questao.getId())
                        .titulo(questao.getTitulo())
                        .enunciado(questao.getEnunciado())
                        .nomeTipoQuestao(questao.getTipoQuestao().getNome())
                        .nivel(questao.getNivelQuestao().name())
                        .itensQuestao(questao.getItens().stream().map(ItemQuestaoDTO::new).collect(Collectors.toList()))
                        .build());
            });
            var avaliacaoDto = AvaliacaoResponseDTO.builder()
                    .idAvaliacao(avaliacao.getId())
                    .titulo(avaliacao.getTitulo())
                    .dataHoraInicio(avaliacao.getDataHoraInicio().toString())
                    .dataHoraFim(avaliacao.getDataHoraFim().toString())
                    .periodo(avaliacao.getSubPeriodo().getPeriodo().getNome())
                    .subperiodo(avaliacao.getSubPeriodo().getNome())
                    .questoes(questoes)
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder()
                    .httpCode(HttpStatus.OK.value())
                    .payload(avaliacaoDto)
                    .build());

        }



        var avaliacaoDto = AvaliacaoResponseDTO.builder()
                .idAvaliacao(avaliacao.getId())
                .titulo(avaliacao.getTitulo())
                .dataHoraInicio(avaliacao.getDataHoraInicio().toString())
                .dataHoraFim(avaliacao.getDataHoraFim().toString())
                .periodo(avaliacao.getSubPeriodo().getPeriodo().getNome())
                .subperiodo(avaliacao.getSubPeriodo().getNome())
                .questoes(null)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .payload(avaliacaoDto)
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponsePattern> listarQuestoes(SpecificationTemplate.AvaliacaoSpec spec,
                                                          @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable,
                                                          @RequestParam(name = "periodo_id") Integer periodoId) {

        Page<Avaliacao> avaliacaos = avaliacaoService.buscarAvaliacoes(SpecificationTemplate.avaliacoesPeriodoId(periodoId).and(spec),pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .payload(avaliacaos)
                .build());
    }

}
