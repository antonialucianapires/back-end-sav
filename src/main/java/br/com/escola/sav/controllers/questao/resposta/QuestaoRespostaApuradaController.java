package br.com.escola.sav.controllers.questao.resposta;

import br.com.escola.sav.dto.questao.resposta.RespostaQuestaoDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.questao.ItemQuestao;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.questao.IQuestaoService;
import br.com.escola.sav.services.questao.resposta.IQuestaoRespostaService;
import br.com.escola.sav.services.resultado.IResultadoAvaliacaoService;
import br.com.escola.sav.services.usuario.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/questoes")
@RequiredArgsConstructor
public class QuestaoRespostaApuradaController {

    private final IQuestaoRespostaService questaoRespostaService;
    private final IAvaliacaoService avaliacaoService;
    private final IUsuarioService usuarioService;

    private final IQuestaoService questaoService;

    private final IResultadoAvaliacaoService resultadoAvaliacaoService;

    @PostMapping("/respostas")
    public ResponseEntity<ResponsePattern> registrarRespostaQuestao(@RequestBody @Validated RespostaQuestaoDTO respostaQuestaoDTO) {

        var usuario = usuarioService.buscarUsuarioPorId(respostaQuestaoDTO.getIdUsuario()).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        var avaliacao = avaliacaoService.buscarPorId(respostaQuestaoDTO.getIdAvaliacao());

        var questoes = avaliacao.getQuestoes();

        List<RespostaQuestaoApurada> respostas = new ArrayList<>();

        respostaQuestaoDTO.getRespostas().forEach(resposta -> {

            AtomicBoolean estaCorreta = new AtomicBoolean(false);

            var questao = questoes.stream().filter(q -> q.getId().equals(resposta.getIdQuestao())).findFirst();


            if(questao.isPresent() && !questao.get().getItens().isEmpty() && questao.get().getItens().stream().anyMatch(item -> item.getId().equals(resposta.getIdItemSelecionado()))) {

                questao.get().getItens().stream().filter(item -> item.getId().equals(resposta.getIdItemSelecionado())).findFirst().ifPresent(item -> {
                    estaCorreta.set(item.getIndicadorGabarito().equals('S'));
                });

            }

            respostas.add(RespostaQuestaoApurada.builder()
                    .usuario(usuario)
                    .avaliacao(avaliacao)
                    .questao(Questao.builder().id(resposta.getIdQuestao()).build())
                    .itemQuestao(resposta.getIdItemSelecionado() == null ? null : ItemQuestao.builder().id(resposta.getIdItemSelecionado()).build())
                    .textoRespostaLivre(resposta.getTextoItem() == null ? null : resposta.getTextoItem())
                    .indicadorAcerto(estaCorreta.get() ? 'S' : 'N')
                    .indicadorCorrecao(estaCorreta.get() ? 'S' : 'N')
                    .dataCriacao(LocalDateTime.now())
                    .build());
        });

        questaoRespostaService.salvarRespostas(respostas);

        resultadoAvaliacaoService.cadastrarResultado(avaliacao, usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder()
                .httpCode(HttpStatus.CREATED.value())
                        .message("respostas salvas com sucesso.")
                .build());

    }
}
