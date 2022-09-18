package br.com.escola.sav.controllers.turma;

import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.dto.turma.TurmaDTO;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.services.periodo.IPeriodoService;
import br.com.escola.sav.services.turma.ITurmaService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

   private final ITurmaService turmaService;
   private final IPeriodoService periodoService;

    @PostMapping
    public ResponseEntity<Object> criarTurma(@RequestBody @Validated(TurmaDTO.TurmaView.CriarTurma.class) @JsonView(TurmaDTO.TurmaView.CriarTurma.class) TurmaDTO turmaDTO) {

        var periodo = periodoService.recuperarPeriodoPorId(turmaDTO.getIdPeriodo());

        var turma = new Turma();
        BeanUtils.copyProperties(turmaDTO, turma);
        turma.setPeriodo(periodo);
        turma.setDataHoraCriacao(LocalDateTime.now());

        var turmaSalva = turmaService.criarTurma(turma);

        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder().httpCode(HttpStatus.CREATED.value())
                .message("Turma criada com sucesso")
                        .payload(TurmaDTO.create(turmaSalva))
                .build());

    }

    @GetMapping
    public ResponseEntity<ResponsePattern> listarTurmas(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        var turmas = turmaService.listarTurmas(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .payload(turmas)
                .build());
    }

}
