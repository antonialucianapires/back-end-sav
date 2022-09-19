package br.com.escola.sav.controllers.disciplina;

import br.com.escola.sav.dto.request.compartilhado.ResultView;
import br.com.escola.sav.dto.request.disciplina.DisciplinaRequestDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.entities.disciplina.Disciplina;
import br.com.escola.sav.enums.disciplina.StatusDisciplina;
import br.com.escola.sav.services.disciplina.IDisciplinaService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("/disciplinas")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DisciplinaController {

    @Autowired
    private IDisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<Object> registrar(@RequestBody @Validated(DisciplinaRequestDTO.DisciplinaView.CadastrarDisciplinaPost.class) @JsonView(DisciplinaRequestDTO.DisciplinaView.CadastrarDisciplinaPost.class) DisciplinaRequestDTO disciplinaRequestDTO) {

        if(disciplinaService.existeNome(disciplinaRequestDTO.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ResultView.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message("Já existe uma disciplina com este nome.")
                    .build());
        }

        var disciplina = new Disciplina();
        BeanUtils.copyProperties(disciplinaRequestDTO, disciplina);
        disciplina.setStatusDisciplina(StatusDisciplina.ATIVA);
        disciplina.setDataHoraCriacao((LocalDateTime.now(ZoneId.of("UTC"))));
        disciplina.setDataHoraAtualizacao((LocalDateTime.now(ZoneId.of("UTC"))));

        disciplinaService.cadastrar(disciplina);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResultView.builder()
                .status(HttpStatus.OK.value())
                .message("Disciplina registrada com sucesso!")
                .payload(disciplina)
                .build());

    }

    @GetMapping
    public ResponseEntity<ResponsePattern> listarDisciplinas(@RequestParam(defaultValue = "true", name = "somente_ativos") Boolean somenteAtivos) {

        if(somenteAtivos){
            var disciplinasAtivas = disciplinaService.listarDisciplinasAtivas();
            return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value()).payload(disciplinasAtivas).build());
        }
        var disciplinas = disciplinaService.listarDisciplinas();
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value()).payload(disciplinas).build());
    }
}
