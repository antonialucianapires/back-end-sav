package br.com.escola.sav.controllers.avaliacao;

import br.com.escola.sav.dto.avaliacao.AvaliacaoDTO;
import br.com.escola.sav.dto.avaliacao.AvaliacaoQuestaoRequestDTO;
import br.com.escola.sav.dto.avaliacao.AvaliacaoResponseDTO;
import br.com.escola.sav.dto.avaliacao.AvalicaoQuestaoValorObjetivoDTO;
import br.com.escola.sav.dto.questao.ItemQuestaoDTO;
import br.com.escola.sav.dto.questao.QuestaoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurmaId;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.QuestaoValorObjetivo;
import br.com.escola.sav.entities.questao.QuestaoValorObjetivoId;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.disciplina.IDisciplinaService;
import br.com.escola.sav.services.periodo.subperiodo.ISubperiodoService;
import br.com.escola.sav.services.questao.IQuestaoService;
import br.com.escola.sav.services.usuario.IUsuarioService;
import br.com.escola.sav.specifications.AavalicaoSpecification;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AvaliacaoController {

    private final IAvaliacaoService avaliacaoService;
    private final ISubperiodoService subperiodoService;

    private final IQuestaoService questaoService;

    private final IUsuarioService usuarioService;

    private final IDisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<ResponsePattern> registrar(@RequestBody @Validated(AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class) @JsonView(AvaliacaoDTO.AvaliacaoView.CriarAvaliacao.class) AvaliacaoDTO avaliacaoDTO) {

        var usuario = usuarioService.buscarUsuarioPorId(avaliacaoDTO.getUsuarioCriacao()).orElseThrow(() -> new SavException("Usuário não encontrado"));
        var subperiodo = subperiodoService.buscarSubperiodoPorId(avaliacaoDTO.getIdSubperiodo());
        var disciplina = disciplinaService.buscarDisciplinaPorId(avaliacaoDTO.getIdDisciplina());

        var avaliacao = new Avaliacao();
        avaliacao.setTitulo(avaliacaoDTO.getTitulo());
        avaliacao.setSubPeriodo(subperiodo);
        avaliacao.setNotaObjetivo(avaliacaoDTO.getNotaObjetivo());
        avaliacao.setDataHoraInicio(avaliacaoDTO.getDataHoraInicio());
        avaliacao.setDataHoraFim(avaliacaoDTO.getDataHoraFim());
        avaliacao.setDataHoraCriacao(LocalDateTime.now());
        avaliacao.setDisciplina(disciplina);
        avaliacao.setUsuarioCriacao(usuario);

        var avaliacaoCriada = avaliacaoService.criarAvaliacao(avaliacao);

        List<AvaliacaoTurma> avaliacaoTurmas = new ArrayList<>();
        avaliacaoDTO.getTurmas().forEach(turma -> {
            avaliacaoTurmas.add(AvaliacaoTurma.builder()
                            .id(AvaliacaoTurmaId.builder()
                                    .idAvaliacao(avaliacaoCriada.getId())
                                    .idTurma(turma)
                                    .build())
                            .dataHoraCriacao(LocalDateTime.now())
                    .build());
        });

        avaliacaoService.distribuirAvaliacaoParaTurmas(avaliacaoTurmas);


        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Avaliação criada com sucesso!")
                .build());

    }

    @PutMapping
    public ResponseEntity<ResponsePattern> atualizar(@RequestBody @Validated(AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class) @JsonView(AvaliacaoDTO.AvaliacaoView.AtualizarAvaliacao.class) AvaliacaoDTO avaliacaoDTO) {
        var subperiodo = subperiodoService.buscarSubperiodoPorId(avaliacaoDTO.getIdSubperiodo());
        var avaliacao = avaliacaoService.buscarPorId(avaliacaoDTO.getId());
        var disciplina = disciplinaService.buscarDisciplinaPorId(avaliacaoDTO.getIdDisciplina());

        avaliacao.setTitulo(avaliacaoDTO.getTitulo());
        avaliacao.setSubPeriodo(subperiodo);
        avaliacao.setDataHoraInicio(avaliacaoDTO.getDataHoraInicio());
        avaliacao.setDataHoraFim(avaliacaoDTO.getDataHoraFim());
        avaliacao.setNotaObjetivo(avaliacaoDTO.getNotaObjetivo());
        avaliacao.setDisciplina(disciplina);
        avaliacao.setDataHoraCriacao(LocalDateTime.now(ZoneId.of("UTC")));

        avaliacaoService.criarAvaliacao(avaliacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Avaliação atualizada com sucesso!")
                .build());

    }

   @PutMapping("/questoes")
    public ResponseEntity<ResponsePattern> atacharQuestaoNaAvaliacao(@RequestBody @Validated AvaliacaoQuestaoRequestDTO questoesAvaliacao) {

        var idsQuestoes = questoesAvaliacao.getQuestoes().stream().map(AvalicaoQuestaoValorObjetivoDTO::getIdQuestao).collect(Collectors.toList());

        var avaliacao = avaliacaoService.buscarPorId(questoesAvaliacao.getIdAvaliacao());
        var questoes = questaoService.listarQuestoesPorId(idsQuestoes);
        avaliacao.setQuestoes(questoes);
        avaliacaoService.criarAvaliacao(avaliacao);

       salvarValorObjetivoDasQuestoes(questoesAvaliacao, avaliacao, questoes);


       return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .message("Questões associadas com sucesso nesta avaliação")
                .build());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePattern> buscarAvaliacaoPorId(@PathVariable("id") Long id, @RequestParam(required = false, name = "com_questoes") Character comQuestoes) {

        var avaliacao = avaliacaoService.buscarPorId(id);

        var mapaValores = questaoService.buscarValorQuestoes(avaliacao.getId(), avaliacao.getQuestoes().stream().map(Questao::getId).collect(Collectors.toList()));

        if(comQuestoes.equals('S')) {

            List<QuestaoDTO> questoes = new ArrayList<>();

            avaliacao.getQuestoes().forEach(questao -> {

                var valorQuestao = mapaValores.get(questao.getId());

                questoes.add(QuestaoDTO.builder()
                        .id(questao.getId())
                        .titulo(questao.getTitulo())
                        .enunciado(questao.getEnunciado())
                        .nomeTipoQuestao(questao.getTipoQuestao().getNome())
                        .nivel(questao.getNivelQuestao().name())
                                .valorQuestao(valorQuestao.size() == 1 ? valorQuestao.stream().findFirst().get().getValorObjetivo().doubleValue() : null)
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
    public ResponseEntity<ResponsePattern> listarAvaliacoes(AavalicaoSpecification.AvaliacaoSpec spec,
                                                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable,
                                                            @RequestParam(name = "periodo_id") Integer periodoId,
                                                            @RequestParam(name = "usuario_criacao", required = false) Long idUsuario) {

        Page<Avaliacao> avaliacaos = avaliacaoService.buscarAvaliacoes(AavalicaoSpecification.filtroPeriodoId(periodoId)
                .and(spec)
                .and(AavalicaoSpecification.filtroUsuarioCriacao(idUsuario))
                ,pageable);

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .payload(avaliacaos)
                .build());
    }



    private void salvarValorObjetivoDasQuestoes(AvaliacaoQuestaoRequestDTO questoesAvaliacao, Avaliacao avaliacao, Set<Questao> questoes) {
        List<QuestaoValorObjetivo> valoresQuestoes = new ArrayList<>();
        questoes.forEach(questaoDominio -> {
            var questao = questoesAvaliacao.getQuestoes().stream().filter(q -> q.getIdQuestao().equals(questaoDominio.getId())).findFirst();

            questao.ifPresent(avalicaoQuestaoValorObjetivoDTO -> valoresQuestoes.add(QuestaoValorObjetivo.builder()
                    .id(QuestaoValorObjetivoId.builder()
                            .idAvaliacao(avaliacao.getId())
                            .idQuestao(questaoDominio.getId())
                            .build())
                    .valorObjetivo(BigDecimal.valueOf(avalicaoQuestaoValorObjetivoDTO.getValorQuestao()))
                    .dataHoraCriacao(LocalDateTime.now())
                    .build()));

        });

        questaoService.registrarValorDaQuestao(valoresQuestoes);
    }

}

