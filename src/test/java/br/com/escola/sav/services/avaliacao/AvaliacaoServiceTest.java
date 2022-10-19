package br.com.escola.sav.services.avaliacao;

import br.com.escola.sav.builders.AvaliacaoBuilder;
import br.com.escola.sav.builders.TurmaBuilder;
import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurma;
import br.com.escola.sav.entities.avaliacao.AvaliacaoTurmaId;
import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoRepository;
import br.com.escola.sav.repositories.avaliacao.AvaliacaoTurmaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvaliacaoServiceTest {

    @InjectMocks
    private AvaliacaoService avaliacaoService;
    @Mock
    private AvaliacaoRepository repository;
    @Mock
    private AvaliacaoTurmaRepository avaliacaoTurmaRepository;
    @Captor
    private ArgumentCaptor<Avaliacao> avaliacaoArgumentCaptor;

    @Test
    @DisplayName("Deve criar uma nova avaliação quando todos os dados são válidos")
    void deveCriarAvaliacaoComDadosValidos() {

        var avaliacao = AvaliacaoBuilder.builder()
                .comId()
                .comTitulo()
                .comNotaObjetivo()
                .comDataInicio()
                .comDataFim()
                .comSubperiodo()
                .build();

        avaliacaoService.criarAvaliacao(avaliacao);

        verify(repository, times(1)).saveAndFlush(avaliacaoArgumentCaptor.capture());

        var avaliacaoCapturada = avaliacaoArgumentCaptor.getValue();

        assertEquals(avaliacao,avaliacaoCapturada);
    }

    @Test
    @DisplayName("Deve retornar uma avaliação quando o id informado existir")
    void buscarPorIdComSucesso() {

        var avaliacao = AvaliacaoBuilder.builder()
                .comId()
                .comTitulo()
                .comNotaObjetivo()
                .comDataInicio()
                .comDataFim()
                .comSubperiodo()
                .build();

        when(repository.findById(anyLong())).thenReturn(Optional.of(avaliacao));

        var avaliacaoGerada = avaliacaoService.buscarPorId(1L);

        assertEquals(avaliacao,avaliacaoGerada);
    }

    @Test
    @DisplayName("Deve retornar erro quando o id informado não existir")
    void buscarPorIdComErroNaoEncontrado() {

        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        var erro = assertThrows(ObjectNotFound.class, () -> avaliacaoService.buscarPorId(1L));

        assertEquals("Avaliação não encontrada", erro.getMessage());

    }

    @Test
    @DisplayName("Deve retornar todas as avaliações com paginação quando existirem")
    void buscarAvaliacoes() {

        var listaAvaliacoes = new ArrayList<Avaliacao>();
        var pageable = PageRequest.of(1,1);
        var specification = mock(Specification.class);

        listaAvaliacoes.add(AvaliacaoBuilder.builder()
                        .comId().comTitulo().comSubperiodo().comDataFim().comDataInicio().comNotaObjetivo().comDisciplina(new Disciplina())
                .build());

        Page<Avaliacao> page = new PageImpl<>(listaAvaliacoes, pageable, listaAvaliacoes.size());

        when(repository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(page);

        var resultado = avaliacaoService.buscarAvaliacoes(specification,pageable);

        verify(repository, times(1)).findAll(specification, pageable);
        assertEquals(page,resultado);
    }

    @Test
    @DisplayName("Deve distribuir uma avaliação para todas as turmas elegívies a mesma com sucesso")
    void distribuirAvaliacaoParaTurmas() {
        var avaliacoes = List.of(AvaliacaoTurma.builder()
                .id(AvaliacaoTurmaId.builder()
                        .idAvaliacao(2L)
                        .idTurma(2L)
                        .build())
                .dataHoraCriacao(LocalDateTime.now())
                .build());

        avaliacaoService.distribuirAvaliacaoParaTurmas(avaliacoes);

        verify(avaliacaoTurmaRepository, times(1)).saveAll(avaliacoes);

    }

    @Test
    @DisplayName("Deve retornar avaliações de uma turma quando existir avaliações associadas a turma informada")
    void buscarAvaliacoesPorTurma() {
        var turma = TurmaBuilder.builder()
                .comId().comTitulo().comPeriodo()
                .build();

        var avaliacaoTurma = AvaliacaoTurma.builder()
                .id(AvaliacaoTurmaId.builder()
                        .idAvaliacao(1L)
                        .idTurma(1L)
                        .build())
                .dataHoraCriacao(LocalDateTime.now()).build();

        when(avaliacaoTurmaRepository.findByIdIdTurma(any())).thenReturn(List.of(avaliacaoTurma));
        when(repository.findAllById(Set.of(1L))).thenReturn(List.of(AvaliacaoBuilder.builder().build()));

        var resultado = avaliacaoService.buscarAvaliacoesPorTurma(turma);

        verify(repository, times(1)).findAllById(Set.of(1L));
        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar todas as avaliações sem paginação quando existirem")
    void listarAvaliacoes() {
        var avaliacoes = List.of(AvaliacaoBuilder.builder().build());

        when(repository.findAll()).thenReturn(avaliacoes);

        var reusltado = avaliacaoService.listarAvaliacoes();

        assertEquals(avaliacoes, reusltado );
    }
}