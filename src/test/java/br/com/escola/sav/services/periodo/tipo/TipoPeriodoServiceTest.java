package br.com.escola.sav.services.periodo.tipo;

import br.com.escola.sav.builders.TipoPeriodoBuilder;
import br.com.escola.sav.entities.periodo.tipo.TipoPeriodo;
import br.com.escola.sav.repositories.periodo.tipo.TipoPeriodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TipoPeriodoServiceTest {

    @InjectMocks
    private TipoPeriodoService tipoPeriodoService;

    @Mock
    private TipoPeriodoRepository tipoPeriodoRepository;

    @Test
    @DisplayName("Deve criar um tipo de período com sucesso quando as informações necessárias forem válidas")
    void criarTipoPeriodo() {
        tipoPeriodoService.criarTipoPeriodo("SEMESTRAL");
        verify(tipoPeriodoRepository,times(1)).save(any(TipoPeriodo.class));
    }

    @Test
    @DisplayName("Deve listar um ou mais tipos de períodos quando existirem")
    void listarTipoPeriodos() {
        var tipoPeriodo = TipoPeriodoBuilder.builder().comId().comNome().build();
        when(tipoPeriodoRepository.findAll()).thenReturn(List.of(tipoPeriodo));

        var tiposPeriodo = tipoPeriodoService.listarTipoPeriodos();

        assertFalse(tiposPeriodo.isEmpty());
    }

    @Test
    @DisplayName("Deve atualizar um tipo de periodo quando existir um periodo para o id informado")
    void atualizar() {
        var tipoPeriodo = TipoPeriodoBuilder.builder().comId().comNome().build();

        when(tipoPeriodoRepository.findById(any())).thenReturn(Optional.of(tipoPeriodo));

        tipoPeriodoService.atualizar("bimestral", 1);

        verify(tipoPeriodoRepository, times(1)).save(any(TipoPeriodo.class));
    }

    @Test
    @DisplayName("Deve deletar todos os tipos de períodos quando existirem")
    void deletarTipos() {
        tipoPeriodoService.deletarTipos();

        verify(tipoPeriodoRepository,times(1)).deleteAll();
    }
}