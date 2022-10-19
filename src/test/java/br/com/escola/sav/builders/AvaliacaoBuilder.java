package br.com.escola.sav.builders;

import br.com.escola.sav.entities.avaliacao.Avaliacao;
import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.entities.periodo.subperiodo.SubPeriodo;
import br.com.escola.sav.entities.questao.Questao;
import br.com.escola.sav.entities.usuario.Usuario;

import java.time.LocalDateTime;
import java.util.Set;

public class AvaliacaoBuilder {

    private Avaliacao avaliacao;

    public  AvaliacaoBuilder () {
        this.avaliacao = new Avaliacao();
        this.avaliacao.setDataHoraCriacao(LocalDateTime.now());
    }

    public static AvaliacaoBuilder builder() {
        return new AvaliacaoBuilder();
    }

    public AvaliacaoBuilder comId() {
        this.avaliacao.setId(1L);
        return this;
    }

    public AvaliacaoBuilder comTitulo() {
        this.avaliacao.setTitulo("Titulo avaliação");
        return this;
    }

    public AvaliacaoBuilder comNotaObjetivo() {
        this.avaliacao.setNotaObjetivo(10.0);
        return this;
    }

    public AvaliacaoBuilder comDataInicio() {
        this.avaliacao.setDataHoraInicio(LocalDateTime.now());
        return this;
    }

    public AvaliacaoBuilder comDataFim() {
        this.avaliacao.setDataHoraInicio(LocalDateTime.now());
        return this;
    }

    public AvaliacaoBuilder comSubperiodo() {
        this.avaliacao.setSubPeriodo(SubperiodoBuilder.builder()
                        .comId().comNome().comDataInicio().comDataFim().comPeriodo()
                .build());
        return this;
    }

    public AvaliacaoBuilder comUsuarioCriacao(Usuario usuarioCriacao) {
        this.avaliacao.setUsuarioCriacao(usuarioCriacao);
        return this;
    }

    public AvaliacaoBuilder comDisciplina(Disciplina disciplina) {
        this.avaliacao.setDisciplina(disciplina);
        return this;
    }

    public AvaliacaoBuilder comQuestoes(Set<Questao> questoes) {
        this.avaliacao.setQuestoes(questoes);
        return this;
    }

    public Avaliacao build() {
        return this.avaliacao;
    }


}
