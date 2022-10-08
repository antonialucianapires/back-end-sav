package br.com.escola.sav.controllers.turma;

import br.com.escola.sav.dto.request.usuario.UsuarioRequestDTO;
import br.com.escola.sav.dto.response.pattern.ResponsePattern;
import br.com.escola.sav.dto.turma.TurmaDTO;
import br.com.escola.sav.dto.turma.TurmaInscritosDTO;
import br.com.escola.sav.entities.turma.Turma;
import br.com.escola.sav.enums.usuario.StatusUsuario;
import br.com.escola.sav.enums.usuario.TipoUsuario;
import br.com.escola.sav.exception.ObjectNotFound;
import br.com.escola.sav.services.periodo.IPeriodoService;
import br.com.escola.sav.services.turma.ITurmaService;
import br.com.escola.sav.services.usuario.IUsuarioService;
import br.com.escola.sav.specifications.TurmaSpecification;
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
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

   private final ITurmaService turmaService;
   private final IPeriodoService periodoService;

   private final IUsuarioService usuarioService;

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
    public ResponseEntity<ResponsePattern> listarTurmas(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,@RequestParam(name = "id_usuario") Long usuarioId) {
        var usuario = usuarioService.buscarUsuarioPorId(usuarioId).orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));

        if(usuario.isAdmin()) {
            var turmas = turmaService.listarTurmas(pageable);
            turmas.getContent().forEach(turma -> turma.setTotalEstudantes((int) turma.getUsuarios().stream().filter(u -> u.getTipoUsuario().equals(TipoUsuario.ESTUDANTE) && u.getStatusUsuario().equals(StatusUsuario.ATIVO)).count()));
            return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                    .payload(turmas)
                    .build());
        }

        var turmas = turmaService.listarTurmas(pageable, TurmaSpecification.filtroUsuarioId(usuarioId));
        turmas.getContent().forEach(turma -> turma.setTotalEstudantes((int) turma.getUsuarios().stream().filter(u -> u.getTipoUsuario().equals(TipoUsuario.ESTUDANTE) && u.getStatusUsuario().equals(StatusUsuario.ATIVO)).count()));
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .payload(turmas)
                .build());
    }

    @PostMapping("/inscricao")
    public ResponseEntity<ResponsePattern> inscreverUsuarioNaTurma(@RequestBody @Validated(TurmaDTO.TurmaView.InscricaoUsuarioTurma.class) @JsonView(TurmaDTO.TurmaView.InscricaoUsuarioTurma.class) TurmaDTO turmaDTO) {
        turmaService.adicionarMatriculadoNaTurma(turmaDTO.getId(), turmaDTO.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponsePattern.builder().httpCode(HttpStatus.CREATED.value())
                        .message("Usuário foi inscrito na turma com sucesso")
                .build());
    }

    @DeleteMapping("/inscricao")
    public ResponseEntity<ResponsePattern> removerUsuarioDaTurma(@RequestBody @Validated(TurmaDTO.TurmaView.InscricaoUsuarioTurma.class) @JsonView(TurmaDTO.TurmaView.InscricaoUsuarioTurma.class) TurmaDTO turmaDTO) {
        turmaService.removerMatriculadoDaTurma(turmaDTO.getId(), turmaDTO.getIdUsuario());
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .message("Usuário foi removido da turma com sucesso")
                .build());
    }

    @GetMapping("/{id}/inscritos")
    public ResponseEntity<ResponsePattern> listarInscritosNaTurma(@PathVariable("id") @JsonView(UsuarioRequestDTO.UsuarioView.VisualizarUsuario.class) Long idTurma) {

        var turma  = turmaService.listarUsuarioPorTurma(idTurma);

        var turmadto = TurmaInscritosDTO.builder()
                .idTurma(turma.getId())
                .nomeTurma(turma.getNome())
                .nomePeriodo(turma.getPeriodo().getNome())
                .descricaoTurma(turma.getDescricao())
                .dataCriacao(turma.getDataHoraCriacao())
                .inscritos(turma.getUsuarios().stream().map(UsuarioRequestDTO::create).collect(Collectors.toList()))
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                        .payload(turmadto)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePattern> removerTurma(@PathVariable("id") Long idTurma) {
        turmaService.removerTurmaPorId(idTurma);
        return ResponseEntity.status(HttpStatus.OK).body(ResponsePattern.builder().httpCode(HttpStatus.OK.value())
                .message("Turma removida com sucesso")
                .build());
    }


}
