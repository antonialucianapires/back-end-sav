package br.com.escola.sav.services.disciplina;

import br.com.escola.sav.builders.DisciplinaBuilder;
import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.entities.disciplina.DisciplinaDocente;
import br.com.escola.sav.enums.disciplina.StatusDisciplina;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.repositories.disciplina.DisciplinaDocenteRepository;
import br.com.escola.sav.repositories.disciplina.DisciplinaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisciplinaServiceTest {

    @InjectMocks
    private DisciplinaService disciplinaService;
    @Mock
    private DisciplinaRepository repository;
    @Mock
    private DisciplinaDocenteRepository disciplinaDocenteRepository;

    @Test
    @DisplayName("Deve retornar true quando existir uma disciplina com o nome informado")
    void existeNome() {

        var disciplina = DisciplinaBuilder.builder().comId().comNome().build();

        when(repository.existsByNome(any())).thenReturn(true);

        var resultado = disciplinaService.existeNome("Matemática");

        verify(repository, times(1)).existsByNome(disciplina.getNome());
        assertTrue(resultado);

    }

    @Test
    @DisplayName("Deve registrar uma disciplina com sucesso quando todos os dados forem válidos e não nulos")
    void cadastrar() {

        var disciplina = DisciplinaBuilder.builder().comId().comNome().build();
        var captor = ArgumentCaptor.forClass(Disciplina.class);

        disciplinaService.cadastrar(disciplina);
        verify(repository, times(1)).save(captor.capture());

        var disciplinaRegistrada = captor.getValue();
        assertEquals(disciplina,disciplinaRegistrada);


    }

    @Test
    @DisplayName("Deve retornar disciplinas quando existirem disciplinas com status ATIVA")
    void listarDisciplinasAtivas() {
        var disciplina = DisciplinaBuilder.builder().comId().comNome().build();

        when(repository.findAllByStatusDisciplina(StatusDisciplina.ATIVA)).thenReturn(List.of(disciplina));

        var disciplinasAtivas = disciplinaService.listarDisciplinasAtivas();

        verify(repository,times(1)).findAllByStatusDisciplina(StatusDisciplina.ATIVA);
        assertFalse(disciplinasAtivas.isEmpty());

    }

    @Test
    @DisplayName("Deve retornar disciplinas independente do status quando existirem")
    void listarDisciplinas() {
        var disciplina = DisciplinaBuilder.builder().comId().comNome().build();

        when(repository.findAll()).thenReturn(List.of(disciplina));

        var disciplinasResultado = disciplinaService.listarDisciplinas();

        assertEquals(List.of(disciplina),disciplinasResultado);
    }

    @Test
    @DisplayName("Deve associar uma disciplina a uma pessoa docente com sucesso quando ambos existirem")
    void associarDocenteDisciplina() {
        var captor = ArgumentCaptor.forClass(DisciplinaDocente.class);
        disciplinaService.associarDocenteDisciplina(1L, 1L);
        verify(disciplinaDocenteRepository, times(1)).save(captor.capture());
        var associacaoSalva = captor.getValue();
        assertEquals(1L,associacaoSalva.getId().getIdDisciplina());
        assertEquals(1L,associacaoSalva.getId().getIdDocente());

    }

    @Test
    @DisplayName("Deve retornar uma disciplina quando o id informado existir")
    void buscarDisciplinaPorId() {
        var disciplina = DisciplinaBuilder.builder().comId().comNome().build();
        when(repository.findById(any())).thenReturn(Optional.of(disciplina));

        var resultado = disciplinaService.buscarDisciplinaPorId(disciplina.getId());
        assertEquals(disciplina, resultado);
    }
    @Test
    @DisplayName("Deve não retornar uma disciplina quando não existir para o id informado")
    void buscarDisciplinaPorIdComErro() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        var error = assertThrows(ObjectNotFound.class, () -> disciplinaService.buscarDisciplinaPorId(1L));
        assertEquals("Disciplina não encontrada", error.getMessage());
    }

}