package br.com.escola.sav.services.periodo.subperiodo;

import br.com.escola.sav.builders.PeriodoBuilder;
import br.com.escola.sav.builders.SubperiodoBuilder;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.repositories.periodo.PeriodoRepository;
import br.com.escola.sav.repositories.periodo.subperiodo.SubperiodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubperiodoServiceTest {

    @InjectMocks
    private SubperiodoService subperiodoService;
    @Mock
    private SubperiodoRepository subperiodoRepository;
    @Mock
    private  PeriodoRepository periodoRepository;

    @Test
    @DisplayName("Deve criar um novo subperiodo quando as informações obrigatórias forem válidas")
    void criarSubperiodo() {
        when(periodoRepository.findById(1)).thenReturn(Optional.of(PeriodoBuilder.builder().comId().comTitulo().comDataInicio().comDataFim().build()));

        subperiodoService.criarSubperiodo("subperiodo1",1, new Date(), new Date());

        verify(subperiodoRepository, times(1)).save(any(SubPeriodo.class));
    }

    @Test
    @DisplayName("Deve atualizar um subperiodo quando existir para o id informado")
    void atualizarSubperiodo() {
        when(periodoRepository.findById(1)).thenReturn(Optional.of(PeriodoBuilder.builder().comId().comTitulo().comDataInicio().comDataFim().build()));

        when(subperiodoRepository.findById(1)).thenReturn(Optional.of(SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build()));

        subperiodoService.atualizarSubperiodo(1, "subperiodo1",1, new Date(), new Date());

        verify(subperiodoRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Deve listar todos os subperíodos de um período quando existirem")
    void listarSubperiodosPorPeriodo() {

        when(subperiodoRepository.findByPeriodoId(1)).thenReturn(List.of(SubperiodoBuilder.builder()
                .comId().comNome().comDataInicio().comDataFim().comPeriodo().build()));

        var resultado = subperiodoService.listarSubperiodosPorPeriodo(1);

        assertFalse(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve excluir um subperíodo por id quando este existir")
    void excluirSubperiodo() {
        when(subperiodoRepository.findById(1)).thenReturn(Optional.of(SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build()));

        subperiodoService.excluirSubperiodo(1);

        verify(subperiodoRepository, times(1)).delete(any(SubPeriodo.class));
    }

    @Test
    @DisplayName("Deve excluir múltiplos subperíodos quando existirem")
    void excluirSubperiodos() {
        var subperiodos = Set.of(SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build());

        subperiodoService.excluirSubperiodos(subperiodos);

        verify(subperiodoRepository, times(1)).deleteAll(any());
    }

    @Test
    @DisplayName("Deve criar mais de um subperíodo quando os dados de entrada forem válidos")
    void criarSubperiodos() {

        var subperiodos = new ArrayList<SubPeriodo>();
        subperiodos.add(SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build());

        subperiodoService.criarSubperiodos(subperiodos);

        verify(subperiodoRepository,times(1)).saveAll(any());
    }

    @Test
    @DisplayName("Deve retornar um subperíodo quando id informado existir")
    void buscarSubperiodoPorId() {
        when(subperiodoRepository.findById(any())).thenReturn(Optional.of(SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build()));

        var subperiodo = subperiodoService.buscarSubperiodoPorId(1);

        assertNotNull(subperiodo);
        assertEquals(1, subperiodo.getId());

    }

    @Test
    @DisplayName("Deve excluir todos os subperíodos quando o id do período informado possuir subperíodos associados a ele")
    void excluirSubperiodosPorIdPeriodo() {
        subperiodoService.excluirSubperiodosPorIdPeriodo(1);
        verify(subperiodoRepository, times(1)).deleteAllByPeriodoId(1);
    }

    @Test
    @DisplayName("Deve atualizar múltiplos subperíodos quando existirem")
    void atualizarSubperiodos() {
        var subperiodos = new ArrayList<SubPeriodo>();
        subperiodos.add(SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build());

        subperiodoService.atualizarSubperiodos(subperiodos);

        verify(subperiodoRepository, times(1)).saveAll(any());
    }
}