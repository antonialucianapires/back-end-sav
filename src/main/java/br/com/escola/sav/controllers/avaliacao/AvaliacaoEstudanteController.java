package br.com.escola.sav.controllers.avaliacao;

import br.com.escola.sav.dto.avaliacao.AvaliacaoEstudanteResponseDTO;
import br.com.escola.sav.dto.avaliacao.AvaliacaoResponseDTO;
import br.com.escola.sav.dto.questao.ItemQuestaoDTO;
import br.com.escola.sav.dto.questao.QuestaoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.turma.ITurmaService;
import br.com.escola.sav.services.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/estudante")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AvaliacaoEstudanteController {

    private final IAvaliacaoService avaliacaoService;

    private final ITurmaService turmaService;

    private final IUsuarioService usuarioService;

    @GetMapping("/{id_estudante}/avaliacoes")
    public ResponseEntity<ResponsePattern> listarAvaliacoesEstudante(@PathVariable("id_estudante") Long idUsuarioEstudante, @RequestParam("id_periodo") Integer idPeriodo) {
        var turma = turmaService.buscarTurmaPorUsuarioEPeriodo(idUsuarioEstudante, idPeriodo);
        var avaliacoes = avaliacaoService.buscarAvaliacoesPorTurma(turma);

        avaliacoes.forEach(avaliacao -> {
            var status = "";

            if(LocalDateTime.now().isBefore(avaliacao.getDataHoraInicio())) {
                status = "não liberada";
            } else if(avaliacao.getDataHoraInicio().equals(LocalDateTime.now()) || avaliacao.getDataHoraInicio().isBefore(avaliacao.getDataHoraFim())) {
                status = "liberada";
            } else if(avaliacao.getDataHoraFim().isBefore(LocalDateTime.now())) {
                status = "encerrada";
            }

            avaliacao.setStatus(status);
        });

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .payload(avaliacoes)
                .build());
    }

    @GetMapping("/{id_estudante}/avaliacoes/{id_avaliacao}")
    public ResponseEntity<ResponsePattern> visualizarAvaliacaoLiberada(@PathVariable("id_avaliacao") Long idAvaliacao, @PathVariable("id_estudante") Long idUsuarioEstudante) {

        var estudante = usuarioService.buscarUsuarioPorId(idUsuarioEstudante).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        var avaliacao = avaliacaoService.buscarPorId(idAvaliacao);

        var docente = usuarioService.buscarUsuarioPorId(avaliacao.getUsuarioCriacao().getId()).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        var turma = turmaService.buscarTurmaPorUsuarioEPeriodo(estudante.getId(), avaliacao.getSubPeriodo().getPeriodo().getId());

        if(LocalDateTime.now().isBefore(avaliacao.getDataHoraInicio())) {
            throw new SavException("A avaliação " + avaliacao.getTitulo()+ "ainda não está liberada.");
        }

        List<QuestaoDTO> questoes = new ArrayList<>();

        avaliacao.getQuestoes().forEach(questao -> {
            questoes.add(QuestaoDTO.builder()
                    .id(questao.getId())
                    .titulo(questao.getTitulo())
                    .enunciado(questao.getEnunciado())
                    .nomeTipoQuestao(questao.getTipoQuestao().getNome())
                    .nivel(questao.getNivelQuestao().name())
                    .itensQuestao(questao.getItens().stream().map(item -> {
                        var itemDto = new ItemQuestaoDTO(item);
                        itemDto.setIndicadorGabarito(null);
                        return itemDto;
                    }).collect(Collectors.toList()))
                    .build());
        });

        var avaliacaoDto = AvaliacaoEstudanteResponseDTO.builder()
                .tituloAvaliacao(avaliacao.getTitulo())
                .nomeEstudante(estudante.getNome())
                .matriculaEstudante(estudante.getMatricula())
                .nomeTurma(turma.getNome())
                .nomeDocente(docente.getNome())
                .questoes(questoes)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder()
                .httpCode(HttpStatus.OK.value())
                .payload(avaliacaoDto)
                .build());
    }
}
