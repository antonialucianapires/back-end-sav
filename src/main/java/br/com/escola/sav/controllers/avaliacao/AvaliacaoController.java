package br.com.escola.sav.controllers.avaliacao;

import br.com.escola.sav.dto.avaliacao.AvaliacaoDTO;
import br.com.escola.sav.dto.request.compartilhado.ResultView;
import br.com.escola.sav.dto.request.disciplina.DisciplinaRequestDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.periodo.subperiodo.ISubperiodoService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/avaliacoes")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AvaliacaoController {

    private final IAvaliacaoService avaliacaoService;
    private final ISubperiodoService subperiodoService;

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
}
