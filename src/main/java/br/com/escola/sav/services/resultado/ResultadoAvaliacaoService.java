package br.com.escola.sav.services.resultado;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.questao.resposta.RespostaQuestaoApurada;
import br.com.escola.sav.entities.resultado.ResultadoAvaliacao;
import br.com.escola.sav.entities.usuario.Usuario;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.repositories.resultado.ResultadoAvaliacaoRepository;
import br.com.escola.sav.services.avaliacao.IAvaliacaoService;
import br.com.escola.sav.services.questao.IQuestaoService;
import br.com.escola.sav.services.questao.resposta.IQuestaoRespostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResultadoAvaliacaoService implements IResultadoAvaliacaoService{

    private final ResultadoAvaliacaoRepository resultadoAvaliacaoRepository;
    private final IQuestaoRespostaService questaoRespostaService;
    private final IQuestaoService questaoService;

    @Override
    public ResultadoAvaliacao consultarResultadoAvaliacao(Avaliacao avaliacao, Usuario usuario) {

        var idQuestoes = avaliacao.getQuestoes().stream().map(Questao::getId).collect(Collectors.toList());

        var respostasAvaliacao = questaoRespostaService.buscarRespostasAvaliacao(avaliacao.getId(),usuario.getId(),idQuestoes);

        if(!respostasAvaliacao.stream().allMatch(resposta -> resposta.getIndicadorCorrecao().equals('S') || idQuestoes.size() != respostasAvaliacao.size())) {
            throw new SavException("Não encotramos resultado disponível");
        }
        return resultadoAvaliacaoRepository.findByAvaliacaoIdAndUsuarioId(avaliacao.getId(), usuario.getId()).orElseThrow(() -> new ObjectNotFound("Não encotramos resultado disponível"));

    }

    @Override
    public void cadastrarResultado(Avaliacao avaliacao, Usuario usuario) {
        var idQuestoes = avaliacao.getQuestoes().stream().map(Questao::getId).collect(Collectors.toList());

        var mapaValores = questaoService.buscarValorQuestoes(avaliacao.getId(), idQuestoes);

        var respostasAvaliacao = questaoRespostaService.buscarMapaRespostasAvaliacao(avaliacao.getId(),usuario.getId(),idQuestoes);

        AtomicReference<Double> notalFinal = new AtomicReference<>(0.0);
        avaliacao.getQuestoes().forEach(questao -> {
            var valorQuestao = 0;

            var valorObjetivoQuestao = mapaValores.get(questao.getId()).stream().findFirst();

            var itemGabarito = questao.getItens().stream().filter(i -> i.getIndicadorGabarito().equals('S')).findFirst();

            if(itemGabarito.isPresent()) {

                var itemSelecionado = respostasAvaliacao.get(questao.getId()).stream().map(RespostaQuestaoApurada::getItemQuestao).findFirst();

                if(itemSelecionado.isPresent()) {

                    if(itemGabarito.get().getId().equals(itemSelecionado.get().getId()) && valorObjetivoQuestao.isPresent()) {

                        notalFinal.set(notalFinal.get() + (valorObjetivoQuestao.get().getValorObjetivo().doubleValue()));

                    }
                }
            }


        });

        resultadoAvaliacaoRepository.save(ResultadoAvaliacao.builder()
                        .avaliacao(avaliacao)
                        .usuario(usuario)
                        .notaFinal(notalFinal.get())
                        .dataCriacao(LocalDateTime.now())
                .build());
    }
}
