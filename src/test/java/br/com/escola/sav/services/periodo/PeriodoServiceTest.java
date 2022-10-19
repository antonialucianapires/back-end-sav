package br.com.escola.sav.services.periodo;

import br.com.escola.sav.builders.PeriodoBuilder;
import br.com.escola.sav.builders.SubperiodoBuilder;
import br.com.escola.sav.builders.TipoPeriodoBuilder;
import br.com.escola.sav.builders.TurmaBuilder;
import br.com.escola.sav.dto.request.periodo.subperiodo.SubperiodoRequestDTO;
import br.com.escola.sav.entities.periodo.Periodo;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.exception.SavException;
import br.com.escola.sav.repositories.periodo.PeriodoRepository;
import br.com.escola.sav.repositories.periodo.tipo.TipoPeriodoRepository;
import br.com.escola.sav.services.periodo.subperiodo.ISubperiodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PeriodoServiceTest {

    @InjectMocks
    private PeriodoService periodoService;
    @Mock
    private TipoPeriodoRepository tipoPeriodoRepository;
    @Mock
    private PeriodoRepository periodoRepository;
    @Mock
    private ISubperiodoService subperiodoService;

    @Captor
    private ArgumentCaptor<ArrayList<SubPeriodo>> subperiodoCaptor;

    @Captor
    private ArgumentCaptor<Periodo> periodoArgumentCaptor;


    @Test
    @DisplayName("Deve criar um período quando os subperíodos também são informados")
    void criarPeriodoComSucesso() {

        var subperiodo1 = new SubperiodoRequestDTO();
        subperiodo1.setNomeSubperiodo("subperíodo 1");
        subperiodo1.setDataInicio(new Date());
        subperiodo1.setDataFim(new Date());

        var subperiodos = new ArrayList<SubperiodoRequestDTO>();
        subperiodos.add(subperiodo1);

        when(tipoPeriodoRepository.findById(anyInt())).thenReturn(Optional.of(TipoPeriodoBuilder.builder().comId().build()));

        periodoService.criarPeriodo("Periodo 2022", new Date(), new Date(), 1, subperiodos);

        verify(periodoRepository, times(1)).save(any(Periodo.class));
        verify(subperiodoService, times(1)).criarSubperiodos(any());

    }

    @Test
    @DisplayName("Não deve criar período quando não informarem os subperíodos")
    void naoCriaPeriodoSemSubperiodo() {
        when(tipoPeriodoRepository.findById(anyInt())).thenReturn(Optional.of(TipoPeriodoBuilder.builder().comId().build()));
        assertThrows(SavException.class, () -> periodoService.criarPeriodo("Periodo 2022", new Date(), new Date(), 1, new ArrayList<>()));
    }

    @Test
    @DisplayName("Deve retornar todos os períodos com subperíodos quando a flag com subperíodos for igual a true")
    void consultarPeriodos() throws ParseException {

        var periodo = PeriodoBuilder.builder().comId().comTitulo().comDataInicio().comDataFim().comTipoPeriodo().build();
        var subperiodo = SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build();


        periodo.setSubperiodos(Set.of(subperiodo));

        when(periodoRepository.findAll()).thenReturn(List.of(periodo));

        var periodoComSubperiodos = periodoService.consultarPeriodos(true);

        assertFalse(periodoComSubperiodos.isEmpty());
    }

    @Test
    @DisplayName("Deve retornar todos os períodos sem subperíodos quando a flag com subperíodos for igual a false")
    void consultarPeriodosSemSubperiodos() {

        var periodo = PeriodoBuilder.builder().comId().comTitulo().comDataInicio().comDataFim().comTipoPeriodo().build();
        var subperiodo = SubperiodoBuilder.builder().comId().comNome().comDataInicio().comDataFim().comPeriodo().build();
        periodo.setSubperiodos(Set.of(subperiodo));

        when(periodoRepository.findAll()).thenReturn(List.of(periodo));

        var periodoComSubperiodos = periodoService.consultarPeriodos(false);

        assertFalse(periodoComSubperiodos.isEmpty());
    }

    @Test
    void atualizarPeriodoSemSubperiodos() {
        var periodoAnterior = PeriodoBuilder.builder()
                .comId().comTitulo().comDataInicio().comDataFim().comTipoPeriodo().semSubperiodos()
                .build();

        var periodoAtualizado = PeriodoBuilder.builder()
                .comId().comTitulo().comDataInicio().comDataFim().comTipoPeriodo().semSubperiodos()
                .build();
        periodoAtualizado.setNome("Periodo 2022");
        when(tipoPeriodoRepository.findById(anyInt())).thenReturn(Optional.of(TipoPeriodoBuilder.builder().comId().build()));
        when(periodoRepository.findById(anyInt())).thenReturn(Optional.of(periodoAnterior));
        when(periodoRepository.save(any())).thenReturn(periodoAtualizado);

        var periodoAtualizadoDto = periodoService.atualizarPeriodo(1, "Periodo 2022", new Date(), new Date(), 1, new ArrayList<>());

        verify(periodoRepository, times(1)).save(any());

        assertEquals("Periodo 2022", periodoAtualizadoDto.getNomePeriodo());

    }

    @Test
    @DisplayName("Deve excluir um periodo e seus subperiodos associados quando o periodo solicitado existir e não houver turmas relacionadas")
    void excluirPeriodo() {
        var periodo = PeriodoBuilder.builder()
                .comId().comTitulo().comDataInicio().comDataFim().comTipoPeriodo().comSubperiodos()
                .build();

        periodo.setTurmas(new HashSet<>());

        when(periodoRepository.findById(anyInt())).thenReturn(Optional.of(periodo));

        periodoService.excluirPeriodo(1);

        verify(subperiodoService, times(1)).excluirSubperiodosPorIdPeriodo(any(Integer.class));
        verify(periodoRepository, times(1)).delete(any(Periodo.class));
    }

    @Test
    @DisplayName("Não deve excluir um periodo e seus subperiodos associados quando o periodo solicitado possuir turmas relacionadas")
    void naoExcluirPeriodoComTurmas() {
        var periodo = PeriodoBuilder.builder()
                .comId().comTitulo().comDataInicio().comDataFim().comTipoPeriodo().comSubperiodos()
                .build();

        var turma = TurmaBuilder.builder().comId().comTitulo().comPeriodo().build();

        periodo.setTurmas(Set.of(turma));

        when(periodoRepository.findById(anyInt())).thenReturn(Optional.of(periodo));

        assertThrows(SavException.class, () -> periodoService.excluirPeriodo(1));


    }

    @Test
    @DisplayName("Deve retornar um periodo quando o id informado existir")
    void recuperarPeriodoPorId() {

        var periodo = PeriodoBuilder.builder().comId().comTitulo().comTipoPeriodo().comDataFim().comDataInicio().comSubperiodos().build();

        when(periodoRepository.findById(anyInt())).thenReturn(Optional.of(periodo));

        var resultadoDto = periodoService.recuperarPeriodoPorId(1, true);

        assertFalse(resultadoDto.getSubperiodos().isEmpty());
        assertEquals(periodo.getId(),resultadoDto.getId());
        assertEquals(periodo.getNome(), resultadoDto.getNomePeriodo());

    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro quando não existir período para o id informado")
    void naoRecuperarPeriodoPorIdQuandoNaoExistir() {

        when(periodoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> periodoService.recuperarPeriodoPorId(1, true));
    }

    @Test
    @DisplayName("Deve retornar uma entidade periodo quando o id informado existir")
    void testRecuperarPeriodoPorId() {

        var periodo = PeriodoBuilder.builder().comId().comTitulo().comTipoPeriodo().comDataFim().comDataInicio().comSubperiodos().build();
        when(periodoRepository.findById(anyInt())).thenReturn(Optional.of(periodo));

        var periodoResultado = periodoService.recuperarPeriodoPorId(1);

        assertEquals(periodo,periodoResultado);
    }

    @Test
    @DisplayName("Deve retornar uma mensagem de erro quando não existir uma entidade período para o id informado")
    void naoRecuperarPeriodoEntidadePorIdQuandoNaoExistir() {

        when(periodoRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFound.class, () -> periodoService.recuperarPeriodoPorId(1));
    }
}